package wangyijieholding.agendaapplication.calendar;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;

import wangyijieholding.agendaapplication.R;

/**
 * Custom Adapter for grid view
 */
public class DateAdapter extends BaseAdapter{
    private Context pContext;
    private ArrayList<CalendarDay> monthDays;
    private int currentMonth;

    public DateAdapter (Context c){
        pContext = c;
    }

    public DateAdapter (Context c, ArrayList<CalendarDay> month, int cm){
        pContext = c;
        monthDays = month;
        currentMonth = cm;
    }

    public void changeMonthArray (ArrayList<CalendarDay> month, int cm){
        monthDays = month;
        currentMonth = cm;
    }

    public int getCount(){
        return monthDays.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        DateView dayView;
        if (convertView == null) {
            WindowManager wm = (WindowManager) pContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            int hMargin = (int)pContext.getResources().getDimension(R.dimen.activity_horizontal_margin);
            if(width > height){
                hMargin = hMargin + hMargin/2;
            }
            dayView = new DateView(pContext, monthDays.get(position),currentMonth);
            dayView.setLayoutParams(new GridView.LayoutParams((width - hMargin)/7,height/8));
            dayView.setPadding(8,8,8,8);
        } else {
            dayView = (DateView) convertView;
        }
        return dayView;
    }

    public long getItemId(int position){
        //implement later
        return 0;
    }

    public Object getItem(int position) {
        CalendarDay date = monthDays.get(position);
        return date;
    }
}
