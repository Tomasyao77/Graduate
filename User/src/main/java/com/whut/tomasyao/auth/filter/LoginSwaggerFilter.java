package com.whut.tomasyao.auth.filter;

import edu.whut.pocket.auth.dao.ISwaggerDao;
import edu.whut.pocket.auth.model.Swagger;
import edu.whut.pocket.auth.util.LoginUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * edu.whut.pocket.admin.filter
 * Created by zouy on 2018/5/6.
 */
public class LoginSwaggerFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoginSwaggerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().contains("/swagger-ui")) {//swagger
            try {
                ISwaggerDao swaggerDao = LoginUtil.getSwaggerDao();
                String ip = LoginUtil.getIpAddress(request);
                logger.info("LoginSwaggerFilter: ip "+ip);
                Swagger swagger = swaggerDao.findOne(" from Swagger s" +
                        " where s.ip='"+ip+"' ");
                boolean ifExist = swagger != null;
                if(ifExist){
                    logger.info("LoginSwaggerFilter: filterChain.doFilter()");
                    //如果ip存在放行
                    filterChain.doFilter(request, response);
                }else {
                    //否则跳转到登录页面
                    request.getRequestDispatcher("/jsp/index/login/loginSwagger.html").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

}
