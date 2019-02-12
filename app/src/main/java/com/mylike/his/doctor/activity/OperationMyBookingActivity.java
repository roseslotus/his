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
import com.mylike.his.presener.OperationMyBookPresenter;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.OperationMyBookingItemBean;
import com.mylike.his.entity.OperationMyBookingListResp;
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
 * 手术 我的预约
 * Created by thl on 2018/12/31.
 */

public class OperationMyBookingActivity extends BaseActivity {

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
    OperationMyBookPresenter operationMyBookPresenter;


    private CommonAdapter<OperationMyBookingItemBean> commonAdapter;
    private List<OperationMyBookingItemBean> mDatas = new ArrayList<>();
    private String mName;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", -1);
        setContentView(R.layout.activity_wode_yuyue);
        ButterKnife.bind(this);
        operationMyBookPresenter= new OperationMyBookPresenter(this);

        commonAdapter = new CommonAdapter<OperationMyBookingItemBean>(this, R.layout.item_operation_my_booking_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, OperationMyBookingItemBean item, int position) {
                CustomerMenZhenBean customer= item.getCustomer();
                CustomerUtil.setCustomerInfo(holder,customer);
                holder.setText(R.id.tv_project_name,item.getProductsName());
                holder.setText(R.id.tv_doctor_name,item.getDoctor());
                TextView tvBookTime = holder.getView(R.id.tv_booking_time);
                tvBookTime.setVisibility(View.VISIBLE);
                tvBookTime.setText(item.getAppTime());
                holder.setText(R.id.tv_doctor_name,item.getDoctor());
                holder.setText(R.id.tv_book_people_name,item.getCreater());
                TextView tvStatus = holder.getView(R.id.tv_book_status);
                BusnessUtil.setOperationBookStatus(tvStatus,item.getStatus());
            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OperationMyBookingItemBean data = mDatas.get(i);
                Intent intent = new Intent(OperationMyBookingActivity.this,OperationMyBookDetailActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });

//        if (type==1){
            mTvLeftTitle.setVisibility(View.VISIBLE);
            mTvCenterTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setVisibility(View.VISIBLE);
//        }else if (type==2){
//            mTvLeftTitle.setVisibility(View.GONE);
//            mTvCenterTitle.setVisibility(View.VISIBLE);
//            mTvRightTitle.setVisibility(View.GONE);
//            mTvCenterTitle.setText("已接诊：10");
//        }


        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                operationMyBookPresenter.refresh(new ResponseListener<OperationMyBookingListResp>() {
                    @Override
                    public void onResponse(OperationMyBookingListResp myBookingListResp) {
                        stopOver(mRefreshLayout);
                        mDatas.clear();
                        mDatas.addAll(myBookingListResp.getDataList());
                        bindData(myBookingListResp.getList());
                        commonAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver(mRefreshLayout);
                    }
                });
             }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                operationMyBookPresenter.loadMore(new ResponseListener<OperationMyBookingListResp>() {
                    @Override
                    public void onResponse(OperationMyBookingListResp myBookingListResp) {
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

    private void bindData(List<OperationMyBookingListResp.ListBean> list) {
        if (list != null) {
            for (OperationMyBookingListResp.ListBean listBean : list) {
                if (listBean.getName().equals("总预约")){
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
                if (type==1){
                    choiceList.add("未缴费");
                    choiceList.add("全款");
                    choiceList.add("预约金");
                }else {
                    choiceList.add("接诊中");
                    choiceList.add("已诊");
                    choiceList.add("已取消");
                }

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
