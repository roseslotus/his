package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.MenZhenTreatDengJiDetailBean;
import com.mylike.his.entity.OperationMyBookingDetailResp;
import com.mylike.his.entity.OperationMyBookingItemBean;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.CustomerUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 手术 我的预约
 * Created by thl on 2019/1/1.
 */

public class OperationMyBookDetailActivity extends BaseActivity {

    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.ll_detail_header_info)
    LinearLayout mLlDetailHeaderInfo;
    @BindView(R.id.tv_project_name)
    TextView mTvProjectName;
    @BindView(R.id.tv_book_time)
    TextView mTvBookTime;
    @BindView(R.id.tv_doctor_name)
    TextView mTvDoctorName;
    @BindView(R.id.tv_create_time)
    TextView mTvCreateTime;
    @BindView(R.id.tv_book_people_name)
    TextView mTvBookPeopleName;
    @BindView(R.id.tv_answer_name)
    TextView mTvAnswerName;
    @BindView(R.id.tv_pay_status)
    TextView mTvPayStatus;
    @BindView(R.id.tv_whether_gohosptal)
    TextView mTvWhetherGohosptal;
    @BindView(R.id.tv_hocus_method)
    TextView mTvHocusMethod;
    @BindView(R.id.tv_book_remark)
    TextView mTvBookRemark;

    OperationMyBookingItemBean data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_mybook_detail);
        ButterKnife.bind(this);

        data= (OperationMyBookingItemBean)getIntent().getSerializableExtra("data");
        CustomerUtil.setCustomerDetailHeaderInfo(mLlDetailHeaderInfo,data.getCustomer());
        getOperationMyBookDetail(data.getAppId());

    }

    public void getOperationMyBookDetail(String bookId) {
        CommonUtil.showLoadProgress(OperationMyBookDetailActivity.this);
        HttpClient.getHttpApi().getOperationMyBookDetail(BaseApplication.getLoginEntity().getTenantId(),bookId)
                .enqueue(new Callback<OperationMyBookingDetailResp>() {
                    @Override
                    public void onResponse(Call<OperationMyBookingDetailResp> call, Response<OperationMyBookingDetailResp> response) {
                        CommonUtil.dismissLoadProgress();
                        bindData(response.body());

                    }

                    @Override
                    public void onFailure(Call<OperationMyBookingDetailResp> call, Throwable t) {
                        CommonUtil.dismissLoadProgress();
                    }
                });
    }

    private void bindData(OperationMyBookingDetailResp data) {
        if (data!=null){
            mTvProjectName.setText(data.getOperaName());
            mTvBookTime.setText(data.getTime());
            mTvDoctorName.setText(data.getDoctor());
            mTvCreateTime.setText(data.getCreateTime());
            mTvBookPeopleName.setText(data.getPerson());
            mTvAnswerName.setText(data.getConsultant());
            mTvPayStatus.setText(data.getPay());
            mTvWhetherGohosptal.setText(data.getType()==0?"门诊":"住院");
            mTvHocusMethod.setText(data.getAnesthesia());
            mTvBookRemark.setText(data.getRemark());
        }
    }

    @OnClick({R.id.return_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.return_btn:
                finish();
                break;

        }
    }
}
