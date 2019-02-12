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
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.OperationMyArrangementListBean;
import com.mylike.his.entity.OperationMyArrangementListResp;
import com.mylike.his.entity.OperationMyBookingItemBean;
import com.mylike.his.entity.OperationMyBookingListResp;
import com.mylike.his.presener.OperationMyArrangementPresenter;
import com.mylike.his.utils.BusnessUtil;
import com.mylike.his.utils.Constacts;
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
 * 手术 我的排台
 * Created by thl on 2018/12/31.
 */

public class OperationMyArrangementActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_shuaixuan_panel)
    LinearLayout mLlShuaixuanPanel;
    @BindView(R.id.tv_total_num)
    TextView mTvTotalNum;
    @BindView(R.id.tv_notstart_num)
    TextView mTvNotstartNum;
    @BindView(R.id.tv_operating_num)
    TextView mTvOperatingNum;
    @BindView(R.id.tv_finished_num)
    TextView mTvFinishedNum;


    private CommonAdapter<OperationMyArrangementListBean> commonAdapter;
    private List<OperationMyArrangementListBean> mDatas = new ArrayList<>();
    private String mName;
    private int type;

    OperationMyArrangementPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", -1);
        setContentView(R.layout.activity_operation_my_arrangement);
        ButterKnife.bind(this);
        mPresenter = new OperationMyArrangementPresenter(this);

        commonAdapter = new CommonAdapter<OperationMyArrangementListBean>(this, R.layout.item_operation_my_arrangement_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, OperationMyArrangementListBean item, int position) {
                CustomerMenZhenBean customer= item.getCustomer();
                CustomerUtil.setCustomerInfo(holder,customer);
                TextView tvBookTime = holder.getView(R.id.tv_booking_time);
                tvBookTime.setVisibility(View.VISIBLE);
                tvBookTime.setText(item.getAppTime());
                TextView tvStatus = holder.getView(R.id.tv_book_status);
                BusnessUtil.setArrangementStatus(tvStatus,item.getStatus());

                holder.setText(R.id.tv_operation_room,"手术室:"+item.getOperaRoom());
                holder.setText(R.id.tv_stage_number,"台次:"+item.getStage());
                holder.setText(R.id.tv_project_name,item.getProductsName());
                holder.setText(R.id.tv_doctor_name,item.getDoctor());
                holder.setText(R.id.tv_anesthesia_method,item.getAnesthesia());
                holder.setText(R.id.tv_anesthesia_doctor,item.getAnesthesiaDoc());
                holder.setText(R.id.tv_operating_nurse,item.getSurgicalNurse());
                holder.setText(R.id.tv_pratrol_nurse,item.getCircuitNurse());
                holder.setText(R.id.tv_operating_start_time,item.getStartTime());
                holder.setText(R.id.tv_operating_end_time,item.getEndTime());
            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OperationMyArrangementListBean data = mDatas.get(i);
                Intent intent = new Intent(OperationMyArrangementActivity.this,ShoushuWodePaiTaiDetailActivity.class);
                intent.putExtra(Constacts.CONTENT_DATA,data);
                startActivity(intent);
            }
        });

        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh(new ResponseListener<OperationMyArrangementListResp>() {
                    @Override
                    public void onResponse(OperationMyArrangementListResp resp) {
                        stopOver(mRefreshLayout);
                        mDatas.clear();
                        mDatas.addAll(resp.getDataList());
                        bindData(resp.getList());
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
                mPresenter.loadMore(new ResponseListener<OperationMyArrangementListResp>() {
                    @Override
                    public void onResponse(OperationMyArrangementListResp resp) {
                        mDatas.addAll(resp.getDataList());
                        commonAdapter.notifyDataSetChanged();
                        bindData(resp.getList());
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

    private void bindData(List<OperationMyArrangementListResp.ListBean> list) {
        if (list != null) {
           List<TextView> textViews =new ArrayList<>();
            textViews.add(mTvTotalNum);
            textViews.add(mTvNotstartNum);
            textViews.add(mTvOperatingNum);
            textViews.add(mTvFinishedNum);
            for (int i = 0; i < list.size(); i++) {
                TextView textView = textViews.get(i);
                OperationMyArrangementListResp.ListBean data = list.get(i);
                textView.setText(data.getName()+":"+data.getValue());
            }
        }
    }


    @OnClick({R.id.ll_shuaixuan_panel, R.id.tv_total_num, R.id.tv_notstart_num, R.id.tv_operating_num, R.id.tv_finished_num})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_shuaixuan_panel:
                List<String> choiceList = new ArrayList<>();
                if (type == 1) {
                    choiceList.add("局部麻醉");
                    choiceList.add("全身麻醉");
                } else {
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
            case R.id.tv_total_num:
                break;
            case R.id.tv_notstart_num:
                break;
            case R.id.tv_operating_num:
                break;
            case R.id.tv_finished_num:
                break;
        }
    }
}
