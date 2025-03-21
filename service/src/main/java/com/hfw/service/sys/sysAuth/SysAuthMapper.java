package com.hfw.service.sys.sysAuth;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统权限Mapper
 * @author farkle
 * @date 2025-03-18
 */
public interface SysAuthMapper extends MybatisMapper<SysAuth> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysAuth> page(@Param("page") Page<SysAuth> page, @Param("po") SysAuth po);

    /**
     * 查询用户拥有的权限
     * @param userId 用户id
     * @param unionOwn 是否包含自建权限
     * @param account 用户账号
     * @return 权限列表
     */
    List<SysAuth> userAuths(@Param("userId") Long userId, @Param("unionOwn") Integer unionOwn, @Param("account") String account);

}
