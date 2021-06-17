package com.rsy0921.mancoreman.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.nhn.android.naverlogin.OAuthLogin;
import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.EventHelperClass;
import com.rsy0921.mancoreman.common.GoodDesignerHelperClass;
import com.rsy0921.mancoreman.common.GoodStyleHelperClass;
import com.rsy0921.mancoreman.common.PreferenceManager;
import com.rsy0921.mancoreman.recyclerViewAdpter.HomeEventAdapter;
import com.rsy0921.mancoreman.recyclerViewAdpter.HomeGoodDesignerAdapter;
import com.rsy0921.mancoreman.recyclerViewAdpter.HomeGoodStyleAdapter;

import java.util.ArrayList;

public class FragmentContainerActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private long backTime;

    RecyclerView eventRecycler, goodDesignerRecycler, goodStyleRecycler;
    RecyclerView.Adapter adapter;

    private GradientDrawable gradient1, gradient2, gradient3, gradient4;


    //test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragment_navi);

        bottomNavigationView = findViewById(R.id.bottom_nav_menu);

        //홈으로 선택
        //bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);

        //홈화면 보여주기
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
        
        //네비버튼별 Fragment 이동
        //onChangeBottomNaviFragment();

    }


    @Override
    public void onBackPressed() {
        //1번째 백버튼 클릭
        if(System.currentTimeMillis()>backTime+2000){
            backTime = System.currentTimeMillis();
            Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }else{
            //2번째 백버튼 클릭 (종료)
            moveTaskToBack(true);
            finishAndRemoveTask();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    //네비버튼별 Fragment 이동
    private void onChangeBottomNaviFragment() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.bottom_nav_home:
                        fragment = new FragmentHome();
                        break;

                    case R.id.bottom_nav_reserve:
                        fragment = new BlankFragment_02();
                        break;

                    case R.id.bottom_nav_calendar:
                        fragment = new BlankFragment_03();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


                return false;
            }
        });



    }

    //화면 버튼 클릭이벤트 모음
    public void onButtonClick(View view){

        final Intent intent;

        switch (view.getId()){

            case R.id.btn_logout:

                // 네이버 로그아웃
                OAuthLogin.getInstance().logout(getApplicationContext());
                // 직접로그인 로그아웃
                PreferenceManager.clear(this);

                Toast.makeText(FragmentContainerActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();

                //로그인화면으로 이동
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.layout_notice:

                //공지사항 화면으로 이동
                intent = new Intent(this, NoticeActivity.class);

                Pair[] pairs = new Pair[1];

                pairs[0] = new Pair<View, String>(findViewById(R.id.txt_notice) , "transition_notice");

                ActivityOptions options =  ActivityOptions.makeSceneTransitionAnimation(FragmentContainerActivity.this, pairs);

                startActivity(intent, options.toBundle());
                break;
        }

    }


    public void eventRecyclerView(View view) {

        eventRecycler = view.findViewById(R.id.event_recycler);

        //대부분 RecyclerView를 사용하는 목적은 동일한 크기의 아이템 항목을 사용자에게 리스트로 보여주기 위해서다.
        // 따라서 아이템의 크기가 변하는 경우는 없을 것이고, 그렇다면 setHasFixedSize를 true로 설정함으로써 변경되지 않는다는 것을 명시하는게 좋다
        eventRecycler.setHasFixedSize(true);
        eventRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        //이벤트 카드뷰
        ArrayList<EventHelperClass> eventLocations = new ArrayList<>();

        eventLocations.add(new EventHelperClass(R.drawable.event_01, "첫방문 이벤트", "첫 방문시 헤드스파가 공짜!!!"));
        eventLocations.add(new EventHelperClass(R.drawable.event_01, "6월 이벤트", "6월 이벤트"));
        eventLocations.add(new EventHelperClass(R.drawable.event_01, "깜짝 이벤트", "깜짝 이벤트"));

        adapter = new HomeEventAdapter(eventLocations);
        eventRecycler.setAdapter(adapter);

    }


    public void goodDesignerRecyclerView(View view) {

        goodDesignerRecycler = view.findViewById(R.id.good_designer_recycler);

        //대부분 RecyclerView를 사용하는 목적은 동일한 크기의 아이템 항목을 사용자에게 리스트로 보여주기 위해서다.
        // 따라서 아이템의 크기가 변하는 경우는 없을 것이고, 그렇다면 setHasFixedSize를 true로 설정함으로써 변경되지 않는다는 것을 명시하는게 좋다
        goodDesignerRecycler.setHasFixedSize(true);
        goodDesignerRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //추천 디자이너 카드뷰
        ArrayList<GoodDesignerHelperClass> goodDesignerLocations = new ArrayList<>();

        goodDesignerLocations.add(new GoodDesignerHelperClass(R.drawable.good_designer_01, "김선우", "취향 저격 헤어 디자이너 10년"));
        goodDesignerLocations.add(new GoodDesignerHelperClass(R.drawable.good_designer_02, "윤태열", "헤어 해결자 7년"));
        goodDesignerLocations.add(new GoodDesignerHelperClass(R.drawable.good_designer_03, "박찬순", "센스 디자이너 5년"));

        adapter = new HomeGoodDesignerAdapter(this, goodDesignerLocations);
        goodDesignerRecycler.setAdapter(adapter);

//        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }


    public void goodStyleRecyclerView(View view) {

        goodStyleRecycler = view.findViewById(R.id.good_style_recycler);

        //All Gradients
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});

        //추천 스타일 카드뷰
        ArrayList<GoodStyleHelperClass> goodStyleList = new ArrayList<>();
        goodStyleList.add(new GoodStyleHelperClass(R.drawable.style_01, "리젠트컷", gradient1));
        goodStyleList.add(new GoodStyleHelperClass(R.drawable.style_01, "리젠트컷", gradient2));
        goodStyleList.add(new GoodStyleHelperClass(R.drawable.style_01, "리젠트컷", gradient3));
        goodStyleList.add(new GoodStyleHelperClass(R.drawable.style_01, "리젠트컷", gradient4));


        //대부분 RecyclerView를 사용하는 목적은 동일한 크기의 아이템 항목을 사용자에게 리스트로 보여주기 위해서다.
        // 따라서 아이템의 크기가 변하는 경우는 없을 것이고, 그렇다면 setHasFixedSize를 true로 설정함으로써 변경되지 않는다는 것을 명시하는게 좋다
        goodStyleRecycler.setHasFixedSize(true);
        goodStyleRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new HomeGoodStyleAdapter(goodStyleList);
        goodStyleRecycler.setAdapter(adapter);

//        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});

    }




}