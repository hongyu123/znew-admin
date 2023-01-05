package com.hfw.basesystem.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;

/**
* 系统配置
* @author zyh
* @date 2022-11-25
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_config")
public class SysConfig {
    /** id **/
    private Long id;

    /** key **/
    private String key;

    /** value **/
    private String value;

    /** 备注 **/
    private String comment;


}