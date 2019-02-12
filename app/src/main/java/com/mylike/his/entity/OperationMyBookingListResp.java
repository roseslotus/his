package com.mylike.his.entity;

import java.util.List;

public class OperationMyBookingListResp {
    private List<OperationMyBookingItemBean> dataList;
    private List<OperationMyBookingListResp.ListBean> list;

    public List<OperationMyBookingItemBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<OperationMyBookingItemBean> dataList) {
        this.dataList = dataList;
    }

    public List<OperationMyBookingListResp.ListBean> getList() {
        return list;
    }

    public void setList(List<OperationMyBookingListResp.ListBean> list) {
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
