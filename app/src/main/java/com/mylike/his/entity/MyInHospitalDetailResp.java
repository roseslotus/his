package com.mylike.his.entity;

public class MyInHospitalDetailResp {
      private String bedNo;//		病床
    private String condition;//		病情
    private String level;//	护理等级
    private String depName;//		科室
    private String area;//	病区
    private String enterDate;//		入院日期
    private String outDate;//	出院日期
    private String diagnosis;//		住院诊断
    private String doctor;//	主治医生
    private String residentDoc;//		住院医生
    private String nurse;//		责任护士

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
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

    public String getResidentDoc() {
        return residentDoc;
    }

    public void setResidentDoc(String residentDoc) {
        this.residentDoc = residentDoc;
    }

    public String getNurse() {
        return nurse;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse;
    }
}
