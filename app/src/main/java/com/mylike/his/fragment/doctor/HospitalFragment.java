package com.mylike.his.fragment.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.doctor.HospitalShowActivity;
import com.mylike.his.core.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/3/16.
 * 医生端-住院
 */

public class HospitalFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.today_hospital_btn)
    TextView todayHospitalBtn;
    @Bind(R.id.out_hospital_btn)
    TextView outHospitalBtn;
    private View view;

    public static HospitalFragment newInstance() {
        Bundle args = new Bundle();
        HospitalFragment fragment = new HospitalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hospital, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.today_hospital_btn, R.id.out_hospital_btn})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.today_hospital_btn:
                startActivity(HospitalShowActivity.class);
                break;
            case R.id.out_hospital_btn:
                startActivity(HospitalShowActivity.class);
                break;
        }
    }
}
