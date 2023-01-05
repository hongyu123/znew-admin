package com.hfw.common.util;

import java.util.*;

/**
 * List工具类
 * @author zyh
 * @date 2022-04-06
 */
public class ListUtil {
    /**
     * 将平面list转换成树结构
     * @param list
     * @return
     */
    public static List listTotree(List<? extends ListTree> list) {
        Map<Long, List<ListTree>> map = new HashMap<>();
        List result = new ArrayList<>();
        for (ListTree acl : list) {
            if(acl.getSort()==null){
                acl.setSort(-1);
            }
            if (acl.getParentId() == 0) {
                acl.setLevel(1);
                result.add(acl);
            } else {
                List children = map.get(acl.getParentId());
                if (children == null) {
                    children = new ArrayList<>();
                    map.put(acl.getParentId(), children);
                }
                children.add(acl);
            }
        }
        tree(result, map);
        if(map.size()>0){
            map.forEach( (id,ele)->{
                result.addAll(ele);
            });
        }
        return result;
    }

    /**
     * 将平面list递归转换成树结构
     * @param list
     * @param map
     */
    private static void tree(List<ListTree> list, Map<Long, List<ListTree>> map) {
        list.sort((ListTree m, ListTree n) -> {
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
        for (ListTree acl : list) {
            List<ListTree> children = map.remove(acl.getId());
            if (children != null) {
                for (ListTree c : children) {
                    c.setLevel(acl.getLevel() + 1);
                }
                acl.setChildren(children);
                tree(children, map);
            }
        }
    }

}
