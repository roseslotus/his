package com.mylike.his.entity;

/**
 * 手术进程
 */
public class OperationProcessResp {
    private String preparationTime;		//术前准备时间
    private String enterOperaRoom;		//入手术室时间
    private String anesthesiaStartTime;	//麻醉开始时间
    private String operaStartTime;		//手术开始时间
    private String operaEndTime;		//手术结束时间
    private String anesthesiaEndTime;	//麻醉结束时间
    private String outOperaRoom;		//出手术室时间


    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getEnterOperaRoom() {
        return enterOperaRoom;
    }

    public void setEnterOperaRoom(String enterOperaRoom) {
        this.enterOperaRoom = enterOperaRoom;
    }

    public String getAnesthesiaStartTime() {
        return anesthesiaStartTime;
    }

    public void setAnesthesiaStartTime(String anesthesiaStartTime) {
        this.anesthesiaStartTime = anesthesiaStartTime;
    }

    public String getOperaStartTime() {
        return operaStartTime;
    }

    public void setOperaStartTime(String operaStartTime) {
        this.operaStartTime = operaStartTime;
    }

    public String getOperaEndTime() {
        return operaEndTime;
    }

    public void setOperaEndTime(String operaEndTime) {
        this.operaEndTime = operaEndTime;
    }

    public String getAnesthesiaEndTime() {
        return anesthesiaEndTime;
    }

    public void setAnesthesiaEndTime(String anesthesiaEndTime) {
        this.anesthesiaEndTime = anesthesiaEndTime;
    }

    public String getOutOperaRoom() {
        return outOperaRoom;
    }

    public void setOutOperaRoom(String outOperaRoom) {
        this.outOperaRoom = outOperaRoom;
    }
}
