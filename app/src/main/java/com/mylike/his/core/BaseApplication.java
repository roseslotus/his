package com.mylike.his.core;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.mylike.his.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.text.SimpleDateFormat;
import java.util.jar.Attributes;

import cn.jpush.android.api.JPushInterface;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;


/**
 * Created by zhengluping on 2018/1/2.
 */
public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        RetrofitUrlManager.getInstance().setDebug(true);

        //初始化极光推送
        JPushInterface.setDebugMode(true);//如果是正式版就改成false
        JPushInterface.init(this);

        //初始化日志
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getContext() {
        return context;
    }

    //初始化上下拉控件样式
    static {
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setEnableLastTime(false);
                classicsHeader.setPrimaryColor(Color.TRANSPARENT);
                classicsHeader.setAccentColor(context.getResources().getColor(R.color.green_48));
                classicsHeader.setDrawableMarginRight(10);
                classicsHeader.setSpinnerStyle(SpinnerStyle.Scale);

                layout.setRefreshHeader(classicsHeader);
                layout.setPrimaryColorsId(R.color.green_50, R.color.white);//全局设置主题颜色
                layout.setHeaderHeight(30);
                layout.setFooterHeight(40);
            }
        });

    }
}
