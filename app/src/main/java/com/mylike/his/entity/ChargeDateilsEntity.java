package com.mylike.his.entity;


import java.util.List;
import java.util.Map;

/**
 * Created by zhengluping on 2018/7/11.
 */

public class ChargeDateilsEntity {

    private Info info;
    private List<Productlist> productlist;
    private List<Map<String, String>> subjectlist;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Productlist> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<Productlist> productlist) {
        this.productlist = productlist;
    }

    public List<Map<String, String>> getSubjectlist() {
        return subjectlist;
    }

    public void setSubjectlist(List<Map<String, String>> subjectlist) {
        this.subjectlist = subjectlist;
    }

    public class Info {

        private String CUSTID;
        private String CFRECEIVEID;
        private String CFACCTIME;
        private String CFFEEALL;
        private String CFHANDSET;
        private String CFNAME;
        private String CFREBATE;
        private String CFREMARK;
        private String CHECKOUTREMARK;
        private String DEPTNAME;
        private String FARREARAGEMONEY;
        private String FCHARGESTATE;
        private String FCHARGESTATENUMBER;
        private String FCHARGETYPE;
        private String FCHARGETYPENUMBER;
        private String FCREATETIME;
        private String FDOCTORID;
        private String FDOCTORNAME;
        private String FFACTMONEY;
        private String FID;
        private String FNUMBER;
        private String FPRESENTMONEY;
        private String FSECTION1;
        private String FSHOULDMONEY;
        private String INTENTION;
        private String INTENTION_ID;
        private String PAY_TYPE;
        private String USERNAME;
        private String DISCOUNT;//优惠
        private String POINTS;//积分
        private String ISTODAY;//是否今天
        private String CONTROLCODE;//是否走收银

        public String getCONTROLCODE() {
            return CONTROLCODE;
        }

        public void setCONTROLCODE(String CONTROLCODE) {
            this.CONTROLCODE = CONTROLCODE;
        }

        public String getCUSTID() {
            return CUSTID;
        }

        public void setCUSTID(String CUSTID) {
            this.CUSTID = CUSTID;
        }

        public String getISTODAY() {
            return ISTODAY;
        }

        public void setISTODAY(String ISTODAY) {
            this.ISTODAY = ISTODAY;
        }

        public String getCFRECEIVEID() {
            return CFRECEIVEID;
        }

        public void setCFRECEIVEID(String CFRECEIVEID) {
            this.CFRECEIVEID = CFRECEIVEID;
        }

        public String getDISCOUNT() {
            return DISCOUNT;
        }

        public void setDISCOUNT(String DISCOUNT) {
            this.DISCOUNT = DISCOUNT;
        }

        public String getPOINTS() {
            return POINTS;
        }

        public void setPOINTS(String POINTS) {
            this.POINTS = POINTS;
        }

        public String getCFACCTIME() {
            return CFACCTIME;
        }

        public void setCFACCTIME(String CFACCTIME) {
            this.CFACCTIME = CFACCTIME;
        }

        public String getCFFEEALL() {
            return CFFEEALL;
        }

        public void setCFFEEALL(String CFFEEALL) {
            this.CFFEEALL = CFFEEALL;
        }

        public String getCFHANDSET() {
            return CFHANDSET;
        }

        public void setCFHANDSET(String CFHANDSET) {
            this.CFHANDSET = CFHANDSET;
        }

        public String getCFNAME() {
            return CFNAME;
        }

        public void setCFNAME(String CFNAME) {
            this.CFNAME = CFNAME;
        }

        public String getCFREBATE() {
            return CFREBATE;
        }

        public void setCFREBATE(String CFREBATE) {
            this.CFREBATE = CFREBATE;
        }

        public String getCFREMARK() {
            return CFREMARK;
        }

        public void setCFREMARK(String CFREMARK) {
            this.CFREMARK = CFREMARK;
        }

        public String getCHECKOUTREMARK() {
            return CHECKOUTREMARK;
        }

        public void setCHECKOUTREMARK(String CHECKOUTREMARK) {
            this.CHECKOUTREMARK = CHECKOUTREMARK;
        }

        public String getDEPTNAME() {
            return DEPTNAME;
        }

        public void setDEPTNAME(String DEPTNAME) {
            this.DEPTNAME = DEPTNAME;
        }

        public String getFARREARAGEMONEY() {
            return FARREARAGEMONEY;
        }

        public void setFARREARAGEMONEY(String FARREARAGEMONEY) {
            this.FARREARAGEMONEY = FARREARAGEMONEY;
        }

        public String getFCHARGESTATE() {
            return FCHARGESTATE;
        }

        public void setFCHARGESTATE(String FCHARGESTATE) {
            this.FCHARGESTATE = FCHARGESTATE;
        }

        public String getFCHARGESTATENUMBER() {
            return FCHARGESTATENUMBER;
        }

        public void setFCHARGESTATENUMBER(String FCHARGESTATENUMBER) {
            this.FCHARGESTATENUMBER = FCHARGESTATENUMBER;
        }

        public String getFCHARGETYPE() {
            return FCHARGETYPE;
        }

        public void setFCHARGETYPE(String FCHARGETYPE) {
            this.FCHARGETYPE = FCHARGETYPE;
        }

        public String getFCHARGETYPENUMBER() {
            return FCHARGETYPENUMBER;
        }

        public void setFCHARGETYPENUMBER(String FCHARGETYPENUMBER) {
            this.FCHARGETYPENUMBER = FCHARGETYPENUMBER;
        }

        public String getFCREATETIME() {
            return FCREATETIME;
        }

        public void setFCREATETIME(String FCREATETIME) {
            this.FCREATETIME = FCREATETIME;
        }

        public String getFDOCTORID() {
            return FDOCTORID;
        }

        public void setFDOCTORID(String FDOCTORID) {
            this.FDOCTORID = FDOCTORID;
        }

        public String getFDOCTORNAME() {
            return FDOCTORNAME;
        }

        public void setFDOCTORNAME(String FDOCTORNAME) {
            this.FDOCTORNAME = FDOCTORNAME;
        }

        public String getFFACTMONEY() {
            return FFACTMONEY;
        }

        public void setFFACTMONEY(String FFACTMONEY) {
            this.FFACTMONEY = FFACTMONEY;
        }

        public String getFID() {
            return FID;
        }

        public void setFID(String FID) {
            this.FID = FID;
        }

        public String getFNUMBER() {
            return FNUMBER;
        }

        public void setFNUMBER(String FNUMBER) {
            this.FNUMBER = FNUMBER;
        }

        public String getFPRESENTMONEY() {
            return FPRESENTMONEY;
        }

        public void setFPRESENTMONEY(String FPRESENTMONEY) {
            this.FPRESENTMONEY = FPRESENTMONEY;
        }

        public String getFSECTION1() {
            return FSECTION1;
        }

        public void setFSECTION1(String FSECTION1) {
            this.FSECTION1 = FSECTION1;
        }

        public String getFSHOULDMONEY() {
            return FSHOULDMONEY;
        }

        public void setFSHOULDMONEY(String FSHOULDMONEY) {
            this.FSHOULDMONEY = FSHOULDMONEY;
        }

        public String getINTENTION() {
            return INTENTION;
        }

        public void setINTENTION(String INTENTION) {
            this.INTENTION = INTENTION;
        }

        public String getINTENTION_ID() {
            return INTENTION_ID;
        }

        public void setINTENTION_ID(String INTENTION_ID) {
            this.INTENTION_ID = INTENTION_ID;
        }

        public String getPAY_TYPE() {
            return PAY_TYPE;
        }

        public void setPAY_TYPE(String PAY_TYPE) {
            this.PAY_TYPE = PAY_TYPE;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }
    }

    public class Productlist {

        private String COUNT;
        private String ITEMLX;
        private String PNAME;
        private String PRICE;
        private String PRICE1;
        private String PRICE2;
        private String PRODUCTID;
        private String REBATE;
        private String REMARK;

        public String getCOUNT() {
            return COUNT;
        }

        public void setCOUNT(String COUNT) {
            this.COUNT = COUNT;
        }

        public String getITEMLX() {
            return ITEMLX;
        }

        public void setITEMLX(String ITEMLX) {
            this.ITEMLX = ITEMLX;
        }

        public String getPNAME() {
            return PNAME;
        }

        public void setPNAME(String PNAME) {
            this.PNAME = PNAME;
        }

        public String getPRICE() {
            return PRICE;
        }

        public void setPRICE(String PRICE) {
            this.PRICE = PRICE;
        }

        public String getPRICE1() {
            return PRICE1;
        }

        public void setPRICE1(String PRICE1) {
            this.PRICE1 = PRICE1;
        }

        public String getPRICE2() {
            return PRICE2;
        }

        public void setPRICE2(String PRICE2) {
            this.PRICE2 = PRICE2;
        }

        public String getPRODUCTID() {
            return PRODUCTID;
        }

        public void setPRODUCTID(String PRODUCTID) {
            this.PRODUCTID = PRODUCTID;
        }

        public String getREBATE() {
            return REBATE;
        }

        public void setREBATE(String REBATE) {
            this.REBATE = REBATE;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }
    }
}
