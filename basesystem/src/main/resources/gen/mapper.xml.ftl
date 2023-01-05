<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packageName}.admin.mapper.${className}Mapper">

    <select id="list" resultType="${packageName}.model.entity.${className}">
        select
            ${pk.columnName}
        <#list columnList as c>
            <#--
            <#if c_index==0>
            ,${c.columnName}
            <#else>
            </#if>-->
            ,${c.columnName}
        </#list>
        from ${tableName}
        <where>
            <#if logicDeleted>
                ${logicDeleted.columnName}=0
            </#if>
            <if test="${pk.fieldName}!=null">
                and ${pk.columnName}=${r'#{'}${pk.fieldName}}
            </if>
        <#list columnList as c>
            <#if c.javaType=='String'>
            <if test="${c.fieldName}!=null and ${c.fieldName}!=''">
                and ${c.columnName}=${r'#{'}${c.fieldName}}
            </if>
            <if test="${c.fieldName}_like!=null and ${c.fieldName}_like!=''">
                and ${c.columnName} like concat('%',${r'#{'}${c.fieldName}_like},'%')
            </if>
            <#elseif c.dataType=='date'|| c.dataType=='datetime'>
            <if test="${c.fieldName}!=null">
                and ${c.columnName}=${r'#{'}${c.fieldName}}
            </if>
            <if test="${c.fieldName}_lt!=null">
                and ${c.columnName} &lt;= ${r'#{'}${c.fieldName}_lt}
            </if>
            <if test="${c.fieldName}_gt!=null">
                and ${c.columnName} &gt;= ${r'#{'}${c.fieldName}_gt}
            </if>
            <#else>
            <if test="${c.fieldName}!=null">
                and ${c.columnName}=${r'#{'}${c.fieldName}}
            </if>
            </#if>
        </#list>
        </where>
        <if test="sortByField!=null and sortByWay!=null">
            order by ${r'${'}sortByField} ${r'${'}sortByWay}
        </if>
    </select>

</mapper>