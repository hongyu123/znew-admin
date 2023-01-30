package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.dto.AppBannerDTO;
import com.hfw.basesystem.entity.AppBanner;
import com.hfw.basesystem.enums.AppBannerEnum;
import com.hfw.basesystem.mapper.AppBannerMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.AppBannerService;
import com.hfw.basesystem.service.SysContentService;
import com.hfw.common.entity.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * app轮播图服务实现
 * @author farkle
 * @date 2023-01-29
 */
@Service("appBanner")
public class AppBannerServiceImpl implements AppBannerService {

    @Resource
    private AppBannerMapper appBannerMapper;
    @Resource
    private CommonDao commonDao;
    @Resource
    private SysContentService sysContentService;

    @Override
    public PageResult<AppBanner> page(AppBanner appBanner) {
        PageResult<AppBanner> page = new PageResult<>(appBanner);
        page.startPage();
        List<AppBanner> list = appBannerMapper.list(appBanner);
        page.setList(list);
        return page;
    }

    @Override
    public AppBannerDTO detail(Long id){
        AppBanner appBanner = commonDao.selectByPk(AppBanner.class, id);
        AppBannerDTO dto = AppBannerDTO.of(appBanner);
        if(dto.getType() == AppBannerEnum.content){
            dto.setContent( sysContentService.content(dto.getTargetId()) );
        }
        return dto;
    }

    @Override
    public void add(AppBannerDTO dto){
        AppBanner appBanner = dto.saveFilter().toEntity();
        appBanner.setCreateTime(LocalDateTime.now());
        if(appBanner.getType() == AppBannerEnum.content){
            appBanner.setTargetId( sysContentService.save(dto.getContent()) );
        }
        commonDao.insert(appBanner);
    }

    @Override
    public void edit(AppBannerDTO dto){
        AppBanner appBanner = dto.updateFilter().toEntity();
        appBanner.setUpdateTime(LocalDateTime.now());
        commonDao.updateByPk(appBanner);
        if(appBanner.getType() == AppBannerEnum.content){
            sysContentService.edit(appBanner.getTargetId(), dto.getContent());
        }
    }

    @Override
    public void del(Long id){
        AppBanner appBanner = commonDao.selectByPk(AppBanner.class, id);
        if(appBanner==null){
            return;
        }
        if(appBanner.getType() == AppBannerEnum.content){
            sysContentService.del(appBanner.getTargetId());
        }
        commonDao.deleteByPk(AppBanner.class, id);
    }
}