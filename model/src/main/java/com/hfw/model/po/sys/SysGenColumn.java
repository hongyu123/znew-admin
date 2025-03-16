package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

/**
* 表单生成字段
* @author farkle
* @date 2023-01-04
*/
@Getter @Setter
@Table("sys_gen_column")
public class SysGenColumn {
    @TableId
    private Long id;

    /** 表名 **/
    private String tableName;

    /** 字段名 **/
    private String columnName;

    /** 字段注释 **/
    private String columnRemark;

    /** 显示标题 **/
    private String label;

    /** 属性名 **/
    private String property;

    /** 表单类型 **/
    private String formType;

    /** 必填 */
    private Integer required;

    /** 是否列表 **/
    private Integer listFlag;

    /** 是否搜索 **/
    private Integer searchFlag;

    /** 字符串长度 */
    private Integer maxlength;

}
