package com.hfw.service.sys.sysDataScope;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysDataScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 数据权限表Mapper
 * @author farkle
 * @date 2025-03-30
 */
@Mapper
public interface SysDataScopeMapper extends MybatisMapper<SysDataScope> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysDataScope> page(@Param("page") Page<SysDataScope> page, @Param("po") SysDataScope po);

}
