package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.MenZhenChuFangJiLuDetailResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.Constacts;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/1.
 */

public class ChuFangDetailActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.tv_zhengduan_bingqing)
    TextView mTvZhengduanBingqing;
    @BindView(R.id.tv_kaili_yisheng)
    TextView mTvKailiYisheng;
    @BindView(R.id.tv_kaili_time)
    TextView mTvKailiTime;
    @BindView(R.id.tv_cost_money)
    TextView mTvCostMoney;
    private String presId;


    private CommonAdapter<MenZhenChuFangJiLuDetailResp.DataBean> commonAdapter;
    private List<MenZhenChuFangJiLuDetailResp.DataBean> mDatas = new ArrayList<>();
    private MenZhenChuFangJiLuDetailResp menZhenChuFangJiLuDetailResp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chufang_detail);
        ButterKnife.bind(this);
        presId = getIntent().getStringExtra(Constacts.CONTENT_DATA);

        commonAdapter = new CommonAdapter<MenZhenChuFangJiLuDetailResp.DataBean>(this, R.layout.item_chufang_detail, mDatas) {
            @Override
            protected void convert(ViewHolder holder, MenZhenChuFangJiLuDetailResp.DataBean item, int position) {
                holder.setText(R.id.tv_project_name,item.getDrugName());
                holder.setText(R.id.tv_yao_leixing,"["+item.getType()+"]");
                holder.setText(R.id.tv_yongyao_status,item.getStatus());
                holder.setText(R.id.tv_yongyao_guige,item.getSpecification());
                holder.setText(R.id.tv_yongyao_yongfa,item.getUsage());
                holder.setText(R.id.tv_yongyao_shuliang,item.getNum());
                holder.setText(R.id.tv_yongyao_tianshu,item.getDays());
                holder.setText(R.id.tv_yongyao_danwei,item.getUnit());
                holder.setText(R.id.tv_yongyao_pinglv,item.getFrequency());
                holder.setText(R.id.tv_yongyao_yongliang,item.getEachTime());

            }
        };
        mListView.setAdapter(commonAdapter);
        getChuFangJiLuDetail();
    }

    public void getChuFangJiLuDetail() {
        CommonUtil.showLoadProgress(this);
        HttpClient.getHttpApi().getChuFangJiLuDetail(BaseApplication.getLoginEntity().getTenantId(), presId,BaseApplication.getLoginEntity().getToken())
                .enqueue(new Callback<MenZhenChuFangJiLuDetailResp>() {
                    @Override
                    public void onResponse(Call<MenZhenChuFangJiLuDetailResp> call, Response<MenZhenChuFangJiLuDetailResp> response) {
                        CommonUtil.dismissLoadProgress();
                        menZhenChuFangJiLuDetailResp = response.body();
                        bindData();

                    }

                    @Override
                    public void onFailure(Call<MenZhenChuFangJiLuDetailResp> call, Throwable t) {
                        CommonUtil.dismissLoadProgress();
                    }
                });
    }

    private void bindData() {
        if (menZhenChuFangJiLuDetailResp != null) {
            mTvZhengduanBingqing.setText(menZhenChuFangJiLuDetailResp.getDiagnosis());
            mTvKailiYisheng.setText(menZhenChuFangJiLuDetailResp.getDoctor());
            mTvKailiTime.setText(menZhenChuFangJiLuDetailResp.getPresTime());
            mDatas.addAll(menZhenChuFangJiLuDetailResp.getList());
            commonAdapter.notifyDataSetChanged();
            BigDecimal totalMoney =BigDecimal.ZERO;
            for (MenZhenChuFangJiLuDetailResp.DataBean bean : mDatas) {
                if (bean.getAmount()!=null){
                    totalMoney= totalMoney.add(bean.getAmount());
                }
            }
            DecimalFormat df =new DecimalFormat("########0.00");
            mTvCostMoney.setText(df.format(totalMoney));

        }

    }


    @OnClick({R.id.return_btn, R.id.tv_zhengduan_bingqing, R.id.tv_kaili_yisheng, R.id.tv_kaili_time})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.tv_zhengduan_bingqing:
                break;
            case R.id.tv_kaili_yisheng:
                break;
            case R.id.tv_kaili_time:
                break;
        }
    }
}
