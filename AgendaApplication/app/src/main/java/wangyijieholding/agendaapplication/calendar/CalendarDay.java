package wangyijieholding.agendaapplication.calendar;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import java.util.ArrayList;

import wangyijieholding.agendaapplication.events.EData;
import wangyijieholding.agendaapplication.events.EDataWithTime;

public class CalendarDay implements Serializable {
    int arrayPosition;
    private Calendar date;
    ArrayList<EDataWithTime> events;

    public CalendarDay (){
        date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }
    public CalendarDay (int setYear, int setMonth, int setDate){
        date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        date.set(setYear, setMonth, setDate);
        date.set(Calendar.AM_PM,Calendar.AM);
        date.set(Calendar.HOUR,0);
        date.set(Calendar.MINUTE,0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MILLISECOND,0);
        events = new ArrayList<EDataWithTime>(25);
    }

    public CalendarDay (int setYear, int setMonth, int setDate, int position){
        arrayPosition = position;
        date = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        date.set(setYear, setMonth, setDate);
        date.set(Calendar.AM_PM,Calendar.AM);
        date.set(Calendar.HOUR,0);
        date.set(Calendar.MINUTE,0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MILLISECOND,0);
        events = new ArrayList<EDataWithTime>(25);
    }

    public void addEvent(EDataWithTime event){
        events.add(event);
    }

    public boolean removeEvent(EData event){
        return events.remove(event);
    }

    public Calendar getDate(){
        return date;
    }

    public String getDateString (String[] month, String[] dayOfWeek){
        String result;
        result =  dayOfWeek[date.get(Calendar.DAY_OF_WEEK) - 1] + ", " + month[date.get(Calendar.MONTH)] + " " + Integer.toString(date.get(Calendar.DAY_OF_MONTH)) + ", " + Integer.toString(date.get(Calendar.YEAR));
        return result;
    }
}