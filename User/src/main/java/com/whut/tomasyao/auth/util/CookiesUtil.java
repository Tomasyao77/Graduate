package com.whut.tomasyao.auth.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookiesUtil {

    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equals("token")) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

}
