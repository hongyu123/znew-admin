package com.hfw.model.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

/**
 * 对称加密
 * @author farkle
 * @date 2022-11-09
 */
public class AesUtil {
    //加密算法
    private String algorithm = "AES";
    //密钥
    private String key = "";
    private byte[] keys;
    //加密模式
    private String mode = "ECB";
    //填充方式
    private String padding = "PKCS5Padding";
    //向量
    private byte[] iv;
    private String provider;

    public AesUtil(String algorithm, String mode, String padding) {
        this.algorithm = algorithm;
        this.mode = mode;
        this.padding = padding;
    }
    public AesUtil(String transformation) {
        String[] split = transformation.split("/");
        this.algorithm = split[0];
        this.mode = split[1];
        this.padding = split[2];
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

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
    public byte[] getKeys() {
        return keys;
    }
    public void setKeys(byte[] keys) {
        this.keys = keys;
    }
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
    public String getPadding() {
        return padding;
    }
    public void setPadding(String padding) {
        this.padding = padding;
    }
    public byte[] getIv() {
        return iv;
    }
    public void setIv(byte[] iv) {
        this.iv = iv;
    }
    public String getProvider() {
        return provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public byte[] doFinal(int encrypt_mode, byte[] key, byte[] data, byte[] iv) throws Exception{
        String transformation = algorithm+"/"+mode+"/"+padding;
        Cipher cipher = provider==null ?Cipher.getInstance(transformation) :Cipher.getInstance(transformation, provider);
        SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
        if(iv!=null){
            IvParameterSpec ivParameter = new IvParameterSpec(iv);
            cipher.init(encrypt_mode, secretKey, ivParameter);
        }else{
            cipher.init(encrypt_mode, secretKey);
        }
        return cipher.doFinal(data);
    }

    /**
     * 加密
     * @param plaintext 明文
     * @return base64编码密文
     * @throws Exception
     */
    public String encrypt(String plaintext) throws Exception {
        if(keys==null){
            keys = key.getBytes();
        }
        byte[] encrypt = doFinal(Cipher.ENCRYPT_MODE, keys, plaintext.getBytes(), iv);
        return Base64.getEncoder().encodeToString(encrypt);
    }

    /**
     * 解密
     * @param ciphertext base64编码密文
     * @return 明文
     * @throws Exception
     */
    public String decrypt(String ciphertext) throws Exception {
        if(keys==null){
            keys = key.getBytes();
        }
        byte[] decrypt = doFinal(Cipher.DECRYPT_MODE, keys, Base64.getDecoder().decode(ciphertext), iv);
        return new String(decrypt, StandardCharsets.UTF_8);
    }


    public static void main(String[] args) throws Exception {
        AesUtil aes = new AesUtil("AES/CBC/PKCS5Padding");
        aes.setKey("1234567890123456");
        aes.setIv(new byte[16]);
        String ciphertext = aes.encrypt("abcdefghigklmnopqrstuvwxyz0123456789");
        String plaintext = aes.decrypt(ciphertext);
        System.out.println(ciphertext); //8Z3dZzqn05FmiuBLowExK0CAbs4TY2GorC2dDPVlsn/tP+VuJGePqIMv1uSaVErr
        System.out.println(plaintext);

        aes = new AesUtil("AES/ECB/PKCS7Padding");
        aes.setProvider("BC");
        aes.setKeys(Base64.getDecoder().decode("pyJxan+SxWU8sGiZYL9Nqw=="));
        ciphertext = aes.encrypt("1");
        plaintext = aes.decrypt(ciphertext);
        System.out.println(ciphertext); //e2eXyCsfJ66lPHYb9gm44w==
        System.out.println(plaintext);
    }

}
