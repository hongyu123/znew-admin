package ${packageName}.model.po.${projectName};

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
<#list columnList as c>
    <#if c.enumClass??>
import ${c.enumClass};
    </#if>
</#list>
import lombok.Getter;
import lombok.Setter;

/**
* ${tableComment}
* @author ${author}
* @date ${.now?string('yyyy-MM-dd')}
*/
@Getter @Setter
@Table("${tableName}")
public class ${className} {

    @TableId
    private ${pk.javaType} ${pk.fieldName};

<#list columnList as c>
    /** ${c.columnComment} */
    <#if c.columnName=='create_user'>
    @TableField(insertValue = "$CURRENT_USER")
    <#elseif c.columnName=='create_time'>
    @TableField(insertValue = "$NOW")
    <#elseif c.columnName=='update_user'>
    @TableField(updateValue = "$CURRENT_USER")
    <#elseif c.columnName=='update_time'>
    @TableField(updateValue = "$NOW")
    </#if>
    private ${c.javaType} ${c.fieldName};
    <#if c.enumClass??>
    public String get${c.fieldName?cap_first}Desc(){
        return ${c.fieldName}==null ?"":${c.fieldName}.getDesc();
    }
    </#if>

</#list>
<#if logicDeleted??>
    /** ${logicDeleted.columnComment} */
    @LogicDelete
    private ${logicDeleted.javaType} ${logicDeleted.columnName};
</#if>

    public enum COLUMN implements Column<${className}>{
        ${pk.fieldName},
        <#list columnList as c>${c.fieldName}<#if c_has_next || logicDeleted??>,</#if><#if c_index!=0 && (c_index+1)%5==0> </#if><#if c_index!=0 && (c_index+1)%10==0>${"\r\n\t\t"}</#if></#list>
        <#if logicDeleted??>
        ${logicDeleted.columnName}
        </#if>
    }
}
