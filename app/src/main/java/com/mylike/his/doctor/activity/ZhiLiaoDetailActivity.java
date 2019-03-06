package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.MenZhenTreatDengJiDetailBean;
import com.mylike.his.entity.MenZhenZhiLiaoDengJiBean;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.verticaltablayout.widget.ITabView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/1.
 */

public class ZhiLiaoDetailActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;


    private CommonAdapter<MenZhenTreatDengJiDetailBean> commonAdapter;
    private List<MenZhenTreatDengJiDetailBean> mDatas = new ArrayList<>();
    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhiliao_detail);
        ButterKnife.bind(this);

        commonAdapter = new CommonAdapter<MenZhenTreatDengJiDetailBean>(this, R.layout.item_zhiliao_detail, mDatas) {
            @Override
            protected void convert(ViewHolder holder, MenZhenTreatDengJiDetailBean item, int position) {
                holder.setText(R.id.tv_first_project_name,item.getProductsName());

                LinearLayout llChildPanel = holder.getView(R.id.ll_child_panel);
                List<MenZhenTreatDengJiDetailBean.ItemsBean> items= item.getItems();
                if (items!=null){
                    for (MenZhenTreatDengJiDetailBean.ItemsBean childBean:items){
                        View childview = LayoutInflater.from(ZhiLiaoDetailActivity.this).inflate(R.layout.item_zhiliao_child_detail,null,false);
                        llChildPanel.addView(childview);
                        TextView childProjectName = childview.findViewById(R.id.tv_second_project_name);
                        TextView childZhuzhiYiSheng = childview.findViewById(R.id.tv_zhuzhi_yisheng);
                        TextView childYiZhuli = childview.findViewById(R.id.tv_yi_zhuli);
                        TextView childZhiXingRen = childview.findViewById(R.id.tv_zhixingren);
                        TextView childUseNumber = childview.findViewById(R.id.tv_use_number);
                        TextView childTreatTime= childview.findViewById(R.id.tv_treat_time);
                        childProjectName.setText(childBean.getTreatName());
                        childZhuzhiYiSheng.setText(childBean.getDoctor());
                        childYiZhuli.setText(childBean.getAssistance());
                        childZhiXingRen.setText(childBean.getExeDoc());
                        childUseNumber.setText(childBean.getNum());
                        childTreatTime.setText(childBean.getTreatTime());
                    }
                }
            }
        };
        mListView.setAdapter(commonAdapter);

        getZhiLiaoDengJiDetail(getIntent().getStringExtra("treatId"));
    }

    public void getZhiLiaoDengJiDetail(String treatId) {
        CommonUtil.showLoadProgress(ZhiLiaoDetailActivity.this);
        HttpClient.getHttpApi().getZhiLiaoDengJiDetail(BaseApplication.getLoginEntity().getTenantId(),treatId,BaseApplication.getLoginEntity().getToken())
                .enqueue(new Callback<List<MenZhenTreatDengJiDetailBean>>() {
                    @Override
                    public void onResponse(Call<List<MenZhenTreatDengJiDetailBean>> call, Response<List<MenZhenTreatDengJiDetailBean>> response) {
                CommonUtil.dismissLoadProgress();
                        mDatas.clear();
                        if (response!=null&&response.body()!=null){
                            mDatas.addAll(response.body());
                        }
                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<MenZhenTreatDengJiDetailBean>> call, Throwable t) {
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
