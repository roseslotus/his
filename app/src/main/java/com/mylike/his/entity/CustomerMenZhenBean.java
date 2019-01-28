package com.mylike.his.entity;

import java.util.List;

public class CustomerMenZhenBean {
    /**
     * id : 7264ea0e-7d0c-4ca7-a9f0-a51f18a4c2e9
     * cusName : 王雨
     * brithday : 1992-06-29
     * tel : 159****1964
     * caseNo : 20190123000067
     * sex : 女
     * labels : [{"name":"VIP","value":"0"},{"name":"重点顾客","value":null},{"name":"卡等级","value":"时尚卡"}]
     */

    private String id;
    private String cusName;
    private String brithday;
    private String tel;
    private String caseNo;
    private String sex;
    private List<LabelsBean> labels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<LabelsBean> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelsBean> labels) {
        this.labels = labels;
    }

}
