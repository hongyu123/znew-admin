package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.AppBanner;
import lombok.Data;

/**
 * app轮播图DTO
 */
@Data
public class AppBannerDTO extends AppBanner {

    /*************************查询用*****************************/


    /*************************显示用*****************************/
    private String content;

    public AppBanner toEntity(){
        AppBanner appBanner = new AppBanner();
        appBanner.setId(this.getId());
        appBanner.setTitle(this.getTitle());
        appBanner.setPicture(this.getPicture());
        appBanner.setType(this.getType());
        appBanner.setLinkUrl(this.getLinkUrl());
        appBanner.setTargetId(this.getTargetId());
        appBanner.setCreateTime(this.getCreateTime());
        appBanner.setUpdateTime(this.getUpdateTime());
        return appBanner;
    }
    public AppBannerDTO saveFilter(){
        this.setId(null);
        this.setCreateTime(null);
        this.setUpdateTime(null);
        return this;
    }
    public AppBannerDTO updateFilter(){
        this.setCreateTime(null);
        this.setUpdateTime(null);
        return this;
    }

    public static AppBannerDTO of(AppBanner appBanner){
        AppBannerDTO dto = new AppBannerDTO();
        dto.setId(appBanner.getId());
        dto.setTitle(appBanner.getTitle());
        dto.setPicture(appBanner.getPicture());
        dto.setType(appBanner.getType());
        dto.setLinkUrl(appBanner.getLinkUrl());
        dto.setTargetId(appBanner.getTargetId());
        dto.setCreateTime(appBanner.getCreateTime());
        dto.setUpdateTime(appBanner.getUpdateTime());
        return dto;
    }
}