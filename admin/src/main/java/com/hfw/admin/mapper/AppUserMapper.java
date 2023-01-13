package com.hfw.admin.mapper;

import com.hfw.basesystem.entity.AppUser;
import java.util.List;

/**
 * app用户Dao
 * @author farkle
 * @date 2022-12-11
 */
public interface AppUserMapper {
    /**
     * 条件查询list
     * @return
     */
    List<AppUser> list(AppUser appUser);
}