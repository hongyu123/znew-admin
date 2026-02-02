package com.hfw.model.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * JDK11 HttpClient 请求工具类
 */
@Slf4j
public class HttpUtil {
    public static final int TIMEOUT = 60;

    private Map<String,String> headers = new HashMap<>();
    private boolean session = false;
    private Map<String,Map<String,Cookie>> cookies = new HashMap<>();
    private int retry_count;
    private int retry_sleep = 5000;
    private final HttpClient httpClient;

    public HttpUtil(ProxySelector proxySelector, Authenticator authenticator, HttpClient.Version version){
        HttpClient.Builder clientBuilder = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(HttpUtil.TIMEOUT));
        if(version!=null){
            clientBuilder.version(version);
        }
        if(proxySelector!=null){
            //如果您的jdk版本在Java 8 Update 111以上,需要增加以下代码,设置https允许隧道代理
            //System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "false");
            //System.setProperty("jdk.http.auth.proxying.disabledSchemes", "false");
            clientBuilder.proxy(proxySelector);
        }
        if(authenticator!=null){
            clientBuilder.authenticator(authenticator);
        }
        httpClient = clientBuilder.build();
    }
    public HttpUtil(){
        this(null, null,null);
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

    public Map<String,Map<String,Cookie>> getCookies(){
        return this.cookies;
    }
    private void setCookie(HttpHeaders headers, String domain){
        if(!session){
            return ;
        }
        List<String> cookieStrList = headers.allValues("Set-Cookie");
        if(cookieStrList==null || cookieStrList.isEmpty()){
            return ;
        }
        //JSESSIONID=8848A212F061F833F6B9D735653D48F5; Path=/; HttpOnly
        for (String cookieStr : cookieStrList) {
            Cookie cookie = new Cookie(cookieStr, domain);
            Map<String,Cookie> cookieMap = cookies.computeIfAbsent(cookie.getDomain(), k -> new HashMap<>());
            cookieMap.put(cookie.getName(), cookie);
        }
    }
    private String getCookie(String domain, String path){
        Collection<Cookie> cookieList = new ArrayList<>();
        String subDomain = domain;
        int index = subDomain.indexOf(".");
        while (index>-1){
            Map<String,Cookie> map = cookies.get(subDomain);
            if(map!=null && !map.isEmpty()){
                cookieList.addAll(map.values());
            }
            subDomain = subDomain.substring(index+1);
            index = subDomain.indexOf(".");
        }
        return cookieList.stream().filter(cookie -> path.startsWith(cookie.getPath()))
                .map(Cookie::getCookie)
                .collect(Collectors.joining("; "));
    }

    public static HttpUtil init(){
        return new HttpUtil();
    }
    public static HttpUtil init(ProxySelector proxySelector, Authenticator authenticator, HttpClient.Version version){
        return new HttpUtil(proxySelector, authenticator, version);
    }
    public static HttpUtil session(){
        return session(null,null,null, null);
    }
    public static HttpUtil session(ProxySelector proxySelector, Authenticator authenticator, HttpClient.Version version){
        return session(proxySelector,authenticator,version, null);
    }
    public static HttpUtil session(Map<String,Map<String,Cookie>> cookies){
        return session(null,null,null, cookies);
    }
    public static HttpUtil session(ProxySelector proxySelector, Authenticator authenticator, HttpClient.Version version, Map<String,Map<String,Cookie>> cookies){
        HttpUtil httpUtil = new HttpUtil(proxySelector, authenticator, version);
        httpUtil.session = true;
        if(cookies!=null){
            httpUtil.cookies = cookies;
        }
        return httpUtil;
    }

    public <T> HttpResponse<T> get(String url, HttpResponse.BodyHandler<T> responseBodyHandler, String... headerArr) throws Exception {
        URI uri = URI.create(url);
        // 创建 HttpRequest 请求
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .timeout(Duration.ofSeconds(HttpUtil.TIMEOUT));
        if(session){
            String cookie = this.getCookie(uri.getHost(), uri.getPath());
            requestBuilder.header("Cookie", cookie);
        }
        if(this.headers!=null && !headers.isEmpty()){
            Set<Map.Entry<String, String>> entries = this.headers.entrySet();
            for(Map.Entry<String, String> entry : entries){
                requestBuilder.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if(headerArr!=null && headerArr.length>0){
            if (headerArr.length % 2 != 0) {
                throw new IllegalArgumentException("wrong number of parameters : "+headerArr.length);
            }
            for (int i = 0; i < headerArr.length; i += 2) {
                String name  = headerArr[i];
                String value = headerArr[i + 1];
                requestBuilder.setHeader(name, value);
            }
        }

        // 发送同步请求并获取响应
        for (int i = 1; i < this.retry_count; i++) {
            try {
                HttpResponse<T> response = this.httpClient.send(requestBuilder.build(), responseBodyHandler);
                this.setCookie(response.headers(), uri.getHost());
                return response;
            }catch (IOException e){
                log.error("第{}次 {} error,{},{}", i,url, e.getClass(), e.getMessage());
                Thread.sleep(this.retry_sleep);
            }
        }
        HttpResponse<T> response = this.httpClient.send(requestBuilder.build(), responseBodyHandler);
        this.setCookie(response.headers(), uri.getHost());
        return response;
    }
    public HttpResponse<String> get_str(String url, String... headerArr) throws Exception{
        return this.get(url, HttpResponse.BodyHandlers.ofString(), headerArr);
    }
    public HttpResponse<byte[]> get_byte(String url, String... headerArr) throws Exception{
        return this.get(url, HttpResponse.BodyHandlers.ofByteArray(), headerArr);
    }

    public <T> HttpResponse<T> post(String url, HttpRequest.BodyPublisher bodyPublisher, HttpResponse.BodyHandler<T> responseBodyHandler, String... headerArr) throws Exception {
        URI uri = URI.create(url);
        // 创建 HttpRequest 请求
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .POST(bodyPublisher)
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(HttpUtil.TIMEOUT));
        if(session){
            String cookie = this.getCookie(uri.getHost(), uri.getPath());
            requestBuilder.header("Cookie", cookie);
        }
        if(this.headers!=null){
            Set<Map.Entry<String, String>> entries = this.headers.entrySet();
            for(Map.Entry<String, String> entry : entries){
                requestBuilder.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if(headerArr!=null && headerArr.length>0){
            if (headerArr.length % 2 != 0) {
                throw new IllegalArgumentException("wrong number of parameters : "+headerArr.length);
            }
            for (int i = 0; i < headerArr.length; i += 2) {
                String name  = headerArr[i];
                String value = headerArr[i + 1];
                requestBuilder.setHeader(name, value);
            }
        }

        // 发送同步请求并获取响应
        for (int i = 1; i < this.retry_count; i++) {
            try {
                HttpResponse<T> response = this.httpClient.send(requestBuilder.build(), responseBodyHandler);
                this.setCookie(response.headers(), uri.getHost());
                return response;
            }catch (IOException e){
                log.error("第{}次 {} error,{},{}", i,url, e.getClass(), e.getMessage());
                Thread.sleep(this.retry_sleep);
            }
        }
        HttpResponse<T> response = this.httpClient.send(requestBuilder.build(), responseBodyHandler);
        this.setCookie(response.headers(), uri.getHost());
        return response;
    }
    public HttpResponse<String> post(String url, HttpRequest.BodyPublisher bodyPublisher, String... headerArr) throws Exception{
        return this.post(url, bodyPublisher, HttpResponse.BodyHandlers.ofString(), headerArr);
    }
    public HttpResponse<String> post(String url, String body, String... headerArr) throws Exception{
        return this.post(url, HttpRequest.BodyPublishers.ofString(body), HttpResponse.BodyHandlers.ofString(), headerArr);
    }

    public <T> HttpResponse<T> formData(String url, FormData formData, HttpResponse.BodyHandler<T> responseBodyHandler) throws Exception {
        Map<String, Object> params = formData.params;
        byte[] fileBytes = formData.fileBytes;
        String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
        String bodyParams = "";
        if(params!=null && !params.isEmpty()){
            bodyParams =  params.entrySet().stream().map(entry->
                            "--%s\r\nContent-Disposition: form-data; name=\"%s\"\r\n\r\n%s".formatted(boundary, entry.getKey(),entry.getValue()))
                    .collect(Collectors.joining("\r\n"));
        }
        String bodyStart = "--%s\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\nContent-Type: %s\r\n\r\n".formatted(boundary, formData.name, formData.filename, formData.contentType);
        String bodyEnd = bodyParams.isBlank() ?"\r\n--%s--\r\n".formatted(boundary) :"\r\n%s\r\n--%s--\r\n".formatted(bodyParams, boundary);

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
    public static String formToBody(Map<String, String> form, Charset charset) throws UnsupportedEncodingException {
        StringJoiner stringJoiner = new StringJoiner("&");
        Set<Map.Entry<String, String>> entries = form.entrySet();
        for(Map.Entry<String, String> entry : entries){
            stringJoiner.add(entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), charset));
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
    public static String postForm(String url, Map<String,String> form, Charset charset) throws Exception {
        return HttpUtil.postForm(url, HttpUtil.formToBody(form, charset) );
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

    @Getter
    @Setter
    public static class Cookie{
        private String name;
        private String cookie;
        private String domain;
        private String path;
        public Cookie(){
        }
        public Cookie(String cookieStr, String domain){
            String[] cookieArr = cookieStr.split(";");
            this.cookie = cookieArr[0];
            this.name = this.cookie.split("=")[0];
            for (int i = 1; i < cookieArr.length; i++) {
                String[] attrArr = cookieArr[i].split("=");
                String attr = attrArr[0].trim();
                if("Path".equalsIgnoreCase(attr)){
                    this.path = attrArr[1].trim();
                }else if("Domain".equalsIgnoreCase(attr)){
                    this.domain = attrArr[1].trim();
                }
            }
            if(this.path==null || this.path.isBlank()){
                this.path = "/";
            }
            if(this.domain==null || this.domain.isBlank()){
                this.domain = domain;
            }
        }
    }

}
