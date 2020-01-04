package com.whut.tomasyao.weixin.util;

import java.util.Arrays;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-05-07 13:39
 */

public class SignUtil {
    public SignUtil() {
    }

    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        Arrays.sort(arr);
        String tmpStr = SHA1.encode(arr[0] + arr[1] + arr[2]);
        return tmpStr != null?tmpStr.equals(signature.toUpperCase()):false;
    }
}
