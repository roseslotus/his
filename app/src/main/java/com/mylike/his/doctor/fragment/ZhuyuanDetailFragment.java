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
import com.mylike.his.entity.MyInHospitalDetailResp;
import com.mylike.his.http.HttpClient;
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

public class ZhuyuanDetailFragment extends BaseFragment {

    @BindView(R.id.tv_berth_no)
    TextView mTvBerthNo;
    @BindView(R.id.tv_illness_level)
    TextView mTvIllnessLevel;
    @BindView(R.id.tv_nurse_level)
    TextView mTvNurseLevel;
    @BindView(R.id.tv_depart_name)
    TextView mTvDepartName;
    @BindView(R.id.tv_illness_area)
    TextView mTvIllnessArea;
    @BindView(R.id.tv_ennter_hospital_date)
    TextView mTvEnnterHospitalDate;
    @BindView(R.id.tv_out_hospital_date)
    TextView mTvOutHospitalDate;
    @BindView(R.id.tv_illness_description)
    TextView mTvIllnessDescription;
    @BindView(R.id.tv_doctor_name)
    TextView mTvDoctorName;
    @BindView(R.id.tv_hospital_doctor_name)
    TextView mTvHospitalDoctorName;
    @BindView(R.id.tv_resident_nurse)
    TextView mTvResidentNurse;
    private View view;
    private Unbinder unbinder;
    private String registId;

    public static ZhuyuanDetailFragment newInstance(String registId) {
        ZhuyuanDetailFragment fragment = new ZhuyuanDetailFragment();
        Bundle bundle= new Bundle();
        bundle.putString(Constacts.CONTENT_DATA,registId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_zhuyuan_detail, null, false);
        if (getArguments() != null) {
            registId= getArguments().getString(Constacts.CONTENT_DATA);
        }

        getMyInHospitalDetail();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    public void getMyInHospitalDetail() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getMyInHospitalDetail(BaseApplication.getLoginEntity().getTenantId(), registId)
                .enqueue(new Callback<MyInHospitalDetailResp>() {
                    @Override
                    public void onResponse(Call<MyInHospitalDetailResp> call, Response<MyInHospitalDetailResp> response) {
//                CommonUtil.dismissLoadProgress();
                        bindDate(response.body());

                    }

                    @Override
                    public void onFailure(Call<MyInHospitalDetailResp> call, Throwable t) {
//                CommonUtil.dismissLoadProgress();
                    }
                });
    }

    private void bindDate(MyInHospitalDetailResp body) {
        mTvBerthNo.setText(body.getBedNo());
        mTvIllnessLevel.setText(body.getCondition());
        mTvNurseLevel.setText(body.getLevel());
        mTvDepartName.setText(body.getDepName());
        mTvIllnessArea.setText(body.getArea());
        mTvEnnterHospitalDate.setText(body.getEnterDate());
        mTvOutHospitalDate.setText(body.getOutDate());
        mTvIllnessDescription.setText(body.getDiagnosis());
        mTvDoctorName.setText(body.getDoctor());
        mTvHospitalDoctorName.setText(body.getResidentDoc());
        mTvResidentNurse.setText(body.getNurse());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
