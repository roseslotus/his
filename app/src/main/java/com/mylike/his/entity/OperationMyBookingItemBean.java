package com.mylike.his.entity;

import java.io.Serializable;

public class OperationMyBookingItemBean implements Serializable {
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

    private String appId;//预约id
    private String cusid;//顾客id
    private CustomerMenZhenBean customer;//顾客信息
    private String appTime;//预约时间
    private String status;//付款状态
    private String type;//初/复诊类型
    private String productsName;//项目名称
    private String doctor;//主治医生
    private String creater;//预约人


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public CustomerMenZhenBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerMenZhenBean customer) {
        this.customer = customer;
    }

    public String getCusid() {
        return cusid;
    }

    public void setCusid(String cusid) {
        this.cusid = cusid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
