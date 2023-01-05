package com.hfw.basesystem.mapper;

import com.hfw.basesystem.gen.Column;
import com.hfw.basesystem.gen.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码生成器
 * @author zyh
 * @date 2022-04-15
 */
public interface GenMapper {
    /**
     * 表列表
     * @param dbName
     * @param tableLike
     * @return
     */
    List<Table> tableList(@Param("dbName") String dbName, @Param("tableLike")String tableLike);

    /**
     * 根据表名获取表信息
     * @param dbName
     * @param tableName
     * @return
     */
    Table table(@Param("dbName") String dbName, @Param("tableName") String tableName);

    /**
     * 根据表名获取字段信息
     * @param dbName
     * @param tableName
     * @return
     */
    List<Column> tableColumn(@Param("dbName") String dbName, @Param("tableName")String tableName);

}
