package com.hfw.basesystem.support;

import com.hfw.common.util.LocalDateUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

/**
 * @author farkle
 * @date 2022-12-05
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        if(!StringUtils.hasText(source)){
            return null;
        }
        return LocalDateUtil.parse(source);
    }

}
