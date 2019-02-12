package com.mylike.his.entity;

public class BinLiJiLuBean {

    /**
     * emrId : 9ea5c821f3924927ae95b03c87276fc0
     * emrSummary : 无创初诊模板
     * depName : 口腔美容中心
     * creater : 超级管理员
     * createTime : 2018-11-02 00:00
     * updateTime : 2018-11-02 00:00
     * status : null
     */

    private String emrId;//病历id
    private String emrSummary;//病历摘要
    private String depName;//病历所属科室
    private String creater;//病历书写人
    private String createTime;//创建时间
    private String updateTime;//修改时间
    private String status;//病历状态

    public String getEmrId() {
        return emrId;
    }

    public void setEmrId(String emrId) {
        this.emrId = emrId;
    }

    public String getEmrSummary() {
        return emrSummary;
    }

    public void setEmrSummary(String emrSummary) {
        this.emrSummary = emrSummary;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
