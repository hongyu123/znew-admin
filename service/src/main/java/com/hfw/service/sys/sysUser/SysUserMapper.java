package com.hfw.service.sys.sysUser;

import com.hfw.model.entity.Page;
import com.hfw.model.mybatis.BaseMapper;
import com.hfw.model.po.sys.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户Mapper
 * @author farkle
 * @date 2025-03-17
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    List<SysUser> page(@Param("page") Page<SysUser> page, @Param("po") SysUser po);

}
