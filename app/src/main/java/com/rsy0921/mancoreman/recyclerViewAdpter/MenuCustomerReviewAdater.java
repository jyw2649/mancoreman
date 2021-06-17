package com.rsy0921.mancoreman.recyclerViewAdpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsy0921.mancoreman.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuCustomerReviewAdater extends RecyclerView.Adapter<MenuCustomerReviewAdater.ViewHoler> {

    ArrayList<HashMap> menuCustomerReviewArrayList;

    public MenuCustomerReviewAdater(ArrayList<HashMap> menuCustomerReviewArrayList) {

        this.menuCustomerReviewArrayList = menuCustomerReviewArrayList;
    }

    public  class ViewHoler extends RecyclerView.ViewHolder{

        TextView menuName, designName, description;
        RatingBar ratingBar;

        public ViewHoler(@NonNull View itemView) {

            super(itemView);

            //Hooks
            menuName = itemView.findViewById(R.id.txt_menu_customer_review_menuName);
            designName = itemView.findViewById(R.id.txt_menu_customer_review_designerName);
            description = itemView.findViewById(R.id.txt_menu_customer_review_description);
            ratingBar = itemView.findViewById(R.id.rating_menu_customer_review);

        }
    }


    /**
     *  viewType 형태의 아이템 뷰를 위한 뷰홀더 객체 생성
     * */
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_customer_review, parent, false);
        ViewHoler  viewHoler = new ViewHoler(view);

        return viewHoler;
    }


    /**
     *  position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
     * */
    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {

        HashMap menuCustomerReviewMap = menuCustomerReviewArrayList.get(position);

        holder.menuName.setText(menuCustomerReviewMap.get("menuName").toString());
        holder.designName.setText(menuCustomerReviewMap.get("designerName").toString());
        holder.description.setText(menuCustomerReviewMap.get("description").toString());
        holder.ratingBar.setRating(Float.valueOf(menuCustomerReviewMap.get("rating").toString()));

    }


    /**
     *  전체 아이템 갯수 리턴
     * */
    @Override
    public int getItemCount() {
        return menuCustomerReviewArrayList.size();
    }



}
