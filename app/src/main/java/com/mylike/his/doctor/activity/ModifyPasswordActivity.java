package com.mylike.his.doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.LoginActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.BaseNewEntity;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2018/12/29.
 */

public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.et_old_password)
    EditText mEtOldPassword;
    @BindView(R.id.et_new_password)
    EditText mEtNewPassword;
    @BindView(R.id.et_confirm_password)
    EditText mEtConfirmPassword;
    @BindView(R.id.btn_confirm)
    TextView mBtnConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.return_btn, R.id.et_old_password, R.id.et_new_password, R.id.et_confirm_password, R.id.btn_confirm})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.et_old_password:
                break;
            case R.id.et_new_password:
                break;
            case R.id.et_confirm_password:
                break;
            case R.id.btn_confirm:
                String oldPwd = mEtOldPassword.getText().toString();
                String newPwd = mEtNewPassword.getText().toString();
                String confirmPwd = mEtConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(oldPwd)){
                    CommonUtil.showToast("旧密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(newPwd)){
                    CommonUtil.showToast("新密码不能为空");
                    return;
                }
                if (newPwd.length()<8){
                    CommonUtil.showToast("新密码不能小于8位");
                    return;
                }
                if (TextUtils.isEmpty(confirmPwd)){
                    CommonUtil.showToast("确认密码不能为空");
                    return;
                }
                if (!newPwd.equals(confirmPwd)){
                    CommonUtil.showToast("2次输入的密码不一致");
                    return;
                }
                modifyPwd(oldPwd,newPwd);
                break;
        }
    }

    public void modifyPwd(String oldPwd,String newPwd){
        CommonUtil.showLoadProgress(this);

        HttpClient.getHttpApi().modifyPwd(BaseApplication.getLoginEntity().getUserId(),oldPwd,newPwd).enqueue(new Callback<BaseNewEntity>() {
            @Override
            public void onResponse(Call<BaseNewEntity> call, Response<BaseNewEntity> response) {
                CommonUtil.dismissLoadProgress();
                CommonUtil.showToast("修改成功");
                BaseApplication.setLoginEntity(null);
                Intent intent = new Intent(ModifyPasswordActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<BaseNewEntity> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
            }
        });
    }
}
