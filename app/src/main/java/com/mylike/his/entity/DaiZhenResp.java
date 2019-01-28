package com.mylike.his.entity;

import java.util.List;

/**
 * Created by thl on 2019/1/3.
 */

public class DaiZhenResp {


    private List<WoDeDaiZhenItemBean> dataList;
    private List<ListBean> list;

    public List<WoDeDaiZhenItemBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<WoDeDaiZhenItemBean> dataList) {
        this.dataList = dataList;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
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
