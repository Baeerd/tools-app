package com.app.common.interceptor;

import com.app.common.entity.Response;
import com.app.common.exception.MessageException;
import com.app.common.util.Util;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 */
@Configuration
public class MessageInterceptor implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();

        if(e instanceof MessageException){
            try {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setCharacterEncoding("UTF-8");
                response.setHeader("Cache-Control", "no-cache, must-revalidate");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(Util.beanToJson(new Response().failure(e.getMessage())));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return mv;
    }

}
