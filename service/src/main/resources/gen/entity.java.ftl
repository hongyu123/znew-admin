package ${packageName}.model.po.${projectName};

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableField;
import cn.xbatis.db.annotations.TableId;
<#if logicDeleted??>
import cn.xbatis.db.annotations.LogicDelete;
</#if>
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
    @TableField(defaultValue = "{CREATE_USER}")
    <#elseif c.columnName=='create_time'>
    @TableField(defaultValue = "{CREATE_TIME}")
    <#elseif c.columnName=='update_user'>
    @TableField(updateDefaultValue = "{UPDATE_USER}")
    <#elseif c.columnName=='update_time'>
    @TableField(updateDefaultValue = "{UPDATE_TIME}")
    </#if>
    private ${c.javaType} ${c.fieldName};
    <#if c.enumClass??>
    public String get${c.javaType}Desc(){
        return ${c.fieldName}==null ?"":${c.fieldName}.getDesc();
    }
    </#if>

</#list>
<#if logicDeleted??>
    /** ${logicDeleted.columnComment} */
    @LogicDelete
    private ${logicDeleted.javaType} ${logicDeleted.columnName};

</#if>
}
