package com.mylike.his.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhengluping on 2018/11/21
 */
public class PackageEntity {
    private packageInfo packageInfo;
    private List<product> products;

    public PackageEntity.packageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageEntity.packageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public List<product> getProducts() {
        return products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }

    public class packageInfo {
        private String pkgtypeid;
        private String pkgtypename;
        private String pkgid;
        private String pkgcode;
        private String pkgname;
        private String price;

        private String price1;
        private String price2;
        private String count;

        public String getPkgtypeid() {
            return pkgtypeid;
        }

        public void setPkgtypeid(String pkgtypeid) {
            this.pkgtypeid = pkgtypeid;
        }

        public String getPkgtypename() {
            return pkgtypename;
        }

        public void setPkgtypename(String pkgtypename) {
            this.pkgtypename = pkgtypename;
        }

        public String getPkgid() {
            return pkgid;
        }

        public void setPkgid(String pkgid) {
            this.pkgid = pkgid;
        }

        public String getPkgcode() {
            return pkgcode;
        }

        public void setPkgcode(String pkgcode) {
            this.pkgcode = pkgcode;
        }

        public String getPkgname() {
            return pkgname;
        }

        public void setPkgname(String pkgname) {
            this.pkgname = pkgname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPrice1() {
            return price1;
        }

        public void setPrice1(String price1) {
            this.price1 = price1;
        }

        public String getPrice2() {
            return price2;
        }

        public void setPrice2(String price2) {
            this.price2 = price2;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    public static class product implements Serializable {
        private String description;
        private String num;
        private String pkgid;
        private String pname;
        private String pnumber;
        private String price;
        private String productcenter;
        private String productid;
        private String simplename;
        private String department;
        private String fdoctorid;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPkgid() {
            return pkgid;
        }

        public void setPkgid(String pkgid) {
            this.pkgid = pkgid;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getPnumber() {
            return pnumber;
        }

        public void setPnumber(String pnumber) {
            this.pnumber = pnumber;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductcenter() {
            return productcenter;
        }

        public void setProductcenter(String productcenter) {
            this.productcenter = productcenter;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getSimplename() {
            return simplename;
        }

        public void setSimplename(String simplename) {
            this.simplename = simplename;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getFdoctorid() {
            return fdoctorid;
        }

        public void setFdoctorid(String fdoctorid) {
            this.fdoctorid = fdoctorid;
        }
    }
}
