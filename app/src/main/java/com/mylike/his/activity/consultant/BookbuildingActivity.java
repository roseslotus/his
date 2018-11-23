package com.mylike.his.activity.consultant;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.Constant;
import com.mylike.his.entity.BookbuildingEntity;
import com.mylike.his.entity.CreatorEntity;
import com.mylike.his.entity.IntentionAddEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.entity.ReferrerEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.utils.ViewUtil;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/7/18.
 * 建档
 */
public class BookbuildingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.table1)
    TableLayout table1;
    @BindView(R.id.ll_tag1)
    LinearLayout llTag1;
    @BindView(R.id.table2)
    TableLayout table2;
    @BindView(R.id.ll_tag2)
    LinearLayout llTag2;
    @BindView(R.id.ll_tag3)
    LinearLayout llTag3;
    @BindView(R.id.ll_tag4)
    LinearLayout llTag4;
    @BindView(R.id.ll_tag5)
    LinearLayout llTag5;
    @BindView(R.id.ll_tag6)
    LinearLayout llTag6;

    @BindView(R.id.table3)
    TableLayout table3;
    @BindView(R.id.table4)
    TableLayout table4;
    @BindView(R.id.table5)
    TableLayout table5;
    @BindView(R.id.table6)
    TableLayout table6;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.other_btn)
    TextView otherBtn;
    @BindView(R.id.contact_btn)
    TextView contactBtn;
    @BindView(R.id.appointment_btn)
    TextView appointmentBtn;
    @BindView(R.id.bookbuilding_btn)
    TextView bookbuildingBtn;
    @BindView(R.id.Economics_spr)
    Spinner EconomicsSpr;
    @BindView(R.id.Intention_spr)
    Spinner IntentionSpr;
    @BindView(R.id.marriage_spr)
    Spinner marriageSpr;
    @BindView(R.id.Occupation_spr)
    Spinner OccupationSpr;
    @BindView(R.id.Provinces_btn)
    EditText ProvincesBtn;
    @BindView(R.id.birth_date_btn)
    EditText birthDateBtn;
    @BindView(R.id.medium_btn)
    EditText mediumBtn;
    @BindView(R.id.channel_btn)
    EditText channelBtn;
    @BindView(R.id.flowlayout1)
    TagFlowLayout flowlayout1;
    @BindView(R.id.flowlayout2)
    TagFlowLayout flowlayout2;
    @BindView(R.id.appointment_time_btn)
    EditText appointmentTimeBtn;
    @BindView(R.id.add_btn)
    TextView addBtn;
    @BindView(R.id.intention_list)
    ListView intentionList;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.creation_time)
    EditText creationTime;
    @BindView(R.id.name_edit)
    EditText nameEdit;
    @BindView(R.id.man)
    RadioButton man;
    @BindView(R.id.girl)
    RadioButton girl;
    @BindView(R.id.sex)
    RadioGroup sex;
    @BindView(R.id.age_edit)
    EditText ageEdit;
    @BindView(R.id.address_details_edit)
    EditText addressDetailsEdit;
    @BindView(R.id.save_btn)
    TextView saveBtn;
    @BindView(R.id.custFeedback_edit)
    EditText custFeedbackEdit;
    @BindView(R.id.custSuggest_edit)
    EditText custSuggestEdit;
    @BindView(R.id.custBudget_edit)
    EditText custBudgetEdit;
    @BindView(R.id.processor_price_edit)
    EditText processorPriceEdit;
    @BindView(R.id.cfremark_edit)
    EditText cfremarkEdit;
    @BindView(R.id.qq_edit)
    EditText qqEdit;
    @BindView(R.id.wechat_edit)
    EditText wechatEdit;
    @BindView(R.id.other_contact_edit)
    EditText otherContactEdit;
    @BindView(R.id.yes)
    RadioButton yes;
    @BindView(R.id.no)
    RadioButton no;
    @BindView(R.id.big)
    RadioGroup big;
    @BindView(R.id.mylike_id)
    EditText mylikeId;
    @BindView(R.id.visitor_id_layout)
    TableRow visitorIdLayout;
    @BindView(R.id.referrer_layou)
    TableRow referrerLayou;
    @BindView(R.id.referrer_edit)
    EditText referrerEdit;
    @BindView(R.id.creator_layou)
    TableRow creatorLayou;
    @BindView(R.id.creator_edit)
    AutoCompleteTextView creatorEdit;
    @BindView(R.id.channel_text)
    EditText channelText;
    @BindView(R.id.user_layou)
    TableRow userLayou;

    private ViewUtil viewUtil = new ViewUtil();

    private String tag;//建档标识
    private boolean inputTag = true;//输入标识（避免推荐人点击弹框内容赋值给编辑框重新搜索）
    private String creatorValue;//建档人
    private String referrerVlaue;//推荐人
    private String phoneValue;//手机号
    private String timeValue;//当前时间（建档时间）
    private String channelValue;//一级渠道
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

    //建档人
    private SimpleAdapter simpleAdapter;
    private List<Map<String, String>> creatorEntities = new ArrayList<>();
    //推荐人
    private ListPopupWindow listPopupWindow;
    private CommonAdapter referrerAdapter;
    private List<ReferrerEntity> referrerEntities = new ArrayList<>();


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
        tag = getIntent().getStringExtra("tag");
        phoneValue = getIntent().getStringExtra("phone");
        timeValue = getIntent().getStringExtra("time");
        channelValue = getIntent().getStringExtra("tagText");

        switch (tag) {
            case Constant.B_SS:
                visitorIdLayout.setVisibility(View.VISIBLE);
                break;
            case Constant.B_SJ:
                break;
            case Constant.B_DS:
                break;
            case Constant.B_XXHZ:
                break;
            case Constant.B_CMMT:
                break;
            case Constant.B_YGTJ:
                creatorLayou.setVisibility(View.VISIBLE);
                userLayou.setVisibility(View.GONE);
                setCreatorAdapter();
                getCreatorData();
                break;
            case Constant.B_LDX:
                referrerLayou.setVisibility(View.VISIBLE);
                setReferrer();
                break;
        }

        phoneEdit.setText(phoneValue);
        creationTime.setText(timeValue);
        channelText.setText(channelValue);
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
        map.put("channelFirst", tag);

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

    //获取建档人数据
    private void getCreatorData() {
        HttpClient.getHttpApi().getCreator().enqueue(new BaseBack<CreatorEntity>() {
            @Override
            protected void onSuccess(CreatorEntity creatorEntity) {
                creatorEntities.addAll(creatorEntity.getList());
                simpleAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //获取推荐人数据
    private void getReferrerData(String value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("keyWords", value);//关键字

        HttpClient.getHttpApi().getReferrer(HttpClient.getRequestBody(map)).enqueue(new BaseBack<ReferrerEntity>() {
            @Override
            protected void onSuccess(ReferrerEntity referrerEntity) {
                referrerEntities.clear();
                referrerEntities.addAll(referrerEntity.getList());
                if (referrerEntities.isEmpty()) {
                    listPopupWindow.dismiss();
                } else {
                    listPopupWindow.show();
                }
                referrerAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //保存数据
    private void saveData() {
        Map<String, String> tempCustInfo = new HashMap<>();
        if (Constant.B_SS.equals(tag))//搜索建档
            tempCustInfo.put("visitorImportId", mylikeId.getText().toString());//美莱在线访客ID

        if (Constant.B_LDX.equals(tag))//老带新
            tempCustInfo.put("frecpersonid", referrerVlaue);//推荐人id

        if (Constant.B_YGTJ.equals(tag))//员工推荐
            tempCustInfo.put("buildBy", creatorValue);//建档人id
        else
            tempCustInfo.put("buildBy", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.EMP_ID));//建档人id

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
        tempCustInfo.put("channelFirst", tag);//一级渠道
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
        if (!TextUtils.isEmpty(appointmentTimeBtn.getText().toString()))
            tempCustInfo.put("appointTime", appointmentTimeBtn.getText().toString() + ":00");//预约时间
        tempCustInfo.put("fisbulksale", bigValue);//是否大单
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
                View v = DialogUtil.hintDialog(this, "数据未保存，是否确认退出？");
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
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
        if (TextUtils.isEmpty(nameEdit.getText().toString())) {
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
        } else if (TextUtils.isEmpty(intentionDegree)) {
            CommonUtil.showToast("保存失败，请选择意向度");
        } else if (intentionEntitiesList.isEmpty()) {
            CommonUtil.showToast("保存失败，请添加意向项目");
        } else {
            if (Constant.B_SS.equals(tag) && TextUtils.isEmpty(mylikeId.getText().toString())) {
                CommonUtil.showToast("保存失败，请输入美莱在线访客ID");
            } else if (Constant.B_YGTJ.equals(tag) && creatorValue.isEmpty()) {
                CommonUtil.showToast("保存失败，请输入建档人，建档人需输入后点选生效");
            } else if (Constant.B_LDX.equals(tag) && referrerVlaue.isEmpty()) {
                CommonUtil.showToast("保存失败，请输入推荐人，推荐人需输入后点选生效");
            } else {
                saveData();
            }
        }


        /*else if (TextUtils.isEmpty(addressDetailsEdit.getText().toString())) {
            CommonUtil.showToast("保存失败，输入详细地址");
        }*/
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
                textView.setPadding(20, 30, 20, 30);
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

    //设置建档人下拉框
    private void setCreatorAdapter() {
        simpleAdapter = new SimpleAdapter(this, creatorEntities, R.layout.item_search_auto, new String[]{"empname", "empid"}, new int[]{R.id.name_text, R.id.id_text});
        creatorEdit.setAdapter(simpleAdapter);

        creatorEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //输入框有改变清空存储的值
                creatorValue = "";
            }
        });
        creatorEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatorEdit.showDropDown();//点击输入框显示下拉框
            }
        });
        creatorEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView nameText = view.findViewById(R.id.name_text);
                String nameStr = nameText.getText().toString();
                TextView idText = view.findViewById(R.id.id_text);
                CommonUtil.hideKeyboard(BookbuildingActivity.this);

                creatorEdit.setText(nameStr);
                creatorEdit.setSelection(nameStr.length());//将光标移至文字末尾
                creatorValue = idText.getText().toString();
            }
        });
    }

    //设置推荐人下拉框
    private void setReferrer() {
        referrerEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (inputTag) {
                    referrerVlaue = "";
                    if (editable.toString().length() >= 2) {
                        getReferrerData(editable.toString());
                    }
                } else {
                    inputTag = true;
                }
            }
        });

        referrerAdapter = new CommonAdapter<ReferrerEntity>(this, R.layout.common_item_text, referrerEntities) {
            @Override
            protected void convert(ViewHolder viewHolder, ReferrerEntity item, int position) {
                TextView textView = viewHolder.getView(R.id.text);
                textView.setGravity(Gravity.RIGHT);
                textView.setPadding(20, 30, 20, 30);
                viewHolder.setText(R.id.text, item.getCfname());
            }
        };

        listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(referrerAdapter);
        listPopupWindow.setAnchorView(referrerEdit);
        listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                inputTag = false;
                referrerEdit.setText(referrerEntities.get(i).getNamePhone());
                referrerEdit.setSelection(referrerEdit.getText().toString().length());
                referrerVlaue = referrerEntities.get(i).getId();
                CommonUtil.hideKeyboard(BookbuildingActivity.this);
                listPopupWindow.dismiss();
            }
        });
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
                .setSubCalSize(14)//确认取消文字大小
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

    @Override
    public void onBackPressed() {
        View v = DialogUtil.hintDialog(this, "数据未保存，是否确认退出？");
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}