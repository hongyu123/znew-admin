package com.hfw.model.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名工具类
 * @author farkle
 * @date 2022-04-06
 */
public class SignUtil {

    /**
     * byte转16进制
     * @param bytes
     * @return
     */
    private static String byte2hex(byte[] bytes){
        StringBuilder sb = new StringBuilder();
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
    public static String md5(String s) throws NoSuchAlgorithmException {
        return md5(s.getBytes(StandardCharsets.UTF_8));
    }

    public static String md5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(bytes);
        return byte2hex(digest);
    }

    /**
     * sha256签名
     * @param s
     * @return
     * @throws Exception
     */
    public static String sha256(String s) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
        return byte2hex(bytes);
    }

    /**
     * SHA1签名
     * @param s
     * @return
     * @throws Exception
     */
    public static String sha1(String s) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
        return byte2hex(bytes);
    }

    public static String hmacSHA256(String s, String secretKey) throws Exception{
        byte[] keyByte = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] messageBytes = s.getBytes(StandardCharsets.UTF_8);
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(new SecretKeySpec(keyByte, 0, keyByte.length, "HmacSHA256"));
        byte[] hmacSha256Bytes = hmacSha256.doFinal(messageBytes);
        return SignUtil.byte2hex(hmacSha256Bytes);
    }

}
