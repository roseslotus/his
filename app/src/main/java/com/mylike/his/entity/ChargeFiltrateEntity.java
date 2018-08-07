package com.mylike.his.entity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/7/25.
 */

public class ChargeFiltrateEntity {
    private String paramcode;
    private String paramname;
    private List<TypeInfo> list = new ArrayList<>();

    public String getParamcode() {
        return paramcode;
    }

    public void setParamcode(String paramcode) {
        this.paramcode = paramcode;
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public List<TypeInfo> getList() {
        return list;
    }

    public void setList(List<TypeInfo> list) {
        this.list = list;
    }

    public class TypeInfo {
        private String id;
        private String name;

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
    }
}
