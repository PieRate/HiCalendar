package wangyijieholding.agendaapplication.events;

import java.util.ArrayList;
import java.util.Locale;

import wangyijieholding.agendaapplication.calendar.CalendarDay;
import wangyijieholding.agendaapplication.events.EData;

public class EDataWithTime extends EData {
    int startHour;
    int endYear;
    int endMonth;
    int endDate;
    int startMinute;
    int endHour;
    int endMinute;
    boolean specifyTime;
    static final String[] MONTH_ARRAY = {"Jan.","Feb.","Mar.","Apr.","May.","Jun.","Jul.","Aug","Sep.","Oct.","Nov.","Dec."};

    public EDataWithTime (int new_year, int new_month, int new_date, int new_EventType, String new_title, String new_desc) {
        super(new_year, new_month, new_date, new_EventType, new_title, new_desc);
        specifyTime = false;
    }
    public EDataWithTime (int new_year, int new_month, int new_date, int new_EventType, String new_title, String new_desc, int start_hour, int start_minute, int new_end_year, int new_end_month, int new_end_date, int end_hour, int end_minute) {
        super(new_year, new_month, new_date, new_EventType, new_title, new_desc);
        startHour = start_hour;
        startMinute = start_minute;
        endHour = end_hour;
        endMinute = end_minute;
        endYear = new_end_year;
        endMonth = new_end_month;
        endDate = new_end_date;
        specifyTime = true;
    }

    public void addEventToCalendar (ArrayList<CalendarDay> cdayarray){
        int dayDifference;
        dayDifference = EData.getTimeDifference2015(year, month, date);
        cday = cdayarray.get(dayDifference);
        cday.addEvent(this);
    }

    public String formatStartTime(){
        String startTime;
        String formatStart = String.format(Locale.CANADA,"%02d : %02d",startHour,startMinute);
        startTime = MONTH_ARRAY[month] + " " + date + ", " + year + "  " + formatStart;
        return startTime;
    }

    public String formatEndTime(){
        String endTime;
        String formatEnd = String.format(Locale.CANADA,"%02d : %02d",endHour,endMinute);
        endTime = MONTH_ARRAY[endMonth] + " " + endDate + ", " + endYear + "  " + formatEnd;
        return endTime;
    }

    //Getter and setter methods
    public boolean isSpecifyTime(){
        return specifyTime;
    }

    public int getEndYear(){
        return endYear;
    }

    public int getEndMonth(){
        return endMonth;
    }

    public int getEndDate(){
        return endDate;
    }

    public int getEndHour(){
        return endHour;
    }

    public int getEndMinute(){
        return endMinute;
    }

    public int getStartHour(){
        return startHour;
    }

    public int getStartMinute(){
        return startMinute;
    }
}
