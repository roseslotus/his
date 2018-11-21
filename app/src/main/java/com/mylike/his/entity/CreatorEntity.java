package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengluping on 2018/11/12
 */
public class CreatorEntity {
    private List<Map<String, String>> list;

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }
    /* private String deptid;
    private String empid;
    private String empname;
    private String emptype;
    private List<CreatorEntity> list = new ArrayList<>();

    public CreatorEntity(String empname) {
        this.empname = empname;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
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

    public String getEmptype() {
        return emptype;
    }

    public void setEmptype(String emptype) {
        this.emptype = emptype;
    }

    public List<CreatorEntity> getList() {
        return list;
    }

    public void setList(List<CreatorEntity> list) {
        this.list = list;
    }*/
}
