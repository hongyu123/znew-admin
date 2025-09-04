package com.hfw.model.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JDK11 HttpClient 请求工具类
 */
@Slf4j
public class HttpUtil {
    public static final int TIMEOUT = 30;

    private String cookie;
    private Map<String,String> headers = new HashMap<>();
    private int retry_count;
    private int retry_sleep = 5000;

    private HttpClient.Version version = HttpClient.Version.HTTP_2;
    private ProxySelector proxySelector;
    private Authenticator authenticator;

    public HttpUtil cookie(String cookie) {
        this.cookie = cookie;
        return this;
    }

    public HttpUtil setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
    public HttpUtil setHeader(String name, String value){
        headers.put(name,value);
        return this;
    }

    public HttpUtil retryCount(int retry_count) {
        this.retry_count = retry_count;
        return this;
    }
    public HttpUtil retrySleep(int retry_sleep) {
        this.retry_sleep = retry_sleep;
        return this;
    }

    public HttpUtil version(HttpClient.Version version){
        this.version = version;
        return this;
    }
    public HttpUtil proxySelector(ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
        return this;
    }
    public HttpUtil authenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    public static HttpUtil init(){
        return new HttpUtil();
    }
    public static HttpUtil init(HttpClient.Version version, ProxySelector proxySelector, Authenticator authenticator, int retry_count){
        return new HttpUtil().version(version).proxySelector(proxySelector).authenticator(authenticator).retryCount(retry_count);
    }

