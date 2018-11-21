package com.mylike.his.activity.consultant;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.mylike.his.R;
import com.mylike.his.activity.CustomerDetailsActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.ClientEntity;
import com.mylike.his.entity.ConsumeDDEntity;
import com.mylike.his.entity.IntentionAddEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.entity.ReceptionTypeEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.utils.ViewUtil;
import com.mylike.his.view.ClearEditText;
import com.mylike.his.view.SListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/5/30.
 * 客户列表
 */
public class ClientActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.search_edit)
    ClearEditText searchEdit;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.filtrate_btn)
    ImageView filtrateBtn;
    @BindView(R.id.tag_ll)
    LinearLayout tagLl;
    @BindView(R.id.filtrate_list)
    SListView filtrateList;
    @BindView(R.id.start_time_text)
    TextView startTimeText;
    @BindView(R.id.start_time_btn)
    LinearLayout startTimeBtn;
    @BindView(R.id.end_time_text)
    TextView endTimeText;
    @BindView(R.id.end_time_btn)
    LinearLayout endTimeBtn;
    @BindView(R.id.frequency_low)
    EditText frequencyLow;
    @BindView(R.id.frequency_high)
    EditText frequencyHigh;
    @BindView(R.id.money_low)
    EditText moneyLow;
    @BindView(R.id.money_high)
    EditText moneyHigh;
    @BindView(R.id.reset_btn)
    Button resetBtn;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.filtrate_menu)
    LinearLayout filtrateMenu;
    @BindView(R.id.DrawerLayout)
    android.support.v4.widget.DrawerLayout DrawerLayout;
    @BindView(R.id.clear_text1)
    TextView clearText1;
    @BindView(R.id.clear_text2)
    TextView clearText2;
    @BindView(R.id.yx_text)
    TextView yxText;
    @BindView(R.id.clear_text3)
    TextView clearText3;
    @BindView(R.id.cp_text)
    TextView cpText;

    //客户通讯录
    private CommonAdapter commonAdapter;
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;
    private List<ClientEntity> clientEntityList = new ArrayList<>();//客户列表数据
    //private List<DepartmentEntity> departmentEntitieList = new ArrayList<>();//科室数据

    // 科室及医生
    private OptionsPickerView ConsumePV;//消费选择器
    private List<ConsumeDDEntity> consumeDDEntitie1 = new ArrayList<>();//科室
    private List<List<ConsumeDDEntity>> consumeDDEntitie2 = new ArrayList<>();//医生

    //筛选数据
    private TagAdapter tagAdapter;
    private com.zhy.adapter.abslistview.CommonAdapter commonAdapter1;
    private List<ReceptionTypeEntity> receptionTypeEntitieList = new ArrayList<>();

    //时间选择器
    private TimePickerView TimePV1;
    private TimePickerView TimePV2;
    Calendar startDate = Calendar.getInstance();//选择器开始时间
    Calendar endDate = Calendar.getInstance();//选择结束时间
    Calendar today = Calendar.getInstance();//当天

    //意向选择器
    private ViewUtil viewUtil1 = new ViewUtil();
    private OptionsPickerView IntentionPV1;//意向选择器
    private ViewUtil viewUtil2 = new ViewUtil();
    private OptionsPickerView IntentionPV2;//做过的项目选择器


    //标签存储数据
    Map<String, String> selectedValue = new HashMap<>();
    Map<Integer, Set<Integer>> selectedMap = new HashMap<Integer, Set<Integer>>();

    private String Custid;//客户id
    private String yxIdValue = "";
    private String cpIdValue = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnableLoadMore(false);//弃用加载功能

        initAdapter1();
        initAdapter2();

        //时间初始化
        startDate.set(1900, 0, 1);
        endDate.set(2200, 11, 31);

        //时间器初始化
        initTimeView1();
        initTimeView2();

        //意向选择器初始化
        initIntentionView();

    }

    private void initData() {
        getClientData();
        getClientType();
        getIntentionData();
        //getDepartmentData();
    }

    //客户列表适配器
    private void initAdapter1() {
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
            protected void convert(ViewHolder holder, final ClientEntity clientEntity, final int position) {
                holder.setText(R.id.client_name, clientEntity.getCustomName() + "(" + clientEntity.getCustomMobile() + ")");//姓名+电话
                //性别
                if (clientEntity.getCustomSex().equals("0")) {
                    holder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.girl_c_icon));
                } else {
                    holder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.boy_c_icon));
                }
                //级别（VIP ，FIP）
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
                //星级
                RatingBar star = holder.getView(R.id.star);
                star.setRating(Integer.parseInt(clientEntity.getStarNum()));

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
                       /* View itemView = DialogUtil.commomDialog(ClientActivity.this, R.layout.common_item_list, 0);
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

    //客户筛选适配器
    private void initAdapter2() {
        final float scale = this.getResources().getDisplayMetrics().density;
        commonAdapter1 = new com.zhy.adapter.abslistview.CommonAdapter<ReceptionTypeEntity>(ClientActivity.this, R.layout.item_filtrate_product_list, receptionTypeEntitieList) {
            @Override
            protected void convert(final com.zhy.adapter.abslistview.ViewHolder viewHolder, final ReceptionTypeEntity item, int position) {
                viewHolder.setText(R.id.cover_name, item.getName());
                TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
                tagAdapter = new TagAdapter(item.getList()) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) LayoutInflater.from(ClientActivity.this).inflate(R.layout.item_text_label, null);
                        textView.setTextSize(12);
                        textView.setWidth((filtrateMenu.getWidth() / 3) - (int) (10 * scale + 0.5f));
                        textView.setPadding(0, 30, 0, 30);
                        textView.setGravity(Gravity.CENTER);

                        textView.setText(item.getList().get(position).getName());
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

    //开始时间选择器
    private void initTimeView1() {
        TimePV1 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                startTimeText.setText(CommonUtil.getYMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(today)//默认数据
                .build();
    }

    //结束时间选择器
    private void initTimeView2() {
        TimePV2 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                endTimeText.setText(CommonUtil.getYMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(today)//默认数据
                .build();
    }

    //意向选择器
    private void initIntentionView() {
        viewUtil1.setIntentionListener(new ViewUtil.OnIntentionListener() {
            @Override
            public void onOptionsSelect(IntentionAddEntity intentionAddEntity) {
                yxText.setText(intentionAddEntity.getIntentionStr());
                yxIdValue = intentionAddEntity.getIntentionIdStr();
            }
        });

        viewUtil2.setIntentionListener(new ViewUtil.OnIntentionListener() {
            @Override
            public void onOptionsSelect(IntentionAddEntity intentionAddEntity) {
                cpText.setText(intentionAddEntity.getIntentionStr());
                cpIdValue = intentionAddEntity.getIntentionIdStr();
            }
        });
    }

    //获取客户列表
    private void getClientData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("counselorId", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.EMP_ID));
        map.put("searchText", searchEdit.getText().toString());
        map.put("minComeHospitalCount", frequencyLow.getText().toString());//最低来院频次
        map.put("maxComeHospitalCount", frequencyHigh.getText().toString());//最高来院频次
        map.put("minTotalAmount", moneyLow.getText().toString());//最低消费金额
        map.put("maxTotalAmount", moneyHigh.getText().toString());//最高消费金额
        map.put("startRegisterTime", startTimeText.getText().toString());//开始时间
        map.put("endRegisterTime", endTimeText.getText().toString());//结束时间
        map.put("custItem", yxIdValue);//意向
        map.put("haveProjects", cpIdValue);//项目
        map.putAll(selectedValue);

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
                refreshLayout.finishRefresh();
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);
            }
        });
    }

    //获取筛选数据
    private void getClientType() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("counselorId", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.EMP_ID));

        HttpClient.getHttpApi().getClientType(HttpClient.getRequestBody(map)).enqueue(new BaseBack<ReceptionTypeEntity>() {
            @Override
            protected void onSuccess(ReceptionTypeEntity receptionTypeEntitie) {
                receptionTypeEntitieList.addAll(receptionTypeEntitie.getList());
                commonAdapter1.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    //获取意向数据
    private void getIntentionData() {
        HttpClient.getHttpApi().getIntentionAll().enqueue(new BaseBack<List<IntentionEntity>>() {
            @Override
            protected void onSuccess(List<IntentionEntity> intentionEntities) {
                IntentionPV1 = viewUtil1.initIntention(ClientActivity.this, IntentionPV1, intentionEntities);
                IntentionPV2 = viewUtil2.initIntention(ClientActivity.this, IntentionPV2, intentionEntities);
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //获取科室医生二级列表
    private void getDepartmentData() {
       /* HttpClient.getHttpApi().getDepartmentList().enqueue(new BaseBack<List<DepartmentEntity>>() {
            @Override
            protected void onSuccess(List<DepartmentEntity> departmentEntities) {
                departmentEntitieList.addAll(departmentEntities);
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        }); */

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

    //添加一条分诊，获取分诊id
    private void getTriage(String DoctorDepartment, String doctorId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Custid", Custid);
        map.put("DoctorDepartment", DoctorDepartment);
        map.put("doctorid", doctorId);
        HttpClient.getHttpApi().getTriage(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                // 保存/刷新分诊id
                getSaveData(stringStringMap.get("fid"));
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //获取是否有暂存数据
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

    //初始化科室医生二级联动数据
    public void initChannelData() {
        for (int i = 0; i < consumeDDEntitie1.size(); i++) {//科室
            List<ConsumeDDEntity> doctor = new ArrayList<>();//医生容器
            if (consumeDDEntitie1.get(i).getDoctorlist().isEmpty()) {
                doctor.add(new ConsumeDDEntity("未分配医生"));
            } else {
                doctor.addAll(consumeDDEntitie1.get(i).getDoctorlist());
            }
            consumeDDEntitie2.add(doctor);
        }

        //初始化科室医生选择器
        ConsumePV = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                CommonUtil.showLoadProgress(ClientActivity.this);
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

    private String setDecimalFormat(String numberStr) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (TextUtils.isEmpty(numberStr)) {
            numberStr = "0";
        }
        Double number = Double.parseDouble(numberStr);
        decimalFormat.format(number);
        return decimalFormat.format(number);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getClientData();
    }

    @OnClick({R.id.return_btn, R.id.search_btn, R.id.filtrate_btn, R.id.reset_btn, R.id.confirm_btn, R.id.start_time_btn, R.id.end_time_btn, R.id.yx_text, R.id.cp_text, R.id.clear_text1, R.id.clear_text2, R.id.clear_text3})
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
                getClientData();
                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.reset_btn://筛选重置
                selectedMap.clear();
                selectedValue.clear();
                yxText.setText("");
                cpText.setText("");
                startTimeText.setText("");
                endTimeText.setText("");
                frequencyLow.setText("");
                frequencyHigh.setText("");
                moneyLow.setText("");
                moneyHigh.setText("");
                yxIdValue = "";
                cpIdValue = "";
                commonAdapter1.notifyDataSetChanged();
                break;
            case R.id.confirm_btn://筛选确认
                DrawerLayout.closeDrawer(filtrateMenu);
                getClientData();
                break;
            case R.id.filtrate_btn://接诊筛选
                DrawerLayout.openDrawer(filtrateMenu);
                break;
            case R.id.start_time_btn://开始时间
                TimePV1.show();
                break;
            case R.id.end_time_btn://结束时间
                TimePV2.show();
                break;
            case R.id.yx_text:
                IntentionPV1.show();
                break;
            case R.id.cp_text:
                IntentionPV2.show();
                break;
            case R.id.clear_text1://清除时间
                startTimeText.setText("");
                endTimeText.setText("");
                break;
            case R.id.clear_text2://清除意向
                yxText.setText("");
                break;
            case R.id.clear_text3://清除项目
                cpText.setText("");
                break;
        }
    }
}
