package com.hfw.common.support;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author farkle
 * @date 2022-11-09
 */
public class AesUtil {
    private String algorithm = "AES";
    //密钥
    private String key = "";
    //加密模式
    private String transformation = "AES/ECB/PKCS5Padding";

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public String base64Encode(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }

    public byte[] base64Decode(String base64Code) {
        return StringUtils.isEmpty(base64Code) ? null : Base64.getDecoder().decode(base64Code);
    }

    public byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), algorithm);
        //向量
        IvParameterSpec iv = new IvParameterSpec(new byte[16]);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
    }

    public String aesEncrypt(String content) throws Exception {
        return base64Encode(aesEncryptToBytes(content, this.key));
    }

    public String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), algorithm);
        //向量
        IvParameterSpec iv = new IvParameterSpec(new byte[16]);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes, StandardCharsets.UTF_8);
    }

    public String aesDecrypt(String encryptStr) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), this.key);
    }

    public static void main(String[] args) throws Exception {
        AesUtil aes = new AesUtil();
        aes.setTransformation("AES/CBC/PKCS5Padding");
        aes.setKey("1234567890123456");
        String enc = aes.aesEncrypt("abcdefghigklmnopqrstuvwxyz0123456789");
        String dec = aes.aesDecrypt(enc);
        System.out.println(enc);
        System.out.println(dec);
    }
}