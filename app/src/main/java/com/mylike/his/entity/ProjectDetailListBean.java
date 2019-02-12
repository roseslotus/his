package com.mylike.his.entity;

import java.util.List;

public class ProjectDetailListBean {

    /**
     * productsName : 急速纳米美眼A套餐
     * num : 4
     * dtls : [{"dtlName":"整形专家点名费"},{"dtlName":"重睑成形术(微创去脂法)"},{"dtlName":"重睑成形术(微创去皮法)"},{"dtlName":"重睑成形术(韩式切开法)"}]
     */

    private String productsName;
    private String num;
    private List<DtlsBean> dtls;

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<DtlsBean> getDtls() {
        return dtls;
    }

    public void setDtls(List<DtlsBean> dtls) {
        this.dtls = dtls;
    }

    public static class DtlsBean {
        /**
         * dtlName : 整形专家点名费
         */

        private String dtlName;

        public String getDtlName() {
            return dtlName;
        }

        public void setDtlName(String dtlName) {
            this.dtlName = dtlName;
        }
    }
}
