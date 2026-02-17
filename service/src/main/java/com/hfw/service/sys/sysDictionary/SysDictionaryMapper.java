package com.hfw.service.sys.sysDictionary;

import com.hfw.model.mybatis.BaseMapper;
import com.hfw.model.po.sys.SysDictionary;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典表Mapper
 * @author farkle
 * @date 2025-03-29
 */
@Mapper
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    int updateParentChildrenNum(Long pid);

}
