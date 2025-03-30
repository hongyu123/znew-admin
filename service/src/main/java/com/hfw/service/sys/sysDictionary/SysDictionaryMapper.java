package com.hfw.service.sys.sysDictionary;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysDictionary;
import org.apache.ibatis.annotations.Param;

/**
 * 字典表Mapper
 * @author farkle
 * @date 2025-03-29
 */
public interface SysDictionaryMapper extends MybatisMapper<SysDictionary> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysDictionary> page(@Param("page") Page<SysDictionary> page, @Param("po") SysDictionary po);

    int updateParentChildrenNum(Long pid);

}
