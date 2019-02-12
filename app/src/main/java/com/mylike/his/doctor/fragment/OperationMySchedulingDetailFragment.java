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
import com.mylike.his.doctor.activity.OperationMyBookDetailActivity;
import com.mylike.his.entity.OperationMyBookingDetailResp;
import com.mylike.his.entity.OperationMySchedulingDetailResp;
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
 * Created by thl on 2018/12/31.
 */

public class OperationMySchedulingDetailFragment extends BaseFragment {

    @BindView(R.id.tv_project_name)
    TextView mTvProjectName;
    @BindView(R.id.tv_project_name2)
    TextView mTvProjectName2;
    @BindView(R.id.tv_anesthesia_method)
    TextView mTvAnesthesiaMethod;
    @BindView(R.id.tv_book_time)
    TextView mTvBookTime;
    @BindView(R.id.tv_doctor_name)
    TextView mTvDoctorName;
    @BindView(R.id.tv_create_time)
    TextView mTvCreateTime;
    @BindView(R.id.tv_creater_name)
    TextView mTvCreaterName;
    @BindView(R.id.tv_book_remark)
    TextView mTvBookRemark;
    private View view;
    private Unbinder unbinder;

    public static OperationMySchedulingDetailFragment newInstance(String operationId) {
        OperationMySchedulingDetailFragment fragment = new OperationMySchedulingDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("operationId",operationId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_operation_my_scheduling_detail, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        getOperationMyBookDetail(getArguments().getString("operationId"));
        return rootView;
    }

    public void getOperationMyBookDetail(String operationId) {
        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getOperationSchedulingDetail(BaseApplication.getLoginEntity().getTenantId(),operationId)
                .enqueue(new Callback<OperationMySchedulingDetailResp>() {
                    @Override
                    public void onResponse(Call<OperationMySchedulingDetailResp> call, Response<OperationMySchedulingDetailResp> response) {
                        CommonUtil.dismissLoadProgress();
                        bindData(response.body());

                    }

                    @Override
                    public void onFailure(Call<OperationMySchedulingDetailResp> call, Throwable t) {
                        CommonUtil.dismissLoadProgress();
                    }
                });
    }

    private void bindData(OperationMySchedulingDetailResp data) {
        mTvProjectName.setText(data.getOperaName());
        mTvProjectName2.setText(data.getSurgeryName());
        mTvAnesthesiaMethod.setText(data.getAnesthesia());
        mTvBookTime.setText(data.getTime());
        mTvCreateTime.setText(data.getCreateTime());
        mTvDoctorName.setText(data.getDoctor());
        mTvCreaterName.setText(data.getCreate());
        mTvBookRemark.setText(data.getRemark());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
