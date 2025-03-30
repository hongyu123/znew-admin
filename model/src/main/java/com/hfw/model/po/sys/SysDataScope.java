package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableField;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.sys.DataScope;
import lombok.Getter;
import lombok.Setter;

/**
* 数据权限表
* @author farkle
* @date 2025-03-30
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
    private String customIds;

}
