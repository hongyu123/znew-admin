package com.hfw.basesystem.service;

import com.hfw.basesystem.dto.SysRoleDTO;
import com.hfw.basesystem.dto.SysUserRoleDTO;
import com.hfw.basesystem.entity.SysRole;
import com.hfw.basesystem.entity.SysUser;
import com.hfw.common.entity.PageResult;

/**
 * 系统角色Service
 * @author farkle
 * @date 2022-12-14
 */
public interface SysRoleService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<SysRole> page(SysRole sysRole);

    SysRoleDTO detail(Long id);
    int save(SysRoleDTO dto);
    int edit(SysRoleDTO dto);
    int del(Long id);

    /**
     * 查询角色下的用户
     * @return
     */
    PageResult<SysUser> users(SysUserRoleDTO dto);
    /**
     * 用户是否已经授权角色
     * @param roleId
     * @param userId
     * @return
     */
    boolean exists(Long roleId, Long userId);
    /**
     * 角色授权用户
     */
    int addUsers(SysUserRoleDTO dto);
    /**
     * 角色取消授权用户
     * @param dto
     * @return
     */
    int delUsers(SysUserRoleDTO dto);
}