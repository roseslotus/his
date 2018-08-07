package com.mylike.his.entity;

/**
 * Created by zhengluping on 2018/1/29.
 */


//项目类（测试）
public class ProjectInfoEntity {

    private String name;
    private String money;

    public ProjectInfoEntity(String name, String money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
