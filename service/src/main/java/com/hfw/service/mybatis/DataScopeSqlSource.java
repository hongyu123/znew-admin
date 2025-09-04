package com.hfw.service.mybatis;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

public class DataScopeSqlSource implements SqlSource {
    private SqlSource sqlSource;
    private Configuration configuration;
    public DataScopeSqlSource(Configuration configuration, SqlSource sqlSource){
        this.configuration = configuration;
        this.sqlSource = sqlSource;
    }
    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        sql+="\r\n-- 测试";
        //System.out.println(sql);
        return new BoundSql(configuration, sql, boundSql.getParameterMappings(), parameterObject);
    }

}
