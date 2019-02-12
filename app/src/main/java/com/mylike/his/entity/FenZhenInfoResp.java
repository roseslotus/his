package com.mylike.his.entity;

public class FenZhenInfoResp {


    /**
     * triageTime : 2019-01-23 00:00
     * acceptTime : null
     * type : 首诊
     * productsName : null
     * doctor : 田艳
     * acceptDoc : null
     * triageDoc : 王安娜
     * consultant : 夏振蔷
     */

    private String triageTime;//分诊时间
    private String acceptTime;//接诊时间
    private String type;//初/复诊状态
    private String productsName;//项目名称
    private String doctor;//主治医生
    private String acceptDoc;//接诊医生
    private String triageDoc;//分诊医生
    private String consultant;//咨询医生

    public String getTriageTime() {
        return triageTime;
    }

    public void setTriageTime(String triageTime) {
        this.triageTime = triageTime;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
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

    public String getAcceptDoc() {
        return acceptDoc;
    }

    public void setAcceptDoc(String acceptDoc) {
        this.acceptDoc = acceptDoc;
    }

    public String getTriageDoc() {
        return triageDoc;
    }

    public void setTriageDoc(String triageDoc) {
        this.triageDoc = triageDoc;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }
}
