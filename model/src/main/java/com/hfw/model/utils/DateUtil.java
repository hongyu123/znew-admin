package com.hfw.model.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author farkle
 * @date 2022-04-06
 */
public class DateUtil {
	/**
	 * 默认日期格式-24小时
	 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式-12小时
	 */
	public static final String FORMAT_12 = "yyyy-MM-dd hh:mm:ss a";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 日期转字符串
	 * @param date 日期
	 * @return 字符串
	 */
	public static String format(Date date) {
		return format(date,DEFAULT_FORMAT);
	}
	
	/**
	 * 日期转字符串
	 * @param date 日期
	 * @param format 格式
	 * @return 字符串
	 */
	public static String format(Date date, String format) {
		if(date==null){
			return "";
		}
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}
	/**
	 * 字符串转日期
	 * @param dateStr 字符串
	 * @return 日期
	 */
	public static Date parse(String dateStr) throws ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat(DEFAULT_FORMAT);
		return fmt.parse(dateStr);
	}
	/**
	 * 字符串转日期
	 * @param dateStr 字符串
	 * @param format 格式
	 * @return 日期
	 */
	public static Date parse(String dateStr, String format) throws ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.parse(dateStr);
	}

	/**
	 * 解析世界标准时间
	 * @param date 2018-06-08T10:34:56+08:00
	 * @return
	 */
	public static Date parseStandard(String date) throws ParseException {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		return fmt.parse(date);
	}


	/**
	 * 日期加减
	 * @param date 日期
	 * @param type Calendar.YEAR MONTH DATE
	 * @param i 加减量
	 * @return
	 */
	public static Date add(Date date , int type, int i){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, i);//把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime();
	}

	/**
	 * 判断是否同一天
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static boolean isSameDay(Date day1, Date day2){
		return format(day1,DATE_FORMAT).equals(format(day2,DATE_FORMAT));
	}

	/**
	 * 计算两个时间相差的天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1,Date date2)
	{
		return (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
	}

	/**
	 * 计算两个日期相差的小时
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static double differentHours(Date date1, Date date2){
		return ((date1.getTime() - date2.getTime()) / (1000*3600.0));
	}

}
