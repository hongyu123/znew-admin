package com.hfw.service.sys.sysRole;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysRole;
import com.hfw.model.po.sys.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * 系统角色Mapper
 * @author farkle
 * @date 2025-03-17
 */
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
     * 查询角色下的用户
     */
    Page<SysUser> users(@Param("page") Page<SysUser> page);

}
