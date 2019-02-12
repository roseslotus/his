package com.mylike.his.entity;

public class MenZhenChuFangJiLuBean {
    private String presId;//处方id
    private String presTime;//处方开立时间
    private String diagnosis;//诊断
    private String doctor;//开立医生
    private String type;//处方类型
    private String status;//处方状态

    public String getPresId() {
        return presId;
    }

    public void setPresId(String presId) {
        this.presId = presId;
    }

    public String getPresTime() {
        return presTime;
    }

    public void setPresTime(String presTime) {
        this.presTime = presTime;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
