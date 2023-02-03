package com.hfw.api.config;

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
public class ApiConfig {

    @Resource
    private RedisUtil redisUtil;

    /*public RedisAuth redisAuth(){
        RedisAuth redisAuth = new RedisAuth();
        redisAuth.setMax_login(2);
        redisAuth.setExpire(24*60*60);//24小时
        redisAuth.setRedis_user_key("appuser:");
        redisAuth.setRedis_token_key("appuser_token:");
        redisAuth.setRedisUtil(redisUtil);
        return redisAuth;
    }*/

    //redis认证
    @Bean
    public RedisAuthService redisAuthService(){
        RedisAuthServiceImpl redisAuthService = new RedisAuthServiceImpl();
        redisAuthService.setMax_login(2);
        redisAuthService.setExpire(24*60*60);//24小时
        redisAuthService.setRedisUtil(redisUtil);
        redisAuthService.setToken_key_prefix("appuser_token:");
        redisAuthService.setUser_token_list_prefix("appuser_token_list:");
        return redisAuthService;
    }

}
