package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

/**
* 系统图文内容
* @author farkle
* @date 2022-11-25
*/
@Getter @Setter
@Table("sys_content")
public class SysContent {
    @TableId
    private Long id;

    /** 图文详情 **/
    private String content;

}
