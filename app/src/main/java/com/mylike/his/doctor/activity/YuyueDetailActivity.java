package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;

/**
 * 门诊 我的预约
 * Created by thl on 2019/1/1.
 */

public class YuyueDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuyue_detail);
    }
}
