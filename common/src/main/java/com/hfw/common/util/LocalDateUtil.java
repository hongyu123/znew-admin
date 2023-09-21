package com.hfw.common.util;

import com.hfw.common.support.Entry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;


/**
 * LocalDate工具类
 * @author farkle
 * @date 2022-11-10
 */
public class LocalDateUtil {
    private static ArrayDeque<Character> queue = getPatternQueue();

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
        int dateIndex = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if( queue.contains(pattern.charAt(i)) ){
                dateIndex ++ ;
            }
        }
        return dateIndex<=8;
    }

    public static String toString(LocalDate date, String pattern){
        if(isDatePattern(pattern)){
            return date.format( DateTimeFormatter.ofPattern(pattern) );
        }
        LocalDateTime localDateTime = date.atStartOfDay();
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    public static String toString(LocalDateTime dateTime, String pattern){
        if(isDatePattern(pattern)){
            return dateTime.toLocalDate().format( DateTimeFormatter.ofPattern(pattern) );
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static void main(String[] args) {
        System.out.println( parse("2022-11") );
        /*System.out.println( parse("2022-11-10") );
        System.out.println( parse("2022 年 11 余 10 日 ") );
        System.out.println( parse("2022-11-10 14:39:29  ") );

        System.out.println( parseDateTime("2022-11") );
        System.out.println( parseDateTime("2022-11-10") );
        System.out.println( parseDateTime("2022 年 11 余 10 日 ") );
        System.out.println( parseDateTime("2022-11-10 14:39:29  ") );
        System.out.println( parseDateTime("2022-11") );

        System.out.println(toString(LocalDate.now(),"yyyy"));
        System.out.println(toString(LocalDate.now(),"yyyy-MM-dd"));
        System.out.println(toString(LocalDate.now(),"yyyy-MM-dd HH:mm:ss"));

        System.out.println(toString(LocalDateTime.now(),"yyyy"));
        System.out.println(toString(LocalDateTime.now(),"yyyy-MM-dd"));
        System.out.println(toString(LocalDateTime.now(),"yyyy-MM-dd HH:mm:ss"));*/

    }
}
