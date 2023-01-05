package com.hfw.admin.mapper;

import com.hfw.model.entity.AppUser;
import java.util.List;

/**
 * app用户Dao
 * @author zyh
 * @date 2022-12-11
 */
public interface AppUserMapper {
    /**
     * 条件查询list
     * @return
     */
    List<AppUser> list(AppUser appUser);
}