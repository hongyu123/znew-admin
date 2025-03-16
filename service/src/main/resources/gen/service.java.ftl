package ${packageName}.service.${projectName}.${beanName};

import com.hfw.model.entity.Page;
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

    public Page<${className}> page(Page<${className}> page, ${className} po) {
        return ${beanName}Mapper.page(page, po);
    }
    public ${className} detail(Long id){
        return ${beanName}Mapper.getById(id);
    }

    public int add(${className} ${beanName}){
        return ${beanName}Mapper.save(${beanName});
    }
    public int edit(${className} ${beanName}){
        return ${beanName}Mapper.update(${beanName});
    }
    public int del(Long id){
        return ${beanName}Mapper.deleteById(id);
    }
    public int dels(List<Long> ids){
        return ${beanName}Mapper.deleteByIds(ids);
    }

}
