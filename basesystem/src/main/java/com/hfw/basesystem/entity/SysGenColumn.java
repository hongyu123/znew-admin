package com.hfw.basesystem.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import com.hfw.common.entity.BaseEntity;
import com.hfw.basesystem.support.validation.ValidGroup;

/**
* 表单生成字段
* @author 
* @date 2023-01-04
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_gen_column")
public class SysGenColumn extends BaseEntity {

    /** id **/
    @NotNull(message = "id不能为空",groups = {ValidGroup.Update.class, ValidGroup.Del.class})
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