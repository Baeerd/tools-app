package com.app.user.service.impl;

import com.app.common.exception.MessageException;
import com.app.common.service.impl.BaseServiceImpl;
import com.app.user.entity.User;
import com.app.user.mapper.UserMapper;
import com.app.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User validateLogin(String username, String password) {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new MessageException("用户名密码不能为空！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        List<User> userList = userMapper.find(params);
        if(userList == null || userList.size() == 0) {
            log.warn(username + "用户名不存在！");
            throw new MessageException("用户名不存在！");
        }
        User user = userList.get(0);
        // 校验用户名密码是否相同
        if(!password.equals(user.getPassword())) {
            log.warn(password + "密码不正确！");
            throw new MessageException("密码不正确！");
        }
        return user;
    }
}
