package com.hfw.model.utils;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class Sm2Util {

    private String publicKey;
    private String privateKey;
    private SM2Engine.Mode mode = SM2Engine.Mode.C1C3C2;

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    public void setMode(SM2Engine.Mode mode) {
        this.mode = mode;
    }
    public String getPublicKey() {
        return publicKey;
    }
    public String getPrivateKey() {
        return privateKey;
    }
    public SM2Engine.Mode getMode() {
        return mode;
    }

    public void generateKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        //密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
        //SM2曲线参数
        ECNamedCurveParameterSpec sm2_curve = ECNamedCurveTable.getParameterSpec("sm2p256v1");
        //初始化生成器
        keyPairGenerator.initialize(sm2_curve,  new SecureRandom());
        // 获取密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        BCECPublicKey publicKey = (BCECPublicKey)keyPair.getPublic();
        BCECPrivateKey privateKey = (BCECPrivateKey)keyPair.getPrivate();
        //生成公钥 130 位
        this.publicKey = Hex.toHexString(publicKey.getQ().getEncoded(false));
        //压缩公钥到 66 位
        //this.publicKey = Hex.toHexString(publicKey.getQ().getEncoded(true));
        this.privateKey = Hex.toHexString(privateKey.getD().toByteArray());
    }

    /**
     * 公钥加密
     * @param publicKey 公钥
     * @param plaintext 加密数据
     * @param mode 模式
     */
    public static String encrypt(String publicKey, String plaintext, SM2Engine.Mode mode) throws InvalidCipherTextException {
        //获取一条SM2曲线参数
        ECNamedCurveParameterSpec sm2_curve = ECNamedCurveTable.getParameterSpec("sm2p256v1");
        //构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2_curve.getCurve(), sm2_curve.getG(), sm2_curve.getN());
        //提取公钥点
        ECPoint publicKeyPoint = domainParameters.getCurve().decodePoint(Hex.decode(publicKey));
        //公钥前面的02或者03表示是压缩公钥,04表示未压缩公钥
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(publicKeyPoint, domainParameters);
        //加密模式
        SM2Engine engine = new SM2Engine(mode);
        engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));
        byte[] bytes = plaintext.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = engine.processBlock(bytes, 0, bytes.length);
        return Hex.toHexString(encryptedBytes);
    }

    /**
     * 私钥解密
     * @param privateKey 私钥
     * @param ciphertext 密文
     * @param mode 模式
     */
    public static String decrypt(String privateKey, String ciphertext, SM2Engine.Mode mode) throws InvalidCipherTextException {
        // 使用BC库加解密时密文以04开头，传入的密文前面没有04则补上
        if (!ciphertext.startsWith("04")){
            ciphertext = "04" + ciphertext;
        }
        ECNamedCurveParameterSpec sm2_curve = ECNamedCurveTable.getParameterSpec("sm2p256v1");
        ECDomainParameters domainParameters = new ECDomainParameters(sm2_curve.getCurve(), sm2_curve.getG(), sm2_curve.getN());
        BigInteger privateKeyValue = new BigInteger(privateKey, 16);
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyValue, domainParameters);
        //解密模式
        SM2Engine engine = new SM2Engine(mode);
        engine.init(false, privateKeyParameters);

        byte[] bytes = Hex.decode(ciphertext);
        byte[] decryptBytes = engine.processBlock(bytes, 0, bytes.length);
        return new String(decryptBytes, StandardCharsets.UTF_8);
    }

    public String encrypt(String plaintext) throws InvalidCipherTextException {
        return encrypt(this.publicKey, plaintext, this.mode);
    }
    public String decrypt(String ciphertext) throws InvalidCipherTextException {
        return decrypt(this.privateKey, ciphertext, this.mode);
    }

}
