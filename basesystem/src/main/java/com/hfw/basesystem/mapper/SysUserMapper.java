package com.hfw.basesystem.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.hfw.basesystem.entity.SysAuth;
import com.hfw.basesystem.entity.SysUser;
import com.hfw.basesystem.vo.MenuVO;

import java.util.List;

/**
 * 系统用户Mapper
 * @author farkle
 * @date 2022-12-14
 */
public interface SysUserMapper {
    /**
     * 条件查询list
     * @return
     */
    List<SysUser> list(SysUser sysUser);

    /**
     * 查询用户的权限列表
     * @param userId
     * @return
     */
    List<SysAuth> authList(Long userId);

    /**
     * 查询用户的按钮权限
     * @param userId
     * @return
     */
    List<JSONObject> webMenuButtons(Long userId);

    /**
     * 查询用户的菜单权限
     * @param userId
     * @return
     */
    List<MenuVO> webMenus(Long userId);
    //超级管理员菜单权限
    List<MenuVO> adminWebMenus();
}