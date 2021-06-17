package com.rsy0921.mancoreman.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rsy0921.mancoreman.R;

public class MainActivity extends AppCompatActivity {

    private long backTime;
    private static Context mContext;

    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    HomeActivity homeActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //home.xml과 연결
        setContentView(R.layout.main_activity);

        mContext = this;

//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavi);
//        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
//        homeActivity =  new HomeActivity();



        //제일 처음 띄워줄 뷰를 세팅해줍니다. commit();까지 해줘야 합니다.
//        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,homeActivity).commitAllowingStateLoss();





        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(mContext, item.getTitle().toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        });
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



}
