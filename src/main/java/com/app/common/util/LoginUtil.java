package com.app.common.util;

import com.app.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUtil {

    /**
     * 缓存用户信息
     */
    private static User user;

    public static final String LOGINUSER = "loginUser";

    public static final String LOGINPAGE = "/login.html";

    private static String interceptorPath;

    private static Logger log = LoggerFactory.getLogger(LoginUtil.class);

    /**
     * 登陆，缓存用户信息
     * @param currentUser
     */
    public static void login(User currentUser) {
        // 缓存用户信息
        user = currentUser;
    }

    /**
     * 获取此时登陆用户名
     * @return
     */
    public static String getUserName() {
        return user==null?"":user.getUsername();
    }

    /**
     * 获取此时登陆用户密码
     * @return
     */
    public static String getPassword() {
        return user==null?"":user.getPassword();
    }

    /**
     * 判断是否是当前登录人
     * @param userName
     * @return
     */
    public static boolean isCurrentUser(String userName) {
        if(user == null) {
            log.error("LoginUtil.isCurrentUser() Error: 没有当前登陆人信息");
            return false;
        }
        return user.getUsername().equals(userName);
    }

    public static void setInterceptorPath(String path) {
        interceptorPath = path;
    }

    public static String getInterceptorPath() {
        return interceptorPath;
    }

}
