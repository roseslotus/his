package com.mylike.his.activity.consultant;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.DepartmentEntity;
import com.mylike.his.entity.DoctorEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.DialogUtil;
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
    @Bind(R.id.screen_btn)
    ImageView screenBtn;
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

    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    private TagAdapter mAdapter1;
    private TagAdapter mAdapter2;
    private CommonAdapter commonAdapter;
    private List<DoctorEntity> doctorEntityList = new ArrayList<>();//医生列表
    private List<DepartmentEntity> departmentEntitieList = new ArrayList<>();//科室数据
    private List<Map<String, String>> stateList = new ArrayList<>();

    private String departmentId = "";//科室id的position,-1没有筛选条件
    private String stateValue = "";//状态值的position，-1没有筛选条件


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        ButterKnife.bind(this);
        initView();
        initData();
        getDepartmentData();
    }


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

        commonAdapter = new CommonAdapter<DoctorEntity>(DoctorActivity.this, R.layout.item_doctor_list, doctorEntityList) {
            @Override
            protected void convert(ViewHolder holder, final DoctorEntity doctorEntity, int position) {
                holder.setText(R.id.name_text, doctorEntity.getEmpname());
                holder.setText(R.id.dept_text, doctorEntity.getDeptname());
                if ("空闲".equals(doctorEntity.getOperstatename())) {
                    holder.setTextColor(R.id.state_text, getResources().getColor(R.color.green_40));
                } else {
                    holder.setTextColor(R.id.state_text, getResources().getColor(R.color.orange_70));
                }
                holder.setText(R.id.state_text, doctorEntity.getOperstatename());

                holder.setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(DoctorDetailsActivity.class, "emp_id", doctorEntity.getEmpid());
                    }
                });
            }
        };
        rv.setAdapter(commonAdapter);

        mAdapter1 = new TagAdapter(departmentEntitieList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(DoctorActivity.this).inflate(R.layout.item_text_label, null);
                textView.setText(departmentEntitieList.get(position).getDeptname());
                return textView;
            }
        };

        mAdapter2 = new TagAdapter(stateList) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(DoctorActivity.this).inflate(R.layout.item_text_label, null);
                textView.setText(stateList.get(position).get("stateName"));
                return textView;
            }
        };


    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("searchText", searchEdit.getText().toString());
        map.put("deptid", departmentId);
        map.put("operstatename", stateValue);

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

    private void getDepartmentData() {
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

        //获取科室列表
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

    }

    Map<String, Set<Integer>> selectedMap = new HashMap<String, Set<Integer>>();

    @OnClick({R.id.return_btn, R.id.search_btn, R.id.screen_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.screen_btn:
                mAdapter1.setSelectedList(selectedMap.get("tagFlowLayout1"));
                mAdapter2.setSelectedList(selectedMap.get("tagFlowLayout2"));

                View itemView = DialogUtil.commomDialog(DoctorActivity.this, R.layout.dialog_doctor_filtrate, DialogUtil.RIGHT);
                final TagFlowLayout tagFlowLayout1 = itemView.findViewById(R.id.flowlayout1);
                final TagFlowLayout tagFlowLayout2 = itemView.findViewById(R.id.flowlayout2);
                Button resetBtn = itemView.findViewById(R.id.reset_btn);
                Button confirmBtn = itemView.findViewById(R.id.confirm_btn);

                //科室筛选
                tagFlowLayout1.setAdapter(mAdapter1);
                tagFlowLayout1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        departmentId = "";
                        selectedMap.put("tagFlowLayout1", selectPosSet);
                        for (int i : selectPosSet) {
                            if (TextUtils.isEmpty(departmentId)) {
                                departmentId += departmentEntitieList.get(i).getDeptid();
                            } else {
                                departmentId += "," + departmentEntitieList.get(i).getDeptid();
                            }
                        }
                    }
                });

                //医生状态筛选
                tagFlowLayout2.setAdapter(mAdapter2);
                tagFlowLayout2.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        stateValue = "";
                        selectedMap.put("tagFlowLayout2", selectPosSet);
                        for (int i : selectPosSet) {
                            if (TextUtils.isEmpty(stateValue)) {
                                stateValue += stateList.get(i).get("stateName");
                            } else {
                                stateValue += "," + stateList.get(i).get("stateName");
                            }
                        }
                    }
                });

                //重置按钮
                resetBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter1.setSelectedList(new HashSet<Integer>());
                        mAdapter2.setSelectedList(new HashSet<Integer>());
                    }
                });

                //确认按钮
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.dismissDialog();
                        initData();
                    }
                });

                break;
            case R.id.search_btn:
                initData();
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

}
