package com.mylike.his.activity.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/31.
 */

public class MessageSettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.remind_management_btn)
    TextView remindManagementBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.return_btn,R.id.remind_management_btn})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.remind_management_btn:
                intent = new Intent(this, RemindManagementActivity.class);
                startActivity(intent);
                break;
        }
    }
}
