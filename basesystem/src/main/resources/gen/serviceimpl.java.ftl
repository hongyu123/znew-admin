package ${packageName}.admin.service.impl;

import ${packageName}.admin.service.${className}Service;
import ${packageName}.admin.mapper.${className}Mapper;
import ${packageName}.model.entity.${className};
import com.hfw.common.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * ${tableComment}服务实现
 * @author ${author}
 * @date ${.now?string('yyyy-MM-dd')}
 */
@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Mapper ${beanName}Mapper;

    @Override
    public PageResult<${className}> page(${className} ${beanName}) {
        PageResult<${className}> page = new PageResult<>(${beanName});
        page.startPage();
        List<${className}> list = ${beanName}Mapper.list(${beanName});
        page.setList(list);
        return page;
    }

}