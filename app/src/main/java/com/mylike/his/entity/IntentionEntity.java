package com.mylike.his.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/6/8.
 */

public class IntentionEntity implements IPickerViewData {

    List<IntentionEntity> children = new ArrayList<>();
    private String pbtid="";
    private String pbtname = "";
    private String tenantId;


    public IntentionEntity() {
    }

    public IntentionEntity(String pbtname) {
        this.pbtname = pbtname;
    }

    public List<IntentionEntity> getChildren() {
        return children;
    }

    public void setChildren(List<IntentionEntity> children) {
        this.children = children;
    }

    public String getPbtid() {
        return pbtid;
    }

    public void setPbtid(String pbtid) {
        this.pbtid = pbtid;
    }

    public String getPbtname() {
        return pbtname;
    }

    public void setPbtname(String pbtname) {
        this.pbtname = pbtname;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String getPickerViewText() {
        return pbtname;
    }
}
