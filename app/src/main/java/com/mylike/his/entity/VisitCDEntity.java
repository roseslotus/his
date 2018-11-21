package com.mylike.his.entity;

/**
 * Created by zhengluping on 2018/11/16
 */
public class VisitCDEntity {
    private String planTimeStart;
    private String planTimeEnd;
    private String visitTimeStart;
    private String visitTimeEnd;
    private String visitType;

    public String getPlanTimeStart() {
        return planTimeStart;
    }

    public void setPlanTimeStart(String planTimeStart) {
        this.planTimeStart = planTimeStart;
    }

    public String getPlanTimeEnd() {
        return planTimeEnd;
    }

    public void setPlanTimeEnd(String planTimeEnd) {
        this.planTimeEnd = planTimeEnd;
    }

    public String getVisitTimeStart() {
        return visitTimeStart;
    }

    public void setVisitTimeStart(String visitTimeStart) {
        this.visitTimeStart = visitTimeStart;
    }

    public String getVisitTimeEnd() {
        return visitTimeEnd;
    }

    public void setVisitTimeEnd(String visitTimeEnd) {
        this.visitTimeEnd = visitTimeEnd;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }
}
