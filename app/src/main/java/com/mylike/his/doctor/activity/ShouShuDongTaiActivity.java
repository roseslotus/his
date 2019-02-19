package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.OperationMyArrangementListBean;
import com.mylike.his.entity.OperationMyArrangementListResp;
import com.mylike.his.presener.OperationMyArrangementPresenter;
import com.mylike.his.utils.BusnessUtil;
import com.mylike.his.utils.CustomerUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sevenheaven.segmentcontrol.SegmentControl;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thl on 2019/1/2.
 */

public class ShouShuDongTaiActivity extends BaseActivity {
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.segment_control)
    SegmentControl mSegmentControl;


    private CommonAdapter<OperationMyArrangementListBean> commonAdapter;
    private List<OperationMyArrangementListBean> mDatas = new ArrayList<>();

    OperationMyArrangementPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        mPresenter = new OperationMyArrangementPresenter(this);
        setContentView(R.layout.activity_shoushu_dongtai);
        ButterKnife.bind(this);


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
                startActivity(ShoushuWodePaiTaiDetailActivity.class);
            }
        });


        mSegmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                int status=1;
                if (index==0){
                    status=1;
                }else if (index==1){
                    status=2;
                }else if (index==2){
                    status=3;
                }
                mPresenter.setStatus(status);
                mRefreshLayout.autoRefresh();
            }
        });
        mPresenter.setStatus(1);
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


    @OnClick(R.id.return_btn)
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
