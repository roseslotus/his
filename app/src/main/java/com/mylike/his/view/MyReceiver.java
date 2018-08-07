package com.mylike.his.view;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;


import com.mylike.his.activity.LoginActivity;
import com.mylike.his.activity.consultant.CMainActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.fragment.consultant.CHomeFragment;
import com.mylike.his.utils.SPUtils;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhengluping on 2017/12/29.
 */

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //极光推送，初始化设备
        if (intent.getAction().equals(JPushInterface.ACTION_REGISTRATION_ID)) {
            //获得message的内容
            Bundle bundle = intent.getExtras();
            String registerId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            SPUtils.setCache(SPUtils.FILE_JP, SPUtils.JP_REGISTER_ID, registerId);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            //收到了通知
            ActivityManager am = (ActivityManager) BaseApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
            if (list != null && list.size() > 0) {
                ComponentName cpn = list.get(0).topActivity;
                if ("com.mylike.his.activity.consultant.CMainActivity".equals(cpn.getClassName())) {
                    intent.setClass(context.getApplicationContext(), CMainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent);
                }
            }
        }
    }
}
