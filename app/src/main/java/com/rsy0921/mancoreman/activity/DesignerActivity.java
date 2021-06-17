package com.rsy0921.mancoreman.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.recyclerViewAdpter.DesignerMenuAdater;
import com.rsy0921.mancoreman.common.DesignerMenuDto;

import java.util.ArrayList;

public class DesignerActivity extends AppCompatActivity {

    private long backTime;
    private static Context mContext;

    RecyclerView designerMenuRecyclerView;
//    RecyclerView.Adapter adapter;
    DesignerMenuAdater adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.designer_activity);

        designerMenuRecyclerView = findViewById(R.id.designer_menu_recycler);
        designerMenuRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        designerMenuRecyclerView();
    }

    private void designerMenuRecyclerView() {

        //대부분 RecyclerView를 사용하는 목적은 동일한 크기의 아이템 항목을 사용자에게 리스트로 보여주기 위해서다.
        // 따라서 아이템의 크기가 변하는 경우는 없을 것이고, 그렇다면 setHasFixedSize를 true로 설정함으로써 변경되지 않는다는 것을 명시하는게 좋다
        designerMenuRecyclerView.setHasFixedSize(true);
        designerMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //이벤트 카드뷰
        ArrayList<DesignerMenuDto> designerMenuDtoArrayList = new ArrayList<>();

        designerMenuDtoArrayList.add(new DesignerMenuDto("헤드스파컷", "대표", "33,000원"));
        designerMenuDtoArrayList.add(new DesignerMenuDto("컷트", "만족도가 매우 높습니다.", "22,000원"));
        designerMenuDtoArrayList.add(new DesignerMenuDto("볼륨펌", "대표", "44,000원"));
        designerMenuDtoArrayList.add(new DesignerMenuDto("다운펌", "", "66,000원"));
        designerMenuDtoArrayList.add(new DesignerMenuDto("부분펌", "", "66,000원"));
        designerMenuDtoArrayList.add(new DesignerMenuDto("아이롱펌", "", "150,000원"));
        designerMenuDtoArrayList.add(new DesignerMenuDto("로레알케어염색", "", "77,000원"));

        adapter = new DesignerMenuAdater(designerMenuDtoArrayList, this);

        adapter.setOnItemClickListener(new DesignerMenuAdater.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Toast.makeText(DesignerActivity.this, position + "번째 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        designerMenuRecyclerView.setAdapter(adapter);


    }


    @Override
    public void onBackPressed() {
        finish();

//        //1번째 백버튼 클릭
//        if(System.currentTimeMillis()>backTime+2000){
//            backTime = System.currentTimeMillis();
//            Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
//        }else{
//            //2번째 백버튼 클릭 (종료)
//            moveTaskToBack(true);
//            finishAndRemoveTask();
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
    }



    public void makeToastMeassge(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



}
