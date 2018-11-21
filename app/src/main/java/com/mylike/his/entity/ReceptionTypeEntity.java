package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/11/13
 */
public class ReceptionTypeEntity {
    private String id;
    private String name;
    private String value;
    private List<ReceptionTypeEntity> list = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ReceptionTypeEntity> getList() {
        return list;
    }

    public void setList(List<ReceptionTypeEntity> list) {
        this.list = list;
    }
}
