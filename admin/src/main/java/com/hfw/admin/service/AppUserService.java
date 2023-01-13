package com.hfw.admin.service;

import com.hfw.common.entity.PageResult;
import com.hfw.basesystem.entity.AppUser;

/**
 * app用户Service
 * @author farkle
 * @date 2022-12-11
 */
public interface AppUserService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<AppUser> page(AppUser appUser);
}