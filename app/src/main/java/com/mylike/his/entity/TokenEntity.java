package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zhengluping on 2018/6/8.
 */
public class TokenEntity {
    private List<String> special_role;
    private String token;
    private UserInfoEntity userInfo;
    private List<authList> authList = new ArrayList<>();

    public List<String> getSpecial_role() {
        return special_role;
    }

    public void setSpecial_role(List<String> special_role) {
        this.special_role = special_role;
    }

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

    public List<TokenEntity.authList> getAuthList() {
        return authList;
    }

    public void setAuthList(List<TokenEntity.authList> authList) {
        this.authList = authList;
    }

    public class authList {
        private String auth;
        private String text;

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
            this.auth = auth;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
