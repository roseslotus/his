package com.mylike.his.entity;

/**
 * Created by zhengluping on 2018/6/8.
 */
public class TokenEntity {
    private String token;
    private UserInfoEntity userInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }
}
