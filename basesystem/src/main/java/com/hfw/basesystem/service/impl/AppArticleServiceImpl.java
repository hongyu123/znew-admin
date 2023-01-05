package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.AppArticle;
import com.hfw.basesystem.entity.SysContent;
import com.hfw.basesystem.mapper.AppArticleMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.AppArticleService;
import com.hfw.common.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * app文章服务实现
 * @author zyh
 * @date 2022-12-20
 */
@Service
public class AppArticleServiceImpl implements AppArticleService {

    @Autowired
    private AppArticleMapper appArticleMapper;
    @Autowired
    private CommonDao commonDao;

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
        if(appArticle!=null && appArticle.getContentId()>0){
            SysContent content = commonDao.findByPk(SysContent.class, appArticle.getContentId());
            if(content!=null){
                appArticle.setContent(content.getContent());
            }
        }
        return appArticle;
    }
    @Transactional
    @Override
    public int save(AppArticle appArticle){
        SysContent sysContent = new SysContent().setContent(appArticle.getContent());
        appArticle.setContentId(sysContent.getId());
        return commonDao.insert(appArticle);
    }
    @Transactional
    @Override
    public int edit(AppArticle appArticle){
        commonDao.updateByPk(new SysContent().setId(appArticle.getContentId()).setContent(appArticle.getContent()));
        return commonDao.updateByPk(appArticle);
    }
    @Transactional
    @Override
    public int del(Long id){
        AppArticle appArticle = commonDao.findByPk(AppArticle.class, id);
        if(appArticle==null){
            return 0;
        }
        commonDao.deleteByPk(SysContent.class, appArticle.getContentId());
        return commonDao.deleteByPk(AppArticle.class, id);
    }

}