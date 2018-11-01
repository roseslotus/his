package com.mylike.his.entity;

import java.io.Serializable;

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


    //套餐
    private String pkgid;
    private String pkgname;
    //产品
    private String productid;
    private String pname;
    //细目
    private String chaitemCd;
    private String itemName;

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


    /*   private String creatorid;
    private String isshow;
    private String isvalidity;
    private String lastupdateuserid;
    private String pkgcode;
    private String pkgid;
    private String pkgname;
    private String pkgtypeid;
    private int price;
    private String remark;
    private String simplename;
    private String tenantId;
    private String type;

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getIsvalidity() {
        return isvalidity;
    }

    public void setIsvalidity(String isvalidity) {
        this.isvalidity = isvalidity;
    }

    public String getLastupdateuserid() {
        return lastupdateuserid;
    }

    public void setLastupdateuserid(String lastupdateuserid) {
        this.lastupdateuserid = lastupdateuserid;
    }

    public String getPkgcode() {
        return pkgcode;
    }

    public void setPkgcode(String pkgcode) {
        this.pkgcode = pkgcode;
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

    public String getPkgtypeid() {
        return pkgtypeid;
    }

    public void setPkgtypeid(String pkgtypeid) {
        this.pkgtypeid = pkgtypeid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSimplename() {
        return simplename;
    }

    public void setSimplename(String simplename) {
        this.simplename = simplename;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }*/
}
