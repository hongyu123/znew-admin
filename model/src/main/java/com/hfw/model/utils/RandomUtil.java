package com.hfw.model.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机字符串工具
 * @author farkle
 * @date 2022-05-11
 */
public class RandomUtil {
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String randomString(int length){
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int size = CHARS.length();
        for (int i = 0; i < length; i++) {
            sb.append( CHARS.charAt(random.nextInt(size)) );
        }
        return sb.toString();
    }
    public static String randomNumer(int length){
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            sb.append( CHARS.charAt(random.nextInt(10)) );
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
