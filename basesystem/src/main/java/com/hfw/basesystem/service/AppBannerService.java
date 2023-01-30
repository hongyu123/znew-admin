package com.hfw.basesystem.service;

import com.hfw.basesystem.dto.AppBannerDTO;
import com.hfw.basesystem.entity.AppBanner;
import com.hfw.common.entity.PageResult;

/**
 * app轮播图Service
 * @author farkle
 * @date 2023-01-29
 */
public interface AppBannerService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<AppBanner> page(AppBanner appBanner);

    AppBannerDTO detail(Long id);
    void add(AppBannerDTO dto);
    void edit(AppBannerDTO dto);
    void del(Long id);
}