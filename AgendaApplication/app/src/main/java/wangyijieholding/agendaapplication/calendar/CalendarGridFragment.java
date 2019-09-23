package wangyijieholding.agendaapplication.calendar;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;

import wangyijieholding.agendaapplication.R;
import wangyijieholding.agendaapplication.events.EData;
import wangyijieholding.agendaapplication.MainActivity;


/**
 * The calendar grid, shows the monthly calendar as a grid
 * This is now converted into a fragment, to enable better control of the program's UI system
 */

public class CalendarGridFragment extends CalendarFragment {
    //C for current
    private int cYear;
    private int cMonth;
    private int cDate;
    private CalendarGestureListener gestureListener;
    private DateAdapter calendarAdapter;
    private GridView calendarGrid;
    private Calendar thisMonth;

    private static final String GRID_STATE_MONTH = "gridCurrentMonth";
    private static final String GRID_STATE_YEAR = "gridCurrentYear";
    
    private MainActivity mContext;

    //private OnFragmentInteractionListener mListener;

    public CalendarGridFragment() {
        // Required empty public constructor
    }

    public static CalendarGridFragment newInstance() {
        CalendarGridFragment fragment = new CalendarGridFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Listener for swiping gestures
        gestureListener = new CalendarGestureListener(mContext){
            @Override
            public void onSwipeRight() {
                lastMonth();
            }

            @Override
            public void onSwipeLeft() {
                nextMonth();
            }
        };

        mContext = (MainActivity) getActivity();

        if(mContext.getMonth() != null){
            thisMonth = mContext.getMonth();
        } else{
            thisMonth = Calendar.getInstance();
            thisMonth.set(Calendar.DATE,1);

            if(savedInstanceState != null){
                thisMonth.set(Calendar.MONTH,savedInstanceState.getInt(GRID_STATE_MONTH));
                thisMonth.set(Calendar.YEAR,savedInstanceState.getInt(GRID_STATE_YEAR));
            }
            mContext.setMonth(thisMonth);
        }

        //Select today for calendar grid
        cYear = thisMonth.get(Calendar.YEAR);
        cMonth = thisMonth.get(Calendar.MONTH);
        //Always set the day of month to 1 for consistency between months. The actual date is irrelevant
        cDate = thisMonth.get(Calendar.DAY_OF_MONTH);
    }

    public void lastMonth(){
        if (cMonth > Calendar.JANUARY){
            cMonth = cMonth - 1;
        } else if (cYear > 2016){
            cYear = cYear - 1;
            cMonth = Calendar.DECEMBER;
        }
        thisMonth.set(Calendar.YEAR,cYear);
        thisMonth.set(Calendar.MONTH,cMonth);
        int todayIndex = EData.getTimeDifference2015(cYear,cMonth,cDate);
        int gridStartIndex = todayIndex - thisMonth.get(Calendar.DAY_OF_WEEK) - thisMonth.get(Calendar.WEEK_OF_MONTH) * 7 + 8;
        ArrayList<CalendarDay> currentMonthArray = new ArrayList<CalendarDay>(mContext.getCalendarDays().subList(gridStartIndex,gridStartIndex + 42));
        calendarAdapter.changeMonthArray(currentMonthArray,cMonth);
        calendarGrid.setAdapter(calendarAdapter);
        mContext.setToolbarMonth(cMonth,cYear);
        LinearLayout bg = (LinearLayout) mContext.findViewById(R.id.lowestLayout);
        bg.invalidate();
    }

    public void nextMonth(){
        if (cMonth < Calendar.DECEMBER){
            cMonth = cMonth + 1;
        } else if (cYear < 2040){
            cYear = cYear + 1;
            cMonth = Calendar.JANUARY;
        }
        thisMonth.set(Calendar.YEAR,cYear);
        thisMonth.set(Calendar.MONTH,cMonth);
        int todayIndex = EData.getTimeDifference2015(cYear,cMonth,cDate);
        int gridStartIndex = todayIndex - thisMonth.get(Calendar.DAY_OF_WEEK) - thisMonth.get(Calendar.WEEK_OF_MONTH) * 7 + 8;
        ArrayList<CalendarDay> currentMonthArray = new ArrayList<CalendarDay>(mContext.getCalendarDays().subList(gridStartIndex,gridStartIndex + 42));
        calendarAdapter.changeMonthArray(currentMonthArray,cMonth);
        calendarGrid.setAdapter(calendarAdapter);
        mContext.setToolbarMonth(cMonth,cYear);
        LinearLayout bg = (LinearLayout) mContext.findViewById(R.id.lowestLayout);
        bg.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_grid, container, false);
        int todayIndex = EData.getTimeDifference2015(cYear,cMonth,cDate);
        int gridStartIndex = todayIndex - thisMonth.get(Calendar.DAY_OF_WEEK) - thisMonth.get(Calendar.WEEK_OF_MONTH) * 7 + 8;
        ArrayList<CalendarDay> currentMonthArray = new ArrayList<CalendarDay>(mContext.getCalendarDays().subList(gridStartIndex,gridStartIndex + 42));
        calendarGrid = (GridView) view.findViewById(R.id.dateGrid);
        calendarAdapter = new DateAdapter(mContext, currentMonthArray,thisMonth.get(Calendar.MONTH));
        calendarGrid.setAdapter(calendarAdapter);
        calendarGrid.setOnTouchListener(gestureListener);
        calendarGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                CalendarDay selectedDay = (CalendarDay)calendarAdapter.getItem(i);
                mContext.viewDateDetail(selectedDay);
                return true;
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        //Save the current month selected
        saveInstanceState.putInt(GRID_STATE_MONTH,cMonth);
        saveInstanceState.putInt(GRID_STATE_YEAR,cYear);
        super.onSaveInstanceState(saveInstanceState);
    }

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    public void processTouchEvent(MotionEvent event){
        gestureListener.gd.onTouchEvent(event);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
     */
}
