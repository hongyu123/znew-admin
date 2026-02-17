package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import com.hfw.model.enums.sys.DataScope;
import com.hfw.model.mybatis.typehandler.DBList;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据权限表
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_data_scope")
public class SysDataScope {

    @TableId
    private Long id;

    /** 配置类型(1用户 2组织机构) */
    private Integer configType;

    /** 用户/机构id */
    private Long configId;

    /** 用户/机构名称 */
    private String configName;

    /** 数据key */
    private String dataKey;

    /** 数据权限 */
    private DataScope dataScope;
    public String getDataScopeDesc(){
        return dataScope==null ?"":dataScope.getDesc();
    }

    /** 自定义数据权限ids */
    private DBList customIds;


    public enum COLUMN implements Column<SysDataScope>{
        id,
        configType,configId,configName,dataKey,dataScope, customIds
    }
}
