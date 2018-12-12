package com.mylike.his.fragment.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mylike.his.R;
import com.mylike.his.activity.CustomerDetailsActivity;
import com.mylike.his.activity.consultant.DepositHospitalActivity;
import com.mylike.his.activity.consultant.OrderActivity;
import com.mylike.his.activity.consultant.ProductActivity;
import com.mylike.his.activity.consultant.StoredValueActivity;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.IntentionAddEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.entity.ReceptionEntity;
import com.mylike.his.entity.ReceptionInfoEntity;
import com.mylike.his.entity.ReceptionTypeEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.utils.ViewUtil;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by zhengluping on 2018/1/2.
 * 销售
 */
public class SalesFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    Unbinder unbinder;
    @BindView(R.id.search_edit)
    ClearEditText searchEdit;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.tag_ll)
    LinearLayout tagLl;
    @BindView(R.id.reception_list)
    ListView receptionList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    private View view;

    private int pageSize = 10;//每页数据
    private int pageNumber = 1;//页码
    private String DateLvel = "";//底部标识
    private String EndCreatetime;//最后时间（后台需要）
    private String EndCreatetimeQ;//最后时间（后台需要）
    private String departmentId;//科室id

    //接诊数据
    private TextView textView;//页脚
    private CommonAdapter commonAdapter;
    private List<ReceptionInfoEntity> listAll = new ArrayList<>();

    /*//筛选数据
    private TagAdapter tagAdapter;
    private CommonAdapter commonAdapter1;
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
    Map<String, Set<String>> selectedValue = new HashMap<String, Set<String>>();
    Map<Integer, Set<Integer>> selectedMap = new HashMap<Integer, Set<Integer>>();

    //意向/项目
    Map<String, String> yx = new HashMap<>();
    Map<String, String> cp = new HashMap<>();*/


    public static SalesFragment newInstance() {
        Bundle args = new Bundle();
        SalesFragment fragment = new SalesFragment();
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
        view = inflater.inflate(R.layout.fragment_sales, null);
        unbinder = ButterKnife.bind(this, view);

        initView();
        pageNumber = 1;
        DateLvel = "";

        listAll.clear();
        initData();

        return view;
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableAutoLoadMore(false);

        //初始化适配器
        initAdapter1();
        //initAdapter2();

        //时间初始化
        /*startDate.set(1900, 0, 1);
        endDate.set(2200, 11, 31);*/

        //时间器初始化
        /*initTimeView1();
        initTimeView2();*/

        //意向选择器初始化
        //initIntentionView();

    }

    private void initData() {
        //初始化意向/项目数据，后台硬性要求一定要有字段
        /*yx.put("yxitemFirst", "");
        yx.put("yxitemSecond", "");
        yx.put("yxitemThird", "");
        cp.put("cpitemFirst", "");
        cp.put("cpitemSecond", "");
        cp.put("cpitemThird", "");*/

        /*getReceptionType();
        getIntentionData();*/

        getReceptionData();
    }

    //接诊列表适配器
    private void initAdapter1() {
        commonAdapter = new CommonAdapter<ReceptionInfoEntity>(getActivity(), R.layout.item_reception_has_list, listAll) {
            @Override
            protected void convert(ViewHolder viewHolder, final ReceptionInfoEntity item, int position) {
                viewHolder.setText(R.id.user_info_text, item.getCFNAME() + "  (" + item.getCFHANDSET() + ")");
                //性别
                if (TextUtils.isEmpty(item.getCFSEX())) {
                    viewHolder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.girl_c_icon));
                } else if (item.getCFSEX().equals("1")) {
                    viewHolder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.boy_c_icon));
                } else {
                    viewHolder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.girl_c_icon));
                }
