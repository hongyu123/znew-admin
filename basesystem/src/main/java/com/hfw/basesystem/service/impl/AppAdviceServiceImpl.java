package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.dto.AppAdviceDTO;
import com.hfw.basesystem.service.AppAdviceService;
import com.hfw.basesystem.mapper.AppAdviceMapper;
import com.hfw.basesystem.entity.AppAdvice;
import com.hfw.common.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * app建议反馈服务实现
 * @author farkle
 * @date 2022-12-20
 */
@Service
public class AppAdviceServiceImpl implements AppAdviceService {

    @Autowired
    private AppAdviceMapper appAdviceMapper;

    @Override
    public PageResult<AppAdviceDTO> page(AppAdvice appAdvice) {
        PageResult<AppAdviceDTO> page = new PageResult<>(appAdvice);
        page.startPage();
        List<AppAdviceDTO> list = appAdviceMapper.list(appAdvice);
        page.setList(list);
        return page;
    }

    @Override
    public int batchRead(List<Long> ids){
        return appAdviceMapper.batchRead(ids);
    }
}