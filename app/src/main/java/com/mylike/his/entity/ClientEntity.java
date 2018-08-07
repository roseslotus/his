package com.mylike.his.entity;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import java.util.List;

/**
 * Created by zhengluping on 2018/6/12.
 */

public class ClientEntity extends BaseIndexPinyinBean {

    private String customId;
    private String customMobile;
    private String customName;
    private String customNamePinyin;
    private String customSex;
    private String membershipLevel;
    private String activeLevel;
    private String activeLevelName;
    private String star;
    private String comeHospitalNum;
    private String totalAmounts;
    private String cardName;
    private String custLogo;

    public String getCustLogo() {
        return custLogo;
    }

    public void setCustLogo(String custLogo) {
        this.custLogo = custLogo;
    }

    public String getActiveLevel() {
        return activeLevel;
    }

    public void setActiveLevel(String activeLevel) {
        this.activeLevel = activeLevel;
    }

    public String getActiveLevelName() {
        return activeLevelName;
    }

    public void setActiveLevelName(String activeLevelName) {
        this.activeLevelName = activeLevelName;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getComeHospitalNum() {
        return comeHospitalNum;
    }

    public void setComeHospitalNum(String comeHospitalNum) {
        this.comeHospitalNum = comeHospitalNum;
    }

    public String getTotalAmounts() {
        return totalAmounts;
    }

    public void setTotalAmounts(String totalAmounts) {
        this.totalAmounts = totalAmounts;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getCustomMobile() {
        return customMobile;
    }

    public void setCustomMobile(String customMobile) {
        this.customMobile = customMobile;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getCustomNamePinyin() {
        return customNamePinyin;
    }

    public void setCustomNamePinyin(String customNamePinyin) {
        this.customNamePinyin = customNamePinyin;
    }

    public String getCustomSex() {
        return customSex;
    }

    public void setCustomSex(String customSex) {
        this.customSex = customSex;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    @Override
    public String getTarget() {
        return customName;
    }
}
