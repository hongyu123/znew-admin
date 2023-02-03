package com.hfw.basesystem.service;

import java.util.List;

/**
 * 通用服务
 * @author farkle
 * @date 2022-12-08
 */
public interface CommonService<T> {
    List<T> listAll(Class<T> clazz);
    List<T> list(T t);
    List<T> list(T t, Integer pageNumber, Integer pageSize);
    T detail(Class<T> clazz, Long id);
    Long count(T t);
    int save(T t);
    int edit(T t);
    int edit(T t, T cond);
    int del(Class<T> clazz, Long id);
    int dels(Class<T> clazz, List<Long> ids);
}
