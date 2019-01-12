package com.mylike.his.doctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.LoginActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.doctor.activity.BindPhoneNumberActivity;
import com.mylike.his.doctor.activity.ModifyPasswordActivity;
import com.mylike.his.doctor.activity.VersionInfoActivity;
import com.mylike.his.entity.BaseNewEntity;
import com.mylike.his.entity.LoginEntity;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2018/12/29.
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.tv_modify_password)
    TextView mTvModifyPassword;
    @BindView(R.id.tv_bind_phone_number)
    TextView mTvBindPhoneNumber;
    @BindView(R.id.tv_current_version)
    TextView mTvCurrentVersion;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_depart_name)
    TextView mTvDepartName;
    private View view;
    private Unbinder unbinder;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_d_mine, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        LoginEntity loginEntity =BaseApplication.getLoginEntity();
        mTvNickName.setText(loginEntity.getUsername()+" "+loginEntity.getDefaultDepName());
        mTvDepartName.setText(loginEntity.getTenantName());
        return rootView;
    }

    @OnClick({R.id.tv_modify_password, R.id.tv_bind_phone_number, R.id.tv_current_version, R.id.btn_loginout})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_modify_password:
                startActivity(ModifyPasswordActivity.class);
                break;
            case R.id.tv_bind_phone_number:
                startActivity(BindPhoneNumberActivity.class);
                break;
            case R.id.tv_current_version:
                startActivity(VersionInfoActivity.class);
                break;
            case R.id.btn_loginout:
                loginOut();
                break;
        }
    }

    public void loginOut() {
        CommonUtil.showLoadProgress(getActivity());

        HttpClient.getHttpApi().loginOut(BaseApplication.getLoginEntity().getToken()).enqueue(new Callback<BaseNewEntity>() {
            @Override
            public void onResponse(Call<BaseNewEntity> call, Response<BaseNewEntity> response) {
                CommonUtil.dismissLoadProgress();
                BaseApplication.setLoginEntity(null);
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<BaseNewEntity> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
