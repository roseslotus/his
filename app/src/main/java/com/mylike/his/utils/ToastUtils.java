package com.mylike.his.utils;

import android.content.Context;
import android.widget.Toast;

import com.mylike.his.core.BaseApplication;

/**
 * Created by zhengluping on 2018/8/3.
 */

public class ToastUtils {
    private static Toast toast;

    /**
     * 解决Toast重复弹出 长时间不消失的问题
     * @param message
     */
    public static void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
