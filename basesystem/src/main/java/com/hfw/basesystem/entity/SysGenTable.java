package com.hfw.basesystem.entity;

import com.hfw.basesystem.mybatis.FieldIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import com.hfw.common.entity.BaseEntity;
import com.hfw.basesystem.support.validation.ValidGroup;

import java.util.List;

/**
* 表单生成记录
* @author 
* @date 2023-01-04
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_gen_table")
public class SysGenTable extends BaseEntity {

    /** id **/
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** 表名 **/
    @NotBlank(message = "表名不能为空", groups = ValidGroup.Add.class)
    @Length(max = 100,message = "表名最多100字符")
    private String tableName;

    /** 表注释 **/
    private String tableRemark;

    /** bean名称 */
    @FieldIgnore
    private String beanName;
    /** 实体类名 */
    @FieldIgnore
    private String className;

    /** 字段列表 */
    @FieldIgnore
    private List<SysGenColumn> columnList;
    /** 是否有富文本 */
    @FieldIgnore
    private boolean richtext = false;
    @FieldIgnore
    private boolean date = false;
    @FieldIgnore
    private boolean datetime = false;
    @FieldIgnore
    private boolean picture = false;
    @FieldIgnore
    private boolean pictures = false;
    @FieldIgnore
    private boolean file = false;
    @FieldIgnore
    private boolean fileInput = false;
    @FieldIgnore
    private boolean map = false;
}