package com.mylike.his.fragment.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.doctor.SurgeryDynamicActivity;
import com.mylike.his.activity.doctor.SurgeryOrderActivity;
import com.mylike.his.activity.doctor.SurgeryScheduleActivity;
import com.mylike.his.activity.doctor.SurgeryTableActivity;
import com.mylike.his.core.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/3/12.
 * 医生端-手术
 */

public class SurgeryFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.surgery_dynamic)
    TextView surgeryDynamic;
    @Bind(R.id.surgery_order_btn)
    TextView surgeryOrderBtn;
    @Bind(R.id.surgery_schedule_btn)
    TextView surgeryScheduleBtn;
    @Bind(R.id.surgery_table_btn)
    TextView surgeryTableBtn;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_surgery, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public static SurgeryFragment newInstance() {
        Bundle args = new Bundle();
        SurgeryFragment fragment = new SurgeryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.surgery_order_btn, R.id.surgery_dynamic, R.id.surgery_schedule_btn, R.id.surgery_table_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.surgery_order_btn:
                startActivity(SurgeryOrderActivity.class);
                break;
            case R.id.surgery_schedule_btn:
                startActivity(SurgeryScheduleActivity.class);
                break;
            case R.id.surgery_table_btn:
                startActivity(SurgeryTableActivity.class);
                break;
            case R.id.surgery_dynamic:
                startActivity(SurgeryDynamicActivity.class);
                break;
        }
    }
}
