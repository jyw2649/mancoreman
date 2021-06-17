package com.rsy0921.mancoreman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.data.OAuthLoginState;
import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.PreferenceManager;

public class IntroActivity extends AppCompatActivity {

    private Intent intent;

    TextView appname;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        appname = findViewById(R.id.appname);
        lottie = findViewById(R.id.lottie);
        
        // 애니메이션 효과
        appname.animate().translationY(-1400).setDuration(1000).setStartDelay(0);
        //lottie.animate().translationY(2000).setDuration(2000).setStartDelay(2900);


        // 직접 로그인 체크
        String sId = PreferenceManager.getString(this, "id");
        Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> <IntroActivity> sId ->", sId);



        if(sId.isEmpty()){

            //네이버 로그인 체크
            if(OAuthLogin.getInstance().getState(this).equals(OAuthLoginState.NEED_LOGIN)){

                Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> <IntroActivity> 로그인 필요!!!", "Login 이동2");

                intent = new Intent(this, LoginActivity.class);

            }else{

                Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> <IntroActivity> 로그인 불필요!!!", "Home 이동");
                intent = new Intent(this, FragmentContainerActivity.class);
            }

        }else {

            Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> <IntroActivity> 로그인 불필요!!!", "Home 이동");
            intent = new Intent(this, FragmentContainerActivity.class);
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                finish();
            }
        }, 2500);

//        if(!OAuthLoginState.NEED_LOGIN.equals(OAuthLogin.getInstance().getState(this)) &&
//                !OAuthLoginState.NEED_INIT.equals(mOAuthLoginInstance.getState(this))
//        ){
//            Log.e("********** 로그인 여부 ", "로그인완료");
//        }else{
//
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }

    }
}