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
import com.mylike.his.presener.WoDeYuYuePresenter;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.MyBookingItemBean;
import com.mylike.his.entity.MyBookingListResp;
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

public class WoDeYuyueActivity extends BaseActivity {

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

    private WoDeYuYuePresenter woDeYuYuePresenter;
    private CommonAdapter<MyBookingItemBean> commonAdapter;
    private List<MyBookingItemBean> mDatas = new ArrayList<>();
    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_yuyue);
        ButterKnife.bind(this);
        woDeYuYuePresenter =new WoDeYuYuePresenter(this);

        commonAdapter = new CommonAdapter<MyBookingItemBean>(this, R.layout.item_wode_yuyue_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, MyBookingItemBean item, int position) {
                CustomerMenZhenBean customer= item.getCustomer();

                CustomerUtil.setCustomerInfo(holder,customer);

                TextView tvStatus = holder.getView(R.id.tv_book_status);
                BusnessUtil.setMenZhenBookStatus(tvStatus,item.getStatus());

                holder.setText(R.id.tv_project_name,item.getProductsName());
                holder.setText(R.id.tv_booking_time,item.getDate());
                holder.setText(R.id.tv_zhengdan_type,"是".equals(item.getType())?"[初诊]":"[复诊]");

                holder.setText(R.id.tv_doctor_name,item.getDoctor());

            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyBookingItemBean data = mDatas.get(i);
                Intent intent = new Intent(WoDeYuyueActivity.this,BookDetailActivity.class);
                intent.putExtra("MyBookingItemBean",data);
                startActivity(intent);
            }
        });

            mTvLeftTitle.setVisibility(View.VISIBLE);
            mTvCenterTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setVisibility(View.VISIBLE);

        setListeners();
        mRefreshLayout.autoRefresh();
    }

    private void setListeners() {

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                woDeYuYuePresenter.loadMore(new ResponseListener<MyBookingListResp>() {
                    @Override
                    public void onResponse(MyBookingListResp myBookingListResp) {
                        mDatas.addAll(myBookingListResp.getDataList());
                        commonAdapter.notifyDataSetChanged();
                        bindData(myBookingListResp.getList());
                        stopOver(mRefreshLayout);
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver(mRefreshLayout);
                    }
                });
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                woDeYuYuePresenter.refresh(new ResponseListener<MyBookingListResp>() {
                    @Override
                    public void onResponse(MyBookingListResp myBookingListResp) {
                        mDatas.clear();
                        mDatas.addAll(myBookingListResp.getDataList());
                        commonAdapter.notifyDataSetChanged();
                        bindData(myBookingListResp.getList());
                        stopOver(mRefreshLayout);
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver(mRefreshLayout);
                    }
                });
            }
        });
    }

    private void bindData(List<MyBookingListResp.ListBean> list) {
        if (list != null) {
            for (MyBookingListResp.ListBean listBean : list) {
                if (listBean.getName().equals("总数")){
                   mTvLeftTitle.setText("总预约:"+listBean.getValue());
                }else if(listBean.getName().equals("已到院")){
                    mTvCenterTitle.setText("已到院:"+listBean.getValue());
                }else if(listBean.getName().equals("已结束")){
                    mTvRightTitle.setText("已结束:"+listBean.getValue());
                }
            }
        }
    }


    @OnClick({R.id.ll_shuaixuan_panel})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_shuaixuan_panel:
                List<String> choiceList = new ArrayList<>();

                    choiceList.add("已接诊");
                    choiceList.add("已到院");
                    choiceList.add("未到院");


                new ChoiceZhuyuanShuaixuanPopupMenu().setDatas(choiceList).show(getFragmentManager(), "ChoiceZhuyuanShuaixuanPopupMenu");

                break;
            case R.id.tv_left_title:
                break;
            case R.id.tv_center_title:
                break;
            case R.id.tv_right_title:
                break;
        }
    }
}
