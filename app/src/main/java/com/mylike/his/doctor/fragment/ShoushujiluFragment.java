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
import com.mylike.his.entity.OperationMySchedulingDetailResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.Constacts;

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

public class ShoushujiluFragment extends BaseFragment {


    @BindView(R.id.tv_operating_name)
    TextView mTvOperatingName;
    @BindView(R.id.tv_project_name)
    TextView mTvProjectName;
    @BindView(R.id.tv_anesthesia_method)
    TextView mTvAnesthesiaMethod;
    @BindView(R.id.tv_plan_time)
    TextView mTvPlanTime;
    @BindView(R.id.tv_operating_time)
    TextView mTvOperatingTime;
    @BindView(R.id.tv_book_remark)
    TextView mTvBookRemark;
    @BindView(R.id.tv_operation_room)
    TextView mTvOperationRoom;
    @BindView(R.id.tv_stage_number)
    TextView mTvStageNumber;
    @BindView(R.id.tv_doctor_name)
    TextView mTvDoctorName;
    @BindView(R.id.tv_anesthesia_doctor)
    TextView mTvAnesthesiaDoctor;
    @BindView(R.id.tv_doctor_zhuli)
    TextView mTvDoctorZhuli;
    @BindView(R.id.tv_operating_nurse)
    TextView mTvOperatingNurse;
    @BindView(R.id.tv_pratrol_nurse)
    TextView mTvPratrolNurse;
    @BindView(R.id.tv_whether_delay)
    TextView mTvWhetherDelay;
    @BindView(R.id.tv_delay_reason)
    TextView mTvDelayReason;
    private View view;
    private Unbinder unbinder;

    public static ShoushujiluFragment newInstance(String operationId) {
        ShoushujiluFragment fragment = new ShoushujiluFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constacts.CONTENT_DATA,operationId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shoushu_jilu, null, false);

        getOperationSchedulingDetail(getArguments().getString(Constacts.CONTENT_DATA));
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void getOperationSchedulingDetail(String operationId) {
        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getOperationSchedulingDetail(BaseApplication.getLoginEntity().getTenantId(), "32fa7d43be3b4680b12523d82563b161")
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
        mTvOperatingName.setText(data.getOperaName());
        mTvProjectName.setText(data.getSurgeryName());
        mTvAnesthesiaMethod.setText(data.getAnesthesia());
        mTvPlanTime.setText(data.getPlanTime());
        mTvOperatingTime.setText(data.getActualTime());
        mTvBookRemark.setText(data.getRemark());
        mTvOperationRoom.setText(data.getOperaRoom());
        mTvStageNumber.setText(data.getStage());
        mTvDoctorName.setText(data.getDoctor());
        mTvAnesthesiaDoctor.setText(data.getAnesthesiadoc());
        mTvDoctorZhuli.setText(data.getAssistant());
        mTvOperatingNurse.setText(data.getScrubNurse());
        mTvPratrolNurse.setText(data.getCircuitNurse());
        mTvWhetherDelay.setText(data.getDelay());
        mTvDelayReason.setText(data.getReason());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
