package wangyijieholding.agendaapplication.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import wangyijieholding.agendaapplication.events.EDataWithTime;
import wangyijieholding.agendaapplication.R;

/**
 * Created by wangyijie6646 on 9/7/16.
 */
public class DayDetailListItemAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<EDataWithTime> list = new ArrayList<>();
    private Context context;
    int onHoldIndex;

    public DayDetailListItemAdapter(Context c, ArrayList<EDataWithTime> l){
        context = c;
        list = l;
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        View view;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(list.get(pos).isSpecifyTime()) {
                view = inflater.inflate(R.layout.list_item_with_edit_button_and_time, null);
                EDataWithTime date = list.get(pos);
                TextView startTime = (TextView) view.findViewById(R.id.dayDetailItemStartTime);
                TextView endTime = (TextView) view.findViewById(R.id.dayDetailItemEndTime);
                startTime.setText(date.formatStartTime());
                endTime.setText(date.formatEndTime());
            } else view = inflater.inflate(R.layout.list_item_with_edit_button,null);
        } else{
            view = convertView;
        }
        //Fill in the text
        TextView title = (TextView) view.findViewById(R.id.dayDetailItemTitle);
        title.setText(list.get(pos).getEventTitle());
        TextView desc = (TextView) view.findViewById(R.id.dayDetailItemDesc);
        desc.setText(list.get(pos).getEventDesc());

        //Add listener to the button
        //A method is called to create the fragment from the context
        ImageButton editButton = (ImageButton) view.findViewById(R.id.dayDetailItemEditButton);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                DayDetailActivity d = (DayDetailActivity)context;
                d.onHoldEvent = pos;
                d.showDialog();
            }
        });
        return view;
    }

    @Override
    public long getItemId(int pos){
        return 0;
    }

    @Override
    public Object getItem(int pos){
        return list.get(pos);
    }
}
