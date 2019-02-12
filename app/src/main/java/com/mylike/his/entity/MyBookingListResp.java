package com.mylike.his.entity;

import java.util.List;

public class MyBookingListResp {
    private List<MyBookingItemBean> dataList;
    private List<MyBookingListResp.ListBean> list;

    public List<MyBookingItemBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<MyBookingItemBean> dataList) {
        this.dataList = dataList;
    }

    public List<MyBookingListResp.ListBean> getList() {
        return list;
    }

    public void setList(List<MyBookingListResp.ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 总计数
         * value : 62
         */

        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
