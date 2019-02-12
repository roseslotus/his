package com.mylike.his.doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.presener.WoDeDaiZhenPresenter;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.DaiZhenResp;
import com.mylike.his.entity.WoDeDaiZhenItemBean;
import com.mylike.his.utils.BusnessUtil;
import com.mylike.his.utils.CustomerUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 门诊 我的预约
 * Created by thl on 2018/12/31.
 */

public class WoDeJieZhenActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_shuaixuan_panel)
    LinearLayout mLlShuaixuanPanel;
    @BindView(R.id.tv_left_title)
    TextView mTvLeftTitle;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.tv_right_title)
    TextView mTvRightTitle;

    private WoDeDaiZhenPresenter woDeDaiZhenPresenter;
    private CommonAdapter<WoDeDaiZhenItemBean> commonAdapter;
    private List<WoDeDaiZhenItemBean> mDatas = new ArrayList<>();
    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_yuyue);
        ButterKnife.bind(this);
        woDeDaiZhenPresenter =new WoDeDaiZhenPresenter(this);
        woDeDaiZhenPresenter.setStatus(2);
        woDeDaiZhenPresenter.setDepartId("85101050");
        woDeDaiZhenPresenter.setUserId("85100548");
        commonAdapter = new CommonAdapter<WoDeDaiZhenItemBean>(this, R.layout.item_wode_yuyue_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, WoDeDaiZhenItemBean item, int position) {
                CustomerMenZhenBean customer= item.getCustomer();
                CustomerUtil.setCustomerInfo(holder,customer);

                TextView tvStatus = holder.getView(R.id.tv_book_status);
                BusnessUtil.setMenZhenJieZhenStatus(tvStatus,item.getStatus());

                    holder.setText(R.id.tv_project_name,item.getProductsName());
                    holder.setText(R.id.tv_book_status,item.getStatus());
                    holder.setText(R.id.tv_booking_time,item.getTriageTime());
                    holder.setText(R.id.tv_zhengdan_type,"是".equals(item.getType())?"[初诊]":"[复诊]");



                    holder.setText(R.id.tv_doctor_name,item.getDoctor());

            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WoDeDaiZhenItemBean data = mDatas.get(i);
                Intent intent = new Intent(WoDeJieZhenActivity.this,MenZhenDetailActivity.class);
                intent.putExtra("WoDeDaiZhenItemBean",data);
                startActivity(intent);
            }
        });

            mTvLeftTitle.setVisibility(View.GONE);
            mTvCenterTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setVisibility(View.GONE);
            mTvCenterTitle.setText("已接诊：0");
        setListeners();
        mRefreshLayout.autoRefresh();
    }

    private void setListeners() {

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                woDeDaiZhenPresenter.loadMore(new ResponseListener<DaiZhenResp>() {
                    @Override
                    public void onResponse(DaiZhenResp daiZhenResp) {
                        mDatas.addAll(daiZhenResp.getDataList());
                        commonAdapter.notifyDataSetChanged();
                        bindData(daiZhenResp.getList());
                        stopOver();
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver();
                    }
                });
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                woDeDaiZhenPresenter.refresh(new ResponseListener<DaiZhenResp>() {
                    @Override
                    public void onResponse(DaiZhenResp daiZhenResp) {
                        mDatas.clear();
                        mDatas.addAll(daiZhenResp.getDataList());
                        commonAdapter.notifyDataSetChanged();
                        bindData(daiZhenResp.getList());
                        stopOver();
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver();
                    }
                });
            }
        });
    }

    private void bindData(List<DaiZhenResp.ListBean> list) {
        if (list != null) {
            for (DaiZhenResp.ListBean listBean : list) {
                if (listBean.getName().equals("总计数")){
                    mTvCenterTitle.setText("已接诊:"+listBean.getValue());
                }
            }
        }
    }

    private void stopOver(){
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }


    @OnClick({R.id.ll_shuaixuan_panel})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_shuaixuan_panel:
                List<String> choiceList = new ArrayList<>();
                    choiceList.add("接诊中");
                    choiceList.add("已诊");
                    choiceList.add("已取消");

                new ChoiceZhuyuanShuaixuanPopupMenu().setDatas(choiceList).show(getFragmentManager(), "ChoiceZhuyuanShuaixuanPopupMenu");

                break;
        }
    }
}
