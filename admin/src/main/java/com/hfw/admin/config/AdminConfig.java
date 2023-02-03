package com.hfw.admin.config;

import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.service.RedisAuthService;
import javax.annotation.Resource;

import com.hfw.basesystem.service.impl.RedisAuthServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * admin配置
 * @author farkle
 * @date 2022-12-16
 */
@Configuration
public class AdminConfig {

    @Resource
    private RedisUtil redisUtil;

    /*public RedisAuth redisAuth(){
        RedisAuth redisAuth = new RedisAuth();
        redisAuth.setMax_login(2);
        redisAuth.setExpire(30*60);//30分钟
        redisAuth.setRedis_user_key("sysuser:");
        redisAuth.setRedis_token_key("sysuser_token:");
        redisAuth.setRedisUtil(redisUtil);
        return redisAuth;
    }*/

    //redis认证
    @Bean
    public RedisAuthService redisAuthService(){
        RedisAuthServiceImpl redisAuthService = new RedisAuthServiceImpl();
        redisAuthService.setMax_login(2);
        redisAuthService.setExpire(30*60);//30分钟
        redisAuthService.setRedisUtil(redisUtil);
        redisAuthService.setToken_key_prefix("sysuser_token:");
        redisAuthService.setUser_token_list_prefix("sysuser_token_list:");
        return redisAuthService;
    }

}
