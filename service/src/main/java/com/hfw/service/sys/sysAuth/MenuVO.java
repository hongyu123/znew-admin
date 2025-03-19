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
        menuVO.setName(sysAuth.getWebCode());
        menuVO.setPath(sysAuth.getPath());
        menuVO.setComponent(sysAuth.getComponent());
        Meta meta = new Meta();
        meta.setTitle(sysAuth.getName());
        meta.setIcon(sysAuth.getIcon());
        meta.setShowLink(sysAuth.getShowFlag() != null && sysAuth.getShowFlag() == 1);
        meta.setKeepAlive(sysAuth.getCacheFlag() != null && sysAuth.getCacheFlag() == 1);
        if(sysAuth.getFrameFlag()!=null && sysAuth.getFrameFlag() == 1){
            meta.setFrameSrc(sysAuth.getFrameUrl());
        }
        menuVO.setMeta(meta);
        return menuVO;
    }

    @Getter
    @Setter
    public static class Meta{
        // 菜单名称
        private String title;
        // 菜单图标
        private String icon;
        // 是否显示该菜单
        private Boolean showLink;
        //是否缓存该路由页面
        private Boolean keepAlive;
        //外部链接,内嵌标签, 不内嵌标签的话直接指定name为外链的地址
        private String frameSrc;
        //当子菜单只有一项时是否显示父级菜单
        private Boolean showParent = true;
        /** 当前菜单名称是否固定显示在标签页且不可关闭（默认`false`） */
        private Boolean fixedTag;
        // 将某个菜单激活（主要用于通过query或params传参的路由，当它们通过配置showLink: false后不在菜单中显示，就不会有任何菜单高亮，而通过设置activePath指定激活菜单即可获得高亮，activePath为指定激活菜单的path）
        private String activePath;
    }

}
