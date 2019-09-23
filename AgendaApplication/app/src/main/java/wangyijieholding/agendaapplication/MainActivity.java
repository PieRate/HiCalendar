package wangyijieholding.agendaapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

import wangyijieholding.agendaapplication.calendar.CalendarDay;
import wangyijieholding.agendaapplication.calendar.CalendarGridFragment;
import wangyijieholding.agendaapplication.calendar.DayDetailActivity;
import wangyijieholding.agendaapplication.calendar.NewEventActivity;
import wangyijieholding.agendaapplication.database.CalendarDatabaseContract;
import wangyijieholding.agendaapplication.database.CalendarDatabaseHelper;
import wangyijieholding.agendaapplication.events.EData;
import wangyijieholding.agendaapplication.events.EDataWithTime;
import wangyijieholding.agendaapplication.userpools.SignInActivity;
import wangyijieholding.agendaapplication.util.SettingsActivity;

public class MainActivity extends AppCompatActivity{
    private ArrayList<CalendarDay> CalendarDays;
    ArrayList<EDataWithTime> Events;
    CalendarDatabaseHelper dbHelper;
    CalendarGridFragment monthFragment;
    private Toolbar toolbar;
    private Calendar thisMonth;

    static final String REFRESH_DATE_EXTRA = "REFRESH_DATE";
    static final int DAY_DETAIL_REQUEST = 301;
    static final String DAY_DETAIL_NOTIFICATION_EXTRA = "selectedDateIndex";
    static final String STATE_MONTH = "currentMonth";
    static final String STATE_YEAR = "currentYear";

    public void createEvent (View view){
        Intent intent = new Intent(this,NewEventActivity.class);
        startActivity(intent);
    }

