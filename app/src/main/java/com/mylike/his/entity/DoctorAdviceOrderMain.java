package com.mylike.his.entity;

public class DoctorAdviceOrderMain {

    private String orderId;//医嘱id
    private String drugName;//药品名称
    private String orderDoc;//医嘱开立医生
    private String orderCreateTime;//医嘱开立时间
    private String orderExeTime;//医嘱执行时间
    private String orderCancelTime;//医嘱取消时间
    private String orderStatus;//医嘱状态
    private String orderConfirmTime;//医嘱确认时间

    private int type;
    private String orderDate;//医嘱日期

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getOrderDoc() {
        return orderDoc;
    }

    public void setOrderDoc(String orderDoc) {
        this.orderDoc = orderDoc;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderExeTime() {
        return orderExeTime;
    }

    public void setOrderExeTime(String orderExeTime) {
        this.orderExeTime = orderExeTime;
    }

    public String getOrderCancelTime() {
        return orderCancelTime;
    }

    public void setOrderCancelTime(String orderCancelTime) {
        this.orderCancelTime = orderCancelTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderConfirmTime() {
        return orderConfirmTime;
    }

    public void setOrderConfirmTime(String orderConfirmTime) {
        this.orderConfirmTime = orderConfirmTime;
    }
}