    public <T> HttpResponse<T> get(String url, HttpResponse.BodyHandler<T> responseBodyHandler) throws Exception {
        // 创建 HttpClient 实例
        HttpClient.Builder clientBuilder = HttpClient.newBuilder()
                .version(this.version)
                .connectTimeout(Duration.ofSeconds(HttpUtil.TIMEOUT));
        if(this.proxySelector!=null){
            //如果您的jdk版本在Java 8 Update 111以上,需要增加以下代码,设置https允许隧道代理
            //System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "false");
            //System.setProperty("jdk.http.auth.proxying.disabledSchemes", "false");
            clientBuilder.proxy(this.proxySelector);
        }
        if(this.authenticator!=null){
            clientBuilder.authenticator(this.authenticator);
        }
        // 创建 HttpRequest 请求
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(HttpUtil.TIMEOUT));
        if(cookie!=null && !cookie.isBlank()){
            requestBuilder.header("Cookie", cookie);
        }
        if(this.headers!=null && !headers.isEmpty()){
            Set<Map.Entry<String, String>> entries = this.headers.entrySet();
            for(Map.Entry<String, String> entry : entries){
                requestBuilder.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 发送同步请求并获取响应
        for (int i = 1; i < this.retry_count; i++) {
            try {
                return clientBuilder.build().send(requestBuilder.build(), responseBodyHandler);
            }catch (IOException e){
                log.error("第{}次 {} error,{},{}", i,url, e.getClass(), e.getMessage());
                Thread.sleep(this.retry_sleep);
            }
        }
        return clientBuilder.build().send(requestBuilder.build(), responseBodyHandler);
    }
    public HttpResponse<String> get_str(String url) throws Exception{
        return this.get(url, HttpResponse.BodyHandlers.ofString());
    }
    public HttpResponse<byte[]> get_byte(String url) throws Exception{
        return this.get(url, HttpResponse.BodyHandlers.ofByteArray());
    }

    public <T> HttpResponse<T> post(String url, HttpRequest.BodyPublisher bodyPublisher, HttpResponse.BodyHandler<T> responseBodyHandler) throws Exception {
        // 创建 HttpClient 实例
        HttpClient.Builder clientBuilder = HttpClient.newBuilder()
                .version(this.version)
                .connectTimeout(Duration.ofSeconds(HttpUtil.TIMEOUT));
        if(this.proxySelector!=null){
            //如果您的jdk版本在Java 8 Update 111以上,需要增加以下代码,设置https允许隧道代理
            //System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "false");
            //System.setProperty("jdk.http.auth.proxying.disabledSchemes", "false");
            clientBuilder.proxy(this.proxySelector);
        }
        if(this.authenticator!=null){
            clientBuilder.authenticator(this.authenticator);
        }
        // 创建 HttpRequest 请求
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .POST(bodyPublisher)
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(HttpUtil.TIMEOUT));
        if(cookie!=null && !cookie.isBlank()){
            requestBuilder.header("Cookie", cookie);
        }
        if(this.headers!=null){
            Set<Map.Entry<String, String>> entries = this.headers.entrySet();
            for(Map.Entry<String, String> entry : entries){
                requestBuilder.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 发送同步请求并获取响应
        for (int i = 1; i < this.retry_count; i++) {
            try {
                return clientBuilder.build().send(requestBuilder.build(), responseBodyHandler);
            }catch (IOException e){
                log.error("第{}次 {} error,{},{}", i,url, e.getClass(), e.getMessage());
                Thread.sleep(this.retry_sleep);
            }
        }
        return clientBuilder.build().send(requestBuilder.build(), responseBodyHandler);
    }
    public HttpResponse<String> post(String url, HttpRequest.BodyPublisher bodyPublisher) throws Exception{
        return this.post(url, bodyPublisher, HttpResponse.BodyHandlers.ofString());
    }
    public HttpResponse<String> post(String url, String body) throws Exception{
        return this.post(url, HttpRequest.BodyPublishers.ofString(body), HttpResponse.BodyHandlers.ofString());
    }

    public <T> HttpResponse<T> formData(String url, FormData formData, HttpResponse.BodyHandler<T> responseBodyHandler) throws Exception {
        Map<String, Object> params = formData.params;
        byte[] fileBytes = formData.fileBytes;
        String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
        String bodyParams = "";
        if(params!=null && !params.isEmpty()){
            bodyParams =  params.entrySet().stream().map(entry->
                            String.format("--%s\r\nContent-Disposition: form-data; name=\"%s\"\r\n\r\n%s", boundary, entry.getKey(),entry.getValue()))
                    .collect(Collectors.joining("\r\n"));
        }
        String bodyStart = String.format("--%s\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\nContent-Type: %s\r\n\r\n", boundary, formData.name, formData.filename, formData.contentType);
        String bodyEnd = bodyParams.isBlank() ?String.format("\r\n--%s--\r\n", boundary) :String.format("\r\n%s\r\n--%s--\r\n", bodyParams, boundary);

        byte[] bodyStartBytes = bodyStart.getBytes(StandardCharsets.UTF_8);
        byte[] bodyEndBytes = bodyEnd.getBytes(StandardCharsets.UTF_8);
        byte[] requestBody = new byte[bodyStartBytes.length + fileBytes.length + bodyEndBytes.length];
        System.arraycopy(bodyStartBytes, 0, requestBody, 0, bodyStartBytes.length);
        System.arraycopy(fileBytes, 0, requestBody, bodyStartBytes.length, fileBytes.length);
        System.arraycopy(bodyEndBytes, 0, requestBody, bodyStartBytes.length + fileBytes.length, bodyEndBytes.length);
        this.setHeader("Content-Type", "multipart/form-data; boundary=" + boundary);
        return this.post(url, HttpRequest.BodyPublishers.ofByteArray(requestBody), responseBodyHandler);
    }
    public HttpResponse<String> formData_str(String url, FormData formData) throws Exception{
        return this.formData(url, formData, HttpResponse.BodyHandlers.ofString());
    }

    public static String get(String url) throws Exception {
        return HttpUtil.init().get(url, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static String formToBody(Map<String, String> form){
        return form.entrySet().stream().map(item->item.getKey()+"="+item.getValue()).collect(Collectors.joining("&"));
    }
    public static String formToBody(Map<String, String> form, String enc) throws UnsupportedEncodingException {
        StringJoiner stringJoiner = new StringJoiner("&");
        Set<Map.Entry<String, String>> entries = form.entrySet();
        for(Map.Entry<String, String> entry : entries){
            stringJoiner.add(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), enc));
        }
        return stringJoiner.toString();
    }

    public static String postJson(String url, String body) throws Exception {
        return HttpUtil.init().setHeader("Content-Type","application/json").post(url,body).body();
    }
    public static String postForm(String url, String body) throws Exception {
        return HttpUtil.init().setHeader("Content-Type","application/x-www-form-urlencoded").post(url,body).body();
    }
    public static String postForm(String url, Map<String,String> form) throws Exception {
        return HttpUtil.postForm(url, HttpUtil.formToBody(form) );
    }
    public static String postForm(String url, Map<String,String> form, String enc) throws Exception {
        return HttpUtil.postForm(url, HttpUtil.formToBody(form, enc) );
    }

    public static String formData(String url, FormData formData) throws Exception {
        return HttpUtil.init().formData_str(url, formData).body();
    }

    public static class FormData{
        //参数名
        private String name;
        //文件名
        private String filename;
        private String contentType = "application/octet-stream";
        private Map<String,Object> params = new HashMap<>();
        private byte[] fileBytes;
        public FormData (String name, String filename, Map<String,Object> params, byte[] fileBytes){
            this.name = name;
            this.filename = filename;
            this.params = params;
            this.fileBytes = fileBytes;
        }
        public FormData (String name, String filename, byte[] fileBytes){
            this.name = name;
            this.filename = filename;
            this.fileBytes = fileBytes;
        }
        public FormData (String filename, byte[] fileBytes){
            this.name = "file";
            this.filename = filename;
            this.fileBytes = fileBytes;
        }
        public FormData (byte[] fileBytes){
            this.name = "file";
            this.filename = UUID.randomUUID().toString();
            this.fileBytes = fileBytes;
        }
        public FormData addParam(String key, String value){
            this.params.put(key, value);
            return this;
        }
        public void setContentType(String contentType){
            this.contentType = contentType;
        }
    }

}
