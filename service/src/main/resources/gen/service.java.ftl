package ${packageName}.service.${projectName}.${beanName};

import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import ${packageName}.model.po.${projectName}.${className};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${tableComment}服务
 * @author ${author}
 * @date ${.now?string('yyyy-MM-dd')}
 */
@Service
public class ${className}Service {
    @Autowired
    private ${className}Mapper ${beanName}Mapper;

    public PageResult<${className}> page(Page<${className}> page, ${className} po) {
        page.startPage();
        List<${className}> list = ${beanName}Mapper.list(page, po);
        return PageResult.of(list);
    }

}
