package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Ignore;
import cn.xbatis.db.annotations.Table;
import com.hfw.model.enums.app.AppArticleEnum;
import com.hfw.model.enums.app.AppArticleType;
import com.hfw.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
* app文章
* @author farkle
* @date 2022-11-25
*/
@Getter @Setter
@Table("app_article")
public class AppArticle extends BaseEntity {
    /** id **/
    private Long id;

    /** 文章类型 **/
    private AppArticleType type;

    /** 标题 **/
    private String title;

    /** 图片 **/
    private String picture;

    /** 简介 **/
    private String introduction;

    /** 1超链接2图文详情 **/
    private AppArticleEnum.ContentType contentType;

    /** 图文详情 **/
    private Long contentId;

    /** 超链接 **/
    private String linkUrl;

    /** 文章位置(方便显示用) **/
    private String location;

    /** 创建时间 */
    private LocalDateTime createTime;


    /** 图文详情 **/
    @Ignore
    private String content;

}