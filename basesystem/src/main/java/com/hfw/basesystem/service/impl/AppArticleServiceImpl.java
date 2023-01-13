package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.AppArticle;
import com.hfw.basesystem.mapper.AppArticleMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.AppArticleService;
import com.hfw.basesystem.service.SysContentService;
import com.hfw.common.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * app文章服务实现
 * @author farkle
 * @date 2022-12-20
 */
@Service
public class AppArticleServiceImpl implements AppArticleService {

    @Autowired
    private AppArticleMapper appArticleMapper;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private SysContentService sysContentService;

    @Override
    public PageResult<AppArticle> page(AppArticle appArticle) {
        PageResult<AppArticle> page = new PageResult<>(appArticle);
        page.startPage();
        List<AppArticle> list = appArticleMapper.list(appArticle);
        page.setList(list);
        return page;
    }

    @Override
    public AppArticle detail(Long id){
        AppArticle appArticle = commonDao.findByPk(AppArticle.class, id);
        appArticle.setContent(sysContentService.content(appArticle.getContentId()) );
        return appArticle;
    }
    @Transactional
    @Override
    public int save(AppArticle appArticle){
        appArticle.setContentId( sysContentService.save(appArticle.getContent()) );
        return commonDao.insert(appArticle);
    }
    @Transactional
    @Override
    public int edit(AppArticle appArticle){
        sysContentService.edit(appArticle.getContentId(), appArticle.getContent());
        return commonDao.updateByPk(appArticle);
    }
    @Transactional
    @Override
    public int del(Long id){
        AppArticle appArticle = commonDao.findByPk(AppArticle.class, id);
        if(appArticle==null){
            return 0;
        }
        sysContentService.del(appArticle.getContentId());
        return commonDao.deleteByPk(AppArticle.class, id);
    }

}