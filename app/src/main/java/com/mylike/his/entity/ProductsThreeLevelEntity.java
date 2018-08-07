package com.mylike.his.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/6/8.
 */

public class ProductsThreeLevelEntity {
    private List<ProductChildrenEntity> comboType = new ArrayList<>();
    private List<ProductChildrenEntity> minutiaType = new ArrayList<>();
    private List<ProductChildrenEntity> subjectProductType = new ArrayList<>();

    public List<ProductChildrenEntity> getComboType() {
        return comboType;
    }

    public void setComboType(List<ProductChildrenEntity> comboType) {
        this.comboType = comboType;
    }

    public List<ProductChildrenEntity> getMinutiaType() {
        return minutiaType;
    }

    public void setMinutiaType(List<ProductChildrenEntity> minutiaType) {
        this.minutiaType = minutiaType;
    }

    public List<ProductChildrenEntity> getSubjectProductType() {
        return subjectProductType;
    }

    public void setSubjectProductType(List<ProductChildrenEntity> subjectProductType) {
        this.subjectProductType = subjectProductType;
    }
}
