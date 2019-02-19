package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

public class OperationPhotoBean {


    private String itemName;
    private String uploadTime;
    private List<PhotoBean> preoperative;
    private List<PhotoBean> intranperative;
    private List<PhotoBean> postoperative;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public List<PhotoBean> getPreoperative() {
        if (preoperative == null) {
            preoperative = new ArrayList<>();
        }
        return preoperative;
    }

    public void setPreoperative(List<PhotoBean> preoperative) {
        this.preoperative = preoperative;
    }

    public List<PhotoBean> getIntranperative() {
        if (intranperative == null) {
            intranperative = new ArrayList<>();
        }
        return intranperative;
    }

    public void setIntranperative(List<PhotoBean> intranperative) {
        this.intranperative = intranperative;
    }

    public List<PhotoBean> getPostoperative() {
        if (postoperative == null) {
            postoperative = new ArrayList<>();
        }
        return postoperative;
    }

    public void setPostoperative(List<PhotoBean> postoperative) {
        this.postoperative = postoperative;
    }

    public static class PhotoBean {

        private String path;
        private String remark;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

}
