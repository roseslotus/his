package com.mylike.his.entity;

/**
 * Created by zhengluping on 2018/6/22.
 */

public class StatisticsEntity {
    private String appointDataCount;
    private String receiveDataCount;
    private String chargeBillDataCount;
    private String planTaskDataCount_already;
    private String planTaskDataCount_wait;

    public String getAppointDataCount() {
        return appointDataCount;
    }

    public void setAppointDataCount(String appointDataCount) {
        this.appointDataCount = appointDataCount;
    }

    public String getReceiveDataCount() {
        return receiveDataCount;
    }

    public void setReceiveDataCount(String receiveDataCount) {
        this.receiveDataCount = receiveDataCount;
    }

    public String getChargeBillDataCount() {
        return chargeBillDataCount;
    }

    public void setChargeBillDataCount(String chargeBillDataCount) {
        this.chargeBillDataCount = chargeBillDataCount;
    }

    public String getPlanTaskDataCount_already() {
        return planTaskDataCount_already;
    }

    public void setPlanTaskDataCount_already(String planTaskDataCount_already) {
        this.planTaskDataCount_already = planTaskDataCount_already;
    }

    public String getPlanTaskDataCount_wait() {
        return planTaskDataCount_wait;
    }

    public void setPlanTaskDataCount_wait(String planTaskDataCount_wait) {
        this.planTaskDataCount_wait = planTaskDataCount_wait;
    }
}
