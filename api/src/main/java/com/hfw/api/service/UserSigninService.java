package com.hfw.api.service;

import com.hfw.api.vo.SigninInfo;

/**
 * 用户签到服务
 * @author farkle
 * @date 2023-01-12
 */
public interface UserSigninService {
    /**
     * 签到信息
     * @param userId
     * @return
     */
    SigninInfo info(Long userId);

    /**
     * 用户签到
     * @param userId
     */
    void signin(Long userId);
}
