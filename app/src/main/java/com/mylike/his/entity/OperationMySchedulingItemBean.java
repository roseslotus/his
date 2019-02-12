package com.mylike.his.entity;

import java.io.Serializable;

public class OperationMySchedulingItemBean implements Serializable {

    private String registId;//登记id
    private String cusid;//顾客id
    private CustomerMenZhenBean customer;//顾客信息
    private String appTime;//预约时间
    private String anesthesia;//麻醉方式
    private String prosthesis;//假体状态
    private String skinTest;//皮试状态
    private String medicalExam;//检查检验状态
    private String productsName;//项目名称
    private String doctor;//主治医生


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

    public String getProsthesis() {
        return prosthesis;
    }

    public void setProsthesis(String prosthesis) {
        this.prosthesis = prosthesis;
    }

    public String getSkinTest() {
        return skinTest;
    }

    public void setSkinTest(String skinTest) {
        this.skinTest = skinTest;
    }

    public String getMedicalExam() {
        return medicalExam;
    }

    public void setMedicalExam(String medicalExam) {
        this.medicalExam = medicalExam;
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
}
