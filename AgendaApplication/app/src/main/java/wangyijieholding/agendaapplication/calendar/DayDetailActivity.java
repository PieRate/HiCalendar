package wangyijieholding.agendaapplication.calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import wangyijieholding.agendaapplication.R;
import wangyijieholding.agendaapplication.database.CalendarDatabaseHelper;
import wangyijieholding.agendaapplication.events.EDataWithTime;


/*
 * This is the activity for displaying the events in detail and handling user inputs
 * A custom adapter class, DayDetailListItemAdapter is used for the items in this view
 * This activity displays the list_item_with_edit_button custom view
 * The adapter class is responsible for maintaining the list and populating it
 * The adapter is also responsible for calling the edit event method when the edit button is pressed
 */

public class DayDetailActivity extends AppCompatActivity implements EditModeChoiceDialogFragment.EditModeDialogListener {
    CalendarDay displayedDate;
    DayDetailListItemAdapter eventAdapter;
    Snackbar editModeHelp;
    boolean editMode;
    int onHoldEvent;
    CalendarDatabaseHelper dbHelper;

    final String CALENDARDAY_EXTRA = "CalendarDayExtra";
    static final String EVENT_DIALOG_TAG = "edit_mode_choice";
    static final int EDIT_EVENT_REQUEST = 701;
    static final String REFRESH_DATE_EXTRA = "REFRESH_DATE";
    static final int NEW_EVENT_REQUEST = 702;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    createEvent(view);
                }
        });

        Intent intent = this.getIntent();
        displayedDate = (CalendarDay)intent.getSerializableExtra("selectedDate");
        Resources res = getResources();
        String[] monthNameArray = res.getStringArray(R.array.month_full_name_array);
        String[] weekdayNameArray = res.getStringArray(R.array.weekday_name_array);
        String title = displayedDate.getDateString(monthNameArray,weekdayNameArray);
        //Change the title to today's date
        getSupportActionBar().setTitle(title);

        //Set edit mode to false initially
        editMode = false;

        //Populate the list view based on the event title and desc pairings
        updateEventList();

        dbHelper = new CalendarDatabaseHelper(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateEventList();
    }

    public void updateEventList(){
        ListView eventList = (ListView) findViewById(R.id.event_day_list);
        eventAdapter = new DayDetailListItemAdapter(this,displayedDate.events);
        eventList.setAdapter(eventAdapter);
    }

    public void showDialog(){
        EditModeChoiceDialogFragment dialog = new EditModeChoiceDialogFragment();
        dialog.show(getSupportFragmentManager(), EVENT_DIALOG_TAG);
    }

    public void createEvent (View view){
        Intent intent = new Intent(this,NewEventActivity.class);
        intent.putExtra(CALENDARDAY_EXTRA,displayedDate);
        startActivityForResult(intent,NEW_EVENT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Check if the request is edit event request
        if (requestCode == EDIT_EVENT_REQUEST){
            if (resultCode == RESULT_OK){
                Intent refresh = new Intent();
                //DisplayedDate ArrayPosition is the position for the date on the array, so the code
                //jumps back to the DayDetailActivity as if nothing happened
                refresh.putExtra(REFRESH_DATE_EXTRA,displayedDate.arrayPosition);
                setResult(Activity.RESULT_OK,refresh);
                finish();
            }
        } if (requestCode == NEW_EVENT_REQUEST){
            if(resultCode == RESULT_OK){
                Intent refresh = new Intent();
                //DisplayedDate ArrayPosition is the position for the date on the array, so the code
                //jumps back to the DayDetailActivity as if nothing happened
                refresh.putExtra(REFRESH_DATE_EXTRA,displayedDate.arrayPosition);
                setResult(Activity.RESULT_OK,refresh);
                finish();
            }
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent editIntent = new Intent(this,EditEventActivity.class);
        editIntent.putExtra("EditEventData",displayedDate.events.get(onHoldEvent));
        startActivityForResult(editIntent,EDIT_EVENT_REQUEST);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        EDataWithTime deleteEvent = displayedDate.events.get(onHoldEvent);
        deleteEvent.removeFromCalendar();
        dbHelper.deleteByID((deleteEvent.getSQLId()));
        updateEventList();
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog){
        System.out.println("Cancel");
    }

}




