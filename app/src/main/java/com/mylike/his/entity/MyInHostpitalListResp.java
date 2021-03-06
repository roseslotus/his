package com.mylike.his.entity;

import java.util.List;

public class MyInHostpitalListResp {
    private List<MyInHostpitalListBean> dataList;
    private List<MyInHostpitalListResp.ListBean> list;

    public List<MyInHostpitalListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<MyInHostpitalListBean> dataList) {
        this.dataList = dataList;
    }

    public List<MyInHostpitalListResp.ListBean> getList() {
        return list;
    }

    public void setList(List<MyInHostpitalListResp.ListBean> list) {
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
