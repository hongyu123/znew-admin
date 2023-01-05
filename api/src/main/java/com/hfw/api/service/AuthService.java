package com.hfw.api.service;

import com.hfw.api.dto.AppleLoginParam;
import com.hfw.api.dto.PhoneCodeParam;
import com.hfw.api.dto.WeixinLoginParam;
import com.hfw.api.support.LoginUser;

/**
 * 认证服务
 * @author zyh
 * @date 2022-11-26
 */
public interface AuthService {
    /**
     * 用户登出(该用户下的所有token失效)
     * @param userId
     */
    void logout(Long userId);

    /**
     * 当前token登出
     * @param token
     */
    void logout(String token);

    /**
     * 手机号码登录
     * @param loginParam
     * @return
     */
    LoginUser loginByPhone(PhoneCodeParam loginParam);

    /**
     * 微信登录
     * @param loginParam
     * @return
     * @throws Exception
     */
    LoginUser loginByWeixin(WeixinLoginParam loginParam) throws Exception;

    /**
     * 苹果登录
     * @param loginParam
     * @return
     * @throws Exception
     */
    LoginUser loginByApple(AppleLoginParam loginParam) throws Exception;

    /**
     * 禁用用户
     * @param id
     * @return
     */
    Boolean disableUser(Long id);
}
