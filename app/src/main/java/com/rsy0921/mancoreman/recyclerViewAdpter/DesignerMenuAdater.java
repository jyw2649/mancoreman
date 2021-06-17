package com.rsy0921.mancoreman.recyclerViewAdpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.activity.MenuActivity;
import com.rsy0921.mancoreman.common.DesignerMenuDto;

import java.util.ArrayList;

public class DesignerMenuAdater extends RecyclerView.Adapter<DesignerMenuAdater.designerMenuViewHolder> {

    ArrayList<DesignerMenuDto> designerMenuDtoArrayList;
    Context context;

    public DesignerMenuAdater(ArrayList<DesignerMenuDto> designerMenuDtoArrayList, Context context) {
        this.designerMenuDtoArrayList = designerMenuDtoArrayList;
        this.context = context;
    }

    //커스텀 리스너 인터페이스 정의
   public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }



    @NonNull
    @Override
    public designerMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.designer_list_menu, parent, false);
        designerMenuViewHolder  designerMenuViewHolder = new designerMenuViewHolder(view);

        return designerMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull designerMenuViewHolder holder, int position) {

        DesignerMenuDto designerMenuDto = designerMenuDtoArrayList.get(position);

        holder.name.setText(designerMenuDto.getName());
        holder.price.setText(designerMenuDto.getPrice());
        holder.description.setText(designerMenuDto.getDescription());

    }

    @Override
    public int getItemCount() {
        return designerMenuDtoArrayList.size();
    }


    public  class designerMenuViewHolder extends RecyclerView.ViewHolder{

        TextView name, description, price;

        public designerMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            name = itemView.findViewById(R.id.txt_designer_menu_name);
            price = itemView.findViewById(R.id.txt_designer_menu_price);
            description = itemView.findViewById(R.id.txt_designer_menu_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = getAdapterPosition() ;

                    if (pos != RecyclerView.NO_POSITION) {

                        Intent intent = new Intent(context, MenuActivity.class);
                        context.startActivity(intent);

//                        // 리스너 객체의 메서드 호출.
//                        if (mListener != null) {
//                            mListener.onItemClick(view, pos) ;
//                        }
                    }
                }
            });
        }
    }



}
