package com.mylike.his.entity;

import java.io.Serializable;

public class OperationMyArrangementListBean implements Serializable {
    private String registId;//登记id
    private String cusid;//顾客id
    private CustomerMenZhenBean customer;
    private String appTime;//预约时间
    private String anesthesia;//麻醉方式
    private String productsName;//项目名称
    private String doctor;//手术医生
    private String status;//状态
    private String anesthesiaDoc;//麻醉医生
    private String assistant;//医生助理
    private String surgicalNurse;//洗手护士
    private String circuitNurse;//巡回护士
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String operaRoom;//手术室
    private String stage;//台次

    public String getRegistId() {
        return registId;
    }

    public void setRegistId(String registId) {
        this.registId = registId;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public CustomerMenZhenBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerMenZhenBean customer) {
        this.customer = customer;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getAnesthesia() {
        return anesthesia;
    }

    public void setAnesthesia(String anesthesia) {
        this.anesthesia = anesthesia;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnesthesiaDoc() {
        return anesthesiaDoc;
    }

    public void setAnesthesiaDoc(String anesthesiaDoc) {
        this.anesthesiaDoc = anesthesiaDoc;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getSurgicalNurse() {
        return surgicalNurse;
    }

    public void setSurgicalNurse(String surgicalNurse) {
        this.surgicalNurse = surgicalNurse;
    }

    public String getCircuitNurse() {
        return circuitNurse;
    }

    public void setCircuitNurse(String circuitNurse) {
        this.circuitNurse = circuitNurse;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOperaRoom() {
        return operaRoom;
    }

    public void setOperaRoom(String operaRoom) {
        this.operaRoom = operaRoom;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
