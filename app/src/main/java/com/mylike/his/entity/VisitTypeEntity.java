package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/11/16
 */
public class VisitTypeEntity {
    private String domainText;
    private String domainValue;
    private List<VisitTypeEntity> sub = new ArrayList<>();
    private List<VisitTypeEntity> list = new ArrayList<>();

    public String getDomainText() {
        return domainText;
    }

    public void setDomainText(String domainText) {
        this.domainText = domainText;
    }

    public String getDomainValue() {
        return domainValue;
    }

    public void setDomainValue(String domainValue) {
        this.domainValue = domainValue;
    }

    public List<VisitTypeEntity> getSub() {
        return sub;
    }

    public void setSub(List<VisitTypeEntity> sub) {
        this.sub = sub;
    }

    public List<VisitTypeEntity> getList() {
        return list;
    }

    public void setList(List<VisitTypeEntity> list) {
        this.list = list;
    }
}
