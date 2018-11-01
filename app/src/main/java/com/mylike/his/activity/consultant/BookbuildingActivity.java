package com.mylike.his.activity.consultant;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.BookbuildingEntity;
import com.mylike.his.entity.IntentionAddEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.utils.ViewUtil;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/7/18.
 * 建档
 */
public class BookbuildingActivity extends BaseActivity implements View.OnClickListener {
    /*@Bind(R.id.table1)
    TableLayout table1;
    @Bind(R.id.ll_tag1)
    LinearLayout llTag1;
    @Bind(R.id.table2)
    TableLayout table2;
    @Bind(R.id.ll_tag2)
    LinearLayout llTag2;
    @Bind(R.id.ll_tag3)
    LinearLayout llTag3;
    @Bind(R.id.ll_tag4)
    LinearLayout llTag4;
    @Bind(R.id.ll_tag5)
    LinearLayout llTag5;
    @Bind(R.id.ll_tag6)
    LinearLayout llTag6;*/

    @Bind(R.id.table3)
    TableLayout table3;
    @Bind(R.id.table4)
    TableLayout table4;
    @Bind(R.id.table5)
    TableLayout table5;
    @Bind(R.id.table6)
    TableLayout table6;
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.other_btn)
    TextView otherBtn;
    @Bind(R.id.contact_btn)
    TextView contactBtn;
    @Bind(R.id.appointment_btn)
    TextView appointmentBtn;
    @Bind(R.id.bookbuilding_btn)
    TextView bookbuildingBtn;
    @Bind(R.id.Economics_spr)
    Spinner EconomicsSpr;
    @Bind(R.id.Intention_spr)
    Spinner IntentionSpr;
    @Bind(R.id.marriage_spr)
    Spinner marriageSpr;
    @Bind(R.id.Occupation_spr)
    Spinner OccupationSpr;
    @Bind(R.id.Provinces_btn)
    EditText ProvincesBtn;
    @Bind(R.id.birth_date_btn)
    EditText birthDateBtn;
    @Bind(R.id.medium_btn)
    EditText mediumBtn;
    @Bind(R.id.channel_btn)
    EditText channelBtn;
    @Bind(R.id.flowlayout1)
    TagFlowLayout flowlayout1;
    @Bind(R.id.flowlayout2)
    TagFlowLayout flowlayout2;
    @Bind(R.id.appointment_time_btn)
    EditText appointmentTimeBtn;
    @Bind(R.id.add_btn)
    TextView addBtn;
    @Bind(R.id.intention_list)
    ListView intentionList;
    @Bind(R.id.phone_edit)
    EditText phoneEdit;
    @Bind(R.id.user_name)
    EditText userName;
    @Bind(R.id.creation_time)
    EditText creationTime;
    @Bind(R.id.name_edit)
    EditText nameEdit;
    @Bind(R.id.man)
    RadioButton man;
    @Bind(R.id.girl)
    RadioButton girl;
    @Bind(R.id.sex)
    RadioGroup sex;
    @Bind(R.id.age_edit)
    EditText ageEdit;
    @Bind(R.id.address_details_edit)
    EditText addressDetailsEdit;
    @Bind(R.id.save_btn)
    TextView saveBtn;
    @Bind(R.id.custFeedback_edit)
    EditText custFeedbackEdit;
    @Bind(R.id.custSuggest_edit)
    EditText custSuggestEdit;
    @Bind(R.id.custBudget_edit)
    EditText custBudgetEdit;
    @Bind(R.id.processor_price_edit)
    EditText processorPriceEdit;
    @Bind(R.id.cfremark_edit)
    EditText cfremarkEdit;
    @Bind(R.id.qq_edit)
    EditText qqEdit;
    @Bind(R.id.wechat_edit)
    EditText wechatEdit;
    @Bind(R.id.other_contact_edit)
    EditText otherContactEdit;
    @Bind(R.id.yes)
    RadioButton yes;
    @Bind(R.id.no)
    RadioButton no;
    @Bind(R.id.big)
    RadioGroup big;
    @Bind(R.id.mylike_id)
    EditText mylikeId;

    private ViewUtil viewUtil = new ViewUtil();

    private String phoneValue;//手机号
    private String timeValue;//当前时间（建档时间）
    private String sexValue;//性别
    private String economicValue;//经济能力
    private Map<String, String> medium = new HashMap<>();//媒介
    private Map<String, String> provinces = new HashMap<>();//省市区
    private String intentionDegree;//意向度
    private String marriageValue;//婚姻
    private String occupationValue;//职业
    private Map<String, String> channel = new HashMap<>();//渠道
    private String transportValue;//交通工具
    private String obstacleValue;//障碍点
    private String bigValue;//是否大单


    //下拉框类型的数据
    private List<BookbuildingEntity> EconomicsEntity = new ArrayList<>();//经济能力
    private List<BookbuildingEntity> IntentionEntity = new ArrayList<>();//意向度
    private List<BookbuildingEntity> MarriageEntity = new ArrayList<>();//婚姻
    private List<BookbuildingEntity> OccupationEntity = new ArrayList<>();//职业
    private CommonAdapter EconomicsAdapter;//经济能力
    private CommonAdapter IntentionAdapter;//意向度
    private CommonAdapter MarriageAdapter;//婚姻
    private CommonAdapter OccupationAdapter;//职业

    //复选框类型的数据
    private int tagValue;//复选框宽的值
    private List<BookbuildingEntity> transportEntity = new ArrayList<>();//交通
    private List<BookbuildingEntity> obstacleEntity = new ArrayList<>();//障碍点
    private TagAdapter transportAdapter;//交通
    private TagAdapter obstacleAdapter;//障碍点

    //省市区数据
    private OptionsPickerView ProvincesPV;//省市区选择器
    private List<BookbuildingEntity> provincesEntity1 = new ArrayList<>();//省
    private List<List<BookbuildingEntity>> provincesEntity2 = new ArrayList<>();//市
    private List<List<List<BookbuildingEntity>>> provincesEntity3 = new ArrayList<>();//区

    //意向数据
    private OptionsPickerView IntentionPV;//意向选择器
    private List<IntentionAddEntity> intentionEntitiesList = new ArrayList<>();//添加的意向list容器
    //    private List<IntentionEntity> intentionEntities1 = new ArrayList<>();//一级意向
