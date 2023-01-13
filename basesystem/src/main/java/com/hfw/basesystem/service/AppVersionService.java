package com.hfw.basesystem.service;


import com.hfw.basesystem.entity.AppVersion;
import com.hfw.common.entity.PageResult;

/**
 * app版本管理Service
 * @author farkle
 * @date 2022-12-20
 */
public interface AppVersionService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<AppVersion> page(AppVersion appVersion);
}