package com.mylike.his.entity;

import java.io.Serializable;

/**
 * Created by zhengluping on 2018/6/12.
 */

public class ProductEntity {
   private String pid;
   private String pname;
   private String price;
   private String type;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
