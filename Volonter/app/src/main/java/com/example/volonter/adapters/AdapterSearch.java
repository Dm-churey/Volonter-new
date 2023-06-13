//package com.example.volonter.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.volonter.R;
//import com.example.volonter.model.Event;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.MyViewHolder> {
//    ArrayList<Event> list;
//    public AdapterSearch(ArrayList<Event> list) {
//        this.list = list;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item, viewGroup, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public void filterList(ArrayList<Event> filteredList) {
//        list = filteredList;
//        notifyDataSetChanged();
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//}
