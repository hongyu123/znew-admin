package com.hfw.service.sys.gen;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PGGenMapper {

    List<Table> tableList(@Param("schema") String schema, @Param("tableLike")String tableLike);

    Table table(@Param("schema") String schema, @Param("tableName") String tableName);

    List<Column> tableColumn(@Param("schema") String schema, @Param("tableName")String tableName);

}
