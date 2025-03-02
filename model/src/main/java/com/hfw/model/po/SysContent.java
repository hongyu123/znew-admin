package com.hfw.model.po;

import cn.xbatis.db.annotations.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
* 系统图文内容
* @author farkle
* @date 2022-11-25
*/
@Getter @Setter
@Accessors(chain = true)
@Table("sys_content")
public class SysContent {
    /** id **/
    private Long id;

    /** 图文详情 **/
    private String content;

}