package com.mylike.his.entity;

import java.math.BigDecimal;
import java.util.List;

public class MenZhenChuFangJiLuDetailResp {
    private String presTime;//处方开立时间
    private String diagnosis;//诊断
    private String doctor;//诊断医师
    private List<DataBean> list;

    public String getPresTime() {
        return presTime;
    }

    public void setPresTime(String presTime) {
        this.presTime = presTime;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public List<DataBean> getList() {
        return list;
    }

    public void setList(List<DataBean> list) {
        this.list = list;
    }

    public static class DataBean{
        private String drugName;//药品名称
        private String specification;//规格
        private String num;//数量
        private String unit;//单位
        private String eachTime;//每次用量
        private String usage;//用法
        private String days;//天数
        private String frequency;//频率
        private String type;//处方类型
        private String status;//处方状态
        private BigDecimal amount;//金额

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

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
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

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }

}
