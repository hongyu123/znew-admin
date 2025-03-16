package com.hfw.service.sys.gen;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysGenTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码生成器
 * @author farkle
 * @date 2022-04-15
 */
public interface GenMapper extends MybatisMapper<SysGenTable> {
    /**
     * 表信息列表
     * @param dbName 库名
     * @param tableLike 表面模糊搜索
     * @return 表信息列表
     */
    @Paging
    Page<Table> tableList(@Param("page") Page<Table> page, @Param("dbName") String dbName, @Param("tableLike")String tableLike);

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
