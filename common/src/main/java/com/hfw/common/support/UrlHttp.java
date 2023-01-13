package com.hfw.common.support;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * JDK自带URL发起http请求
 * @author farkle
 * @date 2022-04-06
 */
public class UrlHttp {
    public static final int timeout = 30*1000;

    public static String get(String url) throws Exception {
        BufferedReader in = null;
        try{
            URL realUrl = new URL(url);
            if(url.startsWith("https")){
                httpsCerts();
            }
            URLConnection conn = realUrl.openConnection();
            // 设置请求头
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 6.0.1; Moto G (4)) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Mobile Safari/537.36");
            conn.setConnectTimeout(timeout);
            conn.connect();
            // 获取所有响应头字段 conn.getHeaderFields();
            in = new BufferedReader( new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line = null;
            while ((line = in.readLine()) != null){
                result.append(line).append("\n");
            }
            return result.toString();
        }finally{
            if (in != null){
                in.close();
            }
        }
    }

    public static String post(String url,  Map<String,String> params) throws Exception{
        StringBuilder sb = new StringBuilder();
        for(String param : params.keySet()){
            sb.append(param+"="+params.get(param)+"&");
        }
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        return post(url, sb.toString(), headers);
    }
    public static String postBody(String url, String body) throws Exception{
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return post(url,body, headers);
    }
    private static String post(String url, String body, Map<String,String> headers) throws Exception {
        OutputStream os = null;
        BufferedReader reader = null;
        try{
            URL realUrl = new URL(url);
            if(url.startsWith("https")){
                httpsCerts();
            }
            URLConnection conn = realUrl.openConnection();
            // 设置请求头
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 6.0.1; Moto G (4)) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Mobile Safari/537.36");
            for (String header : headers.keySet()){
                conn.setRequestProperty(header, headers.get(header));
            }
            //conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(timeout);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            os = conn.getOutputStream();
            os.write(body.getBytes("utf-8"));
            os.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            StringBuilder result = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString();
        }finally {
            if(os!=null){
                os.close();
            }
            if(reader!=null){
                reader.close();
            }
        }
    }

    /**
     * https忽略证书处理
     */
    private static void httpsCerts() throws NoSuchProviderException, NoSuchAlgorithmException, KeyManagementException {
        TrustManager trustManager = new X509TrustManager(){
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };

        SSLContext sslcontext = SSLContext.getInstance("SSL","SunJSSE");
        sslcontext.init(null, new TrustManager[]{trustManager}, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
    }

}
