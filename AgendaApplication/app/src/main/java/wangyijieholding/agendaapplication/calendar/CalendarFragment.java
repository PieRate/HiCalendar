package wangyijieholding.agendaapplication.calendar;

import android.app.Fragment;

import java.util.Calendar;

import wangyijieholding.agendaapplication.MainActivity;

/**
 * Created by wangyijie6646 on 11/26/16.
 */

public abstract class CalendarFragment extends Fragment {

    private int cYear;
    private int cMonth;
    private int cDate;
    private CalendarGestureListener gestureListener;
    private Calendar thisMonth;

    private MainActivity context;
}
