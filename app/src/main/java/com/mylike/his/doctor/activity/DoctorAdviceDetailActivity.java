package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.DoctorAdviceDetailResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.Constacts;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2018/12/31.
 */

public class DoctorAdviceDetailActivity extends BaseActivity {


    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.tv_product_name)
    TextView mTvProductName;
    @BindView(R.id.tv_stanndard_name)
    TextView mTvStanndardName;
    @BindView(R.id.tv_each_time_num)
    TextView mTvEachTimeNum;
    @BindView(R.id.tv_doctor_usage)
    TextView mTvDoctorUsage;
    @BindView(R.id.tv_use_rate)
    TextView mTvUseRate;
    @BindView(R.id.tv_user_frequency)
    TextView mTvUserFrequency;
    @BindView(R.id.tv_order_time)
    TextView mTvOrderTime;
    @BindView(R.id.tv_order_doc)
    TextView mTvOrderDoc;
    private CommonAdapter<DoctorAdviceDetailResp.ExecBean> commonAdapter;
    private List<DoctorAdviceDetailResp.ExecBean> mDatas = new ArrayList<>();
    private String orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_advice_detail);
        ButterKnife.bind(this);
        orderId =getIntent().getStringExtra(Constacts.CONTENT_DATA);
        commonAdapter = new CommonAdapter<DoctorAdviceDetailResp.ExecBean>(this, R.layout.item_doctor_advice_exec_detail, mDatas) {
            @Override
            protected void convert(ViewHolder holder, DoctorAdviceDetailResp.ExecBean item, int position) {
                holder.setText(R.id.tv_exec_order_status,"医嘱执行状态:"+item.getOrderStatus());
                holder.setText(R.id.tv_order_confirm_time,item.getConfirmTime());
                holder.setText(R.id.tv_confirm_people_name,item.getConfirmor());
                holder.setText(R.id.tv_exec_time,item.getExeTime());
                holder.setText(R.id.tv_exec_people_name,item.getExecutive());
            }
        };
        mListView.setAdapter(commonAdapter);
        getDoctorAdviceDetail("");
    }

    public void getDoctorAdviceDetail(String operationId) {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getDoctorAdviceDetail(BaseApplication.getLoginEntity().getTenantId(), orderId)
                .enqueue(new Callback<DoctorAdviceDetailResp>() {
                    @Override
                    public void onResponse(Call<DoctorAdviceDetailResp> call, Response<DoctorAdviceDetailResp> resp) {
                        CommonUtil.dismissLoadProgress();
                        bindData(resp.body());
                        mDatas.addAll(resp.body().getExecs());
                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<DoctorAdviceDetailResp> call, Throwable t) {
                        CommonUtil.dismissLoadProgress();
                    }
                });
    }

    private void bindData(DoctorAdviceDetailResp body) {
        mTvProductName.setText(body.getDrugName());
        mTvStanndardName.setText(body.getSpecification());
        mTvEachTimeNum.setText(body.getEachTime());
        mTvDoctorUsage.setText(body.getUsage());
        mTvUseRate.setText(body.getRate());
        mTvUserFrequency.setText(body.getFrequency());
        mTvOrderTime.setText(body.getOrderTime());
        mTvOrderDoc.setText(body.getOrderDoc());
    }


}
