package com.mylike.his.entity;

/**
 * Created by thl on 2018/12/29.
 */

public class LoginResp extends BaseNewEntity {
    private LoginEntity loginInfo;

    public LoginEntity getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginEntity loginInfo) {
        this.loginInfo = loginInfo;
    }
}
