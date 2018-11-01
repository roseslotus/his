package com.mylike.his.core;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.mylike.his.R;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.view.FloatingDragger;

/**
 * Created by zhengluping on 2018/1/16.
 */
public class BaseActivity extends AppCompatActivity {

    private boolean isLoadProgress = true;//是否显示加载框
    private boolean isActionBar = true;//是否隐藏标题栏
    private boolean isKeyboardUp = true;//是否禁止键盘弹出
    private boolean isScreenRotating = true;//是否禁止横屏
    private boolean isFloatingDragger = false;//是否显示悬浮球
    private boolean isStatusBarColor = true;//是否更改状态栏颜色

    //列表页脚
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isLoadProgress) {
            CommonUtil.showLoadProgress(this);
        }
        if (isActionBar) {//隐藏标题栏
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (isKeyboardUp) {//禁止键盘自动弹出
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        if (isScreenRotating) {//禁止横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (isFloatingDragger) {//显示悬浮球
            super.setContentView(new FloatingDragger(this, layoutResID).getView());
        } else {
            super.setContentView(layoutResID);
        }

        if (isStatusBarColor) {//更改状态栏颜色
            StatusBarUtil.setColor(this, getResources().getColor(R.color.theme_color), 0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //监控触摸事件，点击文本框外部收起键盘
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (CommonUtil.isShouldHideInput(v, ev, 0)) {
                CommonUtil.hideSoftInput(v.getWindowToken(), this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtil.dismissLoadProgress();
        DialogUtil.dismissDialog();
    }

    /**
     * --------------设置是否显示加载框--------------------------------
     *
     * @param isLoadProgress 默认显示
     */
    public void setLoadProgress(boolean isLoadProgress) {
        this.isLoadProgress = isLoadProgress;
    }

    /**
     * --------------设置是否隐藏标题栏--------------------------------
     *
     * @param isActionBar 默认隐藏
     */
    public void setActionBar(boolean isActionBar) {
        this.isActionBar = isActionBar;
    }

    /**
     * ---------------设置是否更改状态栏颜色------------------------------
     *
     * @param isStatusBarColor 默认状态栏颜色与标题颜色一致
     */
    public void setStatusBarColor(boolean isStatusBarColor) {
        this.isStatusBarColor = isStatusBarColor;
    }

    /**
     * ----------------设置是否禁止键盘弹出--------------------------------
     *
     * @param isKeyboardUp
     */
    public void setKeyboardUp(boolean isKeyboardUp) {
        this.isKeyboardUp = isKeyboardUp;
    }

    /**
     * ---------------设置是否禁止横屏---------------------------------------
     *
     * @param isScreenRotating
     */
    public void setScreenRotating(boolean isScreenRotating) {
        this.isScreenRotating = isScreenRotating;
    }

    /**
     * ---------------设置是否显示悬浮球---------------------------------------
     *
     * @param isFloatingDragger
     */
    public void setFloatingDragger(boolean isFloatingDragger) {
        this.isFloatingDragger = isFloatingDragger;
    }

    /**
     * 初始化listview页脚
     *
     * @param listView
     */
    public void setListView(ListView listView) {
        if (listView != null) {
            View view = View.inflate(this, R.layout.common_item_text, null);
            textView = view.findViewById(R.id.text);
            textView.setPadding(10, 30, 10, 30);
            listView.addFooterView(view);
        }
    }

    /**
     * 是否显示页脚
     *
     * @param isListNotData 是否显示
     * @param textValue     显示页脚的文字，null为默认数据
     */
    public void setListNotData(boolean isListNotData, String textValue) {
        if (isListNotData) {
            textView.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(textValue)) {
                textView.setText("没有更多数据了");
            } else {
                textView.setText(textValue);
            }
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * 跳转页面
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle 打发
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 携带String的页面跳转(TAG)
     *
     * @param clz 打发
     */
    public void startActivity(Class<?> clz, String tag, String value) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (!TextUtils.isEmpty(value)) {
            intent.putExtra(tag, value);
        }
        startActivity(intent);
    }

}
