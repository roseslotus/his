package com.mylike.his.entity;

/**
 * Created by zhengluping on 2018/11/7
 */
public class VersionsEntity {
    private String filePath;//apk下载地址
    private String version;//版本号
    private String lowVersion;//最低版本号（判别是否强制更新）
    private String isUpdate;//是否强制更新

    public String getLowVersion() {
        return lowVersion;
    }

    public void setLowVersion(String lowVersion) {
        this.lowVersion = lowVersion;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }
}
