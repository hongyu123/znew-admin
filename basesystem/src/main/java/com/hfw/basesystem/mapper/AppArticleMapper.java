package com.hfw.basesystem.mapper;

import com.hfw.basesystem.entity.AppArticle;

import java.util.List;

/**
 * app文章Mapper
 * @author zyh
 * @date 2022-12-20
 */
public interface AppArticleMapper {
    /**
     * 条件查询list
     * @return
     */
    List<AppArticle> list(AppArticle appArticle);
}