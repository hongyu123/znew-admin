package com.hfw.basesystem.mapper;

import com.hfw.basesystem.entity.AppVersion;

import java.util.List;

/**
 * app版本管理Mapper
 * @author zyh
 * @date 2022-12-20
 */
public interface AppVersionMapper {
    /**
     * 条件查询list
     * @return
     */
    List<AppVersion> list(AppVersion appVersion);
}