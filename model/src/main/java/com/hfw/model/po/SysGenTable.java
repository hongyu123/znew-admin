package com.hfw.model.po;

import cn.xbatis.db.annotations.Ignore;
import cn.xbatis.db.annotations.Table;
import com.hfw.model.entity.BaseEntity;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
* 表单生成记录
* @author farkle
* @date 2023-01-04
*/
@Getter @Setter
@Table("sys_gen_table")
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
    @Ignore
    private String beanName;
    /** 实体类名 */
    @Ignore
    private String className;

    /** 字段列表 */
    @Ignore
    private List<SysGenColumn> columnList;
    /** 是否有富文本 */
    @Ignore
    private boolean richtext = false;
    @Ignore
    private boolean date = false;
    @Ignore
    private boolean datetime = false;
    @Ignore
    private boolean picture = false;
    @Ignore
    private boolean pictures = false;
    @Ignore
    private boolean file = false;
    @Ignore
    private boolean fileInput = false;
    @Ignore
    private boolean map = false;
    @Ignore
    private boolean video = false;
}