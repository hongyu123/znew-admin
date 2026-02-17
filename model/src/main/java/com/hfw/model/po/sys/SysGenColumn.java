package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * 表单生成字段
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_gen_column")
public class SysGenColumn {

    @TableId
    private Long id;

    /** 表名 */
    private String tableName;

    /** 字段名 */
    private String columnName;

    /** 字段注释 */
    private String columnRemark;

    /** 显示标题 */
    private String label;

    /** 属性名 */
    private String property;

    /** 表单类型 */
    private String formType;

    /** 是否列表 */
    private Integer listFlag;

    /** 是否搜索 */
    private Integer searchFlag;

    /** 是否必填 */
    private Integer required;

    /** 字符串长度 */
    private Integer maxlength;


    public enum COLUMN implements Column<SysGenColumn>{
        id,
        tableName,columnName,columnRemark,label,property, formType,listFlag,searchFlag,required,maxlength

    }
}
