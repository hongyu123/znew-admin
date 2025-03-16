package com.hfw.service.sys.gen;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表
 * @author farkle
 * @date 2022-04-15
 */
@Getter
@Setter
public class Table {
    /** 表名 */
    private String tableName;
    /** 实体类名 */
    private String className;
    /** bean名称 */
    private String beanName;
    /** 注释 */
    private String tableComment;

    /** 主键 */
    private Column pk;
    /** 逻辑删除 */
    private Column logicDeleted;
    /** 字段 */
    private List<Column> columnList = new ArrayList<>();
    /** 包名 */
    private String packageName;
    /** 项目名 */
    private String projectName;
    /** 作者 */
    private String author;
}
