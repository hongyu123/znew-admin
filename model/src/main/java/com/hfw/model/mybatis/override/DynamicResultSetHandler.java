package com.hfw.model.mybatis.override;

import com.hfw.model.mybatis.MybatisGlobalConfig;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Map;

public class DynamicResultSetHandler extends DefaultResultSetHandler {
    private static final Field boundSqlField;
    static {
        try {
            boundSqlField = DefaultResultSetHandler.class.getDeclaredField("boundSql");
            boundSqlField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    //private final Configuration configuration;
    public DynamicResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler<?> resultHandler, BoundSql boundSql, RowBounds rowBounds) {
        super(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        //this.configuration = mappedStatement.getConfiguration();
    }

    @Override
    public void handleRowValues(ResultSetWrapper rsw, ResultMap resultMap, ResultHandler<?> resultHandler, RowBounds rowBounds, ResultMapping parentMapping) throws SQLException {
        if(resultMap.getResultMappings().isEmpty()){
            Class<?> type = resultMap.getType();
            // SingleMapper通用查询,返回的泛型实际类型为Object,需要替换成具体实体类类型
            if(type == Object.class){
                try {
                    BoundSql boundSql = (BoundSql) boundSqlField.get(this);
                    Object parameterObject = boundSql.getParameterObject();
                    if(parameterObject instanceof Class<?> param){
                        type = param;
                    }else if(parameterObject instanceof Map<?,?> paramMap && paramMap.containsKey("type")){
                        type = (Class<?>) paramMap.get("type");
                    }
                    ResultMap dynamicResultMap = MybatisGlobalConfig.getDynamicResultMap(type);
                    if(dynamicResultMap!=null){
                        resultMap = dynamicResultMap;
                    }
                } catch (IllegalAccessException e) {
                    throw new SQLException(e);
                }
            }
            // 其它类型在需要映射字段或TypeHandler的时候才替换ResultMap
            else{
                /*MapperBuilderAssistant assistant = new MapperBuilderAssistant(this.configuration, "");
                List<ResultMapping> resultMappings = new ArrayList<>();
                ResultMapping resultMapping = assistant.buildResultMapping(type, "createTime", "create_time", null, null, null, null, null, null, null, null);
                resultMappings.add(resultMapping);
                resultMap = (new ResultMap.Builder(this.configuration, resultMap.getId(), type, resultMappings, null)).discriminator(null).build();*/

                ResultMap dynamicResultMap = MybatisGlobalConfig.getDynamicResultMap(type);
                if(dynamicResultMap!=null && !CollectionUtils.isEmpty(dynamicResultMap.getResultMappings())){
                    resultMap = dynamicResultMap;
                }
            }
        }
        super.handleRowValues(rsw, resultMap, resultHandler, rowBounds, parentMapping);
    }

}
