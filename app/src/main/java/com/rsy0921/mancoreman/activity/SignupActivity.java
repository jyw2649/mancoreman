package com.rsy0921.mancoreman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class SignupActivity extends AppCompatActivity {

    private static Context mContext;

    TextInputLayout layout_id;
    TextInputLayout layout_pw;
    TextInputLayout layout_name;
    TextInputLayout layout_mobile;
    TextInputLayout layout_email;

    TextInputEditText txt_id;
    TextInputEditText txt_pw;
    TextInputEditText txt_name;
    TextInputEditText txt_mobile;
    TextInputEditText txt_email;

    String sId;
    String sPw;
    String sName;
    String sMobile;
    String sEmail;

    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //로그인
        setContentView(R.layout.signup_activity);

        mContext = this;

        if(AppHelper.requestQueue == null){
            AppHelper.requestQueue = Volley.newRequestQueue(mContext);
        }

        layout_id = findViewById(R.id.layout_input_id);
        layout_pw = findViewById(R.id.layout_input_pw);
        layout_name = findViewById(R.id.layout_input_name);
        layout_mobile = findViewById(R.id.layout_input_mobile);
        layout_email = findViewById(R.id.layout_input_email);

        txt_id = findViewById(R.id.edtTxt_id);
        txt_pw = findViewById(R.id.edtTxt_pw);
        txt_name = findViewById(R.id.edtTxt_name);
        txt_mobile = findViewById(R.id.edtTxt_mobile);
        txt_email = findViewById(R.id.edtTxt_email);


        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                //입력 데이터 체크
                if(!fn_Valdation()) return;

                //회원가입 서버 통신
                goSignup();

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


    /**
     * 회원가입 서버 통신
     */
    protected void goSignup() {

        String apiURL = "http://rsy0921.iptime.org:8080/mancoreman/signup/insertUser";

        JSONObject  JSONParam = getJsonParam();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> <goSignup> JSONParam ->" + JSONParam);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                apiURL,
                JSONParam,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> 결과 response ->" + response);

                        fn_signupResult(response);
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
    public void fn_signupResult(JSONObject  pResponse){

        String sResultCode = "";
        String sResultMsg = "";

        try {

            sResultCode = pResponse.getString("resultCode");
            sResultMsg = pResponse.getString("resultMsg");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, sResultMsg, Toast.LENGTH_SHORT).show();

        if(sResultCode.equals("Y")){

            PreferenceManager.setString(this,"id", sId);
            PreferenceManager.setString(this,"name", sName);

            final Intent intent = new Intent(this, FragmentContainerActivity.class);
            startActivity(intent);
            finish();

        }
    }
    

    /**
     * 입력 데이터 체크
     */
    private Boolean fn_Valdation(){

        sId = txt_id.getText().toString().trim();
        sPw = txt_pw.getText().toString().trim();
        sName = txt_name.getText().toString().trim();
        sMobile = txt_mobile.getText().toString().trim();
        sEmail = txt_email.getText().toString().trim();


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

        if(sName.isEmpty() || sName.equals("")){
            layout_name.setErrorEnabled(true);
            layout_name.setError("이름을 입력하세요");
            return false;
        }else{
            layout_name.setErrorEnabled(false);
            layout_name.setError(null);
        }

        if(sMobile.isEmpty() || sMobile.equals("")){
            layout_mobile.setErrorEnabled(true);
            layout_mobile.setError("핸드폰번호를 입력하세요");
            return false;
        }else{
            layout_mobile.setErrorEnabled(false);
            layout_mobile.setError(null);
        }

        if(sEmail.isEmpty() || sEmail.equals("")){
            layout_email.setErrorEnabled(true);
            layout_email.setError("이메일을 입력하세요");
            return false;
        }else{
            layout_email.setErrorEnabled(false);
            layout_email.setError(null);
        }

        return true;
    }

    // 회원가입 데이터 JSONObject에 담기
    public JSONObject getJsonParam(){

        JSONObject jsonParam = new JSONObject();

        try {

            jsonParam.put("id", sId);
            jsonParam.put("pw", sPw);
            jsonParam.put("name", sName);
            jsonParam.put("mobile", sMobile);
            jsonParam.put("email", sEmail);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonParam;
    }

}
