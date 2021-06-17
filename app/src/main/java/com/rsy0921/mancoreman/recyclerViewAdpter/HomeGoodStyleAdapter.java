package com.rsy0921.mancoreman.recyclerViewAdpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.GoodStyleHelperClass;

import java.util.ArrayList;

public class HomeGoodStyleAdapter extends RecyclerView.Adapter<HomeGoodStyleAdapter.goodStyleViewHolder> {

    ArrayList<GoodStyleHelperClass> goodStyleLocations;

    public HomeGoodStyleAdapter(ArrayList<GoodStyleHelperClass> goodStyleLocations) {
        this.goodStyleLocations = goodStyleLocations;
    }


    @NonNull
    @Override
    public goodStyleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_goodstyle, parent, false);

        goodStyleViewHolder goodStyleViewHolder = new goodStyleViewHolder(view);

        return goodStyleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull goodStyleViewHolder holder, int position) {

        GoodStyleHelperClass goodStyleHelperClass = goodStyleLocations.get(position);

        holder.image.setImageResource(goodStyleHelperClass.getImage());
        holder.title.setText(goodStyleHelperClass.getTitle());
        holder.relativeLayout.setBackground(goodStyleHelperClass.getGradient());
    }

    @Override
    public int getItemCount() {
        return goodStyleLocations.size();
    }


    public  static class goodStyleViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        RelativeLayout relativeLayout;

        public goodStyleViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.gs_image);
            title = itemView.findViewById(R.id.gs_title);
            relativeLayout = itemView.findViewById(R.id.gs_layout);
        }
    }




}
