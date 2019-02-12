package com.mylike.his.entity;

public class InspectRecordListBean {
    private String applyId;//检查检验id
    private String applyName;////检查名称
    private String applyType;//检查类型  0301 检验   1201 放射   1202 超声   1204 心电图
    private String applyTime;//检查时间
    private String applyResult;//检查结论
    private String applyDoc;//检查医生

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApplyName() {
        return applyName;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyResult() {
        return applyResult;
    }

    public void setApplyResult(String applyResult) {
        this.applyResult = applyResult;
    }

    public String getApplyDoc() {
        return applyDoc;
    }

    public void setApplyDoc(String applyDoc) {
        this.applyDoc = applyDoc;
    }
}
