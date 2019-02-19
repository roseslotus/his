package com.mylike.his.entity;

public class InHospitalCostDetailBean {
    private String chargeClassName;	//费用类别名称
    private String itemName;	//项目名称
    private String spec;		//规格
    private String billDate;		//账单日期
    private String billNum;	//数量
    private String unit;			//单位
    private String price;			//单价
    private String billMoney;		//订单金额
    private String execDate;		//执行日期
    private String recordCreater;			//记录生成人
    private String costSource;	//费用来源  1医嘱计费  2停嘱计费 3 手工计费
    private String freeFlg;		//免费标识   1免费  2取消免费

    public String getChargeClassName() {
        return chargeClassName;
    }

    public void setChargeClassName(String chargeClassName) {
        this.chargeClassName = chargeClassName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBillMoney() {
        return billMoney;
    }

    public void setBillMoney(String billMoney) {
        this.billMoney = billMoney;
    }

    public String getExecDate() {
        return execDate;
    }

    public void setExecDate(String execDate) {
        this.execDate = execDate;
    }

    public String getRecordCreater() {
        return recordCreater;
    }

    public void setRecordCreater(String recordCreater) {
        this.recordCreater = recordCreater;
    }

    public String getCostSource() {
        return costSource;
    }

    public void setCostSource(String costSource) {
        this.costSource = costSource;
    }

    public String getFreeFlg() {
        return freeFlg;
    }

    public void setFreeFlg(String freeFlg) {
        this.freeFlg = freeFlg;
    }
}
