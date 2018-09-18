package com.mylike.his.entity;

/**
 * Created by zhengluping on 2018/6/19.
 */

public class SVProjectEntity {
    private String ACCOUNT;//项目名称
    private String ACCOUNTID;//项目id
    private int CASH;//赠送金额
    private String ID;//储值类型id
    private int PRESENTMONEY;//储值金额
    private String REMARK;//储值类型拼接术语

    public SVProjectEntity(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getACCOUNT() {
        return ACCOUNT;
    }

    public void setACCOUNT(String ACCOUNT) {
        this.ACCOUNT = ACCOUNT;
    }

    public String getACCOUNTID() {
        return ACCOUNTID;
    }

    public void setACCOUNTID(String ACCOUNTID) {
        this.ACCOUNTID = ACCOUNTID;
    }

    public int getCASH() {
        return CASH;
    }

    public void setCASH(int CASH) {
        this.CASH = CASH;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getPRESENTMONEY() {
        return PRESENTMONEY;
    }

    public void setPRESENTMONEY(int PRESENTMONEY) {
        this.PRESENTMONEY = PRESENTMONEY;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
