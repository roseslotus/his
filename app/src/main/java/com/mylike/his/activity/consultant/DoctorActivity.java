package com.mylike.his.activity.consultant;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.DepartmentEntity;
import com.mylike.his.entity.DoctorEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.view.ClearEditText;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/6/19.
 * 医生查询
 */
public class DoctorActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.filtrate_btn)
    ImageView filtrateBtn;
    @Bind(R.id.search_edit)
    ClearEditText searchEdit;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.indexBar)
    IndexBar indexBar;
    @Bind(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @Bind(R.id.search_btn)
    Button searchBtn;
    @Bind(R.id.flowlayout1)
    TagFlowLayout flowlayout1;
    @Bind(R.id.flowlayout2)
    TagFlowLayout flowlayout2;
    @Bind(R.id.reset_btn)
    Button resetBtn;
    @Bind(R.id.confirm_btn)
    Button confirmBtn;
    @Bind(R.id.filtrate_menu)
    LinearLayout filtrateMenu;
    @Bind(R.id.DrawerLayout)
    android.support.v4.widget.DrawerLayout DrawerLayout;

    //通讯录
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    //筛选
    private TagAdapter mAdapter1;//科室标签适配器
    private TagAdapter mAdapter2;//状态标签适配器
    private List<DepartmentEntity> departmentEntitieList = new ArrayList<>();//科室标签数据
    private List<Map<String, String>> stateList = new ArrayList<>();//状态标签数据

    private CommonAdapter commonAdapter;//医生列表适配器
    private List<DoctorEntity> doctorEntityList = new ArrayList<>();//医生列表数据

    private String departmentId = "";//科室id（多个逗号隔开）
    private String stateValue = "";//状态值（多个逗号隔开）
    Map<String, Set<Integer>> selectedMap = new HashMap<String, Set<Integer>>();//存储筛选选中数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    //初始化控件
    private void initView() {
        rv.setLayoutManager(mManager = new LinearLayoutManager(this));
        mDecoration = new SuspensionDecoration(this, doctorEntityList);
        mDecoration.setColorTitleBg(Color.parseColor("#00000000"));
        mDecoration.setColorTitleFont(Color.parseColor("#00B6B9"));
        rv.addItemDecoration(mDecoration);

        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager


        //医生列表适配器
        commonAdapter = new CommonAdapter<DoctorEntity>(DoctorActivity.this, R.layout.item_doctor_list, doctorEntityList) {
            @Override
            protected void convert(ViewHolder holder, final DoctorEntity doctorEntity, int position) {
                holder.setText(R.id.name_text, doctorEntity.getEmpname());//医生名称
                holder.setText(R.id.dept_text, doctorEntity.getDeptname());//科室名称
                holder.setText(R.id.state_text, doctorEntity.getOperstatename());//状态
                if ("空闲".equals(doctorEntity.getOperstatename())) //状态颜色判断
                    holder.setTextColor(R.id.state_text, getResources().getColor(R.color.green_40));
                else
                    holder.setTextColor(R.id.state_text, getResources().getColor(R.color.orange_70));

                //医生详情
                holder.setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(DoctorDetailsActivity.class, "emp_id", doctorEntity.getEmpid());
                    }
                });
            }
        };
        rv.setAdapter(commonAdapter);

        //科室筛选标签
        mAdapter1 = new TagAdapter(departmentEntitieList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(DoctorActivity.this).inflate(R.layout.item_text_label, null);
                textView.setTextSize(12);
                textView.setWidth((filtrateMenu.getWidth() / 2) - 30);
                textView.setPadding(0, 30, 0, 30);
                textView.setGravity(Gravity.CENTER);

                textView.setText(departmentEntitieList.get(position).getDeptname());
                return textView;
            }
        };
        flowlayout1.setAdapter(mAdapter1);
        flowlayout1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                departmentId = "";
                selectedMap.put("FlowLayout1", selectPosSet);
                for (int i : selectPosSet) {
                    if (!TextUtils.isEmpty(departmentId))
                        departmentId += ",";
                    departmentId += departmentEntitieList.get(i).getDeptid();
                }
            }
        });

        //医生状态筛选标签
        mAdapter2 = new TagAdapter(stateList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(DoctorActivity.this).inflate(R.layout.item_text_label, null);
                textView.setTextSize(12);
                textView.setWidth((filtrateMenu.getWidth() / 2) - 30);
                textView.setPadding(0, 30, 0, 30);
                textView.setGravity(Gravity.CENTER);

                textView.setText(stateList.get(position).get("stateName"));
                return textView;
            }
        };
        flowlayout2.setAdapter(mAdapter2);
        flowlayout2.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                stateValue = "";
                selectedMap.put("FlowLayout2", selectPosSet);
                for (int i : selectPosSet) {
                    if (!TextUtils.isEmpty(stateValue))
                        stateValue += ",";
                    stateValue += stateList.get(i).get("stateName");
                }
            }
        });
    }

    //初始化数据
    private void initData() {
        //获取科室列表
        HttpClient.getHttpApi().getDepartmentList().enqueue(new BaseBack<List<DepartmentEntity>>() {
            @Override
            protected void onSuccess(List<DepartmentEntity> departmentEntities) {
                departmentEntitieList.addAll(departmentEntities);
                mAdapter1.notifyDataChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });

        //获取医生状态
        HttpClient.getHttpApi().getDoctorStatus().enqueue(new BaseBack<List<Map<String, String>>>() {

            @Override
            protected void onSuccess(List<Map<String, String>> maps) {
                stateList.addAll(maps);
                mAdapter2.notifyDataChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });

        getDoctorList();
    }

    //获取医生列表
    private void getDoctorList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("searchText", searchEdit.getText().toString());//搜索内容
        map.put("deptid", departmentId);//医生id
        map.put("operstatename", stateValue);//状态

        HttpClient.getHttpApi().getDoctorList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<DoctorEntity>>() {
            @Override
            protected void onSuccess(final List<DoctorEntity> doctorEntities) {
                doctorEntityList.clear();
                doctorEntityList.addAll(doctorEntities);
                commonAdapter.notifyDataSetChanged();
                indexBar.setmSourceDatas(doctorEntityList).invalidate();
                mDecoration.setmDatas(doctorEntityList);
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    @OnClick({R.id.return_btn, R.id.search_btn, R.id.filtrate_btn, R.id.reset_btn, R.id.confirm_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn://搜索
                getDoctorList();
                break;

            case R.id.filtrate_btn://筛选
                DrawerLayout.openDrawer(filtrateMenu);
                break;

            case R.id.reset_btn://筛选重置
                departmentId = "";
                stateValue = "";
                mAdapter1.setSelectedList(new HashSet<Integer>());
                mAdapter2.setSelectedList(new HashSet<Integer>());
                break;

            case R.id.confirm_btn://筛选确认
                DrawerLayout.closeDrawer(filtrateMenu);
                getDoctorList();
                break;

            case R.id.return_btn://返回
                finish();
                break;
        }
    }

    //物理返回按钮
    @Override
    public void onBackPressed() {
        if (DrawerLayout.isDrawerOpen(filtrateMenu))
            DrawerLayout.closeDrawer(filtrateMenu);
        else
            super.onBackPressed();
    }
}
