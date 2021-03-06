package com.mylike.his.fragment.consultant;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.mylike.his.R;
import com.mylike.his.activity.CustomerDetailsActivity;
import com.mylike.his.activity.consultant.ClientActivity;
import com.mylike.his.activity.consultant.DepositHospitalActivity;
import com.mylike.his.activity.consultant.OrderActivity;
import com.mylike.his.activity.consultant.ProductActivity;
import com.mylike.his.activity.consultant.StoredValueActivity;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.ClientEntity;
import com.mylike.his.entity.ConsumeDDEntity;
import com.mylike.his.entity.DepartmentEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by zhengluping on 2018/1/2.
 * 客户
 */
public class CustomerFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener {
    Unbinder unbinder;
    @BindView(R.id.search_edit)
    ClearEditText searchEdit;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private View view;

    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    private CommonAdapter commonAdapter;
    private List<ClientEntity> clientEntityList = new ArrayList<>();//客户列表
    private List<DepartmentEntity> departmentEntitieList = new ArrayList<>();//科室数据

    // 科室及医生
    private OptionsPickerView ConsumePV;//消费选择器
    private List<ConsumeDDEntity> consumeDDEntitie1 = new ArrayList<>();//科室
    private List<List<ConsumeDDEntity>> consumeDDEntitie2 = new ArrayList<>();//医生

    private String Custid;//客户id

