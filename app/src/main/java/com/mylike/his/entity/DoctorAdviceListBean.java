package com.mylike.his.entity;

import java.util.List;

public class DoctorAdviceListBean {
    private String orderDate;//医嘱日期
    private List<DoctorAdviceOrderMain> orderMains;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<DoctorAdviceOrderMain> getOrderMains() {
        return orderMains;
    }

    public void setOrderMains(List<DoctorAdviceOrderMain> orderMains) {
        this.orderMains = orderMains;
    }
}
