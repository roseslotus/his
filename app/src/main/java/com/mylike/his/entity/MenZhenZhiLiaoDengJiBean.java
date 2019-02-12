package com.mylike.his.entity;

public class MenZhenZhiLiaoDengJiBean {

    private String treatId;//治疗id
    private String treatTime;//治疗时间
    private String treatName;//治疗项目
    private String doctor;//主治医生
    private String exeDoc;//执行医生
    private String depName;//科室
    private String status;//治疗状态
    private String booker;//登记人

    public String getTreatId() {
        return treatId;
    }

    public void setTreatId(String treatId) {
        this.treatId = treatId;
    }

    public String getTreatTime() {
        return treatTime;
    }

    public void setTreatTime(String treatTime) {
        this.treatTime = treatTime;
    }

    public String getTreatName() {
        return treatName;
    }

    public void setTreatName(String treatName) {
        this.treatName = treatName;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getExeDoc() {
        return exeDoc;
    }

    public void setExeDoc(String exeDoc) {
        this.exeDoc = exeDoc;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }
}
