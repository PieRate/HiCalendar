package wangyijieholding.agendaapplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import wangyijieholding.agendaapplication.calendar.CalendarDay;
import wangyijieholding.agendaapplication.events.EData;

public class CalendarTest {
    EData EventCreationTest;
    ArrayList<CalendarDay> CalendarDays;
    ArrayList<EData> Events;
    int CYear;
    int CMonth;
    int CDate;
    int CDateIndex;
    Calendar ThisMonth;
    int startDayOfWeek;
    int dayInMonth;

    public CalendarTest (){
        Events = new ArrayList<EData>(100);
        CalendarDays = new ArrayList<CalendarDay>(20000);
        for (int i = 0; i < 3000; i ++){
            CalendarDays.add(new CalendarDay(2015, Calendar.JANUARY, i + 1));
        }
    }

    public static void main (String args[]){
        CalendarTest c = new CalendarTest();
        c.displayTestingMenu(c.CalendarDays);
        for (int i = 3000; i < 20000; i ++){
            c.CalendarDays.add(new CalendarDay(2015, Calendar.JANUARY, i + 1));
        }
    }
    public void displayTestingMenu (ArrayList<CalendarDay> cd) {
        ThisMonth = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        ThisMonth.set(Calendar.DATE, 1);
        CYear = ThisMonth.get(Calendar.YEAR);
        CMonth = ThisMonth.get(Calendar.MONTH);
        CDateIndex = EData.getTimeDifference2015(CYear, CMonth, 1);
        startDayOfWeek = ThisMonth.get(Calendar.DAY_OF_WEEK);
        dayInMonth = ThisMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    public void createNewEvent (ArrayList<CalendarDay> cd) {
        Integer[] dayValue31 = new Integer[31];
        for (int i = 0; i < dayValue31.length; i++) {
            dayValue31[i] = new Integer(i + 1);
        }

        Integer[] yearValue = new Integer[50];
        for (int i = 0; i < yearValue.length; i++) {
            yearValue[i] = new Integer(2015 + i);
        }

        String[] eventValue = {"Family","Community","Work","Educational"};

    }
}

