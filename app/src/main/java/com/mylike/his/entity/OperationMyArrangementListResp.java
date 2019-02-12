package com.mylike.his.entity;

import java.util.List;

public class OperationMyArrangementListResp {
    private List<OperationMyArrangementListBean> dataList;
    private List<OperationMyArrangementListResp.ListBean> list;

    public List<OperationMyArrangementListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<OperationMyArrangementListBean> dataList) {
        this.dataList = dataList;
    }

    public List<OperationMyArrangementListResp.ListBean> getList() {
        return list;
    }

    public void setList(List<OperationMyArrangementListResp.ListBean> list) {
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
