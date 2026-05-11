package com.hfw.model.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LocalDate工具类
 * @author farkle
 * @date 2022-11-10
 */
public class LocalDateUtil {
    /**
     * 默认日期格式-24小时
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式-12小时
     */
    public static final String DATE_TIME_FORMAT_12 = "yyyy-MM-dd hh:mm:ss a";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 自动解析日期格式
     */
    public static class FormatAutoParse{
        private final List<String> patternList = List.of("yyyy","MM","dd","HH","mm","ss","SSS");
        private int index = 0;
        private final String dateStr;
        public FormatAutoParse(String dateStr){
            this.dateStr = dateStr;
        }

        /**
         * 解析数字，返回对应的日期格式
         */
        private String parse(String partDateStr){
            if(index>=patternList.size()){
                throw new IllegalArgumentException("自动解析日期格式错误："+dateStr);
            }
            String partPattern = patternList.get(index++);
            if(partDateStr.length()>partPattern.length()){
                //String left = partDateStr.substring(0,partPattern.length());
                String right = partDateStr.substring(partPattern.length());
                return partPattern + parse(right);
            }else if(partDateStr.length()<partPattern.length()){
                return partPattern.substring(0, partDateStr.length());
            }
            return partPattern;
        }
        public String parse(){
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(dateStr);
            String formatPattern = dateStr.replace("T","'T'");
            while (matcher.find()){
                String digit = matcher.group();
                String partPattern = this.parse(digit);
                formatPattern = formatPattern.replaceFirst(digit, partPattern);
            }
            return formatPattern;
        }
        public boolean isTimePattern(){
            return index>3;
        }
        public boolean isMonthPattern(){
            return index<3;
        }
    }

    /**
     * 24小时制日期自动解析
     * 仅支持年月日的顺序字符解析
     */
    public static LocalDate parseDate(String date){
        if(!StrUtil.hasText(date)){
            return null;
        }
        FormatAutoParse parse = new FormatAutoParse(date);
        String pattern = parse.parse();
        if(parse.isTimePattern()){
            return parseDateTime(date, pattern).toLocalDate();
        }
        if(parse.isMonthPattern()){
            return YearMonth.parse(date, DateTimeFormatter.ofPattern(pattern)).atDay(1);
        }
        return parseDate(date, pattern);
    }
    /**
     * 24小时制日期自动解析
     * 仅支持年月日的顺序字符解析
     */
    public static LocalDateTime parseDateTime(String date){
        if(!StrUtil.hasText(date)){
            return null;
        }
        FormatAutoParse parse = new FormatAutoParse(date);
        String pattern = parse.parse();
        if(parse.isTimePattern()){
            return parseDateTime(date, pattern);
        }
        if(parse.isMonthPattern()){
            return YearMonth.parse(date, DateTimeFormatter.ofPattern(pattern)).atDay(1).atStartOfDay();
        }
        return parseDate(date, pattern).atStartOfDay();
    }
    public static LocalDate parseDate(String date, String pattern){
        if(date==null){
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }
    public static LocalDateTime parseDateTime(String date, String pattern){
        if(date==null){
            return null;
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(LocalDate date, String pattern){
        if(date==null){
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String format(LocalDateTime dateTime, String pattern){
        if(dateTime==null){
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String format(LocalDate date){
        return format(date, DATE_FORMAT);
    }
    public static String format(LocalDateTime dateTime){
        return format(dateTime, DATE_TIME_FORMAT);
    }

    public static LocalDateTime toLocalDateTime(Long milliseconds){
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public static long toEpochMilli(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
