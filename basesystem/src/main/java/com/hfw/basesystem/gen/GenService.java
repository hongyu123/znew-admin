package com.hfw.basesystem.gen;

import com.hfw.basesystem.entity.SysGenTable;
import com.hfw.common.entity.PageResult;

import java.util.List;

/**
 * 代码生成器
 * @author zyh
 * @date 2022-04-15
 */
public interface GenService {
    /**
     * 表列表
     * @param tableLike
     * @return
     */
    List<Table> tableList(String tableLike);

    /**
     * 表分页
     * @param tableLike
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageResult<Table> tablePage(String tableLike, Integer pageNumber, Integer pageSize);

    /**
     * 表信息
     * @param tableName
     * @return
     */
    Table tableInfo(String tableName);

    /**
     * 表信息以及字段信息获取
     * @param tableName
     * @return
     */
    Table tableColumnInfo(String tableName);

    /**
     * 根据表名获取字段信息
     * @param tableName
     * @return
     */
    List<Column> tableColumn(String tableName);

    /**
     * 代码生成到配置路径
     * @param tableName
     * @throws Exception
     */
    void genToPath(String tableName) throws Exception;

    /**
     * 代码生成到项目路径
     * @param tableName
     * @throws Exception
     */
    void genToProject(String tableName) throws Exception;

    /**
     * 表单生成信息
     * @param tableName
     * @return
     */
    SysGenTable formGenTableInfo(String tableName);

    /**
     * 表单生成到配置路径
     * @param table
     * @throws Exception
     */
    void genFormToPath(SysGenTable table) throws Exception;
    /**
     * 表单生成到项目路径
     * @param table
     * @throws Exception
     */
    void genFormToProject(SysGenTable table) throws Exception;



    /**
     * 代码生成
     * @param tableName
     * @throws Exception
     */
    void gen(String tableName) throws Exception;
    /**
     * 代码生成
     * @throws Exception
     */
    void gen(Table table) throws Exception;
    /**
     * 所有表代码生成
     * @throws Exception
     */
    void genAll(String dbName) throws Exception;

    /**
     * vue增删改仅list页面生成
     * @param tableName
     * @param queryFields
     */
    void genList(String tableName,String queryFields) throws Exception;
}
