package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.InHospitalCostDetailBean;
import com.mylike.his.entity.InHospitalDetailResp;
import com.mylike.his.entity.MyInHospitalCostResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2018/12/31.
 */

public class ZhuyuanFeiyongDetailActivity extends BaseActivity {

    @BindView(R.id.feiyong_list)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private CommonAdapter<InHospitalCostDetailBean> commonAdapter;
    private List<InHospitalCostDetailBean> mDatas =  new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuyuan_feiyong_detail);
        ButterKnife.bind(this);

        commonAdapter = new CommonAdapter<InHospitalCostDetailBean>(this,R.layout.item_zhuyuan_feiyong_detail,mDatas) {
            @Override
            protected void convert(ViewHolder holder, InHospitalCostDetailBean item, int position) {
                holder.setText(R.id.tv_bill_date,item.getBillDate());
                holder.setText(R.id.tv_whether_free,item.getFreeFlg());
                holder.setText(R.id.tv_product_name,item.getItemName());
                holder.setText(R.id.tv_product_num,item.getBillNum());
                holder.setText(R.id.tv_product_amount,item.getBillMoney());
                holder.setText(R.id.tv_receiavle_amount,item.getBillMoney());
            }
        };
        mListView.setAdapter(commonAdapter);
        getInHospitalCostDetail();
    }

    public void getInHospitalCostDetail() {
        CommonUtil.showLoadProgress(this);
        HttpClient.getHttpApi().getInHospitalCostDetail(BaseApplication.getLoginEntity().getTenantId(), "85101020190000000014",1,10,BaseApplication.getLoginEntity().getToken())
                .enqueue(new Callback<InHospitalDetailResp>() {
                    @Override
                    public void onResponse(Call<InHospitalDetailResp> call, Response<InHospitalDetailResp> resp) {
                CommonUtil.dismissLoadProgress();
                        mDatas.addAll(resp.body().getDataList());
                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<InHospitalDetailResp> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
                    }
                });
    }

}
