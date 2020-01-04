package com.whut.tomasyao.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-10 10:24
 */

public class StringUtil {

    //随机长度的字符串
    public static String getRandomString(Integer length) {
        length = length == null ? 8 : length;//默认8位
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //随机长度的验证码
    public static String getRandomCode(Integer length) {
        length = length == null ? 4 : length;//默认4位
        String str = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    //map字符串转回map对象
    public static Map<String,String> mapStringToMap(String str){
        str=str.substring(1, str.length()-1);
        String[] strs=str.split(",");
        Map<String,String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key=string.split("=")[0].trim();
            String value=string.split("=")[1].trim();
            map.put(key, value);
        }
        return map;
    }

}
