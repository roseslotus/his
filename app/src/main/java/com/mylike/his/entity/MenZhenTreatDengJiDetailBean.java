package com.mylike.his.entity;

import java.util.List;

public class MenZhenTreatDengJiDetailBean {

    /**
     * productsName : （衡力）瘦咬肌
     * items : [{"treatTime":"2019-01-23 00:00","treatName":"衡力 100u","doctor":"田艳","exeDoc":"田艳","assistance":null,"num":"1"}]
     */

    private String productsName;//产品
    private List<ItemsBean> items;

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * treatTime : 2019-01-23 00:00
         * treatName : 衡力 100u
         * doctor : 田艳
         * exeDoc : 田艳
         * assistance : null
         * num : 1
         */

        private String treatTime;//治疗时间
        private String treatName;//治疗项目
        private String doctor;//主治医生
        private String exeDoc;//执行医生
        private String assistance;//医助
        private String num;//使用量

        public String getTreatTime() {
            return treatTime;
        }

        public void setTreatTime(String treatTime) {
            this.treatTime = treatTime;
        }

        public String getTreatName() {
            return treatName;
        }

        public void setTreatName(String treatName) {
            this.treatName = treatName;
        }

        public String getDoctor() {
            return doctor;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public String getExeDoc() {
            return exeDoc;
        }

        public void setExeDoc(String exeDoc) {
            this.exeDoc = exeDoc;
        }

        public String getAssistance() {
            return assistance;
        }

        public void setAssistance(String assistance) {
            this.assistance = assistance;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
