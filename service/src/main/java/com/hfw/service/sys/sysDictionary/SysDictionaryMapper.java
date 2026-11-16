package com.hfw.service.sys.sysDictionary;

import com.hfw.model.mybatis.BaseMapper;
import com.hfw.model.po.sys.SysDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 字典表Mapper
 * @author farkle
 * @date 2025-03-29
 */
@Mapper
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    @Update("""
            update sys_dictionary
            set children_num = ( select cnt from (select count(id) cnt from sys_dictionary where pid=#{pid} and leaf_flag=0) tmp)
            where id = #{pid}
            """)
    int updateParentChildrenNum(Long pid);

    @Select("""
            select dict.id, dict.dict_key value, dict.dict_value label
            from sys_dictionary p join sys_dictionary dict on p.id=dict.pid
            where p.dict_key=#{key}
            """)
    List<Map<String,Object>> dictList(String key);

    /*
    CREATE TABLE `c_dept_config` (
      `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
      `category` varchar(255) NOT NULL COMMENT '配置类别',
      `dept_id` bigint NOT NULL COMMENT '部门id',
      `target_id` bigint NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='部门配置'
     */
    // 查询部门配置下的数据字典
    @Select("""
            select dict.id, dict.dict_key value, dict.dict_value label
            from sys_dictionary dict join c_dept_config c on dict.id=c.target_id
            where c.dept_id=#{deptId} and c.category=#{key}
            and dict.dict_key=#{key}
            """)
    List<Map<String,Object>> dptDictList(@Param("key") String key, @Param("deptId")Long deptId);
}
