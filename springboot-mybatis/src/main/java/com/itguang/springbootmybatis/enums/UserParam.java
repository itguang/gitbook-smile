package com.itguang.springbootmybatis.enums;

import com.itguang.springbootmybatis.param.PageParam;

/**
 * @author itguang
 * @create 2017-12-08 15:05
 **/
public class UserParam extends PageParam {
    private String userName;

    private String userSex;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
}
