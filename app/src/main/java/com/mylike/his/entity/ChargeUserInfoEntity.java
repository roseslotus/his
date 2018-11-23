package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/6/29.
 */

public class ChargeUserInfoEntity {

    private String IsCosmetology;
    private ProductDate ProductData = new ProductDate();
    private TriageInfoEntity TriageData = new TriageInfoEntity();

    public String getIsCosmetology() {
        return IsCosmetology;
    }

    public void setIsCosmetology(String isCosmetology) {
        IsCosmetology = isCosmetology;
    }

    public ProductDate getProductData() {
        return ProductData;
    }

    public void setProductData(ProductDate productData) {
        ProductData = productData;
    }

    public TriageInfoEntity getTriageData() {
        return TriageData;
    }

    public void setTriageData(TriageInfoEntity triageData) {
        TriageData = triageData;
    }

    public class ProductDate {
        private String cb;
        private List<PInfo> data = new ArrayList<>();

        public String getCb() {
            return cb;
        }

        public void setCb(String cb) {
            this.cb = cb;
        }

        public List<PInfo> getData() {
            return data;
        }

        public void setData(List<PInfo> data) {
            this.data = data;
        }

        /*private List<PInfo> cp = new ArrayList<>();
        private List<PInfo> tc = new ArrayList<>();
        private List<PInfo> xm = new ArrayList<>();

        public void setCb(String cb) {
            this.cb = cb;
        }

        public String getCb() {
            return cb;
        }

        public void setCp(List<PInfo> cp) {
            this.cp = cp;
        }

        public List<PInfo> getCp() {
            return cp;
        }

        public void setTc(List<PInfo> tc) {
            this.tc = tc;
        }

        public List<PInfo> getTc() {
            return tc;
        }

        public void setXm(List<PInfo> xm) {
            this.xm = xm;
        }

        public List<PInfo> getXm() {
            return xm;
        }*/

    }

    public class PInfo {
        private String cfbillacc;
        private String cfcareacc;
        private String cfisprojectexec;
        private String cfisturn;
        private String cfnumbers;
        private String cfprojectname;
        private String cfrebate;
        private String cfremark;
        private String cfunitid;
        private String chaitemcd;
        private String fappendmoney;
        private String fdiseasesid;
        private String fid;
        private String foddmentmoney;
        private String fparentid;
        private String fproductprice;
        private String fprojectid;
        private String frebatemoney;
        private String frushmoney;
        private String fseq;
        private String fsummoney;
        private String ftracesource;
        private String registerid;
        private String unitprice;
        private String billrebate;
        private String department;
        private String fdoctorid;
        private String itemLx;
        private List<PInfo> product = new ArrayList<>();

        public String getBillrebate() {
            return billrebate;
        }

        public void setBillrebate(String billrebate) {
            this.billrebate = billrebate;
        }

        public String getUnitprice() {
            return unitprice;
        }

        public void setUnitprice(String unitprice) {
            this.unitprice = unitprice;
        }

        public String getCfbillacc() {
            return cfbillacc;
        }

        public void setCfbillacc(String cfbillacc) {
            this.cfbillacc = cfbillacc;
        }

        public String getCfcareacc() {
            return cfcareacc;
        }

        public void setCfcareacc(String cfcareacc) {
            this.cfcareacc = cfcareacc;
        }

        public String getCfisprojectexec() {
            return cfisprojectexec;
        }

        public void setCfisprojectexec(String cfisprojectexec) {
            this.cfisprojectexec = cfisprojectexec;
        }

        public String getCfisturn() {
            return cfisturn;
        }

        public void setCfisturn(String cfisturn) {
            this.cfisturn = cfisturn;
        }

        public String getCfnumbers() {
            return cfnumbers;
        }

        public void setCfnumbers(String cfnumbers) {
            this.cfnumbers = cfnumbers;
        }

        public String getCfprojectname() {
            return cfprojectname;
        }

        public void setCfprojectname(String cfprojectname) {
            this.cfprojectname = cfprojectname;
        }

        public String getCfrebate() {
            return cfrebate;
        }

        public void setCfrebate(String cfrebate) {
            this.cfrebate = cfrebate;
        }

        public String getCfremark() {
            return cfremark;
        }

        public void setCfremark(String cfremark) {
            this.cfremark = cfremark;
        }

        public String getCfunitid() {
            return cfunitid;
        }

        public void setCfunitid(String cfunitid) {
            this.cfunitid = cfunitid;
        }

        public String getChaitemcd() {
            return chaitemcd;
        }

        public void setChaitemcd(String chaitemcd) {
            this.chaitemcd = chaitemcd;
        }

        public String getFappendmoney() {
            return fappendmoney;
        }

        public void setFappendmoney(String fappendmoney) {
            this.fappendmoney = fappendmoney;
        }

        public String getFdiseasesid() {
            return fdiseasesid;
        }

        public void setFdiseasesid(String fdiseasesid) {
            this.fdiseasesid = fdiseasesid;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getFoddmentmoney() {
            return foddmentmoney;
        }

        public void setFoddmentmoney(String foddmentmoney) {
            this.foddmentmoney = foddmentmoney;
        }

        public String getFparentid() {
            return fparentid;
        }

        public void setFparentid(String fparentid) {
            this.fparentid = fparentid;
        }

        public String getFproductprice() {
            return fproductprice;
        }

        public void setFproductprice(String fproductprice) {
            this.fproductprice = fproductprice;
        }

        public String getFprojectid() {
            return fprojectid;
        }

        public void setFprojectid(String fprojectid) {
            this.fprojectid = fprojectid;
        }

        public String getFrebatemoney() {
            return frebatemoney;
        }

        public void setFrebatemoney(String frebatemoney) {
            this.frebatemoney = frebatemoney;
        }

        public String getFrushmoney() {
            return frushmoney;
        }

        public void setFrushmoney(String frushmoney) {
            this.frushmoney = frushmoney;
        }

        public String getFseq() {
            return fseq;
        }

        public void setFseq(String fseq) {
            this.fseq = fseq;
        }

        public String getFsummoney() {
            return fsummoney;
        }

        public void setFsummoney(String fsummoney) {
            this.fsummoney = fsummoney;
        }

        public String getFtracesource() {
            return ftracesource;
        }

        public void setFtracesource(String ftracesource) {
            this.ftracesource = ftracesource;
        }

        public String getRegisterid() {
            return registerid;
        }

        public void setRegisterid(String registerid) {

            this.registerid = registerid;
        }

        public String getItemLx() {
            return itemLx;
        }

        public void setItemLx(String itemLx) {
            this.itemLx = itemLx;
        }

        public List<PInfo> getProduct() {
            return product;
        }

        public void setProduct(List<PInfo> product) {
            this.product = product;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getFdoctorid() {
            return fdoctorid;
        }

        public void setFdoctorid(String fdoctorid) {
            this.fdoctorid = fdoctorid;
        }
    }


}
