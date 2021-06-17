package com.rsy0921.mancoreman.recyclerViewAdpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.EventHelperClass;

import java.util.ArrayList;

public class HomeEventAdapter extends RecyclerView.Adapter<HomeEventAdapter.eventViewHolder> {

    ArrayList<EventHelperClass> eventLocations;

    public HomeEventAdapter(ArrayList<EventHelperClass> eventLocations) {
        this.eventLocations = eventLocations;
    }


    @NonNull
    @Override
    public eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_event, parent, false);
        eventViewHolder eventViewHolder = new eventViewHolder(view);

        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull eventViewHolder holder, int position) {

        EventHelperClass eventHelperClass = eventLocations.get(position);

        holder.image.setImageResource(eventHelperClass.getImage());
        holder.title.setText(eventHelperClass.getTitle());
        holder.desc.setText(eventHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return eventLocations.size();
    }


    public  static class eventViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc;

        public eventViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.event_image);
            title = itemView.findViewById(R.id.event_title);
            desc = itemView.findViewById(R.id.event_desc);
        }
    }




}
