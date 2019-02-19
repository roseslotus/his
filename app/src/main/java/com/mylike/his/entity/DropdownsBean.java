package com.mylike.his.entity;

import java.util.List;

public class DropdownsBean {

    private String name;
    private List<SelectsBean> selects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SelectsBean> getSelects() {
        return selects;
    }

    public void setSelects(List<SelectsBean> selects) {
        this.selects = selects;
    }

}
