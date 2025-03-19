package com.hfw.service.sys.sysUser;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysUser;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户Mapper
 * @author farkle
 * @date 2025-03-17
 */
public interface SysUserMapper extends MybatisMapper<SysUser> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysUser> page(@Param("page") Page<SysUser> page, @Param("po") SysUser po);

}
