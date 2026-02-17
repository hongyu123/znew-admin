package com.hfw.service.sys.gen;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码生成器
 * @author farkle
 * @date 2022-04-15
 */
@Mapper
public interface GenMapper {
    /**
     * 表信息列表
     * @param schema
     * @param tableLike 表面模糊搜索
     * @return 表信息列表
     */
    List<Table> tableList(@Param("schema") String schema, @Param("tableLike")String tableLike);

    /**
     * 根据表名获取表信息
     * @param schema
     * @param tableName
     * @return
     */
    Table table(@Param("schema") String schema, @Param("tableName") String tableName);

    /**
     * 根据表名获取字段信息
     * @param schema
     * @param tableName
     * @return
     */
    List<Column> tableColumn(@Param("schema") String schema, @Param("tableName")String tableName);

}
