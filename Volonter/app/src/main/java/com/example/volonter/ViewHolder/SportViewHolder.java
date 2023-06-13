package com.example.volonter.ViewHolder;//package com.example.volonter.ViewHolder;
//
//import android.view.View;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.volonter.Interface.ItemClickListener;
import com.example.volonter.R;
import com.example.volonter.model.Event;

import java.util.List;

public class SportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView events_item_text, events_item_organizer, events_item_date, events_item_place, events_item_direction;
    //public Button events_item_button;
    public ImageView image_events;
    public ItemClickListener listener;

    public SportViewHolder(View itemView) {
        super(itemView);

        events_item_text = itemView.findViewById(R.id.events_item_text);
        events_item_organizer = itemView.findViewById(R.id.events_item_organizer);
        events_item_date = itemView.findViewById(R.id.events_item_date);
        events_item_place = itemView.findViewById(R.id.events_item_place);
        events_item_direction = itemView.findViewById(R.id.events_item_direction);
        //events_item_button = itemView.findViewById(R.id.events_item_button);
        image_events = itemView.findViewById(R.id.image_events);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(),false);
    }
}
