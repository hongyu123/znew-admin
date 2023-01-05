package com.hfw.common.util;

import java.text.DecimalFormat;

/**
 * Number工具类
 * @author zyh
 * @date 2022-04-06
 */
public class NumberUtil {

	/**
	 * 判读是否是奇数
	 * 本质:判断第一位是否为1
	 * @param n
	 * @return
	 */
	public static boolean isOdd(int n){
		return (n&1) == 1;
	}
	/**
	 * 判读是否是偶数
	 * @param n
	 * @return
	 */
	public static boolean isEven(int n){
		return (n&1) == 0;
	}

	/**
	 * 数字保留两位小数
	 * @return
	 */
	public static String scale2(Number n) {
		return new DecimalFormat("####.##").format(n);
	}

}