    public void openSettings (View view){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    public void openSignIn(View view){
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
    }

    public void viewDateDetail (CalendarDay displayedDate){
        Intent intent = new Intent(this,DayDetailActivity.class);
        intent.putExtra("selectedDate",displayedDate);
        startActivityForResult(intent,DAY_DETAIL_REQUEST);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        //Check the request code
        if (requestCode == DAY_DETAIL_REQUEST){
            if (resultCode == Activity.RESULT_OK){
                onResume();
                int refreshDate = data.getIntExtra(REFRESH_DATE_EXTRA,-1);
                if (refreshDate >= 0){
                    viewDateDetail(CalendarDays.get(refreshDate));
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instantiate the fields from the original CalendarTest.java
        CalendarDays = new ArrayList<CalendarDay>(10000);
        for (int i = 0; i < 10000; i ++){
            CalendarDays.add(new CalendarDay(2015, Calendar.JANUARY, i + 1, i));
        }

        if(thisMonth == null){
            thisMonth = Calendar.getInstance();
            //Always set the day of month to 1 for consistency between months. The actual date is irrelevant
            thisMonth.set(Calendar.DAY_OF_MONTH, 1);
            if (savedInstanceState != null){
                thisMonth.set(Calendar.YEAR,savedInstanceState.getInt(STATE_YEAR));
                thisMonth.set(Calendar.MONTH,savedInstanceState.getInt(STATE_MONTH));
            }
        }

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setToolbarMonth(thisMonth.get(Calendar.MONTH),thisMonth.get(Calendar.YEAR));
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent(view);
            }
        });

        FloatingActionButton fabDebug = (FloatingActionButton) findViewById(R.id.fab_delete);
        fabDebug.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                /*ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                boolean isWifiConn = false;
                boolean isMobileConn = false;
                if (networkInfo != null) {
                    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        isWifiConn = true;
                    }
                    if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        isMobileConn = true;
                    }
                }
                Log.d("NetworkStatusExample", "Wifi connected " + isWifiConn);
                Log.d("NetworkStatusExample", "Mobile connected " + isMobileConn);*/


                /*Intent alarmIntent = new Intent(MainActivity.this,ClockAlarmService.class);
                alarmIntent.setAction(ClockAlarmService.ACTION_SETALARM);
                startService(alarmIntent);*/
                openSignIn(view);
            }
        });

        FloatingActionButton fabSettings = (FloatingActionButton) findViewById(R.id.fab_setting);
        fabSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openSettings(view);
            }
        });


        dbHelper = new CalendarDatabaseHelper(this);
        Events = updateFromDatabase();

        Fragment f = getFragmentManager().findFragmentById(R.id.fragment_calendar_grid);
        if (f == null) {
            //Request the fragment for the month CalendarGridFragment, then add it to the main layout
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            monthFragment = CalendarGridFragment.newInstance();
            fragmentTransaction.add(R.id.lowestLayout, monthFragment);
            fragmentTransaction.commit();
        }

        //Main activity handles every intent requests, this following segment redirects the request
        //to the DayDetailActivity
        Intent redirectIntent = getIntent();
        if (redirectIntent.hasExtra(DAY_DETAIL_NOTIFICATION_EXTRA)){
            int dayIndex = redirectIntent.getIntExtra(DAY_DETAIL_NOTIFICATION_EXTRA, EData.getTimeDifference2015Today());
            viewDateDetail(CalendarDays.get(dayIndex));
        }
    }

    public void setToolbarMonth (int month, int year){
        String[] monthName = getResources().getStringArray(R.array.month_full_name_array);
        toolbar.setTitle(monthName[month] + " " + Integer.toString(year));
    }

    public Calendar getMonth(){
        return thisMonth;
    }

    public ArrayList<CalendarDay> getCalendarDays(){
        return CalendarDays;
    }

    public void setMonth(Calendar newMonth){
        thisMonth = newMonth;
    }

    @Override
    public boolean onTouchEvent (MotionEvent event){
        monthFragment.processTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        //Save the current month selected
        saveInstanceState.putInt(STATE_MONTH,thisMonth.get(Calendar.MONTH));
        saveInstanceState.putInt(STATE_YEAR,thisMonth.get(Calendar.YEAR));
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        for (EDataWithTime e : Events){
            e.removeFromCalendar();
        }
        Events = updateFromDatabase();
    }

    public ArrayList<EDataWithTime> updateFromDatabase (){
        //Reading previously stored events from a database
        ArrayList<EDataWithTime> saveEvents;
        saveEvents = new ArrayList<>(100);
        Cursor c = dbHelper.readAllEvent();
        if (c.moveToFirst()) {
            do {
                String saveTitle;
                String saveDesc;
                int saveYear;
                int saveMonth;
                int saveDate;
                int saveType;
                int saveStartHour;
                int saveStartMinute;
                int saveEndYear;
                int saveEndMonth;
                int saveEndDate;
                int saveEndHour;
                int saveEndMinute;
                String saveID;
                saveID = c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry._ID));
                saveTitle = c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_TITLE));
                saveDesc = c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_DESC));
                saveYear = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_YEAR)));
                saveMonth = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_MONTH)));
                saveDate = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_DATE)));
                saveType = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_TYPE)));
                saveStartHour = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_HOUR)));
                saveStartMinute = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_START_MINUTE)));
                saveEndYear = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_YEAR)));
                saveEndMonth = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MONTH)));
                saveEndDate = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_DATE)));
                saveEndHour = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_HOUR)));
                saveEndMinute = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(CalendarDatabaseContract.CalendarDatabaseEntry.COLUMN_NAME_EVENT_END_MINUTE)));

                if (saveStartHour >= 0) {
                    EDataWithTime saveED = new EDataWithTime(saveYear, saveMonth, saveDate, saveType, saveTitle, saveDesc, saveStartHour, saveStartMinute, saveEndYear, saveEndMonth, saveEndDate, saveEndHour, saveEndMinute);
                    saveEvents.add(saveED);
                    saveED.addEventToCalendar(CalendarDays);
                    saveED.setSQLId(saveID);
                } else {
                    EDataWithTime saveED = new EDataWithTime(saveYear, saveMonth, saveDate, saveType, saveTitle, saveDesc);
                    saveEvents.add(saveED);
                    saveED.addEventToCalendar(CalendarDays);
                    saveED.setSQLId(saveID);
                }
            } while (c.moveToNext());
        }
        c.close();
        return saveEvents;
    }
}
