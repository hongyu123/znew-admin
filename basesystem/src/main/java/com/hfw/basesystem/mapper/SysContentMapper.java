package com.hfw.basesystem.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * 系统图文内容
 * @author zyh
 * @date 2022-11-25
 */
public interface SysContentMapper {

    @Select("select content from sys_content where id=#{id}")
    String getContent(Long id);

}
