package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by zhengluping on 2018/1/24.
 * 测试
 */
public class TestActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

    }
}
