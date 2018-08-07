package com.mylike.his.entity;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * Created by zhengluping on 2018/6/19.
 */

public class DoctorEntity extends BaseIndexPinyinBean {

    private String deptid;
    private String deptname;
    private String empid;
    private String empname;
    private String operstatename;
    private String tenantid;

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getOperstatename() {
        return operstatename;
    }

    public void setOperstatename(String operstatename) {
        this.operstatename = operstatename;
    }

    public String getTenantid() {
        return tenantid;
    }

    public void setTenantid(String tenantid) {
        this.tenantid = tenantid;
    }

    @Override
    public String getTarget() {
        return empname;
    }
}
