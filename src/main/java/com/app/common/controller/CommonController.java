package com.app.common.controller;

import com.app.common.entity.AppConfig;
import com.app.common.util.Util;
import com.app.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 通用Controller，页面跳转等功能
 */
@Controller
public class CommonController {

    @Autowired
    private AppConfig appConfig;

    @RequestMapping("/{page}.html")
    public String showPage(@PathVariable String page) {
        return page;
    }

    /**
     * 跳转到首页,首页路径在application.yml文件中配置
     * @param model 可以向请求中放值
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {
        User user = new User();
        user.setUsername("username1");
        user.setPassword("password1");
        user.setLoginDt(new Date());
        System.out.println(Util.beanToJson(user));
        model.addAttribute("user", Util.beanToJson(user));
        return appConfig.getIndex();
    }

}
