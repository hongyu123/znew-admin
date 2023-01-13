package com.hfw.basesystem.service;

import com.hfw.basesystem.dto.AppAdviceDTO;
import com.hfw.basesystem.entity.AppAdvice;
import com.hfw.common.entity.PageResult;

import java.util.List;

/**
 * app建议反馈Service
 * @author farkle
 * @date 2022-12-20
 */
public interface AppAdviceService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<AppAdviceDTO> page(AppAdvice appAdvice);

    /**
     * 批量已读
     * @param ids
     * @return
     */
    int batchRead(List<Long> ids);
}