package com.mylike.his.entity;

/**
 * 手术模块 我的排期 手术详情
 */
public class OperationMySchedulingDetailResp {
    private String operaName;//手术名称
    private String doctor;//手术医生
    private String surgeryName;//术式名称
    private String time;//预约时间
    private String anesthesia;//麻醉方式
    private String anesthesiadoc;//麻醉医生
    private String assistant;//助理医生
    private String scrubNurse;//洗手护士
    private String circuitNurse;//巡回护士
    private String operaRoom;//手术室
    private String stage;//台次
    private String planTime;//计划时间
    private String actualTime;//实际时间
    private String delay;//是否推迟
    private String reason;//推迟原因
    private String create;//创建人
    private String createTime;//创建时间
    private String remark;//备注

    public String getOperaName() {
        return operaName;
    }

    public void setOperaName(String operaName) {
        this.operaName = operaName;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getSurgeryName() {
        return surgeryName;
    }

    public void setSurgeryName(String surgeryName) {
        this.surgeryName = surgeryName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAnesthesia() {
        return anesthesia;
    }

    public void setAnesthesia(String anesthesia) {
        this.anesthesia = anesthesia;
    }

    public String getAnesthesiadoc() {
        return anesthesiadoc;
    }

    public void setAnesthesiadoc(String anesthesiadoc) {
        this.anesthesiadoc = anesthesiadoc;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getScrubNurse() {
        return scrubNurse;
    }

    public void setScrubNurse(String scrubNurse) {
        this.scrubNurse = scrubNurse;
    }

    public String getCircuitNurse() {
        return circuitNurse;
    }

    public void setCircuitNurse(String circuitNurse) {
        this.circuitNurse = circuitNurse;
    }

    public String getOperaRoom() {
        return operaRoom;
    }

    public void setOperaRoom(String operaRoom) {
        this.operaRoom = operaRoom;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