//                if (TextUtils.isEmpty(item.getCFSEX())) {
//
//                    if (item.getCFSEX().equals("0")) {
//                        viewHolder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.girl_c_icon));
//                    } else {
//                        viewHolder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.boy_c_icon));
//                    }
//                } else {
//                    viewHolder.setImageDrawable(R.id.sex_img, getResources().getDrawable(R.mipmap.girl_c_icon));
//                }
                //级别（ 0：VIP ，1：FIP）
                if (item.getIsVip().equals("1"))
                    viewHolder.setVisible(R.id.vip_img, true);
                else
                    viewHolder.setVisible(R.id.vip_img, false);

                if (item.getIsEmphasis().equals("1"))
                    viewHolder.setVisible(R.id.fip_img, true);
                else
                    viewHolder.setVisible(R.id.fip_img, false);
                //卡
                if (TextUtils.isEmpty(item.getKJB())) {
                    viewHolder.setVisible(R.id.card_tv, false);
                } else {
                    viewHolder.setVisible(R.id.card_tv, true);
                    viewHolder.setText(R.id.card_tv, item.getKJB());
                }
                //活跃度
                if (TextUtils.isEmpty(item.getHYD())) {
                    viewHolder.setVisible(R.id.liveness_text, false);
                } else {
                    viewHolder.setVisible(R.id.liveness_text, true);
                    viewHolder.setText(R.id.liveness_text, item.getHYD());
                }
                //来院次数
                viewHolder.setText(R.id.number, item.getLYPC());
                //消费金额
                viewHolder.setText(R.id.money_text, CommonUtil.setTwoNumber(item.getXFZJ()));
                //星级
                RatingBar star = viewHolder.getView(R.id.star);
                star.setRating(Integer.parseInt((item.getXJ() != null ? item.getXJ() : "0")));
                //重咨或跨科
                if (item.getREGISTERTYPE() == null || TextUtils.isEmpty(item.getREGISTERTYPE())) {
                    viewHolder.setText(R.id.state_text, "");
                } else if (item.getREGISTERTYPE().equals("1")) {//重咨
                    viewHolder.setText(R.id.state_text, "重咨");
                } else if (item.getREGISTERTYPE().equals("2")) {//跨科
                    viewHolder.setText(R.id.state_text, "跨科");
                } else {//正常开单
                    viewHolder.setText(R.id.state_text, "");
                }
                //是否显示消费按钮（条件：今天和有科室的分诊单显示消费按钮）
                if ("1".equals(item.getCanBillingFlag())) {//显示消费
                    viewHolder.setVisible(R.id.consumption_btn, true);
                } else {//隐藏消费
                    viewHolder.setVisible(R.id.consumption_btn, false);
                }
                //时间"2018-10-08"

                viewHolder.setText(R.id.time_text, item.getCREATE_DATE().substring(0, 16));
                //接诊状态
                viewHolder.setText(R.id.reception_state_text, item.getKHSTATE());

                //科室医生
                if (TextUtils.isEmpty(item.getYSNAME())) {//只科室
                    viewHolder.setText(R.id.department_text, item.getDOCTOR_DEPARTMENT());
                } else {//显示科室和医生
                    viewHolder.setText(R.id.department_text, item.getDOCTOR_DEPARTMENT() + " - " + item.getYSNAME());
                }

                //客户详情
                viewHolder.setOnClickListener(R.id.user_info_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(CustomerDetailsActivity.class, "clientId", item.getCUSTID());
                    }
                });

                //消费
                viewHolder.setOnClickListener(R.id.consumption_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        departmentId = item.getDepartmentId();
                        getSaveData(item.getFID(), item.getCUSTID());

                    }
                });

                //储值
                viewHolder.setOnClickListener(R.id.stored_value_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(StoredValueActivity.class, "clientId", item.getCUSTID());
                    }
                });

                //住院押金
                viewHolder.setOnClickListener(R.id.hospital_deposit_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(DepositHospitalActivity.class, "clientId", item.getCUSTID());
                    }
                });

                //添加问诊
                viewHolder.setOnClickListener(R.id.add_inquiry_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("tag", "inquiry");
                        bundle.putString("clientId", item.getCUSTID());
                        bundle.putString("fid", item.getFID());
                        startActivity(CustomerDetailsActivity.class, bundle);
                    }
                });
            }
        };
        //底部
        View view = View.inflate(getActivity(), R.layout.common_item_text, null);
        textView = view.findViewById(R.id.text);
        textView.setPadding(10, 30, 10, 30);
        receptionList.addFooterView(view);
        receptionList.setAdapter(commonAdapter);
    }

