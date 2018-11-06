package com.mylike.his.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.security.KeyStore;
import java.util.List;

/**
 * Created by zhengluping on 2018/11/5
 */
public class ConsumeDDEntity implements IPickerViewData {
    private String departmentid;
    private String departmentname;
    private List<ConsumeDDEntity> doctorlist;

    public ConsumeDDEntity(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public List<ConsumeDDEntity> getDoctorlist() {
        return doctorlist;
    }

    public void setDoctorlist(List<ConsumeDDEntity> doctorlist) {
        this.doctorlist = doctorlist;
    }

    @Override
    public String getPickerViewText() {
        return departmentname;
    }
}
