package com.mylike.his.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;
import java.util.List;

public class BookbuildingEntity implements IPickerViewData{
    private String domainText;
    private String domainValue;
    private List<BookbuildingEntity> sub=new ArrayList<>();

    public BookbuildingEntity() {
    }

    public BookbuildingEntity(String domainText) {
        this.domainText = domainText;
    }

    public BookbuildingEntity(String domainText, String domainValue) {
        this.domainText = domainText;
        this.domainValue = domainValue;
    }

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

    public List<BookbuildingEntity> getSub() {
        return sub;
    }

    public void setSub(List<BookbuildingEntity> sub) {
        this.sub = sub;
    }

    @Override
    public String getPickerViewText() {
        return domainText;
    }
}
