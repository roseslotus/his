package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.utils.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thl on 2018/12/29.
 */

public class VersionInfoActivity extends BaseActivity {


    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.tv_current_version)
    TextView mTvCurrentVersion;
    @BindView(R.id.tv_soft_version)
    TextView mTvSoftVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_version_info);
        ButterKnife.bind(this);
        mTvCurrentVersion.setText(SystemUtil.getLocalVersionName(this));
        mTvSoftVersion.setText(SystemUtil.getLocalVersionName(this));

    }

    @OnClick({R.id.return_btn, R.id.tv_current_version, R.id.tv_soft_version})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.tv_current_version:
                break;
            case R.id.tv_soft_version:
                break;
        }
    }
}
