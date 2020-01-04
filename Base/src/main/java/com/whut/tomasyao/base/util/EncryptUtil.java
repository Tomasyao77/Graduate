package com.whut.tomasyao.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * com.whut.athena.util
 * Created by YTY on 2016/3/31.
 */
public class EncryptUtil {

    //md5 32位小写
    public static String md5(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString().toLowerCase();
    }

}
