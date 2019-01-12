package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;

/**
 * 消费记录详情
 * Created by thl on 2018/12/30.
 */

public class XiaofeijiluDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaofeijilu_detail);
    }
}
