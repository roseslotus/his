package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mylike.his.R;
import com.mylike.his.activity.CustomerDetailsActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.BasePageEntity;
import com.mylike.his.entity.ChargeFiltrateEntity;
import com.mylike.his.entity.ChargeInfoEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/31.
 * 收费单列表
 */
public class ChargeShowActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.charge_list)
    ListView chargeList;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.filtrate_btn)
    ImageView filtrateBtn;
    @BindView(R.id.search_edit)
    ClearEditText searchEdit;
    @BindView(R.id.search_btn)
    Button searchBtn;

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

    private OptionsPickerView optionsPickerView;

    private int sumPage = 1;//总页数
    private int pageSize = 10;//每页数据
    private int pageNumber = 1;//页码
    private CommonAdapter commonAdapter;
    private List<ChargeInfoEntity> listAll = new ArrayList<>();

    private List<IntentionEntity> intentionEntities1 = new ArrayList<>();
    private List<List<IntentionEntity>> intentionEntities2 = new ArrayList<>();
    private List<List<List<IntentionEntity>>> intentionEntities3 = new ArrayList<>();

    private TagAdapter tagAdapter;
    private List<ChargeFiltrateEntity> chargeFiltrateEntityList = new ArrayList<>();

    private String[] Intention;
    private String fidValue;
    private String remarkValue;
    private boolean tag = true;
    private CommonAdapter commonAdapter1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_list);
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(getIntent().getStringExtra(CMainActivity.GO_OA))) {
            startActivity(OAActivity.class, "fid", getIntent().getStringExtra(CMainActivity.GO_OA));
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra(CMainActivity.GO_PAYMENT))) {
            Intent intent = new Intent();
            intent.putExtra("fid", getIntent().getStringExtra(CMainActivity.GO_PAYMENT));
            intent.putExtra("money", getIntent().getStringExtra("money"));
            intent.setClass(ChargeShowActivity.this, PaymentActivity.class);
            startActivity(intent);
        }

        initView();
        initChargeFiltrate();
        initData();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        //列表适配器
        commonAdapter = new CommonAdapter<ChargeInfoEntity>(ChargeShowActivity.this, R.layout.item_charge_details_list, listAll) {
            @Override
            protected void convert(ViewHolder viewHolder, final ChargeInfoEntity item, int position) {
//                viewHolder.setText(R.id.user_info_text, item.getCFNAME() + "   " + item.getCFHANDSET());//姓名+手机
                viewHolder.setText(R.id.name_text, item.getCFNAME());//姓名
                viewHolder.setText(R.id.phone_text, "(" + item.getCFHANDSET() + ")");//手机号
                viewHolder.setText(R.id.state_text, item.getFCHARGESTATE());//收费单状态
                viewHolder.setText(R.id.type_text, item.getFCHARGETYPE());//收费单类型
                viewHolder.setText(R.id.time_text, item.getFCREATETIME());//时间
                viewHolder.setText(R.id.money_text, setDecimalFormat(item.getCFFEEALL()));//金额
                viewHolder.setText(R.id.count_text, "共" + item.getProjectList().size() + "件商品");//商品件数
                if (TextUtils.isEmpty(item.getDOCTORNAME())) {//医生为空
                    viewHolder.setText(R.id.department_text, item.getDEPTNAME());//科室
                } else {
                    viewHolder.setText(R.id.department_text, item.getDEPTNAME() + " - " + item.getDOCTORNAME());//科室跟医生
                }

                //初始化按钮
                viewHolder.setVisible(R.id.btn_ll, false);//按钮布局
                viewHolder.setVisible(R.id.again_consult_btn, false);//重咨
                viewHolder.setVisible(R.id.bridge_section_btn, false);//跨科
                viewHolder.setVisible(R.id.compile_btn, false);//编辑
                viewHolder.setVisible(R.id.payment_btn, false);//去支付
                viewHolder.setVisible(R.id.oa_btn, false);//OA申请
//                viewHolder.setVisible(R.id.application_drawback_btn, false);//申请退款
                viewHolder.setVisible(R.id.count_text, true);//商品数


                //按钮显示判断
                switch (item.getFCHARGESTATENUMBER()) {
                    case "1"://暂存
                        viewHolder.setVisible(R.id.btn_ll, true);//按钮布局
                        viewHolder.setVisible(R.id.compile_btn, true);//编辑
                        break;
                    case "3"://已结账
                        if ("1".equals(item.getFCHARGETYPENUMBER()) || "2".equals(item.getFCHARGETYPENUMBER())) {//1-消费,2-预约金
                            viewHolder.setVisible(R.id.btn_ll, true);//按钮布局
                            if (item.getISTODAY().equals("0")) {
                                viewHolder.setVisible(R.id.again_consult_btn, true);//重咨
                            }
                            viewHolder.setVisible(R.id.bridge_section_btn, true);//跨科
                        } else if ("3".equals(item.getFCHARGETYPENUMBER()) || "5".equals(item.getFCHARGETYPENUMBER())) {//3-储值,5-预约金
                            viewHolder.setVisible(R.id.count_text, false);//商品数
                        }
                        break;
                    case "7"://待OA申请
                        viewHolder.setVisible(R.id.btn_ll, true);//按钮布局
                        viewHolder.setVisible(R.id.oa_btn, true);//OA申请
                        break;
                    /*case "11"://待扫码支付
                    viewHolder.setVisible(R.id.btn_ll, true);//按钮布局
                        viewHolder.setVisible(R.id.payment_btn, true);//去支付
                        break;*/
                    case "2"://待支付
                        viewHolder.setVisible(R.id.btn_ll, true);//按钮布局
                        viewHolder.setVisible(R.id.payment_btn, true);//去支付
                        break;
                    default:
                        if ("3".equals(item.getFCHARGETYPENUMBER()) || "5".equals(item.getFCHARGETYPENUMBER())) {//3-储值,5-预约金
                            viewHolder.setVisible(R.id.count_text, false);//商品数
                        }
                        break;
                }

                //客户详情
                viewHolder.setOnClickListener(R.id.user_info_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(CustomerDetailsActivity.class, "clientId", item.getID());
                    }
                });
                //重咨
                viewHolder.setOnClickListener(R.id.again_consult_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fidValue = item.getFID();
                        optionsPickerView.show();
                    }
                });
                //跨科
                viewHolder.setOnClickListener(R.id.bridge_section_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("fid", item.getFID());
                        startActivity(MedicineActivity.class, bundle);
                    }
                });
                //编辑
                viewHolder.setOnClickListener(R.id.compile_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPUtils.setCache(SPUtils.FILE_RECEPTION, SPUtils.RECEPTION_ID, item.getCFRECEIVEID());
                        startActivity(OrderActivity.class, "chargeTag", "1");
                    }
                });
                //去支付
                viewHolder.setOnClickListener(R.id.payment_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("fid", item.getFID());
                        intent.putExtra("money", item.getCFFEEALL());
                        intent.setClass(ChargeShowActivity.this, PaymentActivity.class);
                        startActivity(intent);
                    }
                });
                //OA申请
                viewHolder.setOnClickListener(R.id.oa_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(OAActivity.class, "fid", item.getFID());
                    }
                });
            }
        };
        chargeList.setAdapter(commonAdapter);
        chargeList.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ChargeDetailsActivity.class, "fid", listAll.get(position).getFID());
            }
        });

        //筛选适配器
        final float scale = this.getResources().getDisplayMetrics().density;
        commonAdapter1 = new CommonAdapter<ChargeFiltrateEntity>(this, R.layout.item_filtrate_product_list, chargeFiltrateEntityList) {
            @Override
            protected void convert(final ViewHolder viewHolder, final ChargeFiltrateEntity item, int position) {
                viewHolder.setText(R.id.cover_name, item.getParamname());
                TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
                tagAdapter = new TagAdapter(item.getList()) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) LayoutInflater.from(ChargeShowActivity.this).inflate(R.layout.item_text_label, null);
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
                        Set<String> strings = new HashSet<>();
                        for (int i : selectPosSet) {
                            strings.add(item.getList().get(i).getId());
                        }
                        selectedValue.put(item.getParamcode(), strings);
                    }
                });
            }
        };
        filtrateList.setAdapter(commonAdapter1);
    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("selectedValue", selectedValue);//筛选
        map.put("condition", searchEdit.getText().toString());//搜索
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);

        //获取收费单
        HttpClient.getHttpApi().getChargeList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<BasePageEntity<ChargeInfoEntity>>() {
            @Override
            protected void onSuccess(BasePageEntity<ChargeInfoEntity> chargeInfoEntityBasePageEntity) {
                sumPage = chargeInfoEntityBasePageEntity.getTotalPages();
                if (sumPage == pageNumber) {
                    refreshLayout.setNoMoreData(true);
                }
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                listAll.addAll(chargeInfoEntityBasePageEntity.getList());
                commonAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });

        //获取所有意向
        if (tag) {//判断是否第一次，只执行一次
            HttpClient.getHttpApi().getIntentionAll().enqueue(new BaseBack<List<IntentionEntity>>() {
                @Override
                protected void onSuccess(List<IntentionEntity> intentionEntities) {
                    tag = false;
                    intentionEntities1.add(new IntentionEntity("请选择"));
                    intentionEntities1.addAll(intentionEntities);
                    //初始化意向数据
                    initViewData();
                }

                @Override
                protected void onFailed(String code, String msg) {

                }
            });
        }
    }

    private void initViewData() {
        for (int i = 0; i < intentionEntities1.size(); i++) {
            List<IntentionEntity> intentionEntityList2 = new ArrayList<>();//二级意向
            List<List<IntentionEntity>> intentionEntityList3 = new ArrayList<>();//三级意向
            //在第一项添加空意向，如果选择“请选择”则代表此级意向为空
            intentionEntityList2.add(new IntentionEntity("请选择"));
            //如果无意向，添加空对象，防止数据为null 导致三个选项长度不匹配造成崩溃

            if (intentionEntities1.get(i).getChildren() == null || intentionEntities1.get(i).getChildren().size() == 0) {
                intentionEntityList3.add(intentionEntityList2);
            } else {
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

            }
            intentionEntities2.add(intentionEntityList2);
            intentionEntities3.add(intentionEntityList3);
        }

        //重咨弹框初始化
        optionsPickerView = new OptionsPickerBuilder(ChargeShowActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {//选择项
                Intention = new String[]{intentionEntities1.get(options1).getPid(), intentionEntities2.get(options1).get(options2).getPid(), intentionEntities3.get(options1).get(options2).get(options3).getPid()};
                submitData();
            }
        }).setLayoutRes(R.layout.dialog_again_consult, new CustomListener() {//自定义布局
            @Override
            public void customLayout(View v) {
                final Button submitBtn = v.findViewById(R.id.submit_btn);
                final EditText remarkEdit = v.findViewById(R.id.remark_edit);

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remarkValue = remarkEdit.getText().toString();
                        optionsPickerView.returnData();
                    }
                });

            }
        }).setContentTextSize(14)
                .setSelectOptions(0, 0, 0)
                .setDividerColor(getResources().getColor(R.color.green_50))
                .setLineSpacingMultiplier((float) 2.5)
                .isDialog(true)
                .build();
        optionsPickerView.setPicker(intentionEntities1, intentionEntities2, intentionEntities3);//设置数据
    }

    //获取收费单筛选
    private void initChargeFiltrate() {
        HttpClient.getHttpApi().getChargeFiltrate().enqueue(new BaseBack<List<ChargeFiltrateEntity>>() {
            @Override
            protected void onSuccess(List<ChargeFiltrateEntity> chargeFiltrateEntities) {
                chargeFiltrateEntityList.addAll(chargeFiltrateEntities);
                commonAdapter1.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    private void submitData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("fid", fidValue);
        map.put("CustomerOpinion", "重咨" + " " + remarkValue);
        map.put("CustomerIntention", Intention);

        //重咨
        HttpClient.getHttpApi().setIntention(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                optionsPickerView.dismiss();
                CommonUtil.showToast("提交成功");
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    Map<Integer, Set<Integer>> selectedMap = new HashMap<Integer, Set<Integer>>();
    Map<String, Set<String>> selectedValue = new HashMap<String, Set<String>>();

    @OnClick({R.id.return_btn, R.id.search_btn, R.id.filtrate_btn, R.id.reset_btn, R.id.confirm_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_btn://返回
                finish();
                break;

            case R.id.search_btn://搜索
                pageNumber = 1;
                listAll.clear();
                refreshLayout.setNoMoreData(false);
                initData();
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
                initData();
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNumber = 1;
        listAll.clear();
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


    private String setDecimalFormat(String numberStr) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (TextUtils.isEmpty(numberStr)) {
            numberStr = "0";
        }
        Double number = Double.parseDouble(numberStr);
        decimalFormat.format(number);
        return decimalFormat.format(number);
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

