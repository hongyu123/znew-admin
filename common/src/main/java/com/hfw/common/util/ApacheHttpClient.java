package com.hfw.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * apache-Http客户端封装
 * @author zyh
 * @date 2022-04-06
 */
public class ApacheHttpClient {

    private CloseableHttpClient httpClient;
    private CloseableHttpClient httpsClient;
    private int timeout = 5*1000;
    /**
     * get请求
     * @param url
     * @return
     */
    public String get(String url) throws Exception{
        return this.get(url, null,null);
    }
    /**
     * get 请求
     * @param url
     * @param params 参数
     * @return
     */
    public String get(String url, Map<String,String> params) throws Exception{
        return this.get(url,params,null);
    }
    public String get(String url, Map<String,String> params, Map<String,String> headers) throws Exception {
        CloseableHttpResponse response = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if(params!=null && params.size()>0) {
                for (Map.Entry<String, String> e : params.entrySet()) {
                    uriBuilder.addParameter(e.getKey(), e.getValue());
                }
            }
            CloseableHttpClient client = wrapClient(url);
            HttpGet get = new HttpGet(uriBuilder.build());
            //设置请求头
            get.setHeader("Accept", "*/*");
            get.setHeader("Connection", "keep-alive");
            get.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0.1; Moto G (4)) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Mobile Safari/537.36");

            if(headers!=null && headers.size()>0) {
                for (Map.Entry<String, String> e : headers.entrySet()) {
                    get.setHeader(e.getKey(), e.getValue());
                }
            }
            RequestConfig config = RequestConfig.custom().setConnectTimeout(this.timeout).setSocketTimeout(this.timeout).build();
            get.setConfig(config);
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(httpEntity,"utf-8");
            }
            throw new RuntimeException("请求错误码:"+response.getStatusLine().getStatusCode());
        }finally {
            if(response!=null){
                response.close();
            }
        }
    }

    /**
     * post请求
     * @param url
     * @return
     */
    public String post(String url) throws Exception{
        return this.post(url, null, null, null);
    }
    /**
     * post请求
     * @param url
     * @param params 参数
     * @return
     */
    public String post(String url, Map<String,String> params) throws Exception{
        return this.post(url, null, params, null);
    }
    /**
     * post发送json数据
     */
    public String postBody(String url, String body) throws Exception{
        Map<String,String> headrs = new HashMap<>();
        headrs.put("Content-Type","application/json; charset=utf-8");
        return this.post(url, headrs, null, body);
    }

    /**
     * post请求
     * @param url
     * @param headers 请求头
     * @param params Map参数
     * @param body 发送json数据
     * @return
     */
    private String post(String url, Map<String,String> headers, Map<String,String> params, String body) throws Exception {
        CloseableHttpResponse response = null;
        try {
            CloseableHttpClient client = wrapClient(url);
            HttpPost post = new HttpPost(url);
            //设置请求头
            if(headers!=null && headers.size()>0) {
                for (Map.Entry<String, String> e : headers.entrySet()) {
                    post.setHeader(e.getKey(), e.getValue());
                }
            }
            if(params!=null && params.size()>0) {
                //设置表单参数
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> e : params.entrySet()) {
                    paramList.add(new BasicNameValuePair(e.getKey(), e.getValue()));
                }
                post.setEntity(new UrlEncodedFormEntity(paramList, "utf-8"));
            }
            /*if(paramStr!=null && paramStr.trim().length()>0) {
                StringEntity paramEntity = new StringEntity(paramStr, "utf-8");
                paramEntity.setContentType("application/x-www-form-urlencoded; charset=utf-8");//表单提交
                //paramEntity.setContentType("application/json; charset=utf-8"); //json提交
                post.setEntity(paramEntity);
            }*/
            if(body!=null && body.trim().length()>0) {
                StringEntity paramEntity = new StringEntity(body, "utf-8");
                //paramEntity.setContentType("application/x-www-form-urlencoded; charset=utf-8");//表单提交
                //paramEntity.setContentType("application/json; charset=utf-8"); //json提交
                post.setEntity(paramEntity);
            }

            post.setHeader("Accept", "*/*");
            post.setHeader("Connection", "keep-alive");
            post.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0.1; Moto G (4)) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Mobile Safari/537.36");
            RequestConfig config = RequestConfig.custom().setConnectTimeout(this.timeout).setSocketTimeout(this.timeout).build();
            post.setConfig(config);
            response = client.execute(post);
            HttpEntity httpEntity = response.getEntity();
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(httpEntity,"utf-8");
            }
            throw new RuntimeException("请求错误码:"+response.getStatusLine().getStatusCode());
        }finally {
            if(response!=null){
                response.close();
            }
        }
    }

    private CloseableHttpClient wrapClient(String url) throws Exception{
        if (url.startsWith("https://")) {
            if(this.httpsClient==null){
                //使用 loadTrustMaterial() 方法实现一个信任策略，信任所有证书
                SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    // 信任所有
                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return true;
                    }
                }).build();
                //NoopHostnameVerifier类:  作为主机名验证工具，实质上关闭了主机名验证，它接受任何
                //有效的SSL会话并匹配到目标主机。
                //HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
                this.httpsClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            }
            return this.httpsClient;
        }
        if(this.httpClient==null){
            this.httpClient = HttpClients.createDefault();
        }
        return this.httpClient;
    }

    public void close() throws IOException {
        if(this.httpClient!=null){
            this.httpClient.close();
        }
        if(this.httpsClient!=null){
            this.httpsClient.close();
        }
    }
}
