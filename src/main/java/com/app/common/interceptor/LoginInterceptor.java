package com.app.common.interceptor;

import com.app.common.entity.AppConfig;
import com.app.common.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private AppConfig appConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Object user = request.getSession().getAttribute(LoginUtil.LOGINUSER);
        if (user == null) {
            String requestUrl = request.getContextPath()+request.getServletPath();
            log.info("当前请求没有登录...请求url为： "+ requestUrl);
            LoginUtil.setInterceptorPath(requestUrl);
            response.sendRedirect(LoginUtil.LOGINPAGE);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle.......");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("afterCompletion.......");
    }
}
