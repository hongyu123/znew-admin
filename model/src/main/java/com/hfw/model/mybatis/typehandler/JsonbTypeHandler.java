package com.hfw.model.mybatis.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hfw.model.utils.JsonList;
import com.hfw.model.utils.JsonMap;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.*;

/**
 * JSONB 类型处理器
 * SysDemo selectByMap(Map<String,Object> map);
 * 1. 不要注册接口类型 Map.class,List.class, 因为mybatis ParameterMapping 会取实际的实现类类型,而不是接口类型
 * 2. 不要注册 HashMap.class,ArrayList.class, 因为用这两种类型做查询参数, mybatis会调用JsonbTypeHandler把他们视为一个参数,尝试转换json导致取不到正确的参数报错
 * 因此转换json类型建议用专有类型 JsonNode/JsonMap/JsonList
 */
@MappedTypes({JsonNode.class, JsonMap.class, JsonList.class })
@MappedJdbcTypes(JdbcType.OTHER)
public class JsonbTypeHandler<T> extends BaseTypeHandler<T> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> type;

    public JsonbTypeHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            PGobject jsonObject = new PGobject();
            String json = objectMapper.writeValueAsString(parameter);
            ps.setObject(i, json, Types.OTHER);
        }catch (JsonProcessingException e){
            throw new SQLException("Error converting object to JSON", e);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String json = rs.getString(columnName);
            return parseJson(json);
        } catch (JsonProcessingException e) {
            throw new SQLException("Error parsing JSON", e);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String json = rs.getString(columnIndex);
            return parseJson(json);
        } catch (JsonProcessingException e) {
            throw new SQLException("Error parsing JSON", e);
        }
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String json = cs.getString(columnIndex);
            return parseJson(json);
        } catch (JsonProcessingException e) {
            throw new SQLException("Error parsing JSON", e);
        }
    }

    private T parseJson(String json) throws JsonProcessingException {
        if (json==null || json.isBlank()) {
            return null;
        }
        return objectMapper.readValue(json, type);
    }
}