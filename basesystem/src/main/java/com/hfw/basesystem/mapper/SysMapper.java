package com.hfw.basesystem.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * SysMapper
 * @author farkle
 * @date 2022-11-25
 */
public interface SysMapper {

    @Select("select content from sys_content where id=#{id}")
    String getContent(Long id);

    @Update("update sys_config set value=#{value} where `key`=#{key}")
    int config(@Param("key") String key, @Param("value") String value);
}
