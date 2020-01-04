package com.whut.tomasyao.base.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author: zouy
 * Unit: D9lab
 * Date: 2018-07-10 10:33
 */
//没必要用这个
public class IfCheckUtil {

    public static boolean ifExistInArray(String s, String[] strings){
        String[] ss = strings;
        return Arrays.asList(ss).contains(s);
    }

}
