package com.rsy0921.mancoreman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.AppHelper;
import com.rsy0921.mancoreman.common.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private long backTime;
    private static Context mContext;


    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //home.xml과 연결
        setContentView(R.layout.main_activity);

        mContext = this;

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(mContext);
        }

        chipNavigationBar = findViewById(R.id.bottom_nav_menu);
        chipNavigationBar.setItemSelected(R.id.bottom_nav_home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();

        buttomMenu();

//        BtnOnClickListener btnOnClickListener = new BtnOnClickListener();

//        Button btnFootLogout = findViewById(R.id.btn_foot_logout);
//        btnFootLogout.setOnClickListener(btnOnClickListener);

//        getUserInfo();
    }

    private void buttomMenu() {

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                Fragment fragment = null;

                switch (i){
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


    /**
     *  버튼 Listener
    */
//    class BtnOnClickListener implements Button.OnClickListener {
//
//        @Override
//        public void onClick(View view) {
//
//            switch (view.getId()){
//                case R.id.btn_foot_logout :
//                    OAuthLogin.getInstance().logout(getApplicationContext());
//                    Toast.makeText(HomeActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
//
//                    //로그인화면으로 이동
//                    moveLoginActivity();
//                    break;
//            }
//
//        }
//    }


    /**
     *  로그인 화면으로 이동
     */
    protected void moveLoginActivity() {

        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }


    /**
     *  유저정보 가져오기
     */
    protected void getUserInfo() {

        String apiURL = "http://rsy0921.iptime.org:8080/mancoreman/home/userInfo";

        JSONObject paramJson = new JSONObject();
        try {
            paramJson.put("id", PreferenceManager.getString(this,"id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> paramJson ->" + paramJson);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                apiURL,
                paramJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(" ********** <saveUserInfo> DB저장 성공!!!", "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(" ********** <saveUserInfo> DB저장 에러!!! - > ", error.toString());
                    }
                }
        );

//        //아래 add코드처럼 넣어줄때 Volley라고하는게 내부에서 캐싱을 해준다, 즉, 한번 보내고 받은 응답결과가 있으면
//        //그 다음에 보냈을 떄 이전 게 있으면 그냥 이전거를 보여줄수도  있다.
//        //따라서 이렇게 하지말고 매번 받은 결과를 그대로 보여주기 위해 다음과같이 setShouldCache를 false로한다.
//        //결과적으로 이전 결과가 있어도 새로 요청한 응답을 보여줌
        jsonObjectRequest.setShouldCache(false);
        AppHelper.requestQueue.add(jsonObjectRequest);

    }



}
