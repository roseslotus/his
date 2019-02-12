package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.OperationPrePareResp;
import com.mylike.his.http.HttpClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2018/12/31.
 */

public class ShuQianZhunBeiFragment extends BaseFragment {

    @BindView(R.id.tv_prosthesis_status)
    TextView mTvProsthesisStatus;
    @BindView(R.id.tv_skintest_status)
    TextView mTvSkintestStatus;
    @BindView(R.id.tv_medicalexam_status)
    TextView mTvMedicalexamStatus;
    private View view;
    private Unbinder unbinder;

    public static ShuQianZhunBeiFragment newInstance() {
        ShuQianZhunBeiFragment fragment = new ShuQianZhunBeiFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shuqian_zhunbei, null, false);
        getOperationPrePare();
        unbinder = ButterKnife.bind(this, rootView);
        getOperationPrePare();
        return rootView;
    }

    public void getOperationPrePare() {
//        CommonUtil.showLoadProgress(this);
        HttpClient.getHttpApi().getOperationPrePare(BaseApplication.getLoginEntity().getTenantId(), "32fa7d43be3b4680b12523d82563b161")
                .enqueue(new Callback<OperationPrePareResp>() {
                    @Override
                    public void onResponse(Call<OperationPrePareResp> call, Response<OperationPrePareResp> response) {
//                        CommonUtil.dismissLoadProgress();

                        OperationPrePareResp data = response.body();
                        if (data!=null){
                            mTvProsthesisStatus.setText(data.getProsthesis());
                            mTvSkintestStatus.setText(data.getSkinTest());
                            mTvMedicalexamStatus.setText(data.getMedicalExam());

                        }
                    }

                    @Override
                    public void onFailure(Call<OperationPrePareResp> call, Throwable t) {
//                        CommonUtil.dismissLoadProgress();
                    }
                });
    }

    @OnClick({R.id.tv_prosthesis_status, R.id.tv_skintest_status, R.id.tv_medicalexam_status})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_prosthesis_status:
                break;
            case R.id.tv_skintest_status:
                break;
            case R.id.tv_medicalexam_status:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
