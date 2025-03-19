package com.hfw.service.sys.sysAuth;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysAuth;
import org.apache.ibatis.annotations.Param;

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

}
