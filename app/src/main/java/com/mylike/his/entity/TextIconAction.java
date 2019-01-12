package com.mylike.his.entity;

/**
 * Created by thl on 2018/12/30.
 */

public class TextIconAction {

    public String name;
    private int resId;

    public TextIconAction(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
