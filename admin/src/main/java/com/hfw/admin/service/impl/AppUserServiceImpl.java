package com.hfw.admin.service.impl;

import com.hfw.admin.mapper.AppUserMapper;
import com.hfw.admin.service.AppUserService;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.common.entity.PageResult;
import com.hfw.model.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * app用户服务实现
 * @author zyh
 * @date 2022-12-11
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserMapper appUserMapper;
    @Autowired
    private CommonDao commonDao;

    @Override
    public PageResult<AppUser> page(AppUser appUser) {
        PageResult<AppUser> page = new PageResult<>(appUser);
        page.startPage();
        List<AppUser> list = appUserMapper.list(appUser);
        page.setList(list);
        return page;
    }

}