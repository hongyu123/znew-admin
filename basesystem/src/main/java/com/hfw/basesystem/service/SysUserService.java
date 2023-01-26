package com.hfw.basesystem.service;

import com.hfw.basesystem.dto.SysUserDTO;
import com.hfw.basesystem.entity.SysUser;
import com.hfw.basesystem.vo.MenuVO;
import com.hfw.common.entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 系统用户Service
 * @author farkle
 * @date 2022-12-14
 */
public interface SysUserService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<SysUser> page(SysUser sysUser);
    SysUserDTO detail(Long id);
    void save(SysUserDTO dto);
    void edit(SysUserDTO dto);
    void del(Long id);

    /**
     * 修改密码
     * @return
     */
    int changePassword(SysUserDTO dto);

    /**
     * 重置密码
     * @param sysUser
     * @return
     */
    int resetPassword(SysUser sysUser);

    /**
     * 查询用户的按钮权限
     * @param userId
     * @return
     */
    Map<String, Object> webMenuButtons(Long userId);

    /**
     *  查询用户的菜单权限
     * @param userId
     * @return
     */
    List<MenuVO> webMenus(Long userId);
}