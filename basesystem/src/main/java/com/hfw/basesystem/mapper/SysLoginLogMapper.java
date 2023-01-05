package com.hfw.basesystem.mapper;

import com.hfw.basesystem.entity.SysLoginLog;

import java.util.List;

/**
 * 系统登录日志Mapper
 * @author zyh
 * @date 2022-12-17
 */
public interface SysLoginLogMapper {
    /**
     * 条件查询list
     * @return
     */
    List<SysLoginLog> list(SysLoginLog sysLoginLog);

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