package com.mylike.his.entity;

import java.util.List;

/**
 * Created by zhengluping on 2018/6/20.
 */

public class DoctorDetailsEntity {
    private String total;
    private String pageNum;
    private String pages;
    private String pageSize;

    private List<DoctorInfoEntity> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public List<DoctorInfoEntity> getList() {
        return list;
    }

    public void setList(List<DoctorInfoEntity> list) {
        this.list = list;
    }
}
