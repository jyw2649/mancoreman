package com.rsy0921.mancoreman.recyclerViewAdpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.activity.DesignerActivity;
import com.rsy0921.mancoreman.common.GoodDesignerHelperClass;

import java.util.ArrayList;

public class HomeGoodDesignerAdapter extends RecyclerView.Adapter<HomeGoodDesignerAdapter.goodDesignerViewHolder> {

    ArrayList<GoodDesignerHelperClass> goodDesignerLocations;
    Context mContext;

    /**
     * 생성자
     */
    public HomeGoodDesignerAdapter(Context context, ArrayList<GoodDesignerHelperClass> goodDesignerLocations) {
        this.goodDesignerLocations = goodDesignerLocations;
        this.mContext = context;
    }


    /**
     *  레이아웃을 만들어서 Holer에 저장
     *  뷰 홀더를 생성하고 뷰를 붙여주는 부분
     */
    @NonNull
    @Override
    public goodDesignerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card_gooddesigner, parent, false);
        goodDesignerViewHolder goodDesignerViewHolder = new goodDesignerViewHolder(mContext, view);

        return goodDesignerViewHolder;
    }


    /**
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     * 재활용 되는 뷰가 호출하여 실행되는 메소드
     * 뷰 홀더를 전달하고 어댑터는 position 의 데이터를 결합
     */
    @Override
    public void onBindViewHolder(@NonNull goodDesignerViewHolder holder, int position) {

        GoodDesignerHelperClass goodDesignerHelperClass = goodDesignerLocations.get(position);

        holder.image.setImageResource(goodDesignerHelperClass.getImage());
        holder.title.setText(goodDesignerHelperClass.getTitle());
        holder.desc.setText(goodDesignerHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return goodDesignerLocations.size();
    }


    public  static class goodDesignerViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc;

        public goodDesignerViewHolder(@NonNull Context mContext, View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.gd_image);
            title = itemView.findViewById(R.id.gd_title);
            desc = itemView.findViewById(R.id.gd_desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                                Intent intent = new Intent(mContext, DesignerActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                        }
                    }
                }
            );


        }
    }




}
