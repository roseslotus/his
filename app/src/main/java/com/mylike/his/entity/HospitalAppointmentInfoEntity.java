package com.mylike.his.entity;

import java.util.List;

/**
 * Created by zhengluping on 2018/6/21.
 */

public class HospitalAppointmentInfoEntity {
    private String TCI_ID;
    private String NAME;
    private String APPOINT_TIME;
    private List<String> INTENTION;

    public String getTCI_ID() {
        return TCI_ID;
    }

    public void setTCI_ID(String TCI_ID) {
        this.TCI_ID = TCI_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getAPPOINT_TIME() {
        return APPOINT_TIME;
    }

    public void setAPPOINT_TIME(String APPOINT_TIME) {
        this.APPOINT_TIME = APPOINT_TIME;
    }

    public List<String> getINTENTION() {
        return INTENTION;
    }

    public void setINTENTION(List<String> INTENTION) {
        this.INTENTION = INTENTION;
    }
}
