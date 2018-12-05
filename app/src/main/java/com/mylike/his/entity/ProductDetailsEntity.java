package com.mylike.his.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/6/12.
 */

public class ProductDetailsEntity implements Serializable {
    private String itemLx;//类型
    private String rebate = "";//折扣
    private String price;//原有价格
    private String price1 = "";//真实小计价格
    private String price2 = "";//真实单价价格
    private String count;//数量
    private String remark = "";//备注
    private String discount;//手动输入折扣
    private String isgive;//是否赠送产品
    //套餐
    private String pkgid;
    private String pkgname;
    //产品
    private String productid;
    private String pname;
    //细目
    private String chaitemCd;
    private String itemName;

    private List<PackageEntity.product> subItems = new ArrayList<>();


    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getItemLx() {
        return itemLx;
    }

    public void setItemLx(String itemLx) {
        this.itemLx = itemLx;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPkgid() {
        return pkgid;
    }

    public void setPkgid(String pkgid) {
        this.pkgid = pkgid;
    }

    public String getPkgname() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }

    public String getChaitemCd() {
        return chaitemCd;
    }

    public void setChaitemCd(String chaitemCd) {
        this.chaitemCd = chaitemCd;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<PackageEntity.product> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<PackageEntity.product> subItems) {
        this.subItems = subItems;
    }

    public String getIsgive() {
        return isgive;
    }

    public void setIsgive(String isgive) {
        this.isgive = isgive;
    }
}