//    //筛选适配器
//    private void initAdapter2() {
//        final float scale = this.getResources().getDisplayMetrics().density;
//        commonAdapter1 = new CommonAdapter<ReceptionTypeEntity>(NewCReceptionActivity.this, R.layout.item_filtrate_product_list, receptionTypeEntitieList) {
//            @Override
//            protected void convert(final ViewHolder viewHolder, final ReceptionTypeEntity item, int position) {
//                viewHolder.setText(R.id.cover_name, item.getName());
//                TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
//                tagAdapter = new TagAdapter(item.getList()) {
//                    @Override
//                    public View getView(FlowLayout parent, int position, Object o) {
//                        TextView textView = (TextView) LayoutInflater.from(NewCReceptionActivity.this).inflate(R.layout.item_text_label, null);
//                        textView.setTextSize(12);
//                        textView.setWidth((filtrateMenu.getWidth() / 3) - (int) (10 * scale + 0.5f));
//                        textView.setPadding(0, 30, 0, 30);
//                        textView.setGravity(Gravity.CENTER);
//
//                        textView.setText(item.getList().get(position).getName());
//                        return textView;
//                    }
//                };
//                tagFlowLayout.setAdapter(tagAdapter);
//
//                tagAdapter.setSelectedList(selectedMap.get(viewHolder.getItemPosition()));
//                tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
//                    @Override
//                    public void onSelected(Set<Integer> selectPosSet) {
//                        selectedMap.put(viewHolder.getItemPosition(), selectPosSet);
//                        Set<String> strings = new HashSet<>();
//                        for (int i : selectPosSet) {
//                            strings.add(item.getList().get(i).getId());
//                        }
//                        selectedValue.put(item.getId(), strings);
//                    }
//                });
//            }
//        };
//        filtrateList.setAdapter(commonAdapter1);
//    }
//
//    //开始时间选择器
//    private void initTimeView1() {
//        TimePV1 = new TimePickerBuilder(this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {
//                startTimeText.setText(CommonUtil.getYMD(date));
//            }
//        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
//                .setSubCalSize(14)//确认取消文字大小
//                .setContentTextSize(14)//滚轮文字大小
//                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
//                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
//                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
//                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setDate(today)//默认数据
//                .build();
//    }
//
//    //结束时间选择器
//    private void initTimeView2() {
//        TimePV2 = new TimePickerBuilder(this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {
//                endTimeText.setText(CommonUtil.getYMD(date));
//            }
//        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
//                .setSubCalSize(14)//确认取消文字大小
//                .setContentTextSize(14)//滚轮文字大小
//                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
//                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
//                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
//                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setDate(today)//默认数据
//                .build();
//    }
//
//    //意向选择器
//    private void initIntentionView() {
//        viewUtil1.setIntentionListener(new ViewUtil.OnIntentionListener() {
//            @Override
//            public void onOptionsSelect(IntentionAddEntity intentionAddEntity) {
//                yxText.setText(intentionAddEntity.getIntentionStr());
//                yx.put("yxitemFirst", intentionAddEntity.getItemFirst());
//                yx.put("yxitemSecond", intentionAddEntity.getItemSecond());
//                yx.put("yxitemThird", intentionAddEntity.getItemThird());
//            }
//        });
//
//        viewUtil2.setIntentionListener(new ViewUtil.OnIntentionListener() {
//            @Override
//            public void onOptionsSelect(IntentionAddEntity intentionAddEntity) {
//                cpText.setText(intentionAddEntity.getIntentionStr());
//                cp.put("cpitemFirst", intentionAddEntity.getItemFirst());
//                cp.put("cpitemSecond", intentionAddEntity.getItemSecond());
//                cp.put("cpitemThird", intentionAddEntity.getItemThird());
//            }
//        });
//    }

    //获取接诊列表数据
    private void getReceptionData() {
        HashMap<String, Object> paramQery = new HashMap<>();
        paramQery.put("custNameOrPhone", searchEdit.getText().toString());//名字
        /*paramQery.put("startlypc", frequencyLow.getText().toString());//最低来院频次
        paramQery.put("endlypc", frequencyHigh.getText().toString());//最高来院频次
        paramQery.put("startxfzj", moneyLow.getText().toString());//最低消费金额
        paramQery.put("endxfzj", moneyHigh.getText().toString());//最高消费金额
        paramQery.put("startTime", startTimeText.getText().toString());//开始时间
        paramQery.put("endTime", endTimeText.getText().toString());//结束时间
        paramQery.put("yx", yx);//意向
        paramQery.put("cp", cp);//产品
        paramQery.putAll(selectedValue);//多选项类型*/

        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        map.put("DateLvel", DateLvel);
        map.put("EndCreatetime", EndCreatetime);
        map.put("EndCreatetimeQ", EndCreatetimeQ);
        map.put("paramQery", paramQery);

        HttpClient.getHttpApi().getHasReception(HttpClient.getRequestBody(map)).enqueue(new BaseBack<ReceptionEntity>() {
            @Override
            protected void onSuccess(ReceptionEntity receptionEntity) {
                try {
                    EndCreatetime = receptionEntity.getEndCreatetime();
                    EndCreatetimeQ = receptionEntity.getEndCreatetimeQ();

                    if (TextUtils.isEmpty(DateLvel))//后台需要空数据，如果空数据赋值为1；
                        DateLvel = "1";

                    if (pageNumber == 1) {
                        if (textView.getVisibility() != View.VISIBLE) {
                            listAll.clear();
                        }
                    }
                    //判断是否显示页脚
                    if (receptionEntity.getNextLevel().equals(DateLvel)) {
                        textView.setVisibility(View.GONE);
                    } else {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(receptionEntity.getNextLevelText());
                    }
                    DateLvel = receptionEntity.getNextLevel();

                    if (DateLvel.equals("0")) {
                        refreshLayout.setNoMoreData(true);
                    }

                    listAll.addAll(receptionEntity.getList());
                    commonAdapter.notifyDataSetChanged();
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                } catch (Exception e) {
                }
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });
    }

    //获取筛选数据
//    private void getReceptionType() {
//        HttpClient.getHttpApi().getReceptionType().enqueue(new BaseBack<List<ReceptionTypeEntity>>() {
//            @Override
//            protected void onSuccess(List<ReceptionTypeEntity> receptionTypeEntities) {
//                receptionTypeEntitieList.addAll(receptionTypeEntities);
//                for (ReceptionTypeEntity receptionTypeEntity : receptionTypeEntitieList) {
//                    selectedValue.put(receptionTypeEntity.getId(), new HashSet<String>());
//                }
//                commonAdapter1.notifyDataSetChanged();
//
//                getReceptionData();//需要先执行完筛选循环才能获取接诊列表数据，不然字段为空后台会报异常
//            }
//
//            @Override
//            protected void onFailed(String code, String msg) {
//
//            }
//        });
//    }
//
//    //获取意向数据
//    private void getIntentionData() {
//        HttpClient.getHttpApi().getIntentionAll().enqueue(new BaseBack<List<IntentionEntity>>() {
//            @Override
//            protected void onSuccess(List<IntentionEntity> intentionEntities) {
//                IntentionPV1 = viewUtil1.initIntention(NewCReceptionActivity.this, IntentionPV1, intentionEntities);
//                IntentionPV2 = viewUtil2.initIntention(NewCReceptionActivity.this, IntentionPV2, intentionEntities);
//            }
//
//            @Override
//            protected void onFailed(String code, String msg) {
//            }
//        });
//    }

    //获取是否有暂存
    private void getSaveData(final String Triageid, final String Custid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Triageid", Triageid);

        HttpClient.getHttpApi().getSave(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                SPUtils.setCache(SPUtils.FILE_PASS, SPUtils.RECEPTION_ID, Triageid);
                SPUtils.setCache(SPUtils.FILE_PASS, SPUtils.CLIENT_ID, Custid);
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

    //R.id.filtrate_btn, R.id.reset_btn, R.id.confirm_btn, R.id.start_time_btn, R.id.end_time_btn, R.id.yx_text, R.id.cp_text, R.id.clear_text1, R.id.clear_text2, R.id.clear_text3
    @OnClick({R.id.search_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                DateLvel = "";
                pageNumber = 1;
                EndCreatetimeQ = "";
//                listAll.clear();
                getReceptionData();
                break;
            /*case R.id.return_btn:
                finish();
                break;*/
//            case R.id.reset_btn://筛选重置
//                selectedMap.clear();
//                for (String str : selectedValue.keySet()) {
//                    selectedValue.put(str, new HashSet<String>());
//                }
//                yxText.setText("");
//                cpText.setText("");
//                startTimeText.setText("");
//                endTimeText.setText("");
//                /*frequencyLow.setText("");
//                frequencyHigh.setText("");
//                moneyLow.setText("");
//                moneyHigh.setText("");*/
//                commonAdapter1.notifyDataSetChanged();
//                break;
//            case R.id.confirm_btn://筛选确认
//                DrawerLayout.closeDrawer(filtrateMenu);
//                pageNumber = 1;
//                refreshLayout.setNoMoreData(false);
//                getReceptionData();
//                break;
//            case R.id.filtrate_btn://接诊筛选
//                DrawerLayout.openDrawer(filtrateMenu);
//                break;
//            case R.id.start_time_btn://开始时间
//                TimePV1.show();
//                break;
//            case R.id.end_time_btn://结束时间
//                TimePV2.show();
//                break;
//            case R.id.yx_text:
//                IntentionPV1.show();
//                break;
//            case R.id.cp_text:
//                IntentionPV2.show();
//                break;
//            case R.id.clear_text1://清除时间
//                startTimeText.setText("");
//                endTimeText.setText("");
//                break;
//            case R.id.clear_text2://清除意向
//                for (String str : yx.keySet()) {
//                    yx.put(str, "");
//                }
//                yxText.setText("");
//                break;
//            case R.id.clear_text3://清除项目
//                for (String str : cp.keySet()) {
//                    cp.put(str, "");
//                }
//                cpText.setText("");
//                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNumber = 1;
        DateLvel = "";
//        listAll.clear();
        refreshLayout.setNoMoreData(false);
        getReceptionData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNumber = pageNumber + 1;
        if (textView.getVisibility() == View.VISIBLE) {
            pageNumber = 1;
        }
        getReceptionData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
