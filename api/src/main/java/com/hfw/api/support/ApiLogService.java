package com.hfw.api.support;

import com.hfw.basesystem.entity.SysApiLog;
import com.hfw.basesystem.mybatis.CommonDao;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author farkle
 * @date 2023-01-13
 */
@Component
public class ApiLogService {

    @Resource
    public CommonDao commonDao;

    @Async
    public void log(SysApiLog log){
        commonDao.insert(log);
    }

}
