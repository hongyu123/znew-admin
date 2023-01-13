package com.hfw.basesystem.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;

/**
* 系统图文内容
* @author farkle
* @date 2022-11-25
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_content")
public class SysContent {
    /** id **/
    private Long id;

    /** 图文详情 **/
    private String content;


}