package com.mylike.his.entity;

import java.util.List;

public class DoctorAdviceDetailResp {
     private String drugName;//药品名称
    private String specification;//规格
    private String eachTime;//单次剂量
    private String usage;//医嘱用法
    private String rate;//滴速
    private String frequency;//频率
    private String orderTime;//开立时间
    private String orderDoc;//开立医生
    private List<ExecBean> execs;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getEachTime() {
        return eachTime;
    }

    public void setEachTime(String eachTime) {
        this.eachTime = eachTime;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderDoc() {
        return orderDoc;
    }

    public void setOrderDoc(String orderDoc) {
        this.orderDoc = orderDoc;
    }

    public List<ExecBean> getExecs() {
        return execs;
    }

    public void setExecs(List<ExecBean> execs) {
        this.execs = execs;
    }

    public static class ExecBean{
        private String orderStatus;//医嘱状态
        private String confirmTime;//确认时间
        private String exeTime;//执行时间
        private String confirmor;//确认人
        private String executive;//执行人



        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getExeTime() {
            return exeTime;
        }

        public void setExeTime(String exeTime) {
            this.exeTime = exeTime;
        }

        public String getConfirmor() {
            return confirmor;
        }

        public void setConfirmor(String confirmor) {
            this.confirmor = confirmor;
        }

        public String getExecutive() {
            return executive;
        }

        public void setExecutive(String executive) {
            this.executive = executive;
        }
    }


}
