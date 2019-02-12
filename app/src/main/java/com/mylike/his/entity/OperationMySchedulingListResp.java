package com.mylike.his.entity;

import java.util.List;

public class OperationMySchedulingListResp {
    private List<OperationMySchedulingItemBean> dataList;
    private List<OperationMySchedulingListResp.ListBean> list;

    public List<OperationMySchedulingItemBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<OperationMySchedulingItemBean> dataList) {
        this.dataList = dataList;
    }

    public List<OperationMySchedulingListResp.ListBean> getList() {
        return list;
    }

    public void setList(List<OperationMySchedulingListResp.ListBean> list) {
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