    public static CustomerFragment newInstance() {
        Bundle args = new Bundle();
        CustomerFragment fragment = new CustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnableLoadMore(false);//弃用加载功能

        rv.setLayoutManager(mManager = new LinearLayoutManager(getActivity()));
        mDecoration = new SuspensionDecoration(getActivity(), clientEntityList);
        mDecoration.setColorTitleBg(Color.parseColor("#00000000"));
        mDecoration.setColorTitleFont(Color.parseColor("#00B6B9"));
        rv.addItemDecoration(mDecoration);

        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

        commonAdapter = new CommonAdapter<ClientEntity>(getActivity(), R.layout.layaout_item_contacts, clientEntityList) {
            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, final ClientEntity clientEntity, final int position) {
                holder.setText(R.id.client_name, clientEntity.getCustomName() + "（" + clientEntity.getCustomMobile() + "）");//姓名+电话
                //性别
                if (clientEntity.getCustomSex().equals("0")) {
                    holder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.girl_c_icon));
                } else {
                    holder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.boy_c_icon));
                }
                //级别（ 0：VIP ，1：FIP）
                if (clientEntity.getIsVip().equals("1"))
                    holder.setVisible(R.id.vip_img, true);
                else
                    holder.setVisible(R.id.vip_img, false);

                if (clientEntity.getIsEmphasis().equals("1"))
                    holder.setVisible(R.id.fip_img, true);
                else
                    holder.setVisible(R.id.fip_img, false);

                //卡
                if (TextUtils.isEmpty(clientEntity.getCardName())) {
                    holder.setVisible(R.id.card_tv, false);
                } else {
                    holder.setVisible(R.id.card_tv, true);
                    holder.setText(R.id.card_tv, clientEntity.getCardName());
                }
                //活跃度
                if (TextUtils.isEmpty(clientEntity.getActiveLevelName())) {
                    holder.setVisible(R.id.liveness_text, false);
                } else {
                    holder.setVisible(R.id.liveness_text, true);
                    holder.setText(R.id.liveness_text, clientEntity.getActiveLevelName());
                }
                //来院次数
                holder.setText(R.id.number, clientEntity.getComeHospitalNum());
                //消费金额
                holder.setText(R.id.money_text, setDecimalFormat(clientEntity.getTotalAmounts()));
                RatingBar star = holder.getView(R.id.star);
                star.setRating(Integer.parseInt(clientEntity.getStarNum()));

                holder.setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(CustomerDetailsActivity.class, "clientId", clientEntity.getCustomId());
                    }
                });


                //消费
                holder.setOnClickListener(R.id.expense_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*View itemView = DialogUtil.commomDialog(getActivity(), R.layout.common_item_list, 0);
                        ListView listView = itemView.findViewById(R.id.common_list);
                        listView.setBackgroundResource(R.drawable.bg_white_box_10);
                        listView.setAdapter(new com.zhy.adapter.abslistview.CommonAdapter<DepartmentEntity>(getActivity(), R.layout.common_item_text, departmentEntitieList) {
                            @Override
                            protected void convert(ViewHolder viewHolder, DepartmentEntity item, int position) {
                                TextView textView = viewHolder.getView(R.id.text);
                                textView.setPadding(20, 30, 20, 30);
                                textView.setText(item.getDeptname());
                            }
                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                getTriage(clientEntity.getCustomId(), departmentEntitieList.get(position).getDeptid());
                            }
                        });*/

                        Custid = clientEntity.getCustomId();
                        getDepartmentData();
                    }
                });
                //储值
                holder.setOnClickListener(R.id.stored_value_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(StoredValueActivity.class, "clientId", clientEntity.getCustomId());
                    }
                });
                //住院押金
                holder.setOnClickListener(R.id.hospital_deposit_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(DepositHospitalActivity.class, "clientId", clientEntity.getCustomId());
                    }
                });
            }
        };
        rv.setAdapter(commonAdapter);
    }

    private void initData() {
        getClientData();
//        getDepartmentData();
    }

    //获取客户列表
    private void getClientData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("counselorId", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.EMP_ID));
        map.put("searchText", searchEdit.getText().toString());

        HttpClient.getHttpApi().getClientList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<ClientEntity>>() {
            @Override
            protected void onSuccess(final List<ClientEntity> clientEntities) {
                try {

                    if (TextUtils.isEmpty(searchEdit.getHint())) {
                        searchEdit.setHint("在全部" + clientEntities.size() + "个客人中搜索");
                    }
                    clientEntityList.clear();
                    clientEntityList.addAll(clientEntities);
                    commonAdapter.notifyDataSetChanged();
                    indexBar.setmSourceDatas(clientEntityList).invalidate();
                    mDecoration.setmDatas(clientEntityList);
                    refreshLayout.finishRefresh();
                } catch (Exception e) {

                }

            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);

            }
        });
    }

    //获取科室列表
    private void getDepartmentData() {
       /* HttpClient.getHttpApi().getDepartmentList().enqueue(new BaseBack<List<DepartmentEntity>>() {
            @Override
            protected void onSuccess(List<DepartmentEntity> departmentEntities) {
                departmentEntitieList.addAll(departmentEntities);
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });*/
        HashMap<String, Object> map = new HashMap<>();
        map.put("Custid", Custid);

        HttpClient.getHttpApi().getDepartmentAndDoctor(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<ConsumeDDEntity>>() {
            @Override
            protected void onSuccess(List<ConsumeDDEntity> consumeDDEntities) {
                consumeDDEntitie1.clear();
                consumeDDEntitie1.addAll(consumeDDEntities);
                initChannelData();

            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    //初始化科室医生二级联动数据
    public void initChannelData() {
        for (int i = 0; i < consumeDDEntitie1.size(); i++) {//科室
            List<ConsumeDDEntity> doctor = new ArrayList<>();//医生容器
            if (consumeDDEntitie1.get(i).getDoctorlist().isEmpty()) {
                doctor.add(new ConsumeDDEntity("未分配过医生"));
            } else {
                doctor.add(new ConsumeDDEntity("请选择"));
                doctor.addAll(consumeDDEntitie1.get(i).getDoctorlist());
            }
            consumeDDEntitie2.add(doctor);
        }

        //初始化科室医生选择器
        ConsumePV = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                CommonUtil.showLoadProgress(getActivity());
                getTriage(consumeDDEntitie1.get(options1).getDepartmentid(), consumeDDEntitie2.get(options1).get(options2).getDepartmentid());
            }
        })
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))
                .setCancelColor(getResources().getColor(R.color.gray_49))
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .isRestoreItem(true)
                .setTextXOffset(20, 20, 0)
                .build();

        ConsumePV.setPicker(consumeDDEntitie1, consumeDDEntitie2);//二级选择器
        ConsumePV.show();
    }

    //添加一条分诊，获取分诊id
    private void getTriage(final String DoctorDepartment, String doctorId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Custid", Custid);
        map.put("DoctorDepartment", DoctorDepartment);
        map.put("doctorid", doctorId);
        HttpClient.getHttpApi().getTriage(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                // 保存/刷新分诊id
                getSaveData(stringStringMap.get("fid"), DoctorDepartment);
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    private void getSaveData(final String Triageid, final String departmentId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Triageid", Triageid);

        HttpClient.getHttpApi().getSave(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                SPUtils.setCache(SPUtils.FILE_PASS, SPUtils.RECEPTION_ID, Triageid);
                if ("0".equals(stringStringMap.get("isCacheOrder"))) {
                    startActivity(ProductActivity.class, "deptId", departmentId);
                } else {
                    startActivity(OrderActivity.class, "chargeTag", "1");
                }
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    private String setDecimalFormat(String numberStr) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (TextUtils.isEmpty(numberStr)) {
            numberStr = "0";
        }
        Double number = Double.parseDouble(numberStr);
        decimalFormat.format(number);
        return decimalFormat.format(number);
    }


    @OnClick({R.id.search_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn://搜索
                initData();
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getClientData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
