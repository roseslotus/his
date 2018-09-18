package com.mylike.his.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.ProductDetailsEntity;

import java.text.DecimalFormat;
import java.util.logging.Handler;

/**
 * 工具类
 */
public class CommonUtil {

    //-----------------------------------------------------------toast提示-------------------------------------------------------
    private static Toast toast;

    /**
     * 解决Toast重复弹出 长时间不消失的问题
     *
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

    //-----------------------------------------------------------加载框-------------------------------------------------------
    public static Dialog dialog;

    /**
     * 加载动态旋转
     *
     * @param context
     * @return
     */
    public static void showLoadProgress(final Context context) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_load, null);
        dialog = new Dialog(context, R.style.LoadDialog);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        dialog.setContentView(itemView);
        dialog.show();
    }

    //取消弹框
    public static void dismissLoadProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    //-------------------------------------------------------手机号正则表达式-------------------------------------------------------
    /**
     * 判断字符串是否符合手机号码格式
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     * "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
     */
    private static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";

    /**
     * 判断是否是手机号
     *
     * @param mobileNums
     * @return 是手机号true
     */
    public static boolean isChinaPhoneLegal(String mobileNums) {
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(REGEX_MOBILE);
    }

    //-------------------------------------------------------保留后两位小数-------------------------------------------------------
    public static String setTwoNumber(String numberStr) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (TextUtils.isEmpty(numberStr)) {
            numberStr = "0";
        }
        Double number = Double.parseDouble(numberStr);
        decimalFormat.format(number);
        return decimalFormat.format(number);
    }

    public static String setTwoNumber(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.format(number);
        return decimalFormat.format(number);
    }
}
