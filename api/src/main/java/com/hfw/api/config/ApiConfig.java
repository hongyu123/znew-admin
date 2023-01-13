package com.hfw.api.config;

import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.service.impl.RedisAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * admin配置
 * @author farkle
 * @date 2022-12-16
 */
@Configuration
public class ApiConfig {

    @Autowired
    private RedisUtil redisUtil;
    //redis认证
    @Bean
    public RedisAuth redisAuth(){
        RedisAuth redisAuth = new RedisAuth();
        redisAuth.setMax_login(2);
        redisAuth.setExpire(24*60*60);//24小时
        redisAuth.setRedis_user_key("appuser:");
        redisAuth.setRedis_token_key("appuser_token:");
        redisAuth.setRedisUtil(redisUtil);
        return redisAuth;
    }

}
