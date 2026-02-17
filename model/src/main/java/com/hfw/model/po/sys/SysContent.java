package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * 图文内容
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_content")
public class SysContent {

    @TableId
    private Long id;

    /** 图文详情 */
    private String content;


    public enum COLUMN implements Column<SysContent>{
        id,
        content
    }
}
