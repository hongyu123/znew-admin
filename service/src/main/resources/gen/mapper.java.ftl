package ${packageName}.service.${projectName}.${beanName};

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import ${packageName}.model.po.${projectName}.${className};
import org.apache.ibatis.annotations.Param;

/**
 * ${tableComment}Mapper
 * @author ${author}
 * @date ${.now?string('yyyy-MM-dd')}
 */
public interface ${className}Mapper extends MybatisMapper<${className}> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<${className}> page(@Param("page") Page<${className}> page, @Param("po") ${className} po);

}
