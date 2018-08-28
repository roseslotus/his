package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.HospitalAppointmentEntity;
import com.mylike.his.entity.HospitalAppointmentInfoEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/6/4.
 * 到院预约
 */
public class HospitalAppointmentActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.hospital_appointment_list)
    ListView hospitalAppointmentList;
    @Bind(R.id.search_edit)
    ClearEditText searchEdit;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.search_btn)
    Button searchBtn;

    private int pageSize = 10;//每页数据
    private int pageNumber = 1;//页码
    private List<HospitalAppointmentInfoEntity> listAll = new ArrayList<>();
    private CommonAdapter commonAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_money);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        commonAdapter = new CommonAdapter<HospitalAppointmentInfoEntity>(this, R.layout.item_hospital_appointment_list, listAll) {
            @Override
            protected void convert(ViewHolder viewHolder, HospitalAppointmentInfoEntity item, int position) {
                viewHolder.setText(R.id.name_text, item.getNAME());
                viewHolder.setText(R.id.time_text, item.getAPPOINT_TIME().substring(0, 11));
                if (!item.getINTENTION().isEmpty()) {
                    String intention = "";
                    for (String str : item.getINTENTION()) {
                        if (TextUtils.isEmpty(intention)) {
                            intention += str;
                        } else {
                            intention += "\n" + str;
                        }
                    }
                    viewHolder.setText(R.id.opinion_text, intention);
                }
            }
        };
        hospitalAppointmentList.setAdapter(commonAdapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        map.put("condition", searchEdit.getText().toString());

        HttpClient.getHttpApi().getHospitalAppointmentList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<HospitalAppointmentEntity>() {
            @Override
            protected void onSuccess(HospitalAppointmentEntity hospitalAppointmentEntity) {
                if (hospitalAppointmentEntity.getTotalPages() == pageNumber) {
                    refreshLayout.setNoMoreData(true);
                }
                listAll.addAll(hospitalAppointmentEntity.getList());
                commonAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });
    }


    @OnClick({R.id.return_btn, R.id.search_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                pageNumber = 1;
                listAll.clear();
                refreshLayout.setNoMoreData(false);
                initData();
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNumber = 1;
        listAll.clear();
        refreshLayout.setNoMoreData(false);
        initData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNumber = pageNumber + 1;
        initData();
    }


//        hospitalAppointmentList.setAdapter(new CommonAdapter<String>(this, R.layout.item_hospital_appointment_list, DataUtil.getData(2)) {
//            @Override
//            protected void convert(ViewHolder viewHolder, String item, int position) {
//
//                ListView listView = viewHolder.getView(R.id.hospital_appointment_d_list);
//                listView.setAdapter(new CommonAdapter<String>(HospitalAppointmentActivity.this, R.layout.item_hospital_appointment_d_list, DataUtil.getData(5)) {
//                    @Override
//                    protected void convert(ViewHolder viewHolder, String item, int position) {
//                    }
//                });
//
//                setListViewHeightBasedOnChildren(listView);
//            }
//        });
//    //设置列表高度
//    public void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        View listItem = listAdapter.getView(0, null, listView);
//        listItem.measure(0, 0);
//        int listItemHeight = listItem.getMeasuredHeight();
//        int totalHeight = listItemHeight * listAdapter.getCount();
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (1 * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }
}
