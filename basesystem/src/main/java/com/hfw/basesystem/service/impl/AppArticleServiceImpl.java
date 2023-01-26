package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.AppArticle;
import com.hfw.basesystem.mapper.AppArticleMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.AppArticleService;
import com.hfw.basesystem.service.SysContentService;
import com.hfw.common.entity.PageResult;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * app文章服务实现
 * @author farkle
 * @date 2022-12-20
 */
@Service("appArticleService")
public class AppArticleServiceImpl implements AppArticleService {

    @Resource
    private AppArticleMapper appArticleMapper;
    @Resource
    private CommonDao commonDao;
    @Resource
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
        AppArticle appArticle = commonDao.selectByPk(AppArticle.class, id);
        appArticle.setContent(sysContentService.content(appArticle.getContentId()) );
        return appArticle;
    }

    @Override
    public void save(AppArticle appArticle){
        appArticle.setContentId( sysContentService.save(appArticle.getContent()) );
        commonDao.insert(appArticle);
    }

    @Override
    public void edit(AppArticle appArticle){
        sysContentService.edit(appArticle.getContentId(), appArticle.getContent());
        commonDao.updateByPk(appArticle);
    }

    @Override
    public void del(Long id){
        AppArticle appArticle = commonDao.selectByPk(AppArticle.class, id);
        if(appArticle==null){
            return ;
        }
        sysContentService.del(appArticle.getContentId());
        commonDao.deleteByPk(AppArticle.class, id);
    }

}