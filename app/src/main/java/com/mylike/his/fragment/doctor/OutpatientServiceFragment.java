package com.mylike.his.fragment.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.doctor.DepartmentShowActivity;
import com.mylike.his.activity.doctor.MedicalShowActivity;
import com.mylike.his.activity.doctor.VisitShowActivity;
import com.mylike.his.core.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/3/12.
 * 医生端-门诊
 */

public class OutpatientServiceFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.department_btn)
    TextView departmentBtn;
    @Bind(R.id.my_department_btn)
    TextView myDepartmentBtn;
    @Bind(R.id.medical_record_btn)
    TextView medicalRecordBtn;
    @Bind(R.id.medical_history_btn)
    TextView medicalHistoryBtn;
    @Bind(R.id.visit_btn)
    TextView visitBtn;
   /* @Bind(R.id.screening_text)
    TextView screeningText;
    @Bind(R.id.department_btn)
    TextView departmentBtn;
    @Bind(R.id.my_department_btn)
    TextView myDepartmentBtn;
    @Bind(R.id.medical_record_btn)
    TextView medicalRecordBtn;
    @Bind(R.id.medical_history_btn)
    TextView medicalHistoryBtn;
    @Bind(R.id.visit_btn)
    TextView visitBtn;*/

    private View view;

    public static OutpatientServiceFragment newInstance() {
        Bundle args = new Bundle();
        OutpatientServiceFragment fragment = new OutpatientServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outpatient_service, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.department_btn, R.id.my_department_btn, R.id.medical_record_btn, R.id.medical_history_btn, R.id.visit_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.department_btn://科室预约查询
                startActivity(DepartmentShowActivity.class);
                break;
            case R.id.my_department_btn://我的预约治疗
                startActivity(DepartmentShowActivity.class);
                break;
            case R.id.medical_record_btn://治疗记录
                startActivity(MedicalShowActivity.class);
                break;
            case R.id.medical_history_btn://历史诊疗
                startActivity(MedicalShowActivity.class);
                break;
            case R.id.visit_btn://复诊列表
                startActivity(VisitShowActivity.class);
                break;
        }
    }
}
