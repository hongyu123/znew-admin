package com.hfw.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机字符串工具
 * @author farkle
 * @date 2022-05-11
 */
public class RandomUtil {
    private  static List<Character> charList = new ArrayList<>();
    static {
        for (char c = '0'; c <= '9'; c++) {
            charList.add(c);
        }
        for (char c = 'a'; c <= 'z'; c++) {
            charList.add(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            charList.add(c);
        }
    }

    public static String randomString(int length){
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            sb.append( charList.get(random.nextInt(charList.size()) ) );
        }
        return sb.toString();
    }
    public static String randomNumer(int length){
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            sb.append( charList.get(random.nextInt(10)) );
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(randomString(10));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(randomNumer(10));
        }
    }
}
