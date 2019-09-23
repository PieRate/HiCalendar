package wangyijieholding.agendaapplication.calendar;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
* Created by wangyijie6646 on 8/25/16.
* Gesture listener for the calendar
*/
public class CalendarGestureListener implements View.OnTouchListener {
    protected GestureDetector gd;

    public CalendarGestureListener(Context c){
        gd = new GestureDetector(c,new swipeListener());
    }

    public boolean onTouch(View v, MotionEvent e){
        return gd.onTouchEvent(e);
    }

    private final class swipeListener extends GestureDetector.SimpleOnGestureListener {
        //Configurations for the swipe Listener
        private static final String DEBUG_TAG = "Gestures";
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown (MotionEvent e){
            return false;
        }

        @Override
        public boolean onFling (MotionEvent e1, MotionEvent e2,float vx, float vy){
            boolean result = false;
            try {
                float dx = e2.getX() - e1.getX();
                float dy = e2.getY() - e1.getY();
                //check if the swipe is more horizontal or more vertical
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (Math.abs(vx) > SWIPE_VELOCITY_THRESHOLD && Math.abs(dx) > SWIPE_THRESHOLD) {
                        if (dx > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = false;
                } else {
                    if (Math.abs(vy) > SWIPE_VELOCITY_THRESHOLD && Math.abs(dy) > SWIPE_THRESHOLD) {
                        if (dy > 0) {
                            onSwipeUp();
                        } else {
                            onSwipeDown();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeUp() {
    }

    public void onSwipeDown() {
    }
}
