package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thl on 2018/12/29.
 */

public class LoginEntity  extends BaseNewEntity{

    /**
     * userId : 85100548
     * username : 袁玉坤
     * defaultDepId : 85101026
     * defaultDepName : 无创美容中心
     * identity : 医生
     * tenantId : 851010
     * tenantName : 上海美莱
     * sex : 女
     * departments : [{"depId":"85101047","depName":"皮肤美容中心"},{"depId":"85101026","depName":"无创美容中心"},{"depId":"85101014","depName":"口腔美容中心"}]
     * permissions : [{"featuresId":"","featuresName":"手术"}]
     * token : 1111
     */

    private String userId;//用户id
    private String username;//用户姓名
    private String defaultDepId;//默认科室id
    private String defaultDepName;//默认科室名称
    private String identity;//用户身份名称
    private String tenantId;//所属医院id
    private String tenantName;//所属医院名称
    private String depId;//权限科室id
    private String depName;//权限科室名称
    private String sex;//性别
    private String token;//登陆token
    private List<DepartmentBean> depts;
    private List<PermissionsBean> permissions;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDefaultDepId() {
        return defaultDepId;
    }

    public void setDefaultDepId(String defaultDepId) {
        this.defaultDepId = defaultDepId;
    }

    public String getDefaultDepName() {
        return defaultDepName;
    }

    public void setDefaultDepName(String defaultDepName) {
        this.defaultDepName = defaultDepName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DepartmentBean> getDepartments() {
        if (depts==null){
            depts = new ArrayList<>();
        }
        return depts;
    }

    public void setDepartments(List<DepartmentBean> departments) {
        this.depts = departments;
    }

    public List<PermissionsBean> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionsBean> permissions) {
        this.permissions = permissions;
    }


    public static class PermissionsBean {
        /**
         * featuresId :
         * featuresName : 手术
         */

        private String featuresId;//功能权限id
        private String featuresName;//功能权限名称

        public String getFeaturesId() {
            return featuresId;
        }

        public void setFeaturesId(String featuresId) {
            this.featuresId = featuresId;
        }

        public String getFeaturesName() {
            return featuresName;
        }

        public void setFeaturesName(String featuresName) {
            this.featuresName = featuresName;
        }
    }
}
