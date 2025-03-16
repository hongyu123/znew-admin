package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
* app建议反馈
* @author farkle
* @date 2022-11-25
*/
@Getter @Setter
@Table("app_advice")
public class AppAdvice {
    @TableId
    private Long id;

    /** 用户id **/
    private Long userId;

    /** 问题分类 **/
    private String category;

    /** 图片 **/
    private String picture;

    /** 建议内容 **/
    private String content;

    /** 联系电话 **/
    private String phone;

    /** 创建时间(反馈时间) */
    private LocalDateTime createTime;

    /** 是否已读 */
    private Integer readFlag;

}