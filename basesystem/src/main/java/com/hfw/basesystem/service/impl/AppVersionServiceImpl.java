package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.AppVersion;
import com.hfw.basesystem.mapper.AppVersionMapper;
import com.hfw.basesystem.service.AppVersionService;
import com.hfw.common.entity.PageResult;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * app版本管理服务实现
 * @author farkle
 * @date 2022-12-20
 */
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

    @Resource
    private AppVersionMapper appVersionMapper;

    @Override
    public PageResult<AppVersion> page(AppVersion appVersion) {
        PageResult<AppVersion> page = new PageResult<>(appVersion);
        page.startPage();
        List<AppVersion> list = appVersionMapper.list(appVersion);
        page.setList(list);
        return page;
    }

}