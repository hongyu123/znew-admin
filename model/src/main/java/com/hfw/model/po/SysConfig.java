package com.hfw.model.po;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableField;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统配置
 * @author farkle
 * @date 2022-11-25
 */
@Getter @Setter
@Table("sys_config")
public class SysConfig {
    /** id **/
    private Long id;

    /** key **/
    @TableField("`key`")
    private String key;

    /** value **/
    private String value;

    /** 备注 **/
    private String comment;

}