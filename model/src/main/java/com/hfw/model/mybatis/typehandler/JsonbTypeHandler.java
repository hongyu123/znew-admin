package com.hfw.model.mybatis.typehandler;

import com.hfw.model.jackson.JsonUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.sql.*;

/**
 * JSONB 类型处理器
 * SysDemo selectByMap(Map<String,Object> map);
 * 1. 不要注册接口类型 Map.class,List.class, 因为mybatis ParameterMapping 会取实际的实现类类型,而不是接口类型
 * 2. 不要注册 HashMap.class,ArrayList.class, 因为用这两种类型做查询参数, mybatis会调用JsonbTypeHandler把他们视为一个参数,尝试转换json导致取不到正确的参数报错
 * 因此转换json类型建议用专有类型 ObjectNode/ArrayNode/DBMap/DBList
 */
@MappedTypes({ObjectNode.class, ArrayNode.class/*, DBMap.class, DBList.class*/ })
@MappedJdbcTypes(JdbcType.OTHER)
public class JsonbTypeHandler<T> extends BaseTypeHandler<T> {
    private static final JsonMapper jsonMapper = JsonUtil.jsonMapper();
    private final Class<T> type;

    public JsonbTypeHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        String json = jsonMapper.writeValueAsString(parameter);
        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        jsonObject.setValue(json);
        ps.setObject(i, jsonObject, Types.OTHER);
//        ps.setString(i, json);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseJson(json);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return parseJson(json);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return parseJson(json);
    }

    private T parseJson(String json) {
        if (json==null || json.isBlank()) {
            return null;
        }
        return jsonMapper.readValue(json, type);
    }
}