package com.mylike.his.entity;

import java.util.List;

public class InHospitalSortResp {

    private List<DropdownsBean> dropdowns;
    private List<SortsBean> sorts;

    public List<DropdownsBean> getDropdowns() {
        return dropdowns;
    }

    public void setDropdowns(List<DropdownsBean> dropdowns) {
        this.dropdowns = dropdowns;
    }

    public List<SortsBean> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortsBean> sorts) {
        this.sorts = sorts;
    }

}
