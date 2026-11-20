package com.hfw.model.mybatis.typehandler;

import com.hfw.model.jackson.JsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.json.JsonMapper;

import java.lang.reflect.Type;
import java.sql.*;

@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.OTHER)
public class JacksonTypeHandler extends BaseTypeHandler<Object> {
    private static final JsonMapper jsonMapper = JsonUtil.jsonMapper();
    private final JavaType javaType;
    public JacksonTypeHandler(Class<?> cls){
        javaType = jsonMapper.getTypeFactory().constructType(cls);
    }

    /**
     * 基于ObjectMapper 的 JavaType 实现泛型类型映射sql字段
     * @param cls Field字段的Class
     * @param type Field字段带泛型的类型
     */
    public JacksonTypeHandler(Class<?> cls, Type type){
        javaType = jsonMapper.getTypeFactory().constructType( type==null ?cls :type);
    }
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String json = jsonMapper.writeValueAsString(parameter);
        ps.setObject(i, json, Types.OTHER);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseJson(json);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return parseJson(json);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return parseJson(json);
    }

    private Object parseJson(String json) {
        if (json==null || json.isBlank()) {
            return null;
        }
        return jsonMapper.readValue(json, javaType);
    }

}
