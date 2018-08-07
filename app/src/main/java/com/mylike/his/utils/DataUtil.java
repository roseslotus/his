package com.mylike.his.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengluping on 2018/4/24.
 * <p>
 * 假数据
 */
public class DataUtil {
    /**
     * @param sum 数据条数
     * @return
     */
    public static List<String> getData(int sum) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            list.add("data" + i);
        }
        return list;
    }
}
