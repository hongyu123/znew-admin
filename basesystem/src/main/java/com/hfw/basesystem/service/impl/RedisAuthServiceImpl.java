package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.service.RedisAuthService;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * redis认证服务
 * @author farkle
 * @date 2023-02-02
 */
//@Service("redisAuthService")
public class RedisAuthServiceImpl implements RedisAuthService {
    /**
     * 限制同一账号最大登录人数
     */
    private int max_login = 2;

    /**
     * token前缀
     */
    private String token_key_prefix;
    /**
     * 存储所有登录的token前缀
     */
    private String user_token_list_prefix;

    /**
     * 失效时间
     * 30分钟(单位秒)
     */
    private long expire = 30*60;

    private RedisUtil redisUtil;

    public void setMax_login(int max_login) {
        this.max_login = max_login;
    }

    public void setToken_key_prefix(String token_key_prefix) {
        this.token_key_prefix = token_key_prefix;
    }

    public void setUser_token_list_prefix(String user_token_list_prefix) {
        this.user_token_list_prefix = user_token_list_prefix;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public String genToken(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    @Override
    public String store(Long userId, String token, Object obj){
        String userTokenListKey = user_token_list_prefix + userId;
        Long userTokenSize = redisUtil.lPush(userTokenListKey, token);
        redisUtil.setEx(token_key_prefix+ token, obj, expire);
        if(userTokenSize > max_login){
            String rpop = redisUtil.rPop(userTokenListKey);
            if(this.exists(rpop)){
                this.logout(rpop);
                return rpop;
            }
        }
        return null;
    }

    @Override
    public int update(Long userId, Object obj){
        List<String> tokenList = redisUtil.lRange(user_token_list_prefix + userId, 0, max_login);
        if(CollectionUtils.isEmpty(tokenList)){
            return 0;
        }
        int cnt = 0;
        for(String token : tokenList){
            if( redisUtil.setXx(token_key_prefix+token, obj) ){
                cnt++;
            }
        }
        return cnt;
    }
    /**
     * 判断token是否存在
     * @param token
     * @return
     */
    @Override
    public Boolean exists(String token){
        return redisUtil.exists(token_key_prefix+token);
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    @Override
    public <T> T validToken(String token){
        return redisUtil.getEx(token_key_prefix+ token, expire);
    }

    @Override
    public List<String> getValidToken(Long userId){
        List<String> tokenList = redisUtil.lRange(user_token_list_prefix + userId, 0, max_login);
        List<String> validList = tokenList.stream().filter(token -> this.exists(token)).collect(Collectors.toList());
        return validList;
    }

    /**
     * 用户登出
     * @param userId
     */
    @Override
    public Boolean logout(Long userId){
        List<String> tokenList = redisUtil.lRange(user_token_list_prefix + userId, 0, max_login);
        if(CollectionUtils.isEmpty(tokenList)){
            return false;
        }
        List<String> keys = tokenList.stream().map(token -> token_key_prefix + token).collect(Collectors.toList());
        Long dels = redisUtil.del(keys);
        redisUtil.del(user_token_list_prefix+userId);
        return dels>0;
    }
    /**
     * 当前token登出
     * @param token
     */
    @Override
    public Boolean logout(String token){
        return redisUtil.del(token_key_prefix+ token);
    }
    /**
     * 禁用用户
     * @param userId
     * @return
     */
    @Override
    public Boolean disableUser(Long userId){
        return this.logout(userId);
    }

}
