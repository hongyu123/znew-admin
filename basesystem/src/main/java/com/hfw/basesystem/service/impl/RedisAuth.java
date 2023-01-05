package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.entity.Auth;
import com.hfw.basesystem.entity.SysLoginLog;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.common.support.GeneralException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * redis实现登录认证
 * @author zyh
 * @date 2022-12-16
 */
@Data
public class RedisAuth {
    /**
     * 限制同一账号最大登录人数
     */
    private int max_login = 2;
    /**
     * 失效时间
     */
    private long expire = 30*60;
    /**
     * 登录用户信息存储key
     */
    private String redis_user_key;
    /**
     * token存储key
     */
    private String redis_token_key;

    private RedisUtil redisUtil;

    /**
     * 存储认证信息
     * @return 返回被挤下线的token
     */
    public String store(Long id, String token, Object obj){
        Auth auth = new Auth();
        auth.setId(id);
        auth.setToken(token);
        auth.setStoreObj(obj);

        String pushedOff = null;
        //token添加到有效token中
        auth.getValidToken().add(auth.getToken());
        Auth store = redisUtil.get(redis_user_key+ auth.getId());
        if (store!=null && !CollectionUtils.isEmpty(store.getValidToken()) ){
            List<String> storeTokens = store.getValidToken();
            for (int i = 0; i < max_login-1; i++) {
                if(i<storeTokens.size()){
                    auth.getValidToken().add( storeTokens.get(i) );
                }
            }
            if(max_login-1 < storeTokens.size()){
                String t = storeTokens.get(max_login - 1);
                if(exists(t)){
                    pushedOff = t;
                }
            }
        }
        redisUtil.set(redis_user_key+ auth.getId(), auth);

        //存储token
        redisUtil.set(redis_token_key+ auth.getToken(), auth.getId(), expire);
        return pushedOff;
    }

    public void update(Long id, Object obj){
        String key = redis_user_key+ id;
        Auth origin = redisUtil.get(key);
        origin.setStoreObj(obj);
        redisUtil.set(key, origin);
    }

    /**
     * 获取认证信息
     * @param id
     * @return
     */
    public Auth info(Long id){
        return redisUtil.get(redis_user_key+ id);
    }

    /**
     * 判断token是否存在
     * @param token
     * @return
     */
    public Boolean exists(String token){
        return redisUtil.exists(redis_token_key+token);
    }

    /**
     * 获取有效token
     * @param id
     * @return
     */
    public List<String> getValidToken(Long id){
        Auth auth = this.info(id);
        List<String> tokenList = auth.getValidToken();
        if(CollectionUtils.isEmpty(tokenList)){
            return null;
        }
        List<String> list = new ArrayList<>();
        tokenList.forEach( t->{
            if(exists(t)){
                list.add(t);
            }
        });
        return list;
    }

    @Autowired
    private CommonDao commonDao;
    /**
     * 校验token
     * @param token
     * @return
     */
    public <T> T validToken(String token){
        Integer userId = redisUtil.get(redis_token_key+ token);
        if(userId==null){
            return null;
        }
        Auth<T> auth = redisUtil.get(redis_user_key + userId);
        if(auth==null){
            return null;
        }
        //被挤下线
        if(!auth.getValidToken().contains(token)){
            SysLoginLog log = commonDao.findOne(new SysLoginLog().setToken(token));
            if(log!=null){
                throw new GeneralException("您的账号在"+log.getLocation()+"登录,被挤下线!");
            }
        }
        redisUtil.expire(redis_token_key+ token, expire);
        return auth.getStoreObj();
    }

    /**
     * 用户登出
     * @param userId
     */
    public Boolean logout(Long userId){
        return redisUtil.del(redis_user_key+ userId);
    }

    /**
     * 当前token登出
     * @param token
     */
    public Boolean logout(String token){
        return redisUtil.del(redis_token_key+ token);
    }

    /**
     * 禁用用户
     * @param userId
     * @return
     */
    public Boolean disableUser(Long userId){
        return this.logout(userId);
    }
}
