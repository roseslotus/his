package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thl on 2018/12/29.
 */

public class BindPhoneNumberActivity extends BaseActivity {

    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @BindView(R.id.btn_send_verify_code)
    TextView mBtnSendVerifyCode;
    @BindView(R.id.btn_bind_phone_number)
    TextView mBtnBindPhoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone_number);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.return_btn, R.id.et_phone_number, R.id.et_verify_code, R.id.btn_send_verify_code, R.id.btn_bind_phone_number})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.et_phone_number:
                break;
            case R.id.et_verify_code:
                break;
            case R.id.btn_send_verify_code:
                break;
            case R.id.btn_bind_phone_number:
                break;
        }
    }
}
