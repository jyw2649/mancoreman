package com.rsy0921.mancoreman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.NoticeExpandableListAdapter;
import com.rsy0921.mancoreman.common.NoticeParentData;

import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private ArrayList<NoticeParentData> parentList;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_activity);



        floatingActionButton = findViewById(R.id.btn_floating_action);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goInsertNotice();
            }
        });





        // 확장 리스트뷰를 가져온다.
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // 리스트뷰에 데이터를 넣는 곳.
        parentList = new ArrayList<>();

        NoticeParentData parentData = new NoticeParentData("[휴무]2021년 5월 31일(월) 휴무입니다.", "2021년 5월 18일");
        parentData.child.add("맨코어맨 개인 사정으로 인해 2021년 5월 30일에 휴무를 알려드립니다.\n보다 더 나은 서비스로 만나뵙겠습니다. \n감사합니다.");
        parentList.add(parentData);

        parentData = new NoticeParentData("[소식]인스타그램 계정 생성", "2021년 5월 10일");
        parentData.child.add("맨코어맨이 인스타계정을 생성했습니다. \n놀러 오셔서 구경도 하시고 맞팔해요^^");
        parentList.add(parentData);

        listAdapter = new NoticeExpandableListAdapter(this, R.layout.notice_list_parent, R.layout.notice_list_child, parentList);

        // 리스트어댑터 세팅
        expListView.setAdapter(listAdapter);


        // 리스트뷰 그룹(부모)뷰를 클릭 했을 경우
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // 그룹이 열릴 경우 이벤트 발
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // 그룹이 닫힐 경우 이벤트 발생
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // 차일드 뷰를 눌렀을 경우 이벤트 발생
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });
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


    public void goInsertNotice(){

        BottomSheetDialog bottomSheetDialog;
        bottomSheetDialog = new BottomSheetDialog(NoticeActivity.this);
        bottomSheetDialog.setContentView(R.layout.reservation_dialog);
        bottomSheetDialog.show();

//        Intent intent = new Intent(this, DesignerActivity.class);
//        startActivity(intent);
//        finish();
    }



}
