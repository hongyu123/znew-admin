package ${packageName}.admin.service;

import com.hfw.common.entity.PageResult;
import ${packageName}.model.entity.${className};

/**
 * ${tableComment}Service
 * @author ${author}
 * @date ${.now?string('yyyy-MM-dd')}
 */
public interface ${className}Service {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<${className}> page(${className} ${beanName});
}