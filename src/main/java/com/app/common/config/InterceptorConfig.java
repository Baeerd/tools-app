package com.app.common.config;

import com.app.common.entity.AppConfig;
import com.app.common.interceptor.LoginInterceptor;
import com.app.common.util.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置类，用于注册拦截器
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AppConfig appConfig;

    /**
     * 配置静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/META-INF/resources/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/META-INF/resources/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/META-INF/resources/img/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/META-INF/resources/static/");
    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new LoginInterceptor());
        // 拦截的地址
        String[] patterns = new String[]{
                "/"
        };
        interceptorRegistration.addPathPatterns(patterns);
        // 不拦截的地址
        String[] exPatterns = new String[]{
                "/system/login",
                "/login.html"
        };
        interceptorRegistration.excludePathPatterns(exPatterns);
    }
}
