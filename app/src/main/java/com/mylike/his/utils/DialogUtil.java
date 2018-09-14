package com.mylike.his.utils;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.view.NumberPickerView;

/**
 * Created by zhengluping on 2018/2/24.
 * 弹框工具
 */
public class DialogUtil {

    public static Dialog dialog;
    public final static int CENTER = 0;//中间弹框
    public final static int BOTTOM = 1;//下方弹框
    public final static int RIGHT = 2;//右方方弹框

    /**
     * 弹框
     *
     * @param context
     * @param resource 弹框布局样式
     * @param gravity  弹框位置
     * @return
     */
    public static View commomDialog(final Context context, @LayoutRes int resource, int gravity) {
        View itemView = LayoutInflater.from(context).inflate(resource, null);
        dialog = new Dialog(context, R.style.DialogSelectStyle);

        Window window = dialog.getWindow();
        if (gravity == BOTTOM) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.DialogAnimation);//底部弹框添加动画效果
            window.getDecorView().setPadding(0, 0, 0, 0);
        } else if (gravity == RIGHT) {
            window.setGravity(Gravity.RIGHT);
//            window.setWindowAnimations(R.style.DialogAnimation);//底部弹框添加动画效果
            window.getDecorView().setPadding(200, 0, 0, 0);
        } else if (gravity == CENTER) {
            window.setGravity(Gravity.CENTER);
            window.getDecorView().setPadding(40, 0, 40, 0);
        }

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        dialog.setContentView(itemView);
        dialog.show();

        return itemView;
    }

    //提示弹框，只返回确认按钮
    public static View hintDialog(final Context context, String message) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_hint, null);
        TextView hintText = itemView.findViewById(R.id.hintText);
        hintText.setText(message);

        TextView cancelBtn = itemView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView confirmbtn = itemView.findViewById(R.id.confirmbtn);

        dialog = new Dialog(context, R.style.DialogSelectStyle);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(40, 0, 40, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = 800;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setContentView(itemView);
        dialog.show();
        return confirmbtn;
    }

    /**
     * 输入框弹框
     *
     * @param context
     * @param resource
     * @return
     */
    public static View inputDialog(final Context context, @LayoutRes int resource) {
        View itemView = LayoutInflater.from(context).inflate(resource, null);
        dialog = new Dialog(context, R.style.DialogInputSelectStyle);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        dialog.setContentView(itemView);
        dialog.show();
        DialogUtil.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                KeyboardUtils.hideKeyboard(context);
            }
        });
        return itemView;
    }

    //取消弹框
    public static void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
