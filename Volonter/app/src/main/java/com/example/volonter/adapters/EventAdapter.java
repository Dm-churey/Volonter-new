package com.example.volonter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.volonter.R;
import com.example.volonter.model.Event;

import java.util.List;

public class EventAdapter extends BaseAdapter {
    private List<Event> list;
    private Context context;
    private LayoutInflater lInflater;

    public EventAdapter(Context context, List<Event> list){
        this.context = context;
        this.list = list;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Event getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = lInflater.inflate(R.layout.event_item, viewGroup, false);
        }

        TextView title = v.findViewById(R.id.events_item_text);
        //ImageView img = v.findViewById(R.id.imageOfEvent);
        title.setText(getItem(i).getName_events());

        return v;
    }
}
