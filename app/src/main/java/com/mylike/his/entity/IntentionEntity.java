package com.mylike.his.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/6/8.
 */

public class IntentionEntity implements IPickerViewData {
    //    private String pbtid = "";
    //    private String pbtname = "";
    private String pname;
    private String pid;
    private String parentid;
    private String tenantId;
    List<IntentionEntity> children;

    public IntentionEntity(String pname) {
        this.pname = pname;
    }

    public IntentionEntity() {
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
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

    public List<IntentionEntity> getChildren() {
        return children;
    }

    public void setChildren(List<IntentionEntity> children) {
        this.children = children;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String getPickerViewText() {
        return pname;
    }
}
