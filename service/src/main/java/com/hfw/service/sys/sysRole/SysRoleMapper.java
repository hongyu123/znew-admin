package com.hfw.service.sys.sysRole;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysRole;
import com.hfw.model.po.sys.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色Mapper
 * @author farkle
 * @date 2025-03-17
 */
@Mapper
public interface SysRoleMapper extends MybatisMapper<SysRole> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysRole> page(@Param("page") Page<SysRole> page, @Param("po") SysRole po);

    /**
     * 查询角色下已分配的用户
     */
    @Paging
    Page<SysUser> users(@Param("page") Page<SysUser> page, @Param("roleId") Long roleId);

    /**
     * 查询用户拥有的角色
     * @param userId 用户id
     * @param unionOwn 是否包含自建角色
     * @param account 用户账号
     * @return 角色列表
     */
    List<SysRole> userRoles(@Param("userId") Long userId,  @Param("unionOwn") Integer unionOwn, @Param("account") String account);
    //用户拥有的角色分页
    @Paging
    Page<SysRole> userRolePage(@Param("page") Page<SysRole> page, @Param("po") SysRole po);
}
