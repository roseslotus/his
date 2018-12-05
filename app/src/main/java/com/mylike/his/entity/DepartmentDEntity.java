package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengluping on 2018/11/22
 */
public class DepartmentDEntity {
    private List<department> departments = new ArrayList<>();
    private Map<String, List<deptDocker>> deptDockers = new HashMap<>();

    public List<department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<department> departments) {
        this.departments = departments;
    }

    public Map<String, List<deptDocker>> getDeptDockers() {
        return deptDockers;
    }

    public void setDeptDockers(Map<String, List<deptDocker>> deptDockers) {
        this.deptDockers = deptDockers;
    }

    public static class department {
        private String deptcode;
        private String deptid = "";
        private String deptname;
        private String depttype;
        private String grade;
        private String isuse;
        private String leader;
        private String pycode;
        private String sortno;
        private String tenantId;
        private String tranId;
        private String updept;
        private String wbcode;

        public department(String deptname) {
            this.deptname = deptname;
        }

        public String getDeptcode() {
            return deptcode;
        }

        public void setDeptcode(String deptcode) {
            this.deptcode = deptcode;
        }

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

        public String getDepttype() {
            return depttype;
        }

        public void setDepttype(String depttype) {
            this.depttype = depttype;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getIsuse() {
            return isuse;
        }

        public void setIsuse(String isuse) {
            this.isuse = isuse;
        }

        public String getLeader() {
            return leader;
        }

        public void setLeader(String leader) {
            this.leader = leader;
        }

        public String getPycode() {
            return pycode;
        }

        public void setPycode(String pycode) {
            this.pycode = pycode;
        }

        public String getSortno() {
            return sortno;
        }

        public void setSortno(String sortno) {
            this.sortno = sortno;
        }

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String getTranId() {
            return tranId;
        }

        public void setTranId(String tranId) {
            this.tranId = tranId;
        }

        public String getUpdept() {
            return updept;
        }

        public void setUpdept(String updept) {
            this.updept = updept;
        }

        public String getWbcode() {
            return wbcode;
        }

        public void setWbcode(String wbcode) {
            this.wbcode = wbcode;
        }
    }

    public static class deptDocker {
        private String empId = "";
        private String empName;

        public deptDocker(String empName) {
            this.empName = empName;
        }

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }
    }

}
