package com.whut.tomasyao.base.util;

/**
 * edu.whut.change.base.util
 * Created by YTY on 2016/8/11.
 */
public class ParamUtil {
    public static Integer[] stringToIntegerArray(String p) {
        if (p == null || p.isEmpty()) {
            return new Integer[0];
        }
        String[] strings = p.split(",");
        Integer[] integers = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            integers[i] = Integer.parseInt(strings[i].trim());
        }
        return integers;
    }

    public static String[] stringToStringArray(String p) {
        if (p == null || p.isEmpty()) {
            return new String[0];
        }
        return p.split(",");
    }
}
