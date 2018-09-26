package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/24.
 * 测试
 */
public class TestActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.aaa)
    DrawerLayout aaa;
    @Bind(R.id.filtrate_menu)
    LinearLayout filtrateMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.button, R.id.aaa})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                aaa.openDrawer(filtrateMenu);
                break;
            case R.id.aaa:
                break;
        }

    }
}
