package com.hfw.model.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@MappedTypes(LocalDateTime.class)
/**
 * mybatis默认的typeHandler null->LocalDateTime
 * xbatis @TypeHandler默认的typeHandler JdbcType.UNDEFINED->LocalDateTime
 */
@MappedJdbcTypes(value = {JdbcType.TIMESTAMP, JdbcType.TIMESTAMP_WITH_TIMEZONE}, includeNullJdbcType = true)
public class DateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {
    private final ZoneOffset zoneOffset = ZoneOffset.of("+08:00");
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        OffsetDateTime dateTime = OffsetDateTime.of(parameter, zoneOffset);
        ps.setObject(i, dateTime);
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        OffsetDateTime dateTime = rs.getObject(columnName, OffsetDateTime.class);
        if(dateTime!=null){
            return dateTime.toLocalDateTime();
        }
        return null;
    }

    @Override
    public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        OffsetDateTime dateTime = rs.getObject(columnIndex, OffsetDateTime.class);
        if(dateTime!=null){
            return dateTime.toLocalDateTime();
        }
        return null;
    }

    @Override
    public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        OffsetDateTime dateTime = cs.getObject(columnIndex, OffsetDateTime.class);
        if(dateTime!=null){
            return dateTime.toLocalDateTime();
        }
        return null;
    }
}
