package ${packageName}.admin.dto;

import ${packageName}.model.entity.${className};
import lombok.Data;

/**
 * ${tableComment}DTO
 */
@Data
public class ${className}DTO extends ${className} {

    /*************************查询用*****************************/


    /*************************显示用*****************************/


    public ${className} toEntity(){
        ${className} ${beanName} = new ${className}();
        ${beanName}.setId(this.getId());
        <#list columnList as c>
        ${beanName}.set${c.fieldNameFirstUpper()}(this.get${c.fieldNameFirstUpper()}());
        </#list>
        return ${beanName};
    }

}