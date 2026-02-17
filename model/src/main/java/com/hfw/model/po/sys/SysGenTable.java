package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 表单生成记录
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_gen_table")
public class SysGenTable {

    @TableId
    private Long id;

    /** 表名 */
    private String tableName;

    /** 表注释 */
    private String tableRemark;


    public enum COLUMN implements Column<SysGenTable>{
        id,
        tableName,tableRemark
    }

    /** bean名称 */
    private String beanName;
    /** 实体类名 */
    private String className;
    /** 字段列表 */
    private List<SysGenColumn> columnList;
    /** 是否有富文本 */
    private boolean richtext = false;
    private boolean date = false;
    private boolean datetime = false;
    private boolean picture = false;
    private boolean pictures = false;
    private boolean file = false;
    private boolean fileInput = false;
    private boolean map = false;
    private boolean video = false;

}
