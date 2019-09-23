package wangyijieholding.agendaapplication.events;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import wangyijieholding.agendaapplication.calendar.CalendarDay;

public class EData implements Serializable {
    int year;
    int month;
    int date;
    private int eventType;
    private String eventTitle;
    private String eventDesc;
    CalendarDay cday;
    int descLineCount;
    //The id identifier of the event
    String SQLId;

    private static int countStringLines (String str){
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }

    public EData(int new_year, int new_month, int new_date, int new_eventType, String new_title, String new_desc) {
        year = new_year;
        month = new_month;
        date = new_date;
        eventType = new_eventType;
        eventTitle = new_title;
        eventDesc = new_desc;
        descLineCount = countStringLines (eventDesc);
    }

    public void update(int new_year, int new_month, int new_date, int new_eventType, String new_title, String new_desc){
        year = new_year;
        month = new_month;
        date = new_date;
        eventType = new_eventType;
        eventTitle = new_title;
        eventDesc = new_desc;
        descLineCount = countStringLines (eventDesc);
    }

    /*
    public void addEventToCalendar (ArrayList<CalendarDay> cdayarray){
        int dayDifference;
        dayDifference = EData.getTimeDifference2015(year, month, date);
        cday = cdayarray.get(dayDifference);
        cday.events.add(this);
    }
    */

    public boolean removeFromCalendar() {
        return(cday.removeEvent(this));
    }

    public static int getTimeDifference2015 (int gyear, int gmonth, int gdate){
        long timeDifference;
        long dayDifferenceL;
        int dayDifferenceI;
        Calendar date2015;
        CalendarDay eventDate;
        date2015 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        date2015.set(2015, Calendar.JANUARY, 1);
        date2015.set(Calendar.AM_PM,Calendar.AM);
        date2015.set(Calendar.HOUR,0);
        date2015.set(Calendar.MINUTE,0);
        date2015.set(Calendar.SECOND,0);
        date2015.set(Calendar.MILLISECOND,0);
        eventDate = new CalendarDay(gyear,gmonth,gdate);
        timeDifference = eventDate.getDate().getTimeInMillis() - date2015.getTimeInMillis();
        dayDifferenceL = timeDifference/86400000;
        dayDifferenceI = (int)dayDifferenceL;
        return dayDifferenceI;
    }

    public static int getTimeDifference2015Today (){
        Calendar today = Calendar.getInstance();
        return getTimeDifference2015(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE));
    }

    public void setSQLId(String id){
        SQLId = id;
    }

    public void changeEventInt(EData d, int fieldnum, int newValue){
        switch (fieldnum){
            case 1 : d.year = newValue;
                break;
            case 2 : d.month = newValue;
                break;
            case 3 : d.date = newValue;
                break;
            case 4 : d.eventType = newValue;
                break;
            default :
                break;
        }
    }
    public void changeEventStr(EData d, int fieldnum, String newValue)	{
        switch (fieldnum) {
            case 1 : d.eventTitle = newValue;
                break;
            case 2 : d.eventDesc = newValue;
                break;
            default :;
                break;
        }
    }

    //Getters and setters
    public String geteventTitle(){
        return eventTitle;
    }
    
    public String geteventDesc(){
        return eventDesc;
    }
    
    public String getSQLId(){
        return SQLId;
    }
    
    public CalendarDay getCday(){
        return cday;
    }
    
    public int getEventType(){
        return eventType;
    }

    public String getEventTitle(){
        return eventTitle;
    }

    public String getEventDesc(){
        return eventDesc;
    }
}