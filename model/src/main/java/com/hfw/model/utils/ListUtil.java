package com.hfw.model.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * List工具类
 * @author farkle
 * @date 2022-04-06
 */
public class ListUtil {
    /**
     * 树结构数据
     * @author farkle
     * @date 2022-04-06
     */
    public interface Tree {
        Long getId();
        Long getParentId();

        Integer getSort();
        void setSort(Integer sort);

        Integer getLevel();
        void setLevel(Integer level);

        List<? extends Tree> getChildren();
        void setChildren(List<? extends Tree> children);
    }

    /**
     * 将平面list转换成树结构
     */
    public static List<? extends Tree> listTotree(List<? extends Tree> list) {
        Map<Long, List<Tree>> map = new HashMap<>();
        List<Tree> result = new ArrayList<>();
        for (Tree acl : list) {
            if(acl.getSort()==null){
                acl.setSort(-1);
            }
            if (acl.getParentId() == 0) {
                acl.setLevel(1);
                result.add(acl);
            } else {
                List<Tree> children = map.computeIfAbsent(acl.getParentId(), k -> new ArrayList<>());
                children.add(acl);
            }
        }
        tree(result, map);
        if(!map.isEmpty()){
            map.forEach( (id,ele)->{
                result.addAll(ele);
            });
        }
        return result;
    }

    /**
     * 将平面list递归转换成树结构
     */
    private static void tree(List<Tree> list, Map<Long, List<Tree>> map) {
        list.sort((Tree m, Tree n) -> {
            if (m.getSort() == null || n.getSort() == null) {
                return (int) (m.getId() - n.getId());
            }
            if(m.getSort()==null){
                return 1;
            }
            if(n.getSort()==null){
                return 0;
            }
            return m.getSort() - n.getSort();
        });
        for (Tree acl : list) {
            List<Tree> children = map.remove(acl.getId());
            if (children != null) {
                for (Tree c : children) {
                    c.setLevel(acl.getLevel() + 1);
                }
                acl.setChildren(children);
                tree(children, map);
            }
        }
    }

}
