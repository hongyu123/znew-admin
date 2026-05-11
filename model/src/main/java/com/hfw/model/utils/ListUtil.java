package com.hfw.model.utils;

import java.util.*;

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
    public static List<? extends Tree> listToTree(List<? extends Tree> list) {
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

    private static class Partition<T> extends AbstractList<List<T>> {
        final List<T> list;
        final int size;

        public Partition(List<T> list, int size) {
            this.list = list;
            this.size = Math.min(list.size(), size);
        }

        public List<T> get(int index) {
            int start = index * this.size;
            int end = Math.min(start + this.size, this.list.size());
            return this.list.subList(start, end);
        }

        public int size() {
            int size = this.size;
            if (0 == size) {
                return 0;
            } else {
                int total = this.list.size();
                return (total + size - 1) / size;
            }
        }

        public boolean isEmpty() {
            return this.list.isEmpty();
        }
    }
    private static class RandomAccessPartition<T> extends Partition<T> implements RandomAccess {
        RandomAccessPartition(List<T> list, int size) {
            super(list, size);
        }
    }

    /**
     * 列表拆分，拆分后的列表不可进行增加,删除操作
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        return list instanceof RandomAccess ? new RandomAccessPartition<>(list, size) : new Partition<>(list, size);
    }
    /**
     * 列表拆分，拆分后的列表可进行增加,删除操作
     */
    public static <T> List<List<T>> partitionToChange(List<T> list, int size) {
        return partition(list, size).stream().map(item-> (List<T>)new ArrayList<>(item)).toList();
    }

}
