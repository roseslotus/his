package com.mylike.his.entity;

import java.util.List;

public class MyInHospitalCostResp {
    private int page;
    private int total;
    private int records;
    private List<InHospitalCostListBean> dataList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<InHospitalCostListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<InHospitalCostListBean> dataList) {
        this.dataList = dataList;
    }
}
