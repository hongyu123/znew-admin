package ${packageName}.service.${projectName}.${beanName};

import com.hfw.model.entity.Page;
import ${packageName}.model.po.${projectName}.${className};
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * ${tableComment}Mapper
 * @author ${author}
 * @date ${.now?string('yyyy-MM-dd')}
 */
@Mapper
public interface ${className}Mapper {

    List<${className}> list(@Param("page") Page<${className}> page, @Param("po") ${className} po);

}
