package com.hfw.basesystem.service;

import java.util.List;

/**
 * redis认证服务
 * @author farkle
 * @date 2023-02-02
 */
public interface RedisAuthService {
    /**
     * 生成token
     * @return
     */
    String genToken();

    /**
     * 判断token是否存在
     * @param token
     * @return
     */
    Boolean exists(String token);

    /**
     * 存储认证信息
     * @param userId
     * @param token
     * @param obj
     * @return
     */
    String store(Long userId, String token, Object obj);

    /**
     * 更新认证信息
     * @param userId
     * @param obj
     * @return
     */
    int update(Long userId, Object obj);

    /**
     * 校验token
     * @param token
     * @return
     */
    <T> T validToken(String token);

    /**
     * 获取用户有效(在线)token
     * @param userId
     * @return
     */
    List<String> getValidToken(Long userId);

    /**
     * 用户登出
     * @param userId
     */
    Boolean logout(Long userId);

    /**
     * 当前token登出
     * @param token
     */
    Boolean logout(String token);

    /**
     * 禁用用户
     * @param userId
     * @return
     */
    Boolean disableUser(Long userId);
}
