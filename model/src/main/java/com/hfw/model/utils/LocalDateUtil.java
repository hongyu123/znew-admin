package com.hfw.model.utils;

import com.hfw.model.entity.Entry;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;

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

    private static ArrayDeque<Character> getPatternQueue(){
        ArrayDeque<Character> queue = new ArrayDeque<>();
        queue.add('y');
        queue.add('y');
        queue.add('y');
        queue.add('y');

        queue.add('M');
        queue.add('M');

        queue.add('d');
        queue.add('d');

        queue.add('H');
        queue.add('H');

        queue.add('m');
        queue.add('m');

        queue.add('s');
        queue.add('s');

        queue.add('S');
        queue.add('S');
        queue.add('S');
        return queue;
    }

    public static Entry<StringBuilder,Integer> parsePattern(String date, ArrayDeque<Character> queue){
        int dateIndex = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < date.length(); i++) {
            char c = date.charAt(i);
            if(c>='0' && c<='9'){
                Character ch = queue.poll();
                if(ch!=null){
                    sb.append(ch);
                    dateIndex++;
                }
            }else if(c == 'T'){
                sb.append("'T'");
            }else{
                sb.append(c);
            }
        }
        return new Entry<>(sb, dateIndex);
    }

    private static LocalDate appendDate(String date, Entry<StringBuilder, Integer> entry, ArrayDeque<Character> queue){
        for (int i = entry.value(); i < 8; i++) {
            entry.key().append(queue.poll());
        }
        if(entry.value()==4){
            date += "0101";
        }else if(entry.value()==6){
            date += "01";
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(entry.key().toString()));
    }
    public static LocalDate parse(String date){
        if(!StrUtil.hasText(date)){
            return null;
        }
        ArrayDeque<Character> queue = getPatternQueue();
        Entry<StringBuilder, Integer> entry = parsePattern(date,queue);
        if(entry.value()==8){
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(entry.key().toString()));
        }
        if(entry.value()<8){
            return appendDate(date, entry, queue);
        }
        LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(entry.key().toString()));
        return dateTime.toLocalDate();
    }
    public static LocalDateTime parseDateTime(String date){
        if(!StrUtil.hasText(date)){
            return null;
        }
        ArrayDeque<Character> queue = getPatternQueue();
        Entry<StringBuilder, Integer> entry = parsePattern(date,queue);
        if(entry.value()==8){
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(entry.key().toString())).atStartOfDay();
        }
        if(entry.value()<8){
            return appendDate(date, entry, queue).atStartOfDay();
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(entry.key().toString()));
    }
    public static LocalDate parseDate(String date, String pattern){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }
    public static LocalDateTime parseDateTime(String date, String pattern){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    private static boolean isDatePattern(String pattern){
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if( c=='h' || c=='m' || c=='s'){
                return false;
            }
        }
        return true;
    }

    public static String format(LocalDate date, String pattern){
        if(isDatePattern(pattern)){
            return date.format( DateTimeFormatter.ofPattern(pattern) );
        }
        LocalDateTime localDateTime = date.atStartOfDay();
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String format(LocalDate date){
        if(date==null){
            return null;
        }
        return date.format( DateTimeFormatter.ofPattern(DATE_FORMAT) );
    }
    public static String format(LocalDateTime dateTime, String pattern){
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String format(LocalDateTime dateTime){
        return dateTime.format( DateTimeFormatter.ofPattern(DATE_TIME_FORMAT) );
    }

    public static LocalDateTime toLocalDateTime(Long milliseconds){
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    public static long toEpochMilli(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /*public static void main(String[] args) {
        System.out.println( parse("2022-11") );
        System.out.println( parse("2022-11-10") );
        System.out.println( parse("2022 年 11 余 10 日 ") );
        System.out.println( parse("2022-11-10 14:39:29  ") );

        System.out.println( parseDateTime("2022-11") );
        System.out.println( parseDateTime("2022-11-10") );
        System.out.println( parseDateTime("2022 年 11 余 10 日 ") );
        System.out.println( parseDateTime("2022-11-10 14:39:29  ") );
        System.out.println( parseDateTime("2022-11") );

        System.out.println(format(LocalDate.now(),"yyyy"));
        System.out.println(format(LocalDate.now(),"yyyy-MM-dd"));
        System.out.println(format(LocalDate.now(),"yyyy-MM-dd HH:mm:ss"));

        System.out.println(format(LocalDateTime.now(),"yyyy"));
        System.out.println(format(LocalDateTime.now(),"yyyy-MM-dd"));
        System.out.println(format(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss"));
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(format(dateTime,"HH:mm:ss"));
    }*/

}
