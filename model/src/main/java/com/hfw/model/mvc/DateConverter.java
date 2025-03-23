package com.hfw.model.mvc;

import com.hfw.model.utils.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author farkle
 * @date 2022-06-15
 */
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if(source==null){
            return null;
        }
        String format = DateUtil.formatMap.get(source.length());
        if(format==null){
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

}