package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/6/12.
 */

public class ReceptionEntity {

    private String endCreatetime;
    private String endCreatetimeQ;
    private String nextLevel;
    private int total;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private String nextLevelText;
    private List<ReceptionInfoEntity> list = new ArrayList<>();


    public String getEndCreatetimeQ() {
        return endCreatetimeQ;
    }

    public void setEndCreatetimeQ(String endCreatetimeQ) {
        endCreatetimeQ = endCreatetimeQ;
    }

    public String getNextLevelText() {
        return nextLevelText;
    }

    public void setNextLevelText(String nextLevelText) {
        this.nextLevelText = nextLevelText;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
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

    public String getEndCreatetime() {
        return endCreatetime;
    }

    public void setEndCreatetime(String endCreatetime) {
        this.endCreatetime = endCreatetime;
    }

    public List<ReceptionInfoEntity> getList() {
        return list;
    }

    public void setList(List<ReceptionInfoEntity> list) {
        this.list = list;
    }
}
