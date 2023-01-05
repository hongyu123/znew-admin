package com.hfw.basesystem.support;

import com.hfw.common.util.LocalDateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @author zyh
 * @date 2022-12-05
 */
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        if(!StringUtils.hasText(source)){
            return null;
        }
        return LocalDateUtil.parseDateTime(source);
    }

}
