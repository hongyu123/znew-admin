package com.hfw.basesystem.vo;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hfw.basesystem.entity.SysAuth;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.ListTree;
import lombok.Data;

import java.util.List;

/**
 * 菜单VO
 * @author zyh
 * @date 2022-12-16
 */
@Data
@JsonFilter(ApiResult.includeFilter)
public class MenuVO extends SysAuth implements ListTree {
    private Integer level;
    public String getName(){
        return super.getWebCode();
    }
    public Meta getMeta(){
        Meta meta = new Meta();
        meta.setTitle(super.getName());
        meta.setIcon(this.getIcon());
        if(this.getFrameFlag()==1){
            meta.setIsLink(this.getFrameUrl());
        }
        if(this.getShowFlag()==0){
            meta.setHideFlag(true);
        }
        if(this.getCacheFlag()==0){
            meta.setKeepAliveFlag(false);
        }
        return meta;
    }
    private List<MenuVO> children;

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public void setChildren(List<? extends ListTree> children) {
        this.children = (List<MenuVO>) children;
    }

    @Data
    public static class Meta{
        private String title;
        private String icon;
        //外部链接
        @JsonProperty("isLink")
        private String isLink;
        //当前路由为详情页时，需要高亮的菜单
        private String activeMenu;
        //是否影藏
        @JsonProperty("isHide")
        private boolean hideFlag = false;
        //是否全屏
        @JsonProperty("isFull")
        private boolean fullFlag = false;
        //是否固定在 tabs nav
        @JsonProperty("isAffix")
        private boolean affixFlag = false;
        //是否缓存
        @JsonProperty("isKeepAlive")
        private boolean keepAliveFlag = true;
    }

}
