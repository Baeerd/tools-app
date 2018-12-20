package com.app.user.entity;

import com.app.common.entity.AbstractEntity;

import java.util.Date;

/**
 * 用户表
 */
public class User extends AbstractEntity{

    private String username;

    private String password;

    private String age;

    private String email;

    private String gender;

    private Date loginDt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getLoginDt() {
        return loginDt;
    }

    public void setLoginDt(Date loginDt) {
        this.loginDt = loginDt;
    }
}
