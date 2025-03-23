<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packageName}.service.${projectName}.${beanName}.${className}Mapper">

    <select id="page" resultType="${packageName}.model.po.${projectName}.${className}">
        select
            ${pk.columnName},
        <#list columnList as c>
            <#if c_has_next>
            ${c.columnName},
            <#else>
            ${c.columnName}
            </#if>
        </#list>
        from ${tableName}
        <where>
            <#if logicDeleted>
                ${logicDeleted.columnName}=0
            </#if>
            <if test="po.${pk.fieldName}!=null">
                and ${pk.columnName}=${r'#{'}po.${pk.fieldName}}
            </if>
        <#list columnList as c>
            <#if c.javaType=='String'>
            <if test="po.${c.fieldName}!=null and po.${c.fieldName}!=''">
                and ${c.columnName}=${r'#{'}po.${c.fieldName}}
            </if>
            <if test="page.params.${c.fieldName}_like!=null and page.params.${c.fieldName}_like!=''">
                and ${c.columnName} like concat(${r'#{'}page.params.${c.fieldName}_like},'%')
            </if>
            <#elseif c.dataType=='date'|| c.dataType=='datetime'>
            <if test="po.${c.fieldName}!=null">
                and ${c.columnName}=${r'#{'}po.${c.fieldName}}
            </if>
            <if test="page.params.${c.fieldName}_lt!=null and page.params.${c.fieldName}_lt!=''">
                and ${c.columnName} &lt;= ${r'#{'}page.params.${c.fieldName}_lt}
            </if>
            <if test="page.params.${c.fieldName}_gt!=null and page.params.${c.fieldName}_gt!=''">
                and ${c.columnName} &gt;= ${r'#{'}page.params.${c.fieldName}_gt}
            </if>
            <#else>
            <if test="po.${c.fieldName}!=null">
                and ${c.columnName}=${r'#{'}po.${c.fieldName}}
            </if>
            </#if>
        </#list>
        </where>
        <if test="page.sortByField!=null and page.sortByWay!=null">
            order by ${r'${'}page.sortByField} ${r'${'}page.sortByWay}
        </if>
    </select>

</mapper>
