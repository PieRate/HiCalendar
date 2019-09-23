package wangyijieholding.agendaapplication.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

import wangyijieholding.agendaapplication.R;

/**
 * Created by wangyijie6646 on 8/6/16.
 */
public class DateView extends View {
    CalendarDay displayedDate;
    int currentMonth;
    Paint CurrentPaint;
    Paint EventPaint;
    Paint BGPaint;
    String dateOfMonth;
    boolean sameMonth;
    String wordEvent;
    String wordEvents;

    //Constructors
    public DateView(Context context) {
        super(context);
        init();
    }

    public DateView (Context c, AttributeSet attrs){
        super(c,attrs);
        init();
    }

    //If the date belongs to a month other than the current month, mark sameMonth as false, which
    //will cause the date to be set as a different colour;
    @SuppressWarnings("WrongConstant")
    public DateView (Context c, CalendarDay d, int cm) {
        super(c);
        displayedDate = d;
        init();
        currentMonth = cm;
        if (currentMonth != displayedDate.getDate().get(Calendar.MONTH) ){
            sameMonth = false;
        }
    }

    //Initialise the graphic of the view
    public void init (){
        CurrentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        CurrentPaint.setColor(Color.BLACK);
        CurrentPaint.setTextSize(26);
        EventPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        EventPaint.setColor(Color.BLACK);
        EventPaint.setTextSize(20);
        CurrentPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        BGPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        BGPaint.setColor(Color.GRAY);
        this.setWillNotDraw(false);
        Integer dayOfMonthInt = displayedDate.getDate().get(Calendar.DAY_OF_MONTH);
        dateOfMonth = dayOfMonthInt.toString();
        sameMonth = true;
        wordEvent = this.getContext().getString(R.string.word_event);
        wordEvents = this.getContext().getString(R.string.word_events);
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
    }

    //Draw the date when called
    @Override
    protected void onDraw (Canvas canvas){
        super.onDraw(canvas);
        if (!sameMonth){
            canvas.drawPaint(BGPaint);
        }
        canvas.drawText(dateOfMonth,10,30,CurrentPaint);
        /*if (displayedDate.events.size() > 2) {
            for (int i = 0; i < 2; i++) {
                canvas.drawText(displayedDate.events.get(i).EventTitle, 10, 100 + 50 * i, CurrentPaint);
            }
            canvas.drawText("...", 10, 50 * 4, CurrentPaint);
        } else {
            for (int i = 0; i < displayedDate.events.size(); i++) {
                canvas.drawText(displayedDate.events.get(i).EventTitle, 10, 100 + 50 * i, CurrentPaint);
            }
        }
        */
        if (displayedDate.events.size() > 1){
            canvas.drawText(Integer.toString(displayedDate.events.size()) + " " + wordEvents ,10, 60, EventPaint);
        } else if (displayedDate.events.size() > 0){
            canvas.drawText(Integer.toString(displayedDate.events.size()) + " " + wordEvents, 10, 60, EventPaint);
        }
    }
}
