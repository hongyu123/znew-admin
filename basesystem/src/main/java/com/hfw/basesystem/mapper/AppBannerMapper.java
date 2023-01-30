package com.hfw.basesystem.mapper;

import com.hfw.basesystem.entity.AppBanner;

import java.util.List;

/**
 * app轮播图Mapper
 * @author farkle
 * @date 2023-01-29
 */
public interface AppBannerMapper {
    /**
     * 条件查询list
     * @return
     */
    List<AppBanner> list(AppBanner appBanner);
}