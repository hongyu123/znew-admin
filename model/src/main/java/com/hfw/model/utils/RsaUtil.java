package com.hfw.model.utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtil {
    public String algorithm = "RSA";
    private String publicKey;
    private String privateKey;

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    public String getPublicKey() {
        return publicKey;
    }
    public String getPrivateKey() {
        return privateKey;
    }

    public void generateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        // 指定密钥长度，推荐使用2048位或更高
        keyPairGenerator.initialize(2048);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        this.publicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        this.privateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static String encrypt(String algorithm, Key key, String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String algorithm, Key key, String ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decodedData);
    }

    private static String encryptByPublicKey(String algorithm, String publicKey, String plaintext) throws  Exception {
        byte[] decodedPublicKey = Base64.getDecoder().decode(publicKey);
        KeySpec keySpec = new X509EncodedKeySpec(decodedPublicKey);
        PublicKey pubKey = KeyFactory.getInstance(algorithm).generatePublic(keySpec);
        return RsaUtil.encrypt(algorithm, pubKey, plaintext);
    }
    private static String decryptByPrivateKey(String algorithm, String privateKey, String ciphertext) throws  Exception {
        byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKey);
        KeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
        PrivateKey priKey = KeyFactory.getInstance(algorithm).generatePrivate(keySpec);
        return RsaUtil.decrypt(algorithm, priKey, ciphertext);
    }

    private static String encryptByPrivateKey(String algorithm, String privateKey, String plaintext) throws  Exception {
        byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKey);
        KeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
        PrivateKey priKey = KeyFactory.getInstance(algorithm).generatePrivate(keySpec);
        return RsaUtil.encrypt(algorithm, priKey, plaintext);
    }
    private static String decryptByPublicKey(String algorithm, String publicKey, String ciphertext) throws  Exception {
        byte[] decodedPublicKey = Base64.getDecoder().decode(publicKey);
        KeySpec keySpec = new X509EncodedKeySpec(decodedPublicKey);
        PublicKey pubKey = KeyFactory.getInstance(algorithm).generatePublic(keySpec);
        return RsaUtil.decrypt(algorithm, pubKey, ciphertext);
    }

    public String encryptByPublicKey(String plaintext) throws Exception {
        return RsaUtil.encryptByPublicKey(algorithm, publicKey, plaintext);
    }
    public String decryptByPrivateKey(String ciphertext) throws  Exception {
        return RsaUtil.decryptByPrivateKey(algorithm, privateKey, ciphertext);
    }
    public String encryptByPrivateKey(String plaintext) throws Exception {
        return RsaUtil.encryptByPrivateKey(algorithm, privateKey, plaintext);
    }
    public String decryptByPublicKey(String ciphertext) throws  Exception {
        return RsaUtil.decryptByPublicKey(algorithm, publicKey, ciphertext);
    }

}
