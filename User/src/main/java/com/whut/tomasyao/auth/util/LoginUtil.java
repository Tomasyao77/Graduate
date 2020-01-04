package com.whut.tomasyao.auth.util;

import edu.whut.pocket.auth.dao.IAdminRedisVoDao;
import edu.whut.pocket.auth.dao.ISwaggerDao;
import edu.whut.pocket.auth.dao.IUserRedisVoDao;
import edu.whut.pocket.auth.vo.AdminRedisVo;
import edu.whut.pocket.auth.vo.UserRedisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * edu.whut.change.admin.util
 * Created by YTY on 2016/6/14.
 */
@Component
public class LoginUtil {

    private static IUserRedisVoDao userRedisVoDao;
    private static IAdminRedisVoDao adminRedisVoDao;
    private static ISwaggerDao swaggerDao;

    public static UserRedisVo getUserRedisVo(String token) {
        return userRedisVoDao.get(token);
    }

    public static AdminRedisVo getAdminRedisVo(String token) {
        return adminRedisVoDao.get(token);
    }

    public static ISwaggerDao getSwaggerDao() {
        return swaggerDao;
    }

    @Autowired
    public void setUserRedisVoDao(IUserRedisVoDao userRedisVoDao) {
        LoginUtil.userRedisVoDao = userRedisVoDao;
    }

    @Autowired
    public void setAdminRedisVoDao(IAdminRedisVoDao adminRedisVoDao) {
        LoginUtil.adminRedisVoDao = adminRedisVoDao;
    }

    @Autowired
    public void setSwaggerDao(ISwaggerDao swaggerDao) {
        LoginUtil.swaggerDao = swaggerDao;
    }

    public static String getIpAddress(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
