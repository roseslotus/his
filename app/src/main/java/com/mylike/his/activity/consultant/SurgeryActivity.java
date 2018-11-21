package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.DoctorDetailsEntity;
import com.mylike.his.entity.DoctorInfoEntity;
import com.mylike.his.entity.ReceptionTypeEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/7/23.
 * 手术查询
 */
public class SurgeryActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.forwards_day)
    Button forwardsDay;
    @BindView(R.id.time_text)
    TextView timeText;
    @BindView(R.id.later_day)
    Button laterDay;
    @BindView(R.id.surgery_list)
    ListView surgeryList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tag_ll)
    LinearLayout tagLl;
    @BindView(R.id.filtrate_list)
    ListView filtrateList;
    @BindView(R.id.reset_btn)
    Button resetBtn;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.filtrate_menu)
    LinearLayout filtrateMenu;
    @BindView(R.id.DrawerLayout)
    android.support.v4.widget.DrawerLayout DrawerLayout;
    @BindView(R.id.filtrate_btn)
    ImageView filtrateBtn;

    //手术列表数据
    private int sumPage = 1;//总也数
    private int pageSize = 10;//每页数据
    private int pageNumber = 1;//页码
    private CommonAdapter commonAdapter;
    private List<DoctorInfoEntity> listAll = new ArrayList<>();

    //筛选数据
    private TagAdapter tagAdapter;
    private CommonAdapter commonAdapter1;
    private List<ReceptionTypeEntity> receptionTypeEntitieList = new ArrayList<>();

    //日期选择器
    private TimePickerView TimePV1;

    //标签存储数据
    Map<String, String> selectedValue = new HashMap<>();
    Map<Integer, Set<Integer>> selectedMap = new HashMap<Integer, Set<Integer>>();

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
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        timeText.setText(formatter.format(curDate));

        //初始化适配器
        initAdapter1();
        initAdapter2();

        //初始化时间选择器
        initTimeView();
    }

    private void initData() {
        getDoctorDetauksData();
        getDoctorDetauksType();
    }

    //手术列表适配器
    private void initAdapter1() {
        commonAdapter = new CommonAdapter<DoctorInfoEntity>(this, R.layout.item_surgery_list, listAll) {
            @Override
            protected void convert(ViewHolder viewHolder, DoctorInfoEntity item, int position) {
                viewHolder.setText(R.id.department_naem, item.getDeptname().replace("美容中心",""));//科室
                viewHolder.setText(R.id.doctor_naem, item.getEmpname());//医生姓名
                viewHolder.setText(R.id.oper_name, item.getOpername());//手术名称
                if (item.getOperstatename().isEmpty())
                    viewHolder.setVisible(R.id.oper_state_name, false);
                else
                    viewHolder.setText(R.id.oper_state_name, "（" + item.getOperstatename() + "）");//手术状态
                viewHolder.setText(R.id.time_text, item.getStartdate().substring(11, 16) + "-" + item.getEnddate().substring(11, 16));//时间段
                viewHolder.setText(R.id.client_name, "顾客：" + item.getPatientname() + "(" + item.getPhoneNumber() + ")");//客户姓名+电话
                viewHolder.setText(R.id.type_text, item.getLx());//类型
                viewHolder.setText(R.id.sfqk_text, item.getSfqk());//是否全款
            }
        };
        surgeryList.setAdapter(commonAdapter);
    }

    //医生筛选适配器
    private void initAdapter2() {
        final float scale = this.getResources().getDisplayMetrics().density;
        commonAdapter1 = new CommonAdapter<ReceptionTypeEntity>(SurgeryActivity.this, R.layout.item_filtrate_product_list, receptionTypeEntitieList) {
            @Override
            protected void convert(final ViewHolder viewHolder, final ReceptionTypeEntity item, int position) {
                viewHolder.setText(R.id.cover_name, item.getName());
                TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
                tagAdapter = new TagAdapter(item.getList()) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) LayoutInflater.from(SurgeryActivity.this).inflate(R.layout.item_text_label, null);
                        textView.setTextSize(12);
                        textView.setWidth((filtrateMenu.getWidth() / 3) - (int) (10 * scale + 0.5f));
                        textView.setPadding(0, 30, 0, 30);
                        textView.setGravity(Gravity.CENTER);

                        textView.setText(item.getList().get(position).getValue());
                        return textView;
                    }
                };
                tagFlowLayout.setAdapter(tagAdapter);

                tagAdapter.setSelectedList(selectedMap.get(viewHolder.getItemPosition()));
                tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        selectedMap.put(viewHolder.getItemPosition(), selectPosSet);
                        String str = "";
                        for (int i : selectPosSet) {
                            if (str.isEmpty())
                                str += item.getList().get(i).getId();
                            else
                                str += "," + item.getList().get(i).getId();
                        }
                        selectedValue.put(item.getId(), str);
                    }
                });
            }
        };
        filtrateList.setAdapter(commonAdapter1);
    }

    //日期
    private void initTimeView() {
        //时间选择器
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        TimePV1 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                timeText.setText(formatter.format(date));
                getDoctorDetauksData();
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//隐藏秒
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(endDate)
                .build();
    }

    //获取医生列表数据
    private void getDoctorDetauksData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("time", timeText.getText().toString());//时间
        map.put("pageNumber", pageNumber + "");
        map.put("pageSize", pageSize + "");
        map.putAll(selectedValue);

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

    //获取医生筛选数据
    private void getDoctorDetauksType() {
        HttpClient.getHttpApi().getDoctorDetailScreen().enqueue(new BaseBack<List<ReceptionTypeEntity>>() {
            @Override
            protected void onSuccess(List<ReceptionTypeEntity> receptionTypeEntities) {
                receptionTypeEntitieList.addAll(receptionTypeEntities);
                commonAdapter1.notifyDataSetChanged();
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
        getDoctorDetauksData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (sumPage == 1) {
            refreshLayout.finishLoadMore();
        } else {
            pageNumber = pageNumber + 1;
            getDoctorDetauksData();
        }
    }

    @OnClick({R.id.return_btn, R.id.later_day, R.id.forwards_day, R.id.time_text, R.id.filtrate_btn, R.id.reset_btn, R.id.confirm_btn})
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
                getDoctorDetauksData();
                break;
            case R.id.later_day://后一天
                calendar.setTime(curDate);
                calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
                curDate = calendar.getTime();   //这个时间就是日期往后推一天的结果
                timeText.setText(formatter.format(curDate));
                listAll.clear();
                getDoctorDetauksData();
                break;
            case R.id.time_text://日期
                TimePV1.show();
                break;
            case R.id.filtrate_btn://筛选
                DrawerLayout.openDrawer(filtrateMenu);
                break;
            case R.id.reset_btn://筛选重置
                selectedMap.clear();
                selectedValue.clear();
                commonAdapter1.notifyDataSetChanged();
                break;
            case R.id.confirm_btn://筛选确认
                DrawerLayout.closeDrawer(filtrateMenu);
                pageNumber = 1;
                listAll.clear();
                refreshLayout.setNoMoreData(false);
                getDoctorDetauksData();
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
