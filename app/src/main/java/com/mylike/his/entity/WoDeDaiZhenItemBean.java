package com.mylike.his.entity;

import java.io.Serializable;
import java.util.List;

public class WoDeDaiZhenItemBean implements Serializable {
    /**
     * registId : fb75065833b24875921f2c2971347823
     * customer : {"id":"7264ea0e-7d0c-4ca7-a9f0-a51f18a4c2e9","cusName":"王雨","brithday":"1992-06-29","tel":"159****1964","caseNo":"20190123000067","sex":"女","labels":[{"name":"VIP","value":"0"},{"name":"重点顾客","value":null},{"name":"卡等级","value":"时尚卡"}]}
     * waitingTime : 8500
     * triageTime : 2019-01-23 00:00
     * type : 是
     * flag : 否
     * status : 已到店
     * productsName : null
     * doctor : 田艳
     */

    private String registId;
    private CustomerMenZhenBean customer;
    private int waitingTime;
    private String triageTime;
    private String type;
    private String flag;
    private String status;
    private String productsName;
    private String doctor;

    public String getRegistId() {
        return registId;
    }

    public void setRegistId(String registId) {
        this.registId = registId;
    }

    public CustomerMenZhenBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerMenZhenBean customer) {
        this.customer = customer;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getTriageTime() {
        return triageTime;
    }

    public void setTriageTime(String triageTime) {
        this.triageTime = triageTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
