package com.app.user.controller;

import com.app.common.controller.BaseController;
import com.app.common.entity.AppConfig;
import com.app.common.entity.Response;
import com.app.common.exception.MessageException;
import com.app.common.util.Util;
import com.app.user.entity.User;
import com.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册登陆相关操作Controller
 */
@RequestMapping("/user")
@RestController
public class UserController extends BaseController<User>{

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Response login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String json = getJsonFromRequest(request);
        Map<String, String> params = Util.jsonToMap(json);
        // 根据用户名密码查询数据库
        try {
//            User user = userService.validateLogin(params.get("username"), params.get("password"));
            User user = new User();
            user.setUsername("admin");
            user.setPassword("111111");
            Map<String, String> map = new HashMap<>();
            map.put("token", "12111111111111");
            return new Response().success(map);
        } catch (MessageException e) {
            request.setAttribute("error", e.getMessage());
        }
        return new Response().success("12312312312312");
    }

    @RequestMapping("/info")
    @ResponseBody
    public Response info() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("111111");
        return new Response().success(user);
    }

    @RequestMapping("/regist")
    public void regist(HttpServletRequest request) {
        String json = this.getJsonFromRequest(request);
        User user = Util.jsonToBean(json, User.class);
        System.out.println(user);
    }

}
