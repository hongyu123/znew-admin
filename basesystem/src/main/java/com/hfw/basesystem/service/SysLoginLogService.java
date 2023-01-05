package com.hfw.basesystem.service;


import com.hfw.basesystem.entity.SysLoginLog;
import com.hfw.common.entity.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统登录日志Service
 * @author zyh
 * @date 2022-12-17
 */
public interface SysLoginLogService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<SysLoginLog> page(SysLoginLog sysLoginLog);

    /**
     * 登录日志
     * @param account
     * @param message
     * @param request
     * @return
     */
    int login(String token, String account, String message, HttpServletRequest request);

    /**
     * 登出日志
     * @param tokens
     * @return
     */
    int logout(List<String> tokens);

    /**
     * 被挤下线日志
     * @param pushedOffToken
     * @return
     */
    int pushedOff(String pushedOffToken);
}