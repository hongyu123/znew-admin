package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.SysAuth;
import com.hfw.common.util.ListTree;
import lombok.Data;

import java.util.List;

/**
 * 系统权限DTO
 */
@Data
public class SysAuthDTO extends SysAuth implements ListTree {

    private Integer level;
    @Override
    public Integer getLevel() {
        return level;
    }
    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }

    private List children;
    @Override
    public List<? extends ListTree> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<? extends ListTree> children) {
        this.children = children;
    }


    /*************************查询用*****************************/


    /*************************显示用*****************************/
    private Integer childrenNums = 0;
    public boolean getHasChildren(){
        return childrenNums>0;
    }

    public SysAuth toEntity(){
        SysAuth sysAuth = new SysAuth();
        sysAuth.setId(this.getId());
        sysAuth.setParentId(this.getParentId());
        sysAuth.setName(this.getName());
        sysAuth.setCode(this.getCode());
        sysAuth.setWebCode(this.getWebCode());
        sysAuth.setAuthType(this.getAuthType());
        sysAuth.setSort(this.getSort());
        sysAuth.setIcon(this.getIcon());
        sysAuth.setPath(this.getPath());
        sysAuth.setComponent(this.getComponent());
        sysAuth.setFrameFlag(this.getFrameFlag());
        sysAuth.setFrameUrl(this.getFrameUrl());
        sysAuth.setCacheFlag(this.getCacheFlag());
        sysAuth.setShowFlag(this.getShowFlag());
        sysAuth.setState(this.getState());
        sysAuth.setCreator(this.getCreator());
        sysAuth.setCreateTime(this.getCreateTime());
        sysAuth.setUpdator(this.getUpdator());
        sysAuth.setUpdateTime(this.getUpdateTime());
        sysAuth.setRemark(this.getRemark());
        return sysAuth;
    }

}