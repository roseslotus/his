package com.mylike.his.entity;

/**
 * 术前准备
 */
public class OperationPrePareResp {
    private String prosthesis;		//假体状态
    private String skinTest;		//皮试状态
    private String medicalExam;	//检查检验状态

    public String getProsthesis() {
        return prosthesis;
    }

    public void setProsthesis(String prosthesis) {
        this.prosthesis = prosthesis;
    }

    public String getSkinTest() {
        return skinTest;
    }

    public void setSkinTest(String skinTest) {
        this.skinTest = skinTest;
    }

    public String getMedicalExam() {
        return medicalExam;
    }

    public void setMedicalExam(String medicalExam) {
        this.medicalExam = medicalExam;
    }
}
