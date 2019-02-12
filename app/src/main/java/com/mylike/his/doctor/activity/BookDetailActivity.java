package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.presener.WoDeYuYuePresenter;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.MyBookingDetailResp;
import com.mylike.his.entity.MyBookingItemBean;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.CustomerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 门诊 我的预约
 * Created by thl on 2019/1/1.
 */

public class BookDetailActivity extends BaseActivity {

    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.ll_detail_header_info)
    LinearLayout mLlDetailHeaderInfo;

    @BindView(R.id.tv_book_project_name)
    TextView mTvBookProjectName;
    @BindView(R.id.tv_book_doctor_name)
    TextView mTvBookDoctorName;
    @BindView(R.id.tv_book_time)
    TextView mTvBookTime;
    @BindView(R.id.tv_book_no)
    TextView mTvBookNo;
    @BindView(R.id.tv_book_remark)
    TextView mTvBookRemark;
    @BindView(R.id.tv_book_channel)
    TextView mTvBookChannel;
    @BindView(R.id.tv_book_advice)
    TextView mTvBookAdvice;

    MyBookingItemBean myBookingItemBean;
    WoDeYuYuePresenter woDeYuYuePresenter;
    MyBookingDetailResp myBookingDetailResp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuyue_detail);
        ButterKnife.bind(this);
        woDeYuYuePresenter = new WoDeYuYuePresenter(this);
        myBookingItemBean = (MyBookingItemBean)getIntent().getSerializableExtra("MyBookingItemBean");
        bindData();
        getMyBookDetail();
    }

    private void bindData() {
        CustomerMenZhenBean customer = myBookingItemBean.getCustomer();
        CustomerUtil.setCustomerDetailHeaderInfo(mLlDetailHeaderInfo,customer);
        if (myBookingDetailResp != null) {
            mTvBookProjectName.setText(myBookingDetailResp.getProductsName());
            mTvBookDoctorName.setText(myBookingDetailResp.getDoctor()+"/"+myBookingDetailResp.getInstrument());
            mTvBookTime.setText(myBookingDetailResp.getTime());
            mTvBookNo.setText(myBookingDetailResp.getNumber());
            mTvBookRemark.setText(myBookingDetailResp.getRemark());
            mTvBookChannel.setText(myBookingDetailResp.getWay());
            mTvBookAdvice.setText(myBookingDetailResp.getConsultant());

        }
    }

    public void getMyBookDetail() {
        CommonUtil.showLoadProgress(this);
        HttpClient.getHttpApi().getMyBookDetail(BaseApplication.getLoginEntity().getTenantId(), myBookingItemBean.getAppId())
                .enqueue(new Callback<MyBookingDetailResp>() {
                    @Override
                    public void onResponse(Call<MyBookingDetailResp> call, Response<MyBookingDetailResp> response) {
                        CommonUtil.dismissLoadProgress();
                        myBookingDetailResp = response.body();
                        bindData();

                    }

                    @Override
                    public void onFailure(Call<MyBookingDetailResp> call, Throwable t) {
                        CommonUtil.dismissLoadProgress();
                    }
                });
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
