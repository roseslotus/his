package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/11/6
 */
public class MessageTypeEntity {
    private String id;
    private String name;
    private List<MessageTypeEntity> list=new ArrayList<>();

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

    public List<MessageTypeEntity> getList() {
        return list;
    }

    public void setList(List<MessageTypeEntity> list) {
        this.list = list;
    }
}
