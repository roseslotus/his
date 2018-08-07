package com.mylike.his.entity;

/**
 * Created by zhengluping on 2018/6/21.
 */

public class MessageEntity {

    private int RN;
    private String businessPkid;
    private String createDate;
    private String fid;
    private String msgContent;
    private String msgType;
    private String msgTypeName;
    private String readingDate;
    private String readingState;
    private String sendeeid;
    private String tenantId;

    public int getRN() {
        return RN;
    }

    public void setRN(int RN) {
        this.RN = RN;
    }

    public String getBusinessPkid() {
        return businessPkid;
    }

    public void setBusinessPkid(String businessPkid) {
        this.businessPkid = businessPkid;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgTypeName() {
        return msgTypeName;
    }

    public void setMsgTypeName(String msgTypeName) {
        this.msgTypeName = msgTypeName;
    }

    public String getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }

    public String getReadingState() {
        return readingState;
    }

    public void setReadingState(String readingState) {
        this.readingState = readingState;
    }

    public String getSendeeid() {
        return sendeeid;
    }

    public void setSendeeid(String sendeeid) {
        this.sendeeid = sendeeid;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
