package com.hfw.model.po;

import cn.xbatis.db.annotations.Table;
import com.hfw.model.entity.BaseEntity;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
* 表单生成字段
* @author farkle
* @date 2023-01-04
*/
@Getter @Setter
@Table("sys_gen_column")
public class SysGenColumn extends BaseEntity {

    /** id **/
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** 表名 **/
    @NotBlank(message = "表名不能为空", groups = ValidGroup.Add.class)
    @Length(max = 100,message = "表名最多100字符")
    private String tableName;

    /** 字段名 **/
    @NotBlank(message = "字段名不能为空", groups = ValidGroup.Add.class)
    @Length(max = 100,message = "字段名最多100字符")
    private String columnName;

    /** 字段注释 **/
    private String columnRemark;

    /** 显示标题 **/
    private String label;

    /** 属性名 **/
    @NotBlank(message = "属性名不能为空", groups = ValidGroup.Add.class)
    @Length(max = 100,message = "属性名最多100字符")
    private String property;

    /** 表单类型 **/
    @NotBlank(message = "表单类型不能为空", groups = ValidGroup.Add.class)
    @Length(max = 100,message = "表单类型最多100字符")
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