package com.whut.tomasyao.base.util;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-24 11:23
 */

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    // 数字签名，密钥算法
    private static final String RSA_KEY_ALGORITHM = "RSA";

    // 数字签名签名/验证算法
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    // RSA密钥长度
    private static final int KEY_SIZE = 1024;

    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";

    /**
     * 初始化RSA密钥对
     *
     * @return RSA密钥对
     * @throws Exception 抛出异常
     */
    public static Map<String, String> initKey() throws Exception {
        //密钥对不用随机生成,固定就好了.泄露了就像自己把钥匙给别人,还要别人不进你的门.
        /*KeyPairGenerator keygen = KeyPairGenerator
                .getInstance(RSA_KEY_ALGORITHM);
        SecureRandom secrand = new SecureRandom();
        secrand.setSeed("kdgg".getBytes());// 初始化随机产生器
        keygen.initialize(KEY_SIZE, secrand); // 初始化密钥生成器
        KeyPair keys = keygen.genKeyPair();
        String pub_key = new String(Base64.encodeBase64(keys.getPublic().getEncoded()));
        String pri_key = new String(Base64.encodeBase64(keys.getPrivate().getEncoded()));*/
        Map<String, String> keyMap = new HashMap<>();
        /*keyMap.put(PUBLIC_KEY, pub_key);
        keyMap.put(PRIVATE_KEY, pri_key);*/
        keyMap.put(PUBLIC_KEY, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKf9c6sVP4QcxWXC4bpPjD1F/g/6jf8ZasEs0v" +
                "ig02kYZA1frLeH/MMMeLse5DDAVJd526od6d8zahYM0W1KyKS9dAC8mWoXdu8EeqE7Oy4DgOm41s" +
                "PkWQV+VSnfmhF3DBvkxmeR2hE9LFAS5+xDaYjGvru9m6e3ScyDo4QKLkYwIDAQAB");
        keyMap.put(PRIVATE_KEY, "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIp/1zqxU/hBzFZcLhuk+MPUX+D/" +
                "qN/xlqwSzS+KDTaRhkDV+st4f8wwx4ux7kMMBUl3nbqh3p3zNqFgzRbUrIpL10ALyZahd27wR6oT" +
                "s7LgOA6bjWw+RZBX5VKd+aEXcMG+TGZ5HaET0sUBLn7ENpiMa+u72bp7dJzIOjhAouRjAgMBAAEC" +
                "gYBRmDNivqnrrkgF8NSxMKXmKS47TiNdm+Tah1Ee2sQiC+X+OJJCYUFZOLu2zyLBmUlcGP5xv395" +
                "qpnaxT7OXokvKObWW4cscE1zPsMknUP/8ugLHS8Fs90mLn9Vv3xLUEognQYvhaczuKsAFfAD8ebJ" +
                "cZv1pmiMHb0EfnEUC4UiAQJBANbUnQxmC4zfhTlmr4kFYmBpp7SsMCaFgJue/RlSK5BsBcXFqNDt" +
                "mDotz+8Wn8CFQ0PwZS4VhcGrT1mEUtiUvuMCQQClCn31Ll4d0tw6CfjUD+tTlNz30B9WY4AtHTy3" +
                "C4GybMJlCMXTo3X5aZjPGVSjbXf4hhYL7bIQpoEqnnzQgLyBAkEAlWvWO/xyBi6ljBI2/1fGYAa8" +
                "xbaJ2xE6LYN8yPLVKCNqNG/RhlfrbHPWz5Xt0UsfSQ7YYSBJIzZS/5FAyjFTPwJADu0G/TCHVH0o" +
                "a5tRRKR37OQuo77PxrlS80GMpyVEQoWxgv+rHHvRBJB3QGuTCwCwc+p0ffmuO/FDBz60HQs3gQJA" +
                "IoURqXrV/I4BLgjDJroGG9sDyhoKp4C330LL/E0uFSzW2ioO1adnP/fVM8Ap3+c2VFnfOaktrhLQ" +
                "6oGcD9r9DA==");
        return keyMap;
    }

    /**
     * 得到公钥
     *
     * @param keyMap RSA密钥对
     * @return 公钥
     * @throws Exception 抛出异常
     */
    public static String getPublicKey(Map<String, String> keyMap) throws Exception {
        return keyMap.get(PUBLIC_KEY);
    }

    /**
     * 得到私钥
     *
     * @param keyMap RSA密钥对
     * @return 私钥
     * @throws Exception 抛出异常
     */
    public static String getPrivateKey(Map<String, String> keyMap) throws Exception {
        return keyMap.get(PRIVATE_KEY);
    }

    /**
     * 数字签名
     *
     * @param data    待签名数据
     * @param pri_key 私钥
     * @return 签名
     * @throws Exception 抛出异常
     */
    public static String sign(byte[] data, String pri_key) throws Exception {
        // 取得私钥
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key.getBytes());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key_bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 生成私钥
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data);

        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * RSA校验数字签名
     *
     * @param data    数据
     * @param sign    签名
     * @param pub_key 公钥
     * @return 校验结果，成功为true，失败为false
     * @throws Exception 抛出异常
     */
    public boolean verify(byte[] data, byte[] sign, String pub_key) throws Exception {
        // 转换公钥材料
        // 实例化密钥工厂
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 初始化公钥
        // 密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key_bytes);
        // 产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(pubKey);
        // 更新
        signature.update(data);
        // 验证
        return signature.verify(sign);
    }

    /**
     * 公钥加密
     *
     * @param data    待加密数据
     * @param pub_key 公钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    public static byte[] encryptByPubKey(byte[] data, byte[] pub_key) throws Exception {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     *
     * @param data    待加密数据
     * @param pub_key 公钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    public static String encryptByPubKey(String data, String pub_key) throws Exception {
        // 私匙加密
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key.getBytes());
        byte[] enSign = encryptByPubKey(data.getBytes(), pub_key_bytes);
        return new String(Base64.encodeBase64(enSign));
    }

    /**
     * 私钥加密
     *
     * @param data    待加密数据
     * @param pri_key 私钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    public static byte[] encryptByPriKey(byte[] data, byte[] pri_key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥加密
     *
     * @param data    待加密数据
     * @param pri_key 私钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    public static String encryptByPriKey(String data, String pri_key) throws Exception {
        // 私匙加密
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key.getBytes());
        byte[] enSign = encryptByPriKey(data.getBytes(), pri_key_bytes);
        return new String(Base64.encodeBase64(enSign));
    }

    /**
     * 公钥解密
     *
     * @param data    待解密数据
     * @param pub_key 公钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static byte[] decryptByPubKey(byte[] data, byte[] pub_key) throws Exception {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data    待解密数据
     * @param pub_key 公钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static String decryptByPubKey(String data, String pub_key) throws Exception {
        // 公匙解密
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key.getBytes());
        byte[] design = decryptByPubKey(Base64.decodeBase64(data.getBytes()), pub_key_bytes);
        return new String(design);
    }

    /**
     * 私钥解密
     *
     * @param data    待解密数据
     * @param pri_key 私钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static byte[] decryptByPriKey(byte[] data, byte[] pri_key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data    待解密数据
     * @param pri_key 私钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static String decryptByPriKey(String data, String pri_key) throws Exception {
        // 私匙解密
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key.getBytes());
        byte[] design = decryptByPriKey(Base64.decodeBase64(data.getBytes()), pri_key_bytes);
        return new String(design);
    }

    /*public static void main(String[] args) throws Exception {

        RSATest das = new RSATest();

        String datastr = "天街小雨润如酥，草色遥看近却无。最是一年春好处，绝胜烟柳满皇都。cesk";
        System.out.println("待加密数据：\n" + datastr);
        //获取密钥对
        Map<String, String> keyMap = new HashMap<String, String>();
        keyMap = das.initKey();
        String pub_key = (String) keyMap.get(PUBLIC_KEY);
        String pri_key = (String) keyMap.get(PRIVATE_KEY);
        // 公匙加密
        String pubKeyStr = RSATest.encryptByPubKey(datastr, pub_key);
        System.out.println("公匙加密结果：\n" + pubKeyStr);
        // 私匙解密
        String priKeyStr = RSATest.decryptByPriKey(pubKeyStr, pri_key);
        System.out.println("私匙解密结果：\n" + priKeyStr);

        //换行
        System.out.println();

        // 数字签名
        String str1 = "汉兵已略地";
        String str2 = "四面楚歌声";
        System.out.println("正确的签名：" + str1 + "\n错误的签名：" + str2);
        String sign = RSATest.sign(str1.getBytes(), pri_key);
        System.out.println("数字签名：\n" + sign);
        boolean vflag1 = das.verify(str1.getBytes(), Base64.decodeBase64(sign.getBytes()), pub_key);
        System.out.println("数字签名验证结果1：\n" + vflag1);
        boolean vflag2 = das.verify(str2.getBytes(), Base64.decodeBase64(sign.getBytes()), pub_key);
        System.out.println("数字签名验证结果2：\n" + vflag2);
    }*/
}

