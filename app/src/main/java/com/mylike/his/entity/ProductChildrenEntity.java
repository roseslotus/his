package com.mylike.his.entity;

import java.util.List;

/**
 * Created by zhengluping on 2018/6/8.
 */

public class ProductChildrenEntity {

    List<ProductChildrenEntity> children;
    private String pid;
    private String pname;
    private String tenantId;

    public List<ProductChildrenEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ProductChildrenEntity> children) {
        this.children = children;
    }

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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
