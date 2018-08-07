package com.mylike.his.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhengluping on 2018/6/6.
 */

public class BaseEntity<T> {
    private String code;
    private String msg;
    private T data;


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
