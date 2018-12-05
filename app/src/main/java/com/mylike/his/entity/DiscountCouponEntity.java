package com.mylike.his.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/11/24
 */
public class DiscountCouponEntity implements Serializable {
    private String activityId;
    private String activityName;
    private String activityType;
    private String birthday;
    private String custRange;
    private String custType;
    private String discount;
    private String endTime;
    private String lowerMoney;
    private String memberLevel;
    private String money;
    private String packedMoney;
    private String remark;
    private String sex;
    private String startTime;
    private String status;
    private String weekDay;
    private String which;
    private String isOrderly;

    private List<presentItems> items = new ArrayList();
    private List<presentItems> presentItems = new ArrayList<>();

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCustRange() {
        return custRange;
    }

    public void setCustRange(String custRange) {
        this.custRange = custRange;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getLowerMoney() {
        return lowerMoney;
    }

    public void setLowerMoney(String lowerMoney) {
        this.lowerMoney = lowerMoney;
    }

    public String getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPackedMoney() {
        return packedMoney;
    }

    public void setPackedMoney(String packedMoney) {
        this.packedMoney = packedMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getWhich() {
        return which;
    }

    public void setWhich(String which) {
        this.which = which;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getIsOrderly() {
        return isOrderly;
    }

    public void setIsOrderly(String isOrderly) {
        this.isOrderly = isOrderly;
    }

    public List<DiscountCouponEntity.presentItems> getPresentItems() {
        return presentItems;
    }

    public void setPresentItems(List<DiscountCouponEntity.presentItems> presentItems) {
        this.presentItems = presentItems;
    }

    public List<DiscountCouponEntity.presentItems> getItems() {
        return items;
    }

    public void setItems(List<DiscountCouponEntity.presentItems> items) {
        this.items = items;
    }

    public class presentItems implements Serializable {
        private String id;
        private String name;
        private String num;
        private String price;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
