package com.hfw.service.sys.sysAuth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hfw.model.po.sys.SysAuth;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuVO {
    private Long id;
    private Long parentId;
    private String name;
    private String path;
    private String component;
    private List<MenuVO> children;
    private Meta meta;

    public static MenuVO of(SysAuth sysAuth){
        MenuVO menuVO = new MenuVO();
        menuVO.setId(sysAuth.getId());
        menuVO.setParentId(sysAuth.getParentId());
        menuVO.setName(sysAuth.getName());
        menuVO.setPath(sysAuth.getPath());
        menuVO.setComponent(sysAuth.getComponent());
        Meta meta = new Meta();
        meta.setTitle(sysAuth.getTitle());
        meta.setIcon(sysAuth.getIcon());
        meta.setHide(sysAuth.getShowFlag() == null || sysAuth.getShowFlag() == 0);
        meta.setKeepAlive(sysAuth.getCacheFlag() != null && sysAuth.getCacheFlag() == 1);
        if(sysAuth.getFrameFlag()!=null && sysAuth.getFrameFlag() == 1){
            meta.setLink(sysAuth.getFrameUrl());
        }
        menuVO.setMeta(meta);
        return menuVO;
    }

    @Getter
    @Setter
    public static class Meta{
        /// 菜单名称
        private String title;
        // 菜单图标
        private String icon;
        @JsonProperty("isLink")
        private String link = "";
        @JsonProperty("isHide")
        private Boolean hide = false;
        @JsonProperty("isFull")
        private Boolean full = false;
        @JsonProperty("isAffix")
        private Boolean affix = false;
        @JsonProperty("isKeepAlive")
        private Boolean keepAlive = true;
    }

}
