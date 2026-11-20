package com.hfw.model.mybatis.typehandler;

import com.hfw.model.po.sys.SysUpload;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 服务器路径处理
 * 存时去除服务器地址
 * 取时加上服务器地址
 */
@MappedTypes({String.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class URLTypeHandler extends BaseTypeHandler<String> {

    public URLTypeHandler(Class<?> cls){
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String url, JdbcType jdbcType) throws SQLException {
        String path = getPath(url);
        ps.setString(i, path);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String path = rs.getString(columnName);
        return getUrl(path);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String path = rs.getString(columnIndex);
        return getUrl(path);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String path = cs.getString(columnIndex);
        return getUrl(path);
    }
    private String getPath(String url){
        if(url==null){
            return null;
        }
        return url.replace(SysUpload.SERVER,"");
    }
    private String getUrl(String path){
        if(path==null || path.isEmpty()){
            return null;
        }
        return SysUpload.SERVER+path;
    }
}
