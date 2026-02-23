package com.hfw.service.znew.sysDemo;

import com.hfw.model.entity.Page;
import com.hfw.model.po.znew.SysDemo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统示例表Mapper
 * @author farkle
 * @date 2026-02-17
 */
@Mapper
public interface SysDemoMapper {

    List<SysDemo> list(@Param("page") Page<SysDemo> page, @Param("po") SysDemo po);

}
