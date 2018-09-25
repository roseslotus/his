package com.mylike.his.activity.consultant;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.mylike.his.R;
import com.mylike.his.activity.CustomerDetailsActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.ClientEntity;
import com.mylike.his.entity.DepartmentEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.ClearEditText;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/5/30.
 * 客户列表
 */
public class ClientActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.indexBar)
    IndexBar indexBar;
    @Bind(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.search_edit)
    ClearEditText searchEdit;
    @Bind(R.id.search_btn)
    Button searchBtn;

    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    private CommonAdapter commonAdapter;
    private List<ClientEntity> clientEntityList = new ArrayList<>();//客户列表
    //    private List<ClientEntity> clientEntityList2 = new ArrayList<>();//客户列表
    private List<DepartmentEntity> departmentEntitieList = new ArrayList<>();//科室数据


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        rv.setLayoutManager(mManager = new LinearLayoutManager(this));
        mDecoration = new SuspensionDecoration(this, clientEntityList);
        mDecoration.setColorTitleBg(Color.parseColor("#00000000"));
        mDecoration.setColorTitleFont(Color.parseColor("#00B6B9"));
        rv.addItemDecoration(mDecoration);

        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager

        commonAdapter = new CommonAdapter<ClientEntity>(ClientActivity.this, R.layout.layaout_item_contacts, clientEntityList) {
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
                if (clientEntity.getCustLogo().equals("0")) {//vip
                    holder.setVisible(R.id.vip_img, true);
                    holder.setImageDrawable(R.id.vip_img, getResources().getDrawable(R.mipmap.vip_icon));

                } else if (clientEntity.getCustLogo().equals("1")) {//fip
                    holder.setVisible(R.id.vip_img, true);
                    holder.setImageDrawable(R.id.vip_img, getResources().getDrawable(R.mipmap.fip_icon));

                } else {//普通
                    holder.setVisible(R.id.vip_img, false);
                }
                //卡
                holder.setText(R.id.card_tv, clientEntity.getCardName());
                //活跃度
                holder.setText(R.id.liveness_text, clientEntity.getActiveLevelName());
                //来院次数
                holder.setText(R.id.number, clientEntity.getComeHospitalNum());
                //消费金额
                holder.setText(R.id.money_text, setDecimalFormat(clientEntity.getTotalAmounts()));
                RatingBar star = holder.getView(R.id.star);
                star.setRating(Integer.parseInt(clientEntity.getStar()));

                //客户详情
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
                        View itemView = DialogUtil.commomDialog(ClientActivity.this, R.layout.common_item_list, 0);
                        ListView listView = itemView.findViewById(R.id.common_list);
                        listView.setBackgroundResource(R.drawable.bg_white_box_10);
                        listView.setAdapter(new com.zhy.adapter.abslistview.CommonAdapter<DepartmentEntity>(ClientActivity.this, R.layout.common_item_text, departmentEntitieList) {
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
                        });
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
        HashMap<String, Object> map = new HashMap<>();
        map.put("counselorId", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.EMP_ID));
        map.put("searchText", searchEdit.getText().toString());
        HttpClient.getHttpApi().getClientList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<ClientEntity>>() {
            @Override
            protected void onSuccess(final List<ClientEntity> clientEntities) {
                if (TextUtils.isEmpty(searchEdit.getHint())) {
                    searchEdit.setHint("在全部" + clientEntities.size() + "个客人中搜索");
                }
                clientEntityList.clear();
                clientEntityList.addAll(clientEntities);
                commonAdapter.notifyDataSetChanged();
                indexBar.setmSourceDatas(clientEntityList).invalidate();
                mDecoration.setmDatas(clientEntityList);
                getDepartmentData();
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
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    //添加一条分诊，获取分诊id
    private void getTriage(String Custid, String DoctorDepartment) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Custid", Custid);
        map.put("DoctorDepartment", DoctorDepartment);
        HttpClient.getHttpApi().getTriage(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                // 保存/刷新分诊id
                getSaveData(stringStringMap.get("fid"));
                DialogUtil.dismissDialog();
            }

            @Override
            protected void onFailed(String code, String msg) {
                DialogUtil.dismissDialog();
            }
        });
    }

    private void getSaveData(final String Triageid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Triageid", Triageid);

        HttpClient.getHttpApi().getSave(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                SPUtils.setCache(SPUtils.FILE_RECEPTION, SPUtils.RECEPTION_ID, Triageid);
                if ("0".equals(stringStringMap.get("isCacheOrder"))) {
                    startActivity(ProductActivity.class);
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

    @OnClick({R.id.return_btn, R.id.search_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.screen_btn:
//                String searchValue = searchEdit.getText().toString();
//                if (TextUtils.isEmpty(searchValue)) {
//                    clientEntityList.clear();
//                    clientEntityList.addAll(clientEntityList2);
//                    commonAdapter.notifyDataSetChanged();
//                    indexBar.setmSourceDatas(clientEntityList).invalidate();
//                    mDecoration.setmDatas(clientEntityList);
//                } else {
//                    clientEntityList.clear();
//                    for (ClientEntity clientEntity : clientEntityList2) {
//                        if (clientEntity.getCustomName().indexOf(searchValue) != -1) {
//                            clientEntityList.add(clientEntity);
//                        }
//                    }
//                    commonAdapter.notifyDataSetChanged();
//                    indexBar.setmSourceDatas(clientEntityList).invalidate();
//                    mDecoration.setmDatas(clientEntityList);
//                }
//                initData();
//                break;
            case R.id.search_btn:
                initData();
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
