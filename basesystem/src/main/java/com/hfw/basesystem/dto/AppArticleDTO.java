package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.AppArticle;
import lombok.Data;

/**
 * app文章DTO
 */
@Data
public class AppArticleDTO extends AppArticle {

    /*************************查询用*****************************/
    private String title_like;

    /*************************显示用*****************************/


    public AppArticle toEntity(){
        AppArticle appArticle = new AppArticle();
        appArticle.setId(this.getId());
        appArticle.setType(this.getType());
        appArticle.setTitle(this.getTitle());
        appArticle.setPicture(this.getPicture());
        appArticle.setIntroduction(this.getIntroduction());
        appArticle.setContentType(this.getContentType());
        appArticle.setContentId(this.getContentId());
        appArticle.setLinkUrl(this.getLinkUrl());
        appArticle.setLocation(this.getLocation());
        return appArticle;
    }

}