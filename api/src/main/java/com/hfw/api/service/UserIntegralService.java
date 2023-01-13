package com.hfw.api.service;

import com.hfw.model.entity.UserIntegral;

import java.util.List;

/**
 * 用户积分服务
 * @author farkle
 * @date 2023-01-12
 */
public interface UserIntegralService {
    /**
     * 积分明细列表
     * @param cond
     * @return
     */
    List<UserIntegral> list(UserIntegral cond);

    /**
     * 积分收入
     * @param userId
     * @param integral
     * @param source
     * @param orderId
     */
    void addIntegral(Long userId, Integer integral, String source, Long orderId);

    /**
     * 积分支出
     * @param userId
     * @param integral
     * @param source
     * @param orderId
     */
    void subIntegral(Long userId, Integer integral, String source, Long orderId);
}
