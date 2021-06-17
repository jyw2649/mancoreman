package com.rsy0921.mancoreman.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.AppHelper;
import com.rsy0921.mancoreman.common.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static String OAUTH_CLIENT_ID = "4H2Ghi1Rn2E9C7orurBf";
    private static String OAUTH_CLIENT_SECRET = "I2ztgDMyRr";
    private static String OAUTH_CLIENT_NAME = "rsy0921";

    private static OAuthLogin mOAuthLoginInstance;
    private static OAuthLoginButton mOAuthLoginButton;
    private static Context mContext;
    private long backTime;

    private Button btn_direct_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //로그인
        setContentView(R.layout.login_activity);

        mContext = this;

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(mContext);
        }

        //초기화
        naver_initData();


        //직접 로그인 버튼 이벤트
        Button btn_direct_login = (Button)findViewById(R.id.btn_direct_login);
        btn_direct_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveDirectLoginActivity();
            }
        }) ;
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

    private  void naver_initData(){

        //네이버 로그인 SDK 초기화
        //이후에 재호출하여도 토큰 갱신이 되지 않으니 한 번만 하면 된다. 처음에 받았던 Client ID와 Client Secret 값이 SDK 초기화에 쓰인다.
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHanduler);

    }

    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mOAuthLoginHanduler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            
            if(success){

                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                /*String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);*/

                Toast.makeText(mContext, "success:" + accessToken, Toast.LENGTH_SHORT).show();

                getNaverUserInfo(accessToken);

            }else{
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode : " + errorCode + ", errorDesc : " + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };



    /**
     *
     *  토큰으로 네이버에서 유저정보 가져오기
     *
     * */
    protected void getNaverUserInfo(String accessToken) {

        String apiURL = "https://openapi.naver.com/v1/nid/me";

        //StringRequest는 요청객체중 하나이며 가장 많이 쓰인다고한다.
        //화면에 결과를 표시할때 핸들러를 사용하지 않아도되는 장점이있다.
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                apiURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String sResultcode = jsonObject.getString("resultcode");
                            String sMessage = jsonObject.getString("message");

                            if(sResultcode == null || "".equals(sResultcode) || !"00".equals(sResultcode)){

                                Log.e(" ********** 네이버유저정보 호출 오류!!! - > ", sMessage);

                            }else{

                                Log.e(" ********** 네이버유저정보 호출 성공!!! - > ", sMessage);
                                Log.e(" ********** response -> ", response);

                                JSONObject jsonObjectResponse =  jsonObject.getJSONObject("response");
                                jsonObjectResponse.put("sns_id", jsonObjectResponse.getString("id"));

                                PreferenceManager.setString(mContext, "id", jsonObjectResponse.getString("id"));
                                PreferenceManager.setString(mContext, "name", jsonObjectResponse.getString("name"));

                                //SNS 로그인 후 저장
                                saveSnsUserInfo(jsonObjectResponse);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(" ********** onErrorResponse!!! - > ", error.toString());

                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization",  "Bearer " + accessToken);

                System.out.println("************************ headers : " + headers);

                return headers;
            }
        } ;

        //아래 add코드처럼 넣어줄때 Volley라고하는게 내부에서 캐싱을 해준다, 즉, 한번 보내고 받은 응답결과가 있으면
        //그 다음에 보냈을 떄 이전 게 있으면 그냥 이전거를 보여줄수도  있다.
        //따라서 이렇게 하지말고 매번 받은 결과를 그대로 보여주기 위해 다음과같이 setShouldCache를 false로한다.
        //결과적으로 이전 결과가 있어도 새로 요청한 응답을 보여줌
        stringRequest.setShouldCache(false);

        AppHelper.requestQueue.add(stringRequest);

    }

    protected void saveSnsUserInfo(JSONObject pResponse) {

        String apiURL = "http://rsy0921.iptime.org:8080/mancoreman/login/insertSNSUser";

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> response ->" + pResponse);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                apiURL,
                pResponse,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(" ********** <saveUserInfo> DB저장 성공!!!", "");
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 결과 response ->" + response);

                        // 홈화면으로 이동
                        moveHomeActivity();
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
    
    /**
     * 
     *  홈화면으로 이동
     * */
    
    protected void moveHomeActivity() {

        final Intent intent = new Intent(this, FragmentContainerActivity.class);
        startActivity(intent);
        finish();

    }

    /**
     *
     *  직접로그인 화면으로 이동
     * */

    protected void moveDirectLoginActivity() {

        Intent intent = new Intent(this, DirectLoginActivity.class);

        Pair[] pairs = new Pair[2];

        pairs[0] = new Pair<View, String>(findViewById(R.id.txt_main_01) , "transition_main_text_01");
        pairs[1] = new Pair<View, String>(findViewById(R.id.txt_main_02) , "transition_main_text_02");

        ActivityOptions options =  ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);

        startActivity(intent, options.toBundle());

    }



//    String으로 통신
//    protected void saveUserInfo_string(Map<String, String> mapUserInfo) {
//
//        String apiURL = "http://rsy0921.iptime.org:8080/mancoreman/insertUser";
//
//        //StringRequest는 요청객체중 하나이며 가장 많이 쓰인다고한다.
//        //화면에 결과를 표시할때 핸들러를 사용하지 않아도되는 장점이있다.
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST,
//                apiURL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.e(" ********** <saveUserInfo> DB저장 성공!!!", "");
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Log.e(" ********** <saveUserInfo> DB저장 에러!!! - > ", error.toString());
//
//                    }
//                }
//        )
//        {
//            @Override
//            public Map<String, String> getParams() throws AuthFailureError{
//
//                return mapUserInfo;
//            }
//        } ;
//
//        //아래 add코드처럼 넣어줄때 Volley라고하는게 내부에서 캐싱을 해준다, 즉, 한번 보내고 받은 응답결과가 있으면
//        //그 다음에 보냈을 떄 이전 게 있으면 그냥 이전거를 보여줄수도  있다.
//        //따라서 이렇게 하지말고 매번 받은 결과를 그대로 보여주기 위해 다음과같이 setShouldCache를 false로한다.
//        //결과적으로 이전 결과가 있어도 새로 요청한 응답을 보여줌
//        stringRequest.setShouldCache(false);
//
//        AppHelper.requestQueue.add(stringRequest);
//
//    }




}
