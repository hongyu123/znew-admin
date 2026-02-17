package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统配置
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_config")
public class SysConfig {

    @TableId
    private Long id;

    /** key */
    private String key;

    /** value */
    private String value;

    /** 备注 */
    private String comment;


    public enum COLUMN implements Column<SysConfig>{
        id,
        key,value,comment
    }
}
