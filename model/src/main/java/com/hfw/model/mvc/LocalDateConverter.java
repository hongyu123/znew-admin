package com.hfw.model.mvc;

import com.hfw.model.utils.LocalDateUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

/**
 * @author farkle
 * @date 2022-12-05
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        return LocalDateUtil.parseDate(source);
    }

}
