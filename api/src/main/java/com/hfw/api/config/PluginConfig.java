package com.hfw.api.config;

import com.hfw.plugins.apple.Apple;
import com.hfw.plugins.weixin.WeixinSNS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方插件配置
 * @author farkle
 * @date 2022-06-14
 */
@Configuration
public class PluginConfig {

    @Bean
    @ConfigurationProperties("weixin")
    public WeixinSNS weixinSNS() {
        WeixinSNS sns = new WeixinSNS();
        return sns;
    }

    @Value("${spring.profiles.active}")
    private String profiles;

    @Bean
    public Apple apple() {
        Apple apple = new Apple();
        apple.setEnv(profiles);
        return apple;
    }
}
