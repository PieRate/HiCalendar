package wangyijieholding.agendaapplication.calendar;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import wangyijieholding.agendaapplication.database.CalendarDatabaseHelper;
import wangyijieholding.agendaapplication.R;

public class NewEventActivity extends AppCompatActivity implements SelectTimeFragment.TimeFragmentListener {

    private String[] monthArray;
    private String[] yearArray;
    private String[] typeArray;
    private ArrayList<String> dayArray;
    ArrayAdapter<String> yearAdapter;
    ArrayAdapter<String> monthAdapter;
    ArrayAdapter<String> dayAdapter;
    ArrayAdapter<String> endYearAdapter;
    ArrayAdapter<String> endMonthAdapter;
    ArrayAdapter<String> endDayAdapter;
    Spinner yearSpinner;
    Spinner monthSpinner;
    Spinner daySpinner;
    Spinner eventTypeSpinner;
    Spinner endYearSpinner;
    Spinner endMonthSpinner;
    Spinner endDaySpinner;
    CalendarDatabaseHelper cdbHelper;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    TextView startLabel;
    TextView startTime;
    TextView endLabel;
    TextView endTime;
    TextView endCalendarLabel;
    ViewGroup endSpinnerGroup;

    final String CALENDARDAY_EXTRA = "CalendarDayExtra";

    public void validateDateArray (boolean isValidatingEventSpinners){
        Spinner ySpinner;
        Spinner mSpinner;
        ArrayAdapter<String> dAdapter;
        if (isValidatingEventSpinners){
            ySpinner = yearSpinner;
            mSpinner = monthSpinner;
            dAdapter = dayAdapter;
        }else{
            ySpinner = endYearSpinner;
            mSpinner = endMonthSpinner;
            dAdapter = endDayAdapter;
        }
        int currentMonth = mSpinner.getSelectedItemPosition();
        int daysNumber = dAdapter.getCount();
        int currentYear = ySpinner.getSelectedItemPosition() + 2016;
        if (currentMonth == 0 || currentMonth == 2 || currentMonth == 4 || currentMonth == 6 || currentMonth == 7 || currentMonth == 9 || currentMonth == 11){
            for (int i = 0; i < 31 - daysNumber; i++){
                dAdapter.add(Integer.toString(daysNumber + i + 1));
            }
        } else if (currentMonth == 3 || currentMonth == 5 || currentMonth == 8 || currentMonth == 10){
            if (daysNumber > 30){
                for (int i = 0; i < daysNumber - 30; i ++){
                    dAdapter.remove(Integer.toString(daysNumber - i));
                }
            } else if (daysNumber < 30){
                for (int i = 0; i < 30 - daysNumber; i ++){
                    dAdapter.add(Integer.toString(daysNumber + i + 1));
                }
            }
        } else if (currentYear % 4 == 0){
            if (daysNumber > 29){
                for (int i = 0; i < daysNumber - 29; i ++){
                    dAdapter.remove(Integer.toString(daysNumber - i));
                }
            } else if (daysNumber < 29){
                for (int i = 0; i < 29 - daysNumber; i ++){
                    dAdapter.add(Integer.toString(daysNumber + i + 1));
                }
            }
        } else {
            if (daysNumber > 28) {
                for (int i = 0; i < daysNumber - 28; i++) {
                    dAdapter.remove(Integer.toString(daysNumber - i));
                }
            }
        }
        dAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        //Set up the database
        cdbHelper = new CalendarDatabaseHelper(this);

        //Set up the calendar to automatically choose the date
        Calendar today = Calendar.getInstance();

        try {
            Intent intent = getIntent();
            CalendarDay c = (CalendarDay) intent.getSerializableExtra(CALENDARDAY_EXTRA);
            today = c.getDate();
        } catch (NullPointerException e){}

        //Set up the arrays for the spinners
        this.monthArray = getResources().getStringArray(R.array.month_name_array);
        this.yearArray = new String[25];
        for (int i = 0; i < 25; i++){
            yearArray[i] = Integer.toString(2016 + i);
        }
        this.typeArray = getResources().getStringArray(R.array.event_type_name_array);
        this.dayArray = new ArrayList<String>();
        for (int i = 0; i < 31; i++){
            dayArray.add(i,Integer.toString(i + 1));
        }

        //set up the spinners and their adapters
        yearSpinner = (Spinner) findViewById(R.id.spinner_year);
        monthSpinner = (Spinner) findViewById(R.id.spinner_month);
        daySpinner = (Spinner) findViewById(R.id.spinner_day);
        eventTypeSpinner = (Spinner) findViewById(R.id.spinner_event_type);
        yearAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,yearArray);
        yearSpinner.setAdapter(yearAdapter);
        monthAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,monthArray);
        monthSpinner.setAdapter(monthAdapter);
        dayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,dayArray);
        daySpinner.setAdapter(dayAdapter);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,typeArray);
        eventTypeSpinner.setAdapter(typeAdapter);

        //set up the end Spinners
        endYearSpinner = (Spinner) findViewById(R.id.spinner_end_year);
        endMonthSpinner = (Spinner) findViewById(R.id.spinner_end_month);
        endDaySpinner = (Spinner) findViewById(R.id.spinner_end_day);
        endYearAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,yearArray);
        endYearSpinner.setAdapter(endYearAdapter);
        endMonthAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,monthArray);
        endMonthSpinner.setAdapter(endMonthAdapter);
        endDayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,dayArray);
        endDaySpinner.setAdapter(endDayAdapter);

        //Choose the current day using calendar
        yearSpinner.setSelection(today.get(Calendar.YEAR) - 2016);
        monthSpinner.setSelection((today.get(Calendar.MONTH)));
        daySpinner.setSelection((today.get(Calendar.DAY_OF_MONTH)) - 1);
        endYearSpinner.setSelection(today.get(Calendar.YEAR) - 2016);
        endMonthSpinner.setSelection((today.get(Calendar.MONTH)));
        endDaySpinner.setSelection((today.get(Calendar.DAY_OF_MONTH)) - 1);

        //Validate the date array
        this.validateDateArray(true);
        this.validateDateArray(false);

        //Set the on selection listener for year and month spinner
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                validateDateArray(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                validateDateArray(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Set the on selection listener for the end year and end month Spinner
        endYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                validateDateArray(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        endMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                validateDateArray(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startHour = 7;
        startMinute = 0;
        endHour = 9;
        endMinute = 0;

        startLabel = (TextView)findViewById(R.id.new_event_start_time);
        endLabel = (TextView)findViewById(R.id.new_event_end_time);
        startTime = (TextView)findViewById(R.id.new_event_start_clock);
        endTime = (TextView)findViewById(R.id.new_event_end_clock);
        endCalendarLabel = (TextView)findViewById(R.id.new_event_end_calendar_label);
        endSpinnerGroup = (ViewGroup)findViewById(R.id.new_event_end_calendar_spinners);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        startHour = hour;
                        startMinute = minute;
                        TextView startTime = (TextView)findViewById(R.id.new_event_start_clock);
                        String sClock = String.format("%02d",startHour) + ":" + String.format("%02d",startMinute);
                        startTime.setText(sClock);
                    }
                },startHour,startMinute,true);
                timePickerDialog.show();
            }
        });
        endTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(NewEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        endHour = hour;
                        endMinute = minute;
                        TextView startTime = (TextView)findViewById(R.id.new_event_end_clock);
                        String sClock = String.format("%02d",endHour) + ":" + String.format("%02d",endMinute);
                        startTime.setText(sClock);
                    }
                },endHour,endMinute,true);
                timePickerDialog.show();
            }
        });


        //Set up the checkbox
        CheckBox timeCheckBox = (CheckBox)findViewById(R.id.specify_time_check_box);
        timeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    String sClock = String.format("%02d",startHour) + ":" + String.format("%02d",startMinute);
                    String eClock = String.format("%02d",endHour) + ":" + String.format("%02d",endMinute);
                    startTime.setText(sClock);
                    endTime.setText(eClock);
                    startLabel.setVisibility(TextView.VISIBLE);
                    endLabel.setVisibility(TextView.VISIBLE);
                    startTime.setVisibility(TextView.VISIBLE);
                    endTime.setVisibility(TextView.VISIBLE);
                    endCalendarLabel.setVisibility(TextView.VISIBLE);
                    endSpinnerGroup.setVisibility(TextView.VISIBLE);
                } else {
                    startLabel.setVisibility(TextView.GONE);
                    endLabel.setVisibility(TextView.GONE);
                    startTime.setVisibility(TextView.GONE);
                    endTime.setVisibility(TextView.GONE);
                    endCalendarLabel.setVisibility(TextView.GONE);
                    endSpinnerGroup.setVisibility(TextView.GONE);
                }
            }
        });
        timeCheckBox.setChecked(true);
    }

    public void createEventData (View view){
        EditText titleText = (EditText) findViewById(R.id.eventTitle);
        String title = titleText.getText().toString();
        if (title.equals("")){
            title = "Untitled Event";
        }
        EditText descText = (EditText) findViewById(R.id.eventDesc);
        String desc = descText.getText().toString();
        CheckBox timeCheckBox = (CheckBox)findViewById(R.id.specify_time_check_box);
        //store to the database
        //user id for testing purpose only
        //TODO:replace user id with amazon incognito id
        String user_id = "a00000001";
        if (timeCheckBox.isChecked()){
            cdbHelper.insertEvent(user_id,title,desc,yearSpinner.getSelectedItemPosition() + 2016,monthSpinner.getSelectedItemPosition(),daySpinner.getSelectedItemPosition() + 1,eventTypeSpinner.getSelectedItemPosition(),startHour,startMinute,endYearSpinner.getSelectedItemPosition() + 2016,endMonthSpinner.getSelectedItemPosition(),endDaySpinner.getSelectedItemPosition() + 1,endHour,endMinute);
        } else {
            cdbHelper.insertEvent(user_id,title,desc,yearSpinner.getSelectedItemPosition() + 2016,monthSpinner.getSelectedItemPosition(),daySpinner.getSelectedItemPosition() + 1,eventTypeSpinner.getSelectedItemPosition());
        }

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void cancel (View view){

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