//    private List<List<IntentionEntity>> intentionEntities2 = new ArrayList<>();//二级意向
//    private List<List<List<IntentionEntity>>> intentionEntities3 = new ArrayList<>();//三级意向
    private CommonAdapter IntentionListAdapter;

    // 媒介数据
    private OptionsPickerView MediumPV;//媒介选择器
    private List<BookbuildingEntity> mediumEntity1 = new ArrayList<>();//一级媒介
    private List<List<BookbuildingEntity>> mediumEntity2 = new ArrayList<>();//二级媒介

    // 二/三级渠道数据
    private OptionsPickerView ChannelPV;//媒介选择器
    private List<BookbuildingEntity> ChannelEntity1 = new ArrayList<>();//二级渠道
    private List<List<BookbuildingEntity>> ChannelEntity2 = new ArrayList<>();//三级渠道

    //出生日期选择器
    private TimePickerView TimePV1;

    //预约日期选择器
    private TimePickerView TimePV2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookbuilding);
        ButterKnife.bind(this);
        initView();
        initAdapter();
        initData();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //计算每个复选框占用的宽
        float scale = this.getResources().getDisplayMetrics().density;
        tagValue = (flowlayout1.getWidth() / 3) - (int) (10 * scale + 0.5f);

        //重新绘画复选框
        transportAdapter = setTagAdapter(flowlayout1, transportAdapter, transportEntity);
        obstacleAdapter = setTagAdapter(flowlayout2, obstacleAdapter, obstacleEntity);
    }

    //初始化控件
    private void initView() {
        phoneValue = getIntent().getStringExtra("phone");
        timeValue = getIntent().getStringExtra("time");

        phoneEdit.setText(phoneValue);
        creationTime.setText(timeValue);
        userName.setText(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.USER_NAME));

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == man.getId()) {
                    sexValue = "1";
                } else if (i == girl.getId()) {
                    sexValue = "0";
                }
            }
        });
        big.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == yes.getId()) {
                    bigValue = "是";
                } else if (i == no.getId()) {
                    bigValue = "否";
                }
            }
        });

        initTimeView1();
        initTimeView2();

        viewUtil.setIntentionListener(new ViewUtil.OnIntentionListener() {
            @Override
            public void onOptionsSelect(IntentionAddEntity intentionAddEntity) {
                intentionEntitiesList.add(intentionAddEntity);
                IntentionListAdapter.notifyDataSetChanged();
            }
        });
    }

    //初始化适配器
    private void initAdapter() {
        EconomicsAdapter = setAdapter(EconomicsSpr, EconomicsAdapter, EconomicsEntity);
        IntentionAdapter = setAdapter(IntentionSpr, IntentionAdapter, IntentionEntity);
        MarriageAdapter = setAdapter(marriageSpr, MarriageAdapter, MarriageEntity);
        OccupationAdapter = setAdapter(OccupationSpr, OccupationAdapter, OccupationEntity);

        transportAdapter = setTagAdapter(flowlayout1, transportAdapter, transportEntity);
        obstacleAdapter = setTagAdapter(flowlayout2, obstacleAdapter, obstacleEntity);

        //意向适配器
        IntentionListAdapter = new CommonAdapter<IntentionAddEntity>(this, R.layout.item_add_intention, intentionEntitiesList) {
            @Override
            protected void convert(ViewHolder viewHolder, IntentionAddEntity item, final int position) {
                viewHolder.setText(R.id.intention_text, item.getIntentionStr());//意向
                //删除点击
                viewHolder.setOnClickListener(R.id.delete_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intentionEntitiesList.remove(position);
                        IntentionAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        intentionList.setAdapter(IntentionListAdapter);
    }

    //获取数据
    private void initData() {
        Set<String> paramValue = new HashSet<>();
        paramValue.add("Economics");//经济能力
        paramValue.add("medium_src_1");//媒介来源
        paramValue.add("Province");//省市区
        paramValue.add("Intention");//意向度
        paramValue.add("Marriage");//婚姻
        paramValue.add("Occupation");//职业
        paramValue.add("channel_2");//二、三级渠道
        paramValue.add("transport");//交通工具
        paramValue.add("obstacle");//障碍点

        Map<String, Object> map = new HashMap<>();
        map.put("domainNamespace", paramValue);

        //建档所需要的选择信息（有多级级菜单）
        HttpClient.getHttpApi().getDicMult(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, List<BookbuildingEntity>>>() {
            @Override
            protected void onSuccess(Map<String, List<BookbuildingEntity>> stringListMap) {
                EconomicsEntity.addAll(stringListMap.get("Economics"));//经济能力
                EconomicsAdapter.notifyDataSetChanged();

                IntentionEntity.addAll(stringListMap.get("Intention"));//意向度
                IntentionAdapter.notifyDataSetChanged();

                MarriageEntity.addAll(stringListMap.get("Marriage"));//婚姻
                MarriageAdapter.notifyDataSetChanged();

                OccupationEntity.addAll(stringListMap.get("Occupation"));//职业
                OccupationAdapter.notifyDataSetChanged();

                mediumEntity1.addAll(stringListMap.get("medium_src_1"));//媒介来源
                initMediumData();

                provincesEntity1.addAll(stringListMap.get("Province"));//省市区
                initProvincesData();//初始化联动数据

                ChannelEntity1.addAll(stringListMap.get("channel_2"));//二/三级渠道
                initChannelData();//初始化联动数据

                transportEntity.addAll(stringListMap.get("transport"));//交通工具
                transportAdapter.notifyDataChanged();

                obstacleEntity.addAll(stringListMap.get("obstacle"));//障碍点
                obstacleAdapter.notifyDataChanged();
                getIntentionData();
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
//                intentionEntities1.addAll(intentionEntities);
                IntentionPV = viewUtil.initIntention(BookbuildingActivity.this, IntentionPV, intentionEntities);
//                initIntentionData();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //保存数据
    private void saveData() {
        Map<String, String> tempCustInfo = new HashMap<>();
        tempCustInfo.put("visitorImportId", mylikeId.getText().toString());//美莱在线访客ID
        tempCustInfo.put("name", nameEdit.getText().toString());//姓名
        tempCustInfo.put("cftelephone", phoneValue);//手机号
        tempCustInfo.put("cfsex", sexValue);//性别
        tempCustInfo.put("age", ageEdit.getText().toString());//年龄
        tempCustInfo.put("economy", economicValue);//经济能力
        tempCustInfo.put("mediumSrcMain", medium.get("1"));//媒介来源一级
        tempCustInfo.put("mediumSrcSub", medium.get("2"));//媒介来源二级
        tempCustInfo.put("province", provinces.get("province"));//省份
        tempCustInfo.put("city", provinces.get("city"));//城市
        tempCustInfo.put("reqion", provinces.get("district"));//区县
        tempCustInfo.put("address", addressDetailsEdit.getText().toString());//详细地址
        tempCustInfo.put("cfintent", intentionDegree);//意向度
        tempCustInfo.put("cfbirthday", birthDateBtn.getText().toString());//出生日期
        tempCustInfo.put("cfisMarry", marriageValue);//婚姻状况
        tempCustInfo.put("occupation", occupationValue);//职业
        tempCustInfo.put("channelFirst", "1");//一级渠道(搜索渠道为：1)
        tempCustInfo.put("channelSecond", channel.get("2"));//二级渠道
        tempCustInfo.put("channelThird", channel.get("3"));//三级渠道
        tempCustInfo.put("transport", transportValue);//交通工具
        tempCustInfo.put("barrierAspect", obstacleValue);//障碍点
        tempCustInfo.put("custFeedback", custFeedbackEdit.getText().toString());//顾客情况反馈
        tempCustInfo.put("custSuggest", custSuggestEdit.getText().toString());//建议方案
        tempCustInfo.put("custBudget", custBudgetEdit.getText().toString());//顾客预算
        tempCustInfo.put("processorPrice", processorPriceEdit.getText().toString());//专家及报价
        tempCustInfo.put("cfremark", cfremarkEdit.getText().toString());//备注信息
        tempCustInfo.put("qq", qqEdit.getText().toString());//qq号
        tempCustInfo.put("fwechat", wechatEdit.getText().toString());//微信号
        tempCustInfo.put("otherContact", otherContactEdit.getText().toString());//其他联系方式（多个分号隔开）
        if (!TextUtils.isEmpty(appointmentTimeBtn.getText().toString())) {
            tempCustInfo.put("appointTime", appointmentTimeBtn.getText().toString() + ":00");//预约时间
        }
        tempCustInfo.put("fisbulksale", bigValue);//是否大单
        tempCustInfo.put("buildBy", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.EMP_ID));//建档人id
        tempCustInfo.put("buildDate", timeValue);//建档时间（yyyy-MM-dd HH:mm:ss）

        Map<String, Object> map = new HashMap<>();
        map.put("tempCustInfo", tempCustInfo);
        map.put("itemList", intentionEntitiesList);

        //建档所需要的选择信息（有多级级菜单）
        HttpClient.getHttpApi().saveBookbuilding(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Object>() {
            @Override
            protected void onSuccess(Object o) {
                CommonUtil.showToast("提交成功");
                finish();
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    @OnClick({R.id.return_btn, R.id.other_btn, R.id.contact_btn, R.id.appointment_btn, R.id.bookbuilding_btn,
            R.id.Provinces_btn, R.id.birth_date_btn, R.id.medium_btn, R.id.channel_btn, R.id.appointment_time_btn, R.id.add_btn, R.id.save_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_btn://保存
                saveCheck();
                break;
            case R.id.return_btn://返回
                finish();
                break;
            case R.id.other_btn://其他信息
                arrowsChange(table3, otherBtn);
                break;

            case R.id.contact_btn://其他联系方式
                arrowsChange(table4, contactBtn);
                break;

            case R.id.appointment_btn://预约信息
                arrowsChange(table5, appointmentBtn);
                break;

            case R.id.bookbuilding_btn://建档信息
                arrowsChange(table6, bookbuildingBtn);
                break;

            case R.id.birth_date_btn://出生日期
                TimePV1.show();
                break;

            case R.id.medium_btn://媒介来源
                MediumPV.show();
                break;

            case R.id.Provinces_btn://省市区
                ProvincesPV.show();
                break;

            case R.id.channel_btn://二/三级渠道
                ChannelPV.show();
                break;

            case R.id.appointment_time_btn://预约时间
                TimePV2.show();
                break;

            case R.id.add_btn://新增意向
                IntentionPV.show();
                break;

        }
    }

    //保存效验
    private void saveCheck() {
        if (TextUtils.isEmpty(mylikeId.getText().toString())) {
            CommonUtil.showToast("保存失败，请输入美莱在线访客ID");
        } else if (TextUtils.isEmpty(nameEdit.getText().toString())) {
            CommonUtil.showToast("保存失败，请输入姓名");
        } else if (TextUtils.isEmpty(sexValue)) {
            CommonUtil.showToast("保存失败，请选择性别");
        } else if (TextUtils.isEmpty(ageEdit.getText().toString())) {
            CommonUtil.showToast("保存失败，输入年龄");
        } else if (TextUtils.isEmpty(economicValue)) {
            CommonUtil.showToast("保存失败，请选择经济能力");
        } else if (medium.isEmpty()) {
            CommonUtil.showToast("保存失败，请选择媒介来源");
        } else if (provinces.isEmpty()) {
            CommonUtil.showToast("保存失败，请选择省市区");
        } else if (TextUtils.isEmpty(addressDetailsEdit.getText().toString())) {
            CommonUtil.showToast("保存失败，输入详细地址");
        } else if (TextUtils.isEmpty(intentionDegree)) {
            CommonUtil.showToast("保存失败，请选择意向度");
        } else if (intentionEntitiesList.isEmpty()) {
            CommonUtil.showToast("保存失败，请添加意向项目");
        } else {
            saveData();
        }
    }

    //改变展开项箭头
    private void arrowsChange(TableLayout table, TextView text) {
        if (table.getVisibility() == View.GONE) {
            table.setVisibility(View.VISIBLE);
            Drawable nav_up = getResources().getDrawable(R.mipmap.top_green_icon);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            text.setCompoundDrawables(null, null, nav_up, null);
        } else {
            table.setVisibility(View.GONE);
            Drawable nav_up = getResources().getDrawable(R.mipmap.below_green_icon);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            text.setCompoundDrawables(null, null, nav_up, null);
        }
    }

    //设置下拉列表公共适配器
    private CommonAdapter setAdapter(final Spinner spinner, CommonAdapter commonAdapter, final List<BookbuildingEntity> bookbuildingEntity) {
        //spring添加一个默认项
        bookbuildingEntity.add(new BookbuildingEntity("请选择"));

        commonAdapter = new CommonAdapter<BookbuildingEntity>(this, R.layout.common_item_text, bookbuildingEntity) {
            @Override
            protected void convert(ViewHolder viewHolder, BookbuildingEntity item, int position) {
                TextView textView = viewHolder.getView(R.id.text);
                //设置第一项“请选择颜色为灰色”
                if (TextUtils.isEmpty(bookbuildingEntity.get(position).getDomainValue())) {
                    textView.setTextColor(getResources().getColor(R.color.gray_49));
                } else {
                    textView.setTextColor(getResources().getColor(R.color.black_50));
                }
                textView.setGravity(Gravity.RIGHT);
                textView.setPadding(10, 30, 10, 30);
                viewHolder.setText(R.id.text, item.getDomainText());
            }
        };
        spinner.setAdapter(commonAdapter);

        //获取下拉框里的值
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner == EconomicsSpr) {//经济能力
                    economicValue = bookbuildingEntity.get(i).getDomainValue();
                } else if (spinner == IntentionSpr) {//意向度
                    intentionDegree = bookbuildingEntity.get(i).getDomainValue();
                } else if (spinner == marriageSpr) {//婚姻
                    marriageValue = bookbuildingEntity.get(i).getDomainValue();
                } else if (spinner == OccupationSpr) {//职业
                    occupationValue = bookbuildingEntity.get(i).getDomainValue();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return commonAdapter;
    }

    //设置复选标签公共适配器
    private TagAdapter setTagAdapter(final TagFlowLayout tagFlowLayout, TagAdapter tagAdapter, final List<BookbuildingEntity> bookbuildingEntity) {
        tagAdapter = new TagAdapter(bookbuildingEntity) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView textView = (TextView) LayoutInflater.from(BookbuildingActivity.this).inflate(R.layout.item_text_label, null);
                textView.setWidth(tagValue);
                textView.setPadding(0, 20, 0, 20);
                textView.setGravity(Gravity.CENTER);
                textView.setText(bookbuildingEntity.get(position).getDomainText());
                return textView;
            }
        };
        tagFlowLayout.setAdapter(tagAdapter);
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (tagFlowLayout == flowlayout1) {//交通工具选择的值
                    transportValue = "";
                    for (int i : selectPosSet) {
                        if (TextUtils.isEmpty(transportValue)) {
                            transportValue += bookbuildingEntity.get(i).getDomainValue();
                        } else {
                            transportValue += "," + bookbuildingEntity.get(i).getDomainValue();
                        }
                    }
                } else if (tagFlowLayout == flowlayout2) {//障碍点选择的值
                    obstacleValue = "";
                    for (int i : selectPosSet) {
                        if (TextUtils.isEmpty(obstacleValue)) {
                            obstacleValue += bookbuildingEntity.get(i).getDomainValue();
                        } else {
                            obstacleValue += "," + bookbuildingEntity.get(i).getDomainValue();
                        }
                    }
                }

            }
        });

        return tagAdapter;
    }

    //出生日期
    private void initTimeView1() {
        //时间选择器
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        TimePV1 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                birthDateBtn.setText(getTime1(date));
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

    //预约日期
    private void initTimeView2() {
        //时间选择器
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 11, 31);
        TimePV2 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                appointmentTimeBtn.setText(getTime2(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, false})//隐藏秒
                .setSubCalSize(14)//确认取消文字大小s
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(startDate)
                .build();
    }

    private String getTime1(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private String getTime2(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return format.format(date);
    }

    //初始化媒介二级联动数据
    public void initMediumData() {
        for (int i = 0; i < mediumEntity1.size(); i++) {//遍历一级
            List<BookbuildingEntity> medium = new ArrayList<>();//二级容器
            if (mediumEntity1.get(i).getSub().isEmpty()) {
                medium.add(new BookbuildingEntity("暂无"));
            } else {
                medium.addAll(mediumEntity1.get(i).getSub());
            }
            mediumEntity2.add(medium);
        }

        //初始化媒介来源选择器
        MediumPV = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String ProvincesStr = "";
                if (!TextUtils.isEmpty(mediumEntity1.get(options1).getDomainValue())) {//媒介一级
                    ProvincesStr += mediumEntity1.get(options1).getDomainText();
                    medium.put("1", mediumEntity1.get(options1).getDomainValue());
                }
                if (!TextUtils.isEmpty(mediumEntity2.get(options1).get(options2).getDomainValue())) {//媒介二级
                    ProvincesStr += " " + mediumEntity2.get(options1).get(options2).getDomainText();
                    medium.put("2", mediumEntity2.get(options1).get(options2).getDomainValue());
                }
                mediumBtn.setText(ProvincesStr);
            }
        })
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))
                .setCancelColor(getResources().getColor(R.color.gray_49))
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .isRestoreItem(true)
                .build();

        MediumPV.setPicker(mediumEntity1, mediumEntity2);//二级选择器
    }

    //初始化二/三级渠道二级联动数据
    public void initChannelData() {
        for (int i = 0; i < ChannelEntity1.size(); i++) {//遍历二级级
            List<BookbuildingEntity> medium = new ArrayList<>();//三级级容器
            if (ChannelEntity1.get(i).getSub().isEmpty()) {
                medium.add(new BookbuildingEntity("暂无"));
            } else {
                medium.addAll(ChannelEntity1.get(i).getSub());
            }
            ChannelEntity2.add(medium);
        }

        //初始化二/三级渠道选择器
        ChannelPV = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String ProvincesStr = "";
                if (!TextUtils.isEmpty(ChannelEntity1.get(options1).getDomainValue())) {
                    ProvincesStr += ChannelEntity1.get(options1).getDomainText();
                    channel.put("2", ChannelEntity1.get(options1).getDomainValue());
                }
                if (!TextUtils.isEmpty(ChannelEntity2.get(options1).get(options2).getDomainValue())) {
                    ProvincesStr += " " + ChannelEntity2.get(options1).get(options2).getDomainText();
                    channel.put("3", ChannelEntity2.get(options1).get(options2).getDomainValue());
                }
                channelBtn.setText(ProvincesStr);
            }
        })
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))
                .setCancelColor(getResources().getColor(R.color.gray_49))
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .isRestoreItem(true)
                .build();

        ChannelPV.setPicker(ChannelEntity1, ChannelEntity2);//二级选择器
    }

    //初始化（省/市/区）三级联动数据
    public void initProvincesData() {
        for (int i = 0; i < provincesEntity1.size(); i++) {//遍历省份
            List<BookbuildingEntity> CityList = new ArrayList<>();//该省的市列表（第二级）
            List<List<BookbuildingEntity>> DistrictList = new ArrayList<>();//该省的所有区列表（第三极）
            List<BookbuildingEntity> District;//该市的所有区列表

            //如果无城市数据，建议添加空对象，防止数据为null 导致三个选项长度不匹配造成崩溃
            if (provincesEntity1.get(i).getSub().isEmpty()) {
                CityList.add(new BookbuildingEntity("暂无"));
                District = new ArrayList<>();
                District.add(new BookbuildingEntity("暂无"));
                DistrictList.add(District);//添加该省所有地区数据
            } else {
                for (int c = 0; c < provincesEntity1.get(i).getSub().size(); c++) {//遍历该省的所有市
                    CityList.add(provincesEntity1.get(i).getSub().get(c));//添加市
                    District = new ArrayList<>();
                    //如果无地区数据，建议添加空对象，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (provincesEntity1.get(i).getSub().get(c).getSub().isEmpty()) {
                        District.add(new BookbuildingEntity("暂无"));
                    } else {
                        District.addAll(provincesEntity1.get(i).getSub().get(c).getSub());//添加区
                    }
                    DistrictList.add(District);//添加该省所有地区数据
                }
            }
            //添加市级数据
            provincesEntity2.add(CityList);
            //添加区级数据
            provincesEntity3.add(DistrictList);
        }

        //初始化省市区选择器
        ProvincesPV = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String ProvincesStr = "";
                if (!TextUtils.isEmpty(provincesEntity1.get(options1).getDomainValue())) {
                    ProvincesStr += provincesEntity1.get(options1).getDomainText();
                    provinces.put("province", provincesEntity1.get(options1).getDomainValue());
                }
                if (!TextUtils.isEmpty(provincesEntity2.get(options1).get(options2).getDomainValue())) {
                    ProvincesStr += " " + provincesEntity2.get(options1).get(options2).getDomainText();
                    provinces.put("city", provincesEntity2.get(options1).get(options2).getDomainValue());
                }
                if (!TextUtils.isEmpty(provincesEntity3.get(options1).get(options2).get(options3).getDomainValue())) {
                    ProvincesStr += " " + provincesEntity3.get(options1).get(options2).get(options3).getDomainText();
                    provinces.put("district", provincesEntity3.get(options1).get(options2).get(options3).getDomainValue());
                }
                ProvincesBtn.setText(ProvincesStr);
            }
        })
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))
                .setCancelColor(getResources().getColor(R.color.gray_49))
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .isRestoreItem(true)
                .build();

        ProvincesPV.setPicker(provincesEntity1, provincesEntity2, provincesEntity3);//三级选择器
    }

    //初始化意向三级联动数据
   /* private void initIntentionData() {
        for (int i = 0; i < intentionEntities1.size(); i++) {
            List<IntentionEntity> intentionEntityList2 = new ArrayList<>();//二级意向
            List<List<IntentionEntity>> intentionEntityList3 = new ArrayList<>();//三级意向
            //在第一项添加空意向，如果选择“请选择”则代表此级意向为空
            intentionEntityList2.add(new IntentionEntity("请选择"));
            //如果无意向，添加空对象，防止数据为null 导致三个选项长度不匹配造成崩溃
            if (intentionEntities1.get(i).getChildren().size() == 0) {
                intentionEntityList3.add(intentionEntityList2);
            }
            for (int j = 0; j < intentionEntities1.get(i).getChildren().size(); j++) {
                //添加二级意向
                intentionEntityList2.add(intentionEntities1.get(i).getChildren().get(j));

                //如果二级意向循环第一次，这为三级意向添加一个空对象，对应二级意向的“请选择”
                if (j == 0) {
                    List<IntentionEntity> IList = new ArrayList<>();
                    IList.add(new IntentionEntity("请选择"));
                    intentionEntityList3.add(IList);
                }

                //添加三级意向
                List<IntentionEntity> IList3 = new ArrayList<>();
                IList3.add(new IntentionEntity("请选择"));
                if (intentionEntities1.get(i).getChildren().get(j).getChildren() != null || intentionEntities1.get(i).getChildren().get(j).getChildren().size() != 0) {
                    IList3.addAll(intentionEntities1.get(i).getChildren().get(j).getChildren());
                }

                intentionEntityList3.add(IList3);
            }
            intentionEntities2.add(intentionEntityList2);
            intentionEntities3.add(intentionEntityList3);
        }

        //初始化意向选择器
        IntentionPV = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {//选择项
                IntentionAddEntity intentionAddEntity = new IntentionAddEntity();
                String intentionValue = "";
                if (!TextUtils.isEmpty(intentionEntities1.get(options1).getPbtid())) {
                    intentionAddEntity.setItemFirst(intentionEntities1.get(options1).getPbtid());
                    intentionValue = intentionValue + intentionEntities1.get(options1).getPbtname();
                }
                if (!TextUtils.isEmpty(intentionEntities2.get(options1).get(options2).getPbtid())) {
                    intentionAddEntity.setItemSecond(intentionEntities2.get(options1).get(options2).getPbtid());
                    intentionValue = intentionValue + "/" + intentionEntities2.get(options1).get(options2).getPbtname();
                }
                if (!TextUtils.isEmpty(intentionEntities3.get(options1).get(options2).get(options3).getPbtid())) {
                    intentionAddEntity.setItemThird(intentionEntities3.get(options1).get(options2).get(options3).getPbtid());
                    intentionValue = intentionValue + "/" + intentionEntities3.get(options1).get(options2).get(options3).getPbtname();
                }
                intentionAddEntity.setIntentionStr(intentionValue);
                intentionEntitiesList.add(intentionAddEntity);
                IntentionListAdapter.notifyDataSetChanged();
            }
        })
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))
                .setCancelColor(getResources().getColor(R.color.gray_49))
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .isRestoreItem(true)
                .build();
        IntentionPV.setPicker(intentionEntities1, intentionEntities2, intentionEntities3);//设置数据
    }*/

}