package com.hfw.common.support;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author zyh
 * @date 2022-10-02
 */
public class TrustCert {

    public static HostnameVerifier trustAllHostnameVerifier(){
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    public static X509TrustManager trustManager(){
        return new X509TrustManager(){
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static SSLSocketFactory createSSLSocketFactory(TrustManager trustManager) {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,  new TrustManager[] { trustManager }, new SecureRandom());
            SSLSocketFactory ssfFactory = sc.getSocketFactory();
            return ssfFactory;
        }catch (Exception e){
            return null;
        }
    }

}
