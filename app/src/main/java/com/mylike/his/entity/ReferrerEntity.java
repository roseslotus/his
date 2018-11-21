package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/11/12
 */
public class ReferrerEntity {
    private String cfhandset;
    private String cfname;
    private String consultant;
    private String id;
    private String namePhone;
    private List<ReferrerEntity> list = new ArrayList<>();

    public String getCfhandset() {
        return cfhandset;
    }

    public void setCfhandset(String cfhandset) {
        this.cfhandset = cfhandset;
    }

    public String getCfname() {
        return cfname;
    }

    public void setCfname(String cfname) {
        this.cfname = cfname;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamePhone() {
        return namePhone;
    }

    public void setNamePhone(String namePhone) {
        this.namePhone = namePhone;
    }

    public List<ReferrerEntity> getList() {
        return list;
    }

    public void setList(List<ReferrerEntity> list) {
        this.list = list;
    }
}
