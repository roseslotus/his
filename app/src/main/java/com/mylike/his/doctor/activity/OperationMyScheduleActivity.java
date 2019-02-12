package com.mylike.his.doctor.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.presener.OperationMySchedulePresenter;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.mylike.his.entity.OperationMySchedulingListResp;
import com.mylike.his.entity.OperationMySchedulingItemBean;
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
 * 手术 我的排期
 * Created by thl on 2018/12/31.
 */

public class OperationMyScheduleActivity extends BaseActivity {

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


    private CommonAdapter<OperationMySchedulingItemBean> commonAdapter;
    private List<OperationMySchedulingItemBean> mDatas = new ArrayList<>();
    private String mName;

    OperationMySchedulePresenter mPresenter;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", -1);
        setContentView(R.layout.activity_operation_my_schedule);
        ButterKnife.bind(this);
        mPresenter = new OperationMySchedulePresenter(this);

        commonAdapter = new CommonAdapter<OperationMySchedulingItemBean>(this, R.layout.item_operation_my_scheduling_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, OperationMySchedulingItemBean item, int position) {
                CustomerUtil.setCustomerInfo(holder,item.getCustomer());
                TextView tvBookTime = holder.getView(R.id.tv_booking_time);
                tvBookTime.setVisibility(View.VISIBLE);
                tvBookTime.setText(item.getAppTime());

//                TextView tvStatus = holder.getView(R.id.tv_book_status);
//                BusnessUtil.setSchedulingStatus(tvStatus,item.getAnesthesia());

                holder.setText(R.id.tv_project_name,item.getProductsName());
                holder.setText(R.id.tv_anesthesia_method,item.getAnesthesia());
                holder.setText(R.id.tv_doctor_name,item.getDoctor());

                TextView tvProsthesisStatus = holder.getView(R.id.tv_prosthesis_status);
                TextView tvSkinTestStatus = holder.getView(R.id.tv_skintest_status);
                TextView tvMedicalexamStatus = holder.getView(R.id.tv_medicalexam_status);

                boolean isProsthesisNotOk = "未到货".equals(item.getProsthesis()==null?"":item.getProsthesis().trim());
                boolean isSkinTestNotOk = "不通过".equals(item.getSkinTest()==null?"":item.getSkinTest().trim());
                boolean isMedicalExamNotOk = "不通过".equals(item.getMedicalExam()==null?"":item.getMedicalExam().trim());

                int okColor = getResources().getColor(R.color.doctor_green_color);
                int notOkColor = Color.parseColor("#FE4E4E");

                tvProsthesisStatus.setText(item.getProsthesis());
                tvProsthesisStatus.setTextColor(isProsthesisNotOk?notOkColor:okColor);

                tvSkinTestStatus.setText(item.getSkinTest());
                tvSkinTestStatus.setTextColor(isSkinTestNotOk?notOkColor:okColor);

                tvMedicalexamStatus.setText(item.getMedicalExam());
                tvMedicalexamStatus.setTextColor(isMedicalExamNotOk?notOkColor:okColor);

                holder.setImageResource(R.id.iv_test_status,!isProsthesisNotOk&&!isSkinTestNotOk&&!isMedicalExamNotOk?R.mipmap.icon_shoushu_wodepaiqi_tongguo:R.mipmap.icon_shoushu_wodepaiqi_weitongguo);



            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OperationMyScheduleActivity.this,OperationMySchedulingDetailActivity.class);
                intent.putExtra("data",mDatas.get(i));
                startActivity(intent);
            }
        });

        if (type==1){
            mTvLeftTitle.setVisibility(View.VISIBLE);
            mTvCenterTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setVisibility(View.VISIBLE);
        }else if (type==2){
            mTvLeftTitle.setVisibility(View.GONE);
            mTvCenterTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setVisibility(View.GONE);
            mTvCenterTitle.setText("已接诊：10");
        }

        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh(new ResponseListener<OperationMySchedulingListResp>() {
                    @Override
                    public void onResponse(OperationMySchedulingListResp myBookingListResp) {
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
                mPresenter.loadMore(new ResponseListener<OperationMySchedulingListResp>() {
                    @Override
                    public void onResponse(OperationMySchedulingListResp myBookingListResp) {
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

    private void bindData(List<OperationMySchedulingListResp.ListBean> list) {
        if (list != null) {
            for (OperationMySchedulingListResp.ListBean listBean : list) {
                if (listBean.getName().equals("排台总数")){
                    mTvLeftTitle.setText("排台总数:"+listBean.getValue());
                }else if(listBean.getName().equals("已排台")){
                    mTvCenterTitle.setText("已排台:"+listBean.getValue());
                }else if(listBean.getName().equals("已完成")){
                    mTvRightTitle.setText("已完成:"+listBean.getValue());
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
                    choiceList.add("局部麻醉");
                    choiceList.add("全身麻醉");
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
