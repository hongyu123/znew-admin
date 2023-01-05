package com.hfw.basesystem.mapper;

import com.hfw.basesystem.dto.AppAdviceDTO;
import com.hfw.basesystem.entity.AppAdvice;
import java.util.List;

/**
 * app建议反馈Mapper
 * @author zyh
 * @date 2022-12-20
 */
public interface AppAdviceMapper {
    /**
     * 条件查询list
     * @return
     */
    List<AppAdviceDTO> list(AppAdvice appAdvice);

    /**
     * 批量已读
     * @param ids
     * @return
     */
    int batchRead(List<Long> ids);
}