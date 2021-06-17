package com.rsy0921.mancoreman.common;

import android.net.Uri;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ComUtil {


    //-----------------------------------------------------------------------------------
    // 난수 만드는 함수
    //-----------------------------------------------------------------------------------
    public static String Random_Text(){

        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 32; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();
    }

    //-----------------------------------------------------------------------------------
    // 난수 만드는 함수(자릿수, 유형)
    // n : 자릿수 , c : 0(소문자), 1(대문자), 2(숫자)
    //-----------------------------------------------------------------------------------
    public String Random_Type(int n, int c){

        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < n; i++) {
            int rIndex =  c;

            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) (rnd.nextInt(26) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) (rnd.nextInt(26) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();
    }

    //-----------------------------------------------------------------------------------
    // String null 체크
    //-----------------------------------------------------------------------------------
    public static String defaultString(final String str) {
        return str == null ? "" : str;
    }

    //-----------------------------------------------------------------------------------
    // Object 를 string 으로 변환
    //-----------------------------------------------------------------------------------
    public String defaultString(Object src){
        return defaultString(src, null);
    }

    //-----------------------------------------------------------------------------------
    // Object 를 string 으로 변환
    //-----------------------------------------------------------------------------------
    public String defaultString(Object src, final String defaultStr){
        if(src != null){
            if(src instanceof String){
                return (String)src;
            }else{
                return String.valueOf(src);
            }
        }else{
            return (defaultStr == null) ? "" : defaultStr;
        }
    }

    //-----------------------------------------------------------------------------------
    // Map 데이터를 url query 문자열로 변환(url encoding 포함)
    //-----------------------------------------------------------------------------------
    public static String convertMapToQuerystring(Map<?, ?> paramMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> e : paramMap.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s", urlEncode(defaultString((String) e.getKey())), urlEncode(defaultString((String) e.getValue()))));
        }
        return sb.toString();
    }

    //-----------------------------------------------------------------------------------
    // URL encoding wrapper
    //-----------------------------------------------------------------------------------
    public static String urlEncode(String src){

        String ret = "";
        try {
            ret = URLEncoder.encode(defaultString(src), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    //-----------------------------------------------------------------------------------
    // URL encoding wrapper
    //-----------------------------------------------------------------------------------
    public static String getUrlEncode(String pParam){

        String sResult = "";
        try {
            sResult = URLEncoder.encode(pParam, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sResult;
    }

    //-----------------------------------------------------------------------------------
    // URL encoding wrapper
    //-----------------------------------------------------------------------------------
    public String getURLDecode(String pParam){

        String sResult = null;

        try {
            sResult = URLDecoder.decode(pParam, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sResult;
    }

    //-----------------------------------------------------------------------------------
    // 은행고유거래번호 생성
    //-----------------------------------------------------------------------------------
    // 이용기관코드(10자리) + 생성주체구분코드(“U”)*+ 이용기관 부여번호(9자리)
    // 리턴값 : T991626180U(고정) + HHmmssSSS(시분초)
    //-----------------------------------------------------------------------------------
    public static String bankTranId(){

        long now = System.currentTimeMillis();  // 현재시간을 msec 으로 구한다.
        Date date = new Date(now);              // 현재시간을 date 변수에 저장한다.
        SimpleDateFormat sdfNow = new SimpleDateFormat("HHmmssSSS");    // 시간을 나타냇 포맷을 정한다
        String formatDate = "T991626180U"+sdfNow.format(date);    // nowDate 변수에 값을 저장한다.

        return formatDate;
    }


    //-----------------------------------------------------------------------------------
    // 금액에 콤마를 붙여서 리턴
    //-----------------------------------------------------------------------------------
    public String makeComma(String pAmt){

        int iAmt = Integer.parseInt(pAmt);
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(iAmt);
    }


    //-----------------------------------------------------------------------------------
    //년월일 날짜를 문자로 받았을 경우 점을 붙여서 리턴
    //-----------------------------------------------------------------------------------
    public String transDate(String pDate) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyymmdd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy.mm.dd");

        Date date = format.parse(pDate);
        String sDate = format1.format(date);

        return sDate;
    }


    //---------------------------------------------------------------------------------------
    // http 통신할때 url에 사용하는 파라미터를 생성해서 RETURN
    //---------------------------------------------------------------------------------------
    // get -> http://www.test.com?id=aa&pw=1111
    // post -> id=aa&pw=1111
    //---------------------------------------------------------------------------------------
    public String getUrlParam(String pUrl, HashMap<String, String> pParam){

        String sKey = "";
        String sValue = "";

        Uri.Builder builder;

        //파라미터에 url이 없으면 파라미터만 생성
        if(pUrl == null || "".equals(pUrl.trim())){
            builder = new Uri.Builder();
        }else{
            builder = Uri.parse(pUrl).buildUpon();
        }

        for (Map.Entry<String, String> entry : pParam.entrySet()) {

            sValue = entry.getValue();
            sKey = entry.getKey();

            // 파라미터를 만들어줌.
            builder.appendQueryParameter(sKey, sValue);
        }

        return getURLDecode(builder.build().toString());
    }


    public String getJsonUrlParam(HashMap<String, Object> pParam){

        String sJson = "";

        try{

            JSONObject jsonObject = new JSONObject();

            for(Map.Entry<String, Object> mapParam : pParam.entrySet()) {

                jsonObject.put(mapParam.getKey(), mapParam.getValue().toString());
            }

            sJson = jsonObject.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sJson;
    }

    // str : 원래 문자, len : 채울 자릿수, setChar : 채울 문자
    public static String setLpad(String str, int len, String setChar){
        String result = "";

        StringBuilder sdAddChar = new StringBuilder();
        for(int i = str.length(); i< len; i++){
            sdAddChar.append(setChar);
        }
        result = sdAddChar+str;

        return result;
    }

}
