package com.whut.tomasyao.auth.filter;

import edu.whut.pocket.auth.util.CookiesUtil;
import edu.whut.pocket.auth.util.LoginUtil;
import edu.whut.pocket.auth.vo.AdminRedisVo;
import edu.whut.pocket.auth.vo.UserRedisVo;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * edu.whut.pocket.admin.filter
 * Created by YTY on 2016/6/14.
 */
public class LoginCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().startsWith("/jsp/index")) {//登录页面所在的目录
            filterChain.doFilter(request, response);
        }else {//访问其它页面前判断是否登录 即从cookie里取token
            String token = CookiesUtil.getToken(request);
            if (token != null && !token.isEmpty()) {
                //从redis里获取用户信息(包括角色权限)
                AdminRedisVo adminRedisVo = LoginUtil.getAdminRedisVo(token);
                if (adminRedisVo != null) {
                    request.setAttribute("admin", adminRedisVo);
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            //重定向
            request.getRequestDispatcher("/jsp/index/login/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}
