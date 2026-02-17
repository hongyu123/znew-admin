package com.hfw.service.sys.sysAuth;

import com.hfw.model.mybatis.BaseMapper;
import com.hfw.model.po.sys.SysAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统权限Mapper
 * @author farkle
 * @date 2025-03-18
 */
@Mapper
public interface SysAuthMapper extends BaseMapper<SysAuth> {

    /**
     * 查询用户拥有的权限
     * @param userId 用户id
     * @param unionOwn 是否包含自建权限
     * @param account 用户账号
     * @param menu 是否菜单
     * @return 权限列表
     */
    List<SysAuth> userAuths(@Param("userId") Long userId, @Param("unionOwn") Integer unionOwn, @Param("account") String account, @Param("menu") Integer menu);

    /**
     * 查询角色拥有的权限
     * @param roleId 角色id
     * @return 权限列表
     */
    List<SysAuth> roleAuths(Long roleId);

}
