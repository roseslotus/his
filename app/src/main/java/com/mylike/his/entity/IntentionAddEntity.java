package com.mylike.his.entity;

public class IntentionAddEntity {
    private String itemFirst = "";//一级id
    private String itemSecond = "";//二级id
    private String itemThird = "";//三级id
    private String intentionStr;//意向字符串
    private String intentionIdStr;//意向id字符串
    private String remark;//备注


    public String getIntentionIdStr() {
        return intentionIdStr;
    }

    public void setIntentionIdStr(String intentionIdStr) {
        this.intentionIdStr = intentionIdStr;
    }

    public String getItemFirst() {
        return itemFirst;
    }

    public void setItemFirst(String itemFirst) {
        this.itemFirst = itemFirst;
    }

    public String getItemSecond() {
        return itemSecond;
    }

    public void setItemSecond(String itemSecond) {
        this.itemSecond = itemSecond;
    }

    public String getItemThird() {
        return itemThird;
    }

    public void setItemThird(String itemThird) {
        this.itemThird = itemThird;
    }

    public String getIntentionStr() {
        return intentionStr;
    }

    public void setIntentionStr(String intentionStr) {
        this.intentionStr = intentionStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
