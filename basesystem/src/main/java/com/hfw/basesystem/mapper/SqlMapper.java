package com.hfw.basesystem.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 通用sql执行Dao,
 * 推荐执行简单sql
 *
 * @Repository需要在BaseApplication中配置扫描地址，@MapperScan("com.anson.dao")//配置mapper扫描地址
 * @Mapper不需要配置扫描地址
 */
public interface SqlMapper {
    /**
     * 此方法执行sql存在sql拼接问题
     *
     * @param sql
     * @return
     */
    @Update("${sql}")
    int execute(@Param("sql") String sql);

    @Select("${sql}")
    long count(@Param("sql") String sql);

    @Select("${sql}")
    String strField(@Param("sql") String sql);

    @Select("${sql}")
    Map<String, Object> queryMap(@Param("sql") String sql);

    @Select("${sql}")
    List<Map<String, Object>> queryList(@Param("sql") String sql);


    /**
     * 支持sql预处理，防sql拼接
     * 使用示例1：mapper.execute(Paramap.create().put("sql", "update t_app_user set nickname=#{name},status=#{status} where id=#{id}").put("name", "farkle").put("status", 0).put("id", -1));
     * 使用示例2：mapper.execute(new Sql("update t_app_user set nickname=#{p1},status=#{p2} where id=#{p3}","farkle",0,-1));
     *
     * @param params
     * @return
     */
    @Update("${sql}")
    int executeEx(Object params);

    @Select("${sql}")
    long countEx(Object params);

    @Select("${sql}")
    String strFieldEx(Object params);

    @Select("${sql}")
    Map<String, Object> queryMapEx(Object params);

    @Select("${sql}")
    List<Map<String, Object>> queryListEx(Object params);

}
