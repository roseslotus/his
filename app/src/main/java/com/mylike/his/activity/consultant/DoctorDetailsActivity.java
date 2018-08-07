package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.DoctorDetailsEntity;
import com.mylike.his.entity.DoctorInfoEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/6/20.
 * 医生详情
 */
public class DoctorDetailsActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.doctor_d_list)
    ListView doctorDList;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.forwards_day)
    Button forwardsDay;
    @Bind(R.id.time_text)
    TextView timeText;
    @Bind(R.id.later_day)
    Button laterDay;

    private int sumPage = 1;//总也数
    private int pageSize = 10;//每页数据
    private int pageNumber = 1;//页码
    private CommonAdapter commonAdapter;
    private List<DoctorInfoEntity> listAll = new ArrayList<>();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date curDate = new Date(System.currentTimeMillis());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        ButterKnife.bind(this);
        setListView(doctorDList);
        initView();
        initData();
    }

    private void initView() {
        timeText.setText(formatter.format(curDate));

        commonAdapter = new CommonAdapter<DoctorInfoEntity>(DoctorDetailsActivity.this, R.layout.item_doctor_details_list, listAll) {
            @Override
            protected void convert(ViewHolder viewHolder, DoctorInfoEntity item, int position) {
                viewHolder.setText(R.id.oper_name, item.getOpername());//手术名称
                viewHolder.setText(R.id.oper_state_name, "（" + item.getOperstatename() + "）");//手术状态
                viewHolder.setText(R.id.time_text, item.getStartdate().substring(11, 16) + "-" + item.getEnddate().substring(11, 16));
                viewHolder.setText(R.id.client_naem, item.getPatientname());//客户姓名
                viewHolder.setText(R.id.type_text, item.getLx());//类型
                viewHolder.setText(R.id.sfqk_text, item.getSfqk());//是否全款
            }
        };
        doctorDList.setAdapter(commonAdapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    private void initData() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("empid", getIntent().getStringExtra("emp_id"));
        map.put("time", timeText.getText().toString());//时间
        map.put("pageNumber", pageNumber + "");
        map.put("pageSize", pageSize + "");

        HttpClient.getHttpApi().getDoctorDetauksList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<DoctorDetailsEntity>() {
            @Override
            protected void onSuccess(DoctorDetailsEntity doctorDetailsEntity) {
                sumPage = Integer.parseInt(doctorDetailsEntity.getPages());
                if (doctorDetailsEntity.getList().isEmpty()) {
                    setListNotData(true, "暂无数据");
                } else if (pageSize >= sumPage) {
                    refreshLayout.setNoMoreData(true);
                    setListNotData(true, null);
                }
                listAll.addAll(doctorDetailsEntity.getList());
                commonAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNumber = 1;
        listAll.clear();
        setListNotData(false, null);
        initData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (sumPage == 1) {
            refreshLayout.finishLoadMore();
        } else {
            pageNumber = pageNumber + 1;
            initData();
        }
    }

    @OnClick({R.id.return_btn, R.id.forwards_day, R.id.later_day})
    @Override
    public void onClick(View v) {
        Calendar calendar = new GregorianCalendar();
        switch (v.getId()) {
            case R.id.forwards_day://前一天
                calendar.setTime(curDate);
                calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
                curDate = calendar.getTime();   //这个时间就是日期往后推一天的结果
                timeText.setText(formatter.format(curDate));
                listAll.clear();
                initData();
                break;
            case R.id.later_day://后一天
                calendar.setTime(curDate);
                calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
                curDate = calendar.getTime();   //这个时间就是日期往后推一天的结果
                timeText.setText(formatter.format(curDate));
                listAll.clear();
                initData();
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
