package com.hfw.model.utils;

import javax.crypto.Cipher;
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

    public static String encryptByPublicKey(String algorithm, PublicKey publicKey, String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decryptByPrivateKey(String algorithm, PrivateKey privateKey, String ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedData = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decodedData);
    }

    private static String encryptByPublicKey(String algorithm, String publicKey, String plaintext) throws  Exception {
        byte[] decodedPublicKey = Base64.getDecoder().decode(publicKey);
        KeySpec keySpec = new X509EncodedKeySpec(decodedPublicKey);
        PublicKey pubKey = KeyFactory.getInstance(algorithm).generatePublic(keySpec);
        return RsaUtil.encryptByPublicKey(algorithm, pubKey, plaintext);
    }
    private static String decryptByPrivateKey(String algorithm, String privateKey, String ciphertext) throws  Exception {
        byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKey);
        KeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
        PrivateKey priKey = KeyFactory.getInstance(algorithm).generatePrivate(keySpec);
        return RsaUtil.decryptByPrivateKey(algorithm, priKey, ciphertext);
    }

    public String encryptByPublicKey(String plaintext) throws Exception {
        return RsaUtil.encryptByPublicKey(algorithm, publicKey, plaintext);
    }
    public String decryptByPrivateKey(String ciphertext) throws  Exception {
        return RsaUtil.decryptByPrivateKey(algorithm, privateKey, ciphertext);
    }

    public static void main(String[] args) throws Exception {
        RsaUtil rsa = new RsaUtil();
        String data = "KYUCCNABCDEFGHIJ";
        rsa.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCftkekOWqsntfcAvJxIhlK3rGzg3IbkAL4Vw0Jh2SSWBbqKt9mlWIgQ2sR1Zsjo1KXJ7Y9fU5TU0VIQM7mHDzwztbWHV0XwXR2VGjk45utoBWXKxTiGpvmCjPB5JPp+GxlnHLb6dvVKOKbZ6MDLZbSwFDqJ4nHlVP42LIWwFKt9QIDAQAB");
        rsa.setPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ+2R6Q5aqye19wC8nEiGUresbODchuQAvhXDQmHZJJYFuoq32aVYiBDaxHVmyOjUpcntj19TlNTRUhAzuYcPPDO1tYdXRfBdHZUaOTjm62gFZcrFOIam+YKM8Hkk+n4bGWcctvp29Uo4ptnowMtltLAUOoniceVU/jYshbAUq31AgMBAAECgYBKvVWHX6sw/uCLQAHOuaNWayKDnFaw2VLafnpGZErHRVvr14ZWDkAuUv5vCSQhqFOFEvtwiQw3aDd62YE5JCvL0I4D2LVNpEHSChuKg5qePl1mnZiCDfip0gMZyaMR1u74u/YlYXXRK41u1sdtSwksalWRvG6lFSfH0QHqUNZafQJBAPQ0HfXJNPufBcXG7B6Ga87xnPJ3KTNVE/6CQsixVOys8UAjXxdBX/EJ3RxBACWsRd/VMiIxJtxp4npcceoDup8CQQCnbVGri6cPhvk+NgVMOzaKQDUxr9jRr0/3PCuJ/nuezUnh3dZ1RZHt7V/T2U8tsRN84FobS8+CjwLr2uVQGuLrAkEA1Tdv9TsBdLTK8H0Xiitpk91nYFhkc7pT48LOFramZKM3XP3FN+PPpgAru1CRlVMOCzn1NN9fg9E7egSfPWiWFwJAB48xA8zPYy0V7dAklxeJU96oSDEHWhhPRPtrf9SEolvkfRU2DJ9ygkqYbAlAwPBgz9+VUewvV1a7rAh7GA3OtwJAKE8P3EmY64C5ggIhG3C/ceVtR2yEt2YwNMEBG0z2v1oM7n3KY+CXEnlIScDamXvEd8uV/9xI+8v/4HDIRqzzdA==");
        String ciphertext = rsa.encryptByPublicKey(data);
        System.out.println(ciphertext);
        System.out.println(rsa.decryptByPrivateKey(ciphertext));
    }

}
