package com.hfw.basesystem.service;


import com.hfw.basesystem.entity.AppArticle;
import com.hfw.common.entity.PageResult;

/**
 * app文章Service
 * @author farkle
 * @date 2022-12-20
 */
public interface AppArticleService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<AppArticle> page(AppArticle appArticle);

    AppArticle detail(Long id);
    int save(AppArticle appArticle);
    int edit(AppArticle appArticle);
    int del(Long id);
}