package com.whut.tomasyao.weixin.util;

import java.security.MessageDigest;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:39
 */

public class SHA1 {
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public SHA1() {
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);

        for(int j = 0; j < len; ++j) {
            buf.append(HEX_DIGITS[bytes[j] >> 4 & 15]);
            buf.append(HEX_DIGITS[bytes[j] & 15]);
        }

        return buf.toString();
    }

    public static String encode(String str) {
        if(str == null) {
            return null;
        } else {
            try {
                MessageDigest e = MessageDigest.getInstance("SHA1");
                e.update(str.getBytes());
                return getFormattedText(e.digest());
            } catch (Exception var2) {
                throw new RuntimeException(var2);
            }
        }
    }
}
