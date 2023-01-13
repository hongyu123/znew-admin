package com.hfw.admin.config;

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
public class AdminConfig {

    @Autowired
    private RedisUtil redisUtil;
    //redis认证
    @Bean
    public RedisAuth redisAuth(){
        RedisAuth redisAuth = new RedisAuth();
        redisAuth.setMax_login(2);
        redisAuth.setExpire(30*60);//30分钟
        redisAuth.setRedis_user_key("sysuser:");
        redisAuth.setRedis_token_key("sysuser_token:");
        redisAuth.setRedisUtil(redisUtil);
        return redisAuth;
    }

}
