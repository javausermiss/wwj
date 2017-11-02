package com.fh.util.wwjUtil;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具类
 */
public class AESUtils {
    /**
     * 加密
     * @param sSrc 要加密的字符串
     * @param sKey 秘钥（长度必须为16个字符）
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {
        String str = "";
        if (sKey == null) {
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        str = new Base64().encodeToString(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return str;
    }

    /**
     * 解密
     * @param sSrc 要解密的字符串
     * @param sKey 秘钥（长度必须为16个字符）
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {
        String originalString = "";
        // 判断Key是否正确
        if (sKey == null) {
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = new Base64().decode(sSrc);// 先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        originalString = new String(original, "utf-8");
        return originalString;
    }


}
