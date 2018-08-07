package com.mylike.his.entity;

import java.util.List;

/**
 * Created by zhengluping on 2018/6/21.
 */

public class HospitalAppointmentEntity {
    private List<HospitalAppointmentInfoEntity> list;
    private int pageNumber;
    private int pageSize;
    private String stateData;
    private int total;
    private int totalPages;

    public List<HospitalAppointmentInfoEntity> getList() {
        return list;
    }

    public void setList(List<HospitalAppointmentInfoEntity> list) {
        this.list = list;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStateData() {
        return stateData;
    }

    public void setStateData(String stateData) {
        this.stateData = stateData;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

