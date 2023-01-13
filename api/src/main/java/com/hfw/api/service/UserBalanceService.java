package com.hfw.api.service;

import com.hfw.model.entity.UserBalance;

import java.util.List;

/**
 * 用户余额明细服务
 * @author farkle
 * @date 2023-01-12
 */
public interface UserBalanceService {

    /**
     * 余额明细列表
     * @param cond
     * @return
     */
    List<UserBalance> list(UserBalance cond);

    /**
     * 余额收入
     * @param userId
     * @param balance
     * @param source
     * @param orderId
     */
    void addBalance(Long userId, Integer balance, String source, Long orderId);

    /**
     * 余额支出
     * @param userId
     * @param balance
     * @param source
     * @param orderId
     */
    void subBalance(Long userId, Integer balance, String source, Long orderId);

}
