package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/6/12.
 */

public class ChargeInfoEntity {
    //    private String CFARRTYPE;
    private String CFFEEALL;//所有金额
    private String CFHANDSET;//手机号
    private String CFNAME;//用户名称
    private String ISTODAY;//是否是今天
    //    private String CFOWEFLAG;
    //    private String CFPREFERFLAG;
    private String CFRECEIVEID;//分诊id
    //    private String CFREMARK;
    //    private String CFRETFLAG;
    //    private String DEPTID;
    private String DEPTNAME;//医生科室
    //    private String DOCTORID;
    private String DOCTORNAME;//医生名称
    //    private String FARREARAGEMONEY;
    //    private String FARREARAGENUMBER;
    //    private String FARREARAGETIME;
    private String FCHARGESTATENUMBER;//收费单状态 数字
    private String FCHARGESTATE;//收费单状态
    private String FCHARGETYPENUMBER;//消费类型 数字
    private String FCHARGETYPE;//消费类型
    private String FCREATETIME;//时间
    //    private String FFACTMONEY;
    private String FID;
    //    private String FISARREARAGE;
    //    private String FISRUSH;
    //    private String FNUMBER;
    //    private int FSHOULDMONEY;
    private String ID;
    //    private int RN;
    //    private String USERNAME;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getISTODAY() {
        return ISTODAY;
    }

    public void setISTODAY(String ISTODAY) {
        this.ISTODAY = ISTODAY;
    }

    public String getFCHARGESTATENUMBER() {
        return FCHARGESTATENUMBER;
    }

    public void setFCHARGESTATENUMBER(String FCHARGESTATENUMBER) {
        this.FCHARGESTATENUMBER = FCHARGESTATENUMBER;
    }

    public String getFCHARGETYPENUMBER() {
        return FCHARGETYPENUMBER;
    }

    public void setFCHARGETYPENUMBER(String FCHARGETYPENUMBER) {
        this.FCHARGETYPENUMBER = FCHARGETYPENUMBER;
    }

    private List<ProjectList> projectList = new ArrayList<>();//所有产品

    public String getFCREATETIME() {
        return FCREATETIME;
    }

    public void setFCREATETIME(String FCREATETIME) {
        this.FCREATETIME = FCREATETIME;
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

    public String getFCHARGESTATE() {
        return FCHARGESTATE;
    }

    public void setFCHARGESTATE(String FCHARGESTATE) {
        this.FCHARGESTATE = FCHARGESTATE;
    }

    public String getFCHARGETYPE() {
        return FCHARGETYPE;
    }

    public void setFCHARGETYPE(String FCHARGETYPE) {
        this.FCHARGETYPE = FCHARGETYPE;
    }

    public List<ProjectList> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectList> projectList) {
        this.projectList = projectList;
    }

    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public String getCFRECEIVEID() {
        return CFRECEIVEID;
    }

    public void setCFRECEIVEID(String CFRECEIVEID) {
        this.CFRECEIVEID = CFRECEIVEID;
    }

    public String getDEPTNAME() {
        return DEPTNAME;
    }

    public void setDEPTNAME(String DEPTNAME) {
        this.DEPTNAME = DEPTNAME;
    }

    public String getDOCTORNAME() {
        return DOCTORNAME;
    }

    public void setDOCTORNAME(String DOCTORNAME) {
        this.DOCTORNAME = DOCTORNAME;
    }

    class ProjectList {

        private String CFBILLACC;
        private String CFCAREACC;
        private String CFNUMBERS;
        private String CFPROJECTNAME;
        private String CFREMARK;
        private String FAPPENDMONEY;
        private String FID;
        private String FPARENTID;
        private String FPRODUCTPRICE;
        private String FPROJECTID;
        private String FPROJECTTYPE;
        private String FREBATEMONEY;
        private String FSUMMONEY;
        private int RN;

        public String getCFBILLACC() {
            return CFBILLACC;
        }

        public void setCFBILLACC(String CFBILLACC) {
            this.CFBILLACC = CFBILLACC;
        }

        public String getCFCAREACC() {
            return CFCAREACC;
        }

        public void setCFCAREACC(String CFCAREACC) {
            this.CFCAREACC = CFCAREACC;
        }

        public String getCFNUMBERS() {
            return CFNUMBERS;
        }

        public void setCFNUMBERS(String CFNUMBERS) {
            this.CFNUMBERS = CFNUMBERS;
        }

        public String getCFPROJECTNAME() {
            return CFPROJECTNAME;
        }

        public void setCFPROJECTNAME(String CFPROJECTNAME) {
            this.CFPROJECTNAME = CFPROJECTNAME;
        }

        public String getCFREMARK() {
            return CFREMARK;
        }

        public void setCFREMARK(String CFREMARK) {
            this.CFREMARK = CFREMARK;
        }

        public String getFAPPENDMONEY() {
            return FAPPENDMONEY;
        }

        public void setFAPPENDMONEY(String FAPPENDMONEY) {
            this.FAPPENDMONEY = FAPPENDMONEY;
        }

        public String getFID() {
            return FID;
        }

        public void setFID(String FID) {
            this.FID = FID;
        }

        public String getFPARENTID() {
            return FPARENTID;
        }

        public void setFPARENTID(String FPARENTID) {
            this.FPARENTID = FPARENTID;
        }

        public String getFPRODUCTPRICE() {
            return FPRODUCTPRICE;
        }

        public void setFPRODUCTPRICE(String FPRODUCTPRICE) {
            this.FPRODUCTPRICE = FPRODUCTPRICE;
        }

        public String getFPROJECTID() {
            return FPROJECTID;
        }

        public void setFPROJECTID(String FPROJECTID) {
            this.FPROJECTID = FPROJECTID;
        }

        public String getFPROJECTTYPE() {
            return FPROJECTTYPE;
        }

        public void setFPROJECTTYPE(String FPROJECTTYPE) {
            this.FPROJECTTYPE = FPROJECTTYPE;
        }

        public String getFREBATEMONEY() {
            return FREBATEMONEY;
        }

        public void setFREBATEMONEY(String FREBATEMONEY) {
            this.FREBATEMONEY = FREBATEMONEY;
        }

        public String getFSUMMONEY() {
            return FSUMMONEY;
        }

        public void setFSUMMONEY(String FSUMMONEY) {
            this.FSUMMONEY = FSUMMONEY;
        }

        public int getRN() {
            return RN;
        }

        public void setRN(int RN) {
            this.RN = RN;
        }
    }


}
