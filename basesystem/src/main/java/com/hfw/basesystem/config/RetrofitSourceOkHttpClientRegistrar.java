package com.hfw.basesystem.config;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitScan;
import com.github.lianjiatech.retrofit.spring.boot.core.SourceOkHttpClientRegistrar;
import com.github.lianjiatech.retrofit.spring.boot.core.SourceOkHttpClientRegistry;
import com.hfw.common.support.TrustCert;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

import javax.net.ssl.X509TrustManager;
import java.time.Duration;

/**
 * @author zyh
 * @date 2022-10-02
 */
@RetrofitScan("com.hfw.demo.retrofit")
@Component
@Slf4j
public class RetrofitSourceOkHttpClientRegistrar implements SourceOkHttpClientRegistrar {

    @Override
    public void register(SourceOkHttpClientRegistry registry) {
        X509TrustManager trustManager = TrustCert.trustManager();
        // 添加sourceOkHttpClient
        registry.register("retrofitSourceOkHttpClient", new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(5))
                .writeTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(5))
                .addInterceptor(chain -> {
                    //log.info("============use testSourceOkHttpClient=============");
                    return chain.proceed(chain.request());
                })
                .sslSocketFactory(TrustCert.createSSLSocketFactory(trustManager), trustManager)
                .hostnameVerifier(TrustCert.trustAllHostnameVerifier())
                .build());
    }

}