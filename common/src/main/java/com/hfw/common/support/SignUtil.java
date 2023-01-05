package com.hfw.common.support;

import java.security.MessageDigest;

/**
 * 签名工具类
 * @author zyh
 * @date 2022-04-06
 */
public class SignUtil {

    /**
     * byte转16进制
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes){
        StringBuilder sb = new StringBuilder("");
        for (byte b : bytes){
            String temp = Integer.toHexString( b&0xFF );
            if(temp.length()==1){
                sb.append("0");
            }
            sb.append(temp);
        }
        return sb.toString();
    }

    /***
     * md5签名
     * @param s
     * @return
     * @throws Exception
     */
    public static String md5(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(s.getBytes("utf-8"));
        return byte2hex(bytes);
    }

    /**
     * sha256签名
     * @param s
     * @return
     * @throws Exception
     */
    public static String sha256(String s) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(s.getBytes("utf-8"));
        return byte2hex(bytes);
    }

}
