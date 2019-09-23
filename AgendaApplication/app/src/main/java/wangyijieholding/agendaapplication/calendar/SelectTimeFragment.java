package wangyijieholding.agendaapplication.calendar;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import wangyijieholding.agendaapplication.R;


/**
 *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *  !!!!!!!!!!!!!!!!!!!         Currently                       Unused        !!!!!!!!!!!!!!!!!!!!!
 *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

/**
 *Simple fragment for the simple task of selecting time from a timepicker
 */
public class SelectTimeFragment extends Fragment {
    private static final String ARG_ORIGINAL_TIME_HOUR = "original_time_hour";
    private static final String ARG_ORIGINAL_TIME_MINUTE = "original_time_minute";

    // TODO: Rename and change types of parameters
    private int displayedHour;
    private int displayedMinute;

    private TimeFragmentListener mListener;

    public SelectTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTimeFragment newInstance(int param1, int param2) {
        SelectTimeFragment fragment = new SelectTimeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ORIGINAL_TIME_HOUR, param1);
        args.putInt(ARG_ORIGINAL_TIME_MINUTE, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            displayedHour = getArguments().getInt(ARG_ORIGINAL_TIME_HOUR);
            displayedMinute = getArguments().getInt(ARG_ORIGINAL_TIME_MINUTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_select_time, container, false);
        TimePicker timePicker = (TimePicker) fragmentView.findViewById(R.id.select_time_fragment_time_picker);

        //Change the time on the time picker to match that of the given parameters
        //Check the api version as setHour method and setMinute method are only available after Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            timePicker.setHour(displayedHour);
            timePicker.setMinute(displayedMinute);
        } else {
            timePicker.setCurrentHour(displayedHour);
            timePicker.setCurrentMinute(displayedMinute);
        }
        return fragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TimeFragmentListener) {
            mListener = (TimeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement TimeFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //Make activities implement this interface
    public interface TimeFragmentListener {
        void onFragmentInteraction(Uri uri);
    }
}
