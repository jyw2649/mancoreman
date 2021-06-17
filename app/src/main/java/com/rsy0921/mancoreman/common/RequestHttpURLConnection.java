package com.rsy0921.mancoreman.common;

import android.telecom.Call;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RequestHttpURLConnection {

    ComUtil comUtil = new ComUtil();

    //----------------------------------------------------------------------------------------
    // DB 서버 통신
    //----------------------------------------------------------------------------------------
    // pMethod : POST
    //----------------------------------------------------------------------------------------
    public String requestDBServer(String pUrl, HashMap<String, String> pParams) {

        System.out.println("------------------------------------------------------");
        System.out.println("requestDBServer || Start");
        System.out.println("------------------------------------------------------");
        System.out.println("pUrl -> " + pUrl);
        System.out.println("pParams -> " + pParams);
        System.out.println("------------------------------------------------------");

        String sResult = "";

        OkHttpClient client = getUnsafeOkHttpClient();

        //url 생성
        String sUrl = comUtil.getURLDecode(pUrl);

        FormBody.Builder builder = new FormBody.Builder();

        // 파라미터 생성
        for ( Map.Entry<String, String> entry : pParams.entrySet() ) {
            builder.add( entry.getKey(), entry.getValue() );
        }

        RequestBody formBody = builder.build();
        Request request = new Request.Builder().url(sUrl).post(formBody).build();
        //-------------------------------------------------------------------------------------------------------------------


        try {
            // execute 는 동기, enqueue는 비동기(콜백이 필요함)
            Response resp = client.newCall(request).execute();
            sResult = resp.body().string();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("------------------------------------------------------");
        System.out.println("requestDBServer sResult -> " + sResult);
        System.out.println("------------------------------------------------------");

        System.out.println("------------------------------------------------------");
        System.out.println("requestDBServer || End");
        System.out.println("------------------------------------------------------");

        return sResult;

    }

    //https 인증서 처리.
    private static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}



