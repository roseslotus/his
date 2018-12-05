package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mylike.his.R;
import com.mylike.his.activity.CustomerDetailsActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.ChargeDateilsEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.SListView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/31.
 * 收费单详情
 */
public class ChargeDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.name_text)
    TextView nameText;
    @BindView(R.id.doctor_text)
    TextView doctorText;
    @BindView(R.id.intention_text)
    TextView intentionText;
    @BindView(R.id.project_list)
    SListView projectList;
    @BindView(R.id.discounts_text)
    TextView discountsText;
    @BindView(R.id.integral_text)
    TextView integralText;
    @BindView(R.id.remark_text)
    TextView remarkText;
    @BindView(R.id.status_text)
    TextView statusText;
    @BindView(R.id.again_consult_btn)
    Button againConsultBtn;
    @BindView(R.id.bridge_section_btn)
    Button bridgeSectionBtn;
    @BindView(R.id.application_drawback_btn)
    Button applicationDrawbackBtn;
    @BindView(R.id.compile_btn)
    Button compileBtn;
    @BindView(R.id.payment_btn)
    Button paymentBtn;
    @BindView(R.id.oa_btn)
    Button oaBtn;
    @BindView(R.id.doctor_ll)
    LinearLayout doctorLl;
    @BindView(R.id.intention_ll)
    LinearLayout intentionLl;
    @BindView(R.id.cash_ll)
    LinearLayout cashLl;
    @BindView(R.id.sv_cash)
    TextView svCash;
    @BindView(R.id.sv_present)
    TextView svPresent;
    @BindView(R.id.sv_ll)
    LinearLayout svLl;
    @BindView(R.id.discounts_ll)
    LinearLayout discountsLl;
    @BindView(R.id.integral_ll)
    LinearLayout integralLl;
    @BindView(R.id.name_line)
    View nameLine;
    @BindView(R.id.hospital_money)
    TextView hospitalMoney;
    @BindView(R.id.money_text)
    TextView moneyText;
    @BindView(R.id.money_value)
    TextView moneyValue;
    @BindView(R.id.order_number)
    TextView orderNumber;

    private String fid;
    private CommonAdapter commonAdapter;
    private List<ChargeDateilsEntity.Productlist> productlists = new ArrayList<>();//产品集合

    private OptionsPickerView optionsPickerView;
    private List<IntentionEntity> intentionEntities1 = new ArrayList<>();
    private List<List<IntentionEntity>> intentionEntities2 = new ArrayList<>();
    private List<List<List<IntentionEntity>>> intentionEntities3 = new ArrayList<>();

    private String[] Intention;
    private String remarkValue;
    private String clientId;
    private String triageId;
    private String controlcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_details);
        ButterKnife.bind(this);
        fid = getIntent().getStringExtra("fid");
        initView();
        initData();
    }

    private void initView() {
        commonAdapter = new CommonAdapter<ChargeDateilsEntity.Productlist>(this, R.layout.item_charge_package_list, productlists) {
            @Override
            protected void convert(ViewHolder holder, ChargeDateilsEntity.Productlist productlist, int position) {
                holder.setText(R.id.product_name, productlist.getPNAME());
                holder.setText(R.id.price_text, productlist.getPRICE());
                holder.setText(R.id.money_text, setDecimalFormat(productlist.getPRICE2()));
                holder.setText(R.id.money_count_text, setDecimalFormat(productlist.getPRICE1()));
                holder.setText(R.id.count_text, "x" + productlist.getCOUNT());

                TextView priceText = holder.getView(R.id.price_text);
                priceText.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        };

        projectList.setAdapter(commonAdapter);

    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("billId", fid);

        //获取收费单
        HttpClient.getHttpApi().getChargeDetaills(HttpClient.getRequestBody(map)).enqueue(new BaseBack<ChargeDateilsEntity>() {
            @Override
            protected void onSuccess(ChargeDateilsEntity chargeDateilsEntity) {
                clientId = chargeDateilsEntity.getInfo().getCUSTID();//客户ID
                triageId = chargeDateilsEntity.getInfo().getCFRECEIVEID();//分诊ID
                nameText.setText(chargeDateilsEntity.getInfo().getCFNAME() + "  " + chargeDateilsEntity.getInfo().getCFHANDSET());//姓名+手机号
                if (TextUtils.isEmpty(chargeDateilsEntity.getInfo().getDEPTNAME())) {
                    doctorText.setText(chargeDateilsEntity.getInfo().getDEPTNAME());//医生
                } else {
                    doctorText.setText(chargeDateilsEntity.getInfo().getDEPTNAME() + "-" + chargeDateilsEntity.getInfo().getFDOCTORNAME());//医生
                }
                intentionText.setText(chargeDateilsEntity.getInfo().getINTENTION());//意向
                statusText.setText(chargeDateilsEntity.getInfo().getFCHARGESTATE());//状态
                moneyValue.setText(setDecimalFormat(chargeDateilsEntity.getInfo().getFSHOULDMONEY()));//实付款
                orderNumber.setText(chargeDateilsEntity.getInfo().getFNUMBER());//订单号
                controlcode = chargeDateilsEntity.getInfo().getCONTROLCODE();//订单号

                if (!TextUtils.isEmpty(chargeDateilsEntity.getInfo().getDISCOUNT()))
                    discountsText.setText(chargeDateilsEntity.getInfo().getDISCOUNT());//优惠
                if (!TextUtils.isEmpty(chargeDateilsEntity.getInfo().getPOINTS()))
                    integralText.setText(chargeDateilsEntity.getInfo().getPOINTS());//积分
                if (!TextUtils.isEmpty(chargeDateilsEntity.getInfo().getCFREMARK()))
                    remarkText.setText(chargeDateilsEntity.getInfo().getCFREMARK());//备注

                //判断是否储值/住院押金
                if ("3".equals(chargeDateilsEntity.getInfo().getFCHARGETYPENUMBER())) {
                    svLl.setVisibility(View.VISIBLE);
                    svCash.setText(setDecimalFormat(chargeDateilsEntity.getInfo().getCFFEEALL()));//储值现金
                    svPresent.setText(setDecimalFormat(chargeDateilsEntity.getInfo().getFPRESENTMONEY()));//储值赠送

                    projectList.setVisibility(View.GONE);
                    nameLine.setVisibility(View.GONE);
                    doctorLl.setVisibility(View.GONE);
                    intentionLl.setVisibility(View.GONE);
                    discountsLl.setVisibility(View.GONE);
                    integralLl.setVisibility(View.GONE);
                } else if ("5".equals(chargeDateilsEntity.getInfo().getFCHARGETYPENUMBER())) {
                    cashLl.setVisibility(View.VISIBLE);
                    hospitalMoney.setText(setDecimalFormat(chargeDateilsEntity.getInfo().getCFFEEALL()));//住院押金

                    projectList.setVisibility(View.GONE);
                    nameLine.setVisibility(View.GONE);
                    doctorLl.setVisibility(View.GONE);
                    intentionLl.setVisibility(View.GONE);
                    discountsLl.setVisibility(View.GONE);
                    integralLl.setVisibility(View.GONE);
                } else {
                    //产品列表
                    productlists.addAll(chargeDateilsEntity.getProductlist());
                    commonAdapter.notifyDataSetChanged();
                }

                //按钮显示隐藏
                switch (chargeDateilsEntity.getInfo().getFCHARGESTATENUMBER()) {
                    case "1"://暂存
                        if ("1".equals(chargeDateilsEntity.getInfo().getISTODAY()))
                            compileBtn.setVisibility(View.VISIBLE);//编辑
                        break;
                    case "3"://已结账
                        if ("1".equals(chargeDateilsEntity.getInfo().getFCHARGETYPENUMBER()) || "2".equals(chargeDateilsEntity.getInfo().getFCHARGETYPENUMBER())) {//消费/预约金
                            if (chargeDateilsEntity.getInfo().getISTODAY().equals("0")) {
//                                viewHolder.setVisible(R.id.again_consult_btn, true);//重咨
                                againConsultBtn.setVisibility(View.VISIBLE);//重咨
                            }
                            bridgeSectionBtn.setVisibility(View.VISIBLE);//跨科
                            getIntentionData();
                        }
                        moneyValue.setText(setDecimalFormat(chargeDateilsEntity.getInfo().getFFACTMONEY()));//应付款
                        moneyText.setText("实付款");
                        break;
                    case "7"://待OA申请
                        oaBtn.setVisibility(View.VISIBLE);//OA申请
                        break;
//                    case "11"://待扫码支付
//                        paymentBtn.setVisibility(View.VISIBLE);// 去支付
//                        break;
                    case "2"://待支付
                        if ("1".equals(chargeDateilsEntity.getInfo().getISTODAY()))
                            paymentBtn.setVisibility(View.VISIBLE);// 去支付
                        break;
                }

            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });


    }

    public boolean getJudgetoDay(String time) {
        try {
            boolean flag = IsToday(time);
            return flag;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    private String setDecimalFormat(String numberStr) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (TextUtils.isEmpty(numberStr)) {
            numberStr = "0";
        }
        Double number = Double.parseDouble(numberStr);
        decimalFormat.format(number);
        return decimalFormat.format(number);
    }

    @OnClick({R.id.return_btn, R.id.again_consult_btn, R.id.bridge_section_btn, R.id.compile_btn, R.id.payment_btn, R.id.name_text, R.id.oa_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.again_consult_btn://重咨
                optionsPickerView.show();
                break;
            case R.id.bridge_section_btn://跨科
                startActivity(MedicineActivity.class, "fid", fid);
                break;
            case R.id.compile_btn://编辑
                SPUtils.setCache(SPUtils.FILE_PASS, SPUtils.RECEPTION_ID, triageId);
                startActivity(OrderActivity.class, "chargeTag", "1");
                break;
            case R.id.payment_btn://去支付
                if (TextUtils.isEmpty(controlcode)) {
                    Intent intent = new Intent();
                    intent.putExtra("fid", fid);
                    intent.putExtra("money", "总金额");
                    intent.setClass(ChargeDetailsActivity.this, PaymentActivity.class);
                    startActivity(intent);
                } else {

                }

                break;
            case R.id.oa_btn://申请oa
                startActivity(OAActivity.class, "fid", fid);
                break;
            case R.id.name_text://申请oa
                startActivity(CustomerDetailsActivity.class, "clientId", clientId);
                break;
            case R.id.return_btn:
                finish();
                break;

        }
    }

    private void getIntentionData() {
        HttpClient.getHttpApi().getIntentionAll().enqueue(new BaseBack<List<IntentionEntity>>() {
            @Override
            protected void onSuccess(List<IntentionEntity> intentionEntities) {
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
        optionsPickerView = new OptionsPickerBuilder(ChargeDetailsActivity.this, new OnOptionsSelectListener() {
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


    private void submitData() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("fid", fid);
        map.put("CustomerOpinion", remarkValue);
        map.put("CustomerIntention", Intention);

        //获取收费单
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
}