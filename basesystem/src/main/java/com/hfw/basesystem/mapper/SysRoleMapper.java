package com.hfw.basesystem.mapper;

import com.hfw.basesystem.dto.SysUserRoleDTO;
import com.hfw.basesystem.entity.SysRole;
import com.hfw.basesystem.entity.SysUser;

import java.util.List;

/**
 * 系统角色Mapper
 * @author farkle
 * @date 2022-12-14
 */
public interface SysRoleMapper {
    /**
     * 条件查询list
     * @return
     */
    List<SysRole> list(SysRole sysRole);

    /**
     * 查询角色下的用户
     * @return
     */
    List<SysUser> users(SysUserRoleDTO dto);
}