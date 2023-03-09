package ${packageName}.model.entity;

import lombok.*;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import com.hfw.common.entity.BaseEntity;
import com.hfw.basesystem.support.validation.ValidGroup;
<#if logicDeleted??>
import com.hfw.basesystem.mybatis.FieldLogic;
</#if>

/**
* ${tableComment}
* @author ${author}
* @date ${.now?string('yyyy-MM-dd')}
*/
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "${tableName}")
public class ${className} extends BaseEntity {

    /** ${pk.columnComment} */
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private ${pk.javaType} ${pk.fieldName};

<#list columnList as c>
    /** ${c.columnComment} */
    <#if "NO"==c.isNullable && !c.columnDefault??>
        <#if "String"==c.javaType>
    @NotBlank(message = "${c.columnComment}不能为空", groups = ValidGroup.Add.class)
        <#else>
    @NotNull(message = "${c.columnComment}不能为空", groups = ValidGroup.Add.class)
        </#if>
    </#if>
    <#if "NO"==c.isNullable && "String"==c.javaType>
    @Length(max = ${c.characterMaximumLength},message = "${c.columnComment}最多${c.characterMaximumLength}字符")
    </#if>
    private ${c.javaType} ${c.fieldName};

</#list>

<#if logicDeleted??>
    /** ${logicDeleted.columnComment} */
    @FieldLogic
    private ${logicDeleted.javaType} ${logicDeleted.columnName};
</#if>
}