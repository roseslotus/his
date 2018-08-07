package com.mylike.his.activity.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/31.
 */

public class MedicalDetailsActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.medical_record_btn)
    TextView medicalRecordBtn;
    @Bind(R.id.medical_information_btn)
    TextView medicalInformationBtn;
    @Bind(R.id.prescription_btn)
    TextView prescriptionBtn;
    @Bind(R.id.consumption_icon)
    TextView consumptionIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_details);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.return_btn, R.id.medical_record_btn, R.id.prescription_btn, R.id.consumption_icon})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.medical_record_btn://治疗记录
                startActivity(MedicalRecordsActivity.class);
                break;
            case R.id.prescription_btn://已开处方
                startActivity(PrescriptionActivity.class);
                break;
            case R.id.consumption_icon://消费记录
                startActivity(RecordsConsumptionActivity.class);
            break;
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
