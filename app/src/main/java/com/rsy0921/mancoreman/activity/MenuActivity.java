package com.rsy0921.mancoreman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.recyclerViewAdpter.MenuCustomerReviewAdater;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {

    RecyclerView customerReviewRecyclerView;
    MenuCustomerReviewAdater menuCustomerReviewAdater;
    MaterialButton reservationButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        customerReviewRecyclerView = findViewById(R.id.menu_customer_review_recycler);
        customerReviewRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        reservationButton = findViewById(R.id.btn_reservation);

        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goReservation();

            }
        });

        setCustomerReviewRecyclerView();
    }

    @Override
    public void onBackPressed() {

        finish();
    }


    /***************************************************************
     * 사용자 함수
     **************************************************************/

    public void goReservation(){

        Intent intent = new Intent(this, ReservationActivity.class);
        startActivity(intent);
    }

    public void setCustomerReviewRecyclerView(){

        //대부분 RecyclerView를 사용하는 목적은 동일한 크기의 아이템 항목을 사용자에게 리스트로 보여주기 위해서다.
        // 따라서 아이템의 크기가 변하는 경우는 없을 것이고, 그렇다면 setHasFixedSize를 true로 설정함으로써 변경되지 않는다는 것을 명시하는게 좋다
        customerReviewRecyclerView.setHasFixedSize(true);
        customerReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //고객 리뷰 리스트
        ArrayList<HashMap> customerReviewArrayList = new ArrayList<>();
        HashMap customerReviewMap = new HashMap();
        customerReviewMap.put("menuName", "헤드스파컷");
        customerReviewMap.put("designerName", "김선우");
        customerReviewMap.put("rating", "3");
        customerReviewMap.put("description", "편안한 분위기에서 기분좋게 머리 했네요~!!");

        customerReviewArrayList.add(customerReviewMap);
        customerReviewArrayList.add(customerReviewMap);
        customerReviewArrayList.add(customerReviewMap);
        customerReviewArrayList.add(customerReviewMap);
        customerReviewArrayList.add(customerReviewMap);
        customerReviewArrayList.add(customerReviewMap);
        customerReviewArrayList.add(customerReviewMap);
        customerReviewArrayList.add(customerReviewMap);

        menuCustomerReviewAdater = new MenuCustomerReviewAdater(customerReviewArrayList);
        customerReviewRecyclerView.setAdapter(menuCustomerReviewAdater);

    }

}