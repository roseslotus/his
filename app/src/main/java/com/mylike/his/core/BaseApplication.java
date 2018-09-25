package com.mylike.his.core;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mylike.his.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
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

        //*********  Android studio3.1以后需要处理logger格式显示异常  ******************************
        LogStrategy logStrategy = new LogStrategy() {
            private String[] prefix = {". ", " ."};
            private int index = 0;

            @Override
            public void log(int priority, @Nullable String tag, @NonNull String message) {
                index = index ^ 1;
                Log.println(priority, prefix[index] + tag, message);
            }
        };
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .logStrategy(logStrategy)
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(0)         // （可选）要显示的方法行数。 默认2
                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
                .build();
        //*********  Android studio3.1以后需要处理logger格式显示异常  ******************************

        //初始化日志
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static Context getContext() {
        return context;
    }

    //初始化列表上下拉刷新加载控件样式
    static {
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setEnableLastTime(false);
                classicsHeader.setPrimaryColor(Color.TRANSPARENT);
//                classicsHeader.setPrimaryColor(context.getResources().getColor(R.color.green_50));
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
