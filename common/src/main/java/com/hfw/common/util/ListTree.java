package com.hfw.common.util;

import java.util.List;

/**
 * 数结构数据
 * @author farkle
 * @date 2022-04-06
 */
public interface ListTree {
    Long getId();
    Long getParentId();

    Integer getSort();
    void setSort(Integer sort);

    Integer getLevel();
    void setLevel(Integer level);

    List<? extends ListTree> getChildren();
    void setChildren(List<? extends ListTree> children);
}
