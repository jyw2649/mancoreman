package com.rsy0921.mancoreman.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.AppHelper;
import com.rsy0921.mancoreman.common.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;


public class DirectLoginActivity extends AppCompatActivity {

    private static Context mContext;

    TextInputEditText txt_id;
    TextInputEditText txt_pw;

    TextInputLayout layout_id;
    TextInputLayout layout_pw;

    String sId;
    String sPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directlogin_activity);

        mContext = this;

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(mContext);
        }

        txt_id = findViewById(R.id.edtTxt_id);
        txt_pw = findViewById(R.id.edtTxt_pw);

        layout_id = findViewById(R.id.layout_input_id);
        layout_pw = findViewById(R.id.layout_input_pw);


        //로그인 버튼
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                //text박스 널체크
                if(!onChkTextNull()) return;

                // 로그인 시도
                goDirectLogin();
            }
        });

        
        //회원가입 버튼
        Button btnSignup = findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                //회원가입 화면으로 이동
                moveSignupActivity();
            }
        });
    }


//    @Override
//    public void onBackPressed() {
//
//        super.onBackPressed();
//        finish();
//
////        //1번째 백버튼 클릭
////        if(System.currentTimeMillis()>backTime+2000){
////            backTime = System.currentTimeMillis();
////            Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
////        }else{
////            //2번째 백버튼 클릭 (종료)
////            moveTaskToBack(true);
////            finishAndRemoveTask();
////            android.os.Process.killProcess(android.os.Process.myPid());
////        }
//    }



    /**
     *
     *  회원가입 화면으로 이동
     * */

    protected void moveSignupActivity() {

        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);

        Pair[] pairs = new Pair[2];

        pairs[0] = new Pair<View, String>(findViewById(R.id.txt_main_01) , "transition_main_text_01");
        pairs[1] = new Pair<View, String>(findViewById(R.id.txt_main_02) , "transition_main_text_02");

        ActivityOptions options =  ActivityOptions.makeSceneTransitionAnimation(DirectLoginActivity.this, pairs);

        startActivity(intent, options.toBundle());

    }


    /**
     *
     *  홈 화면으로 이동
     * */

    protected void moveHomeActivity() {

        Intent intent = new Intent(getApplicationContext(), FragmentContainerActivity.class);

        startActivity(intent);
        finish();

    }


    // 로그인 시도
    protected void goDirectLogin() {

        String apiURL = "http://rsy0921.iptime.org:8080/mancoreman/directLogin/login";

        JSONObject  JSONParam = getJsonParam();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> goDirectLogin : param ->" + JSONParam);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                apiURL,
                JSONParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> goDirectLogin 결과 response ->" + response);

                        //서버통신 후 작업
                        fn_directLoginResult(response);
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
     *  서버통신 후 작업
     */
    public void fn_directLoginResult(JSONObject  pResponse){

        String sResultCode = "";
        String sResultMsg = "";
        String sName = "";

        try {

            sResultCode = pResponse.getString("resultCode");
            sResultMsg = pResponse.getString("resultMsg");

            JSONObject jsonUserInfo = pResponse.getJSONObject("resultJSONObject");
            sName = jsonUserInfo.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(sResultCode.equals("Y")){

            PreferenceManager.setString(this,"id", sId);
            PreferenceManager.setString(this,"name", sName);

            final Intent intent = new Intent(this, FragmentContainerActivity.class);
            startActivity(intent);
            finish();
        }else{

            Toast.makeText(this, sResultMsg, Toast.LENGTH_SHORT).show();
        }
    }



    private Boolean onChkTextNull(){

        sId = txt_id.getText().toString().trim();
        sPw = txt_pw.getText().toString().trim();

        if(sId.isEmpty() || sId.equals("")){
            layout_id.setErrorEnabled(true);
            layout_id.setError("아이디를 입력하세요");
            return false;
        }else{
            layout_id.setErrorEnabled(false);
            layout_id.setError(null);
        }


        if(sPw.isEmpty() || sPw.equals("")){
            layout_pw.setErrorEnabled(true);
            layout_pw.setError("패스워드를 입력하세요");
            return false;
        }else{
            layout_pw.setErrorEnabled(false);
            layout_pw.setError(null);
        }

        return true;
    }

    // 회원가입 데이터 JSONObject에 담기
    public JSONObject getJsonParam(){

        JSONObject jsonParam = new JSONObject();

        try {

            jsonParam.put("id", sId);
            jsonParam.put("pw", sPw);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonParam;
    }


}
