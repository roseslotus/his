package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
 * Created by zhengluping on 2018/7/23.
 * 手术查询
 */
public class SurgeryActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {


    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.forwards_day)
    Button forwardsDay;
    @Bind(R.id.time_text)
    TextView timeText;
    @Bind(R.id.later_day)
    Button laterDay;
    @Bind(R.id.surgery_list)
    ListView surgeryList;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


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
        setContentView(R.layout.activity_surgery);
        ButterKnife.bind(this);
        setListView(surgeryList);
        initView();
        initData();
    }

    private void initView() {

        timeText.setText(formatter.format(curDate));

        commonAdapter = new CommonAdapter<DoctorInfoEntity>(this, R.layout.item_surgery_list, listAll) {
            @Override
            protected void convert(ViewHolder viewHolder, DoctorInfoEntity item, int position) {
                viewHolder.setText(R.id.doctor_naem, item.getEmpname());//医生姓名
                viewHolder.setText(R.id.oper_name, item.getOpername());//手术名称
                viewHolder.setText(R.id.oper_state_name, "（" + item.getOperstatename() + "）");//手术状态
                viewHolder.setText(R.id.time_text, item.getStartdate().substring(11, 16) + "-" + item.getEnddate().substring(11, 16));//时间段
                viewHolder.setText(R.id.client_name, "顾客：" + item.getPatientname() + "(" + item.getPhoneNumber() + ")");//客户姓名+电话
                viewHolder.setText(R.id.type_text, item.getLx());//类型
                viewHolder.setText(R.id.sfqk_text, item.getSfqk());//是否全款
            }
        };
        surgeryList.setAdapter(commonAdapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
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
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
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

    @OnClick({R.id.return_btn, R.id.later_day, R.id.forwards_day})
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
