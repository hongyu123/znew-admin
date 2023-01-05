package ${packageName}.admin.mapper;

import ${packageName}.model.entity.${className};
import java.util.List;

/**
 * ${tableComment}Mapper
 * @author ${author}
 * @date ${.now?string('yyyy-MM-dd')}
 */
public interface ${className}Mapper {
    /**
     * 条件查询list
     * @return
     */
    List<${className}> list(${className} ${beanName});
}