package com.mylike.his.entity;

import java.io.Serializable;

public class MyInHostpitalListBean implements Serializable {
    private String registId;//登记id
    private String cusid;//顾客id
    private CustomerMenZhenBean customer;//
    private String enterDate;//入院日期
    private String area;//		病区
    private String level;//		护理级别
    private String productsName;//		项目名称
    private String bedNo;//		床号
    private String doctor;//		主治医生
    private String residentDoc;//		住院医生
    private String nurse;//		责任护士

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

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getResidentDoc() {
        return residentDoc;
    }

    public void setResidentDoc(String residentDoc) {
        this.residentDoc = residentDoc;
    }

    public String getNurse() {
        return nurse;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse;
    }
}
