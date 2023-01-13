package com.hfw.api.support;

import com.hfw.basesystem.entity.SysApiLog;
import com.hfw.basesystem.mybatis.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author farkle
 * @date 2023-01-13
 */
@Component
public class ApiLogService {

    @Autowired
    public CommonDao commonDao;

    @Async
    public void log(SysApiLog log){
        commonDao.insert(log);
    }

}
