package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.BookbuildingEntity;
import com.mylike.his.entity.ChargeUserInfoEntity;
import com.mylike.his.entity.DepartmentDEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.entity.PackageEntity;
import com.mylike.his.entity.ProductDetailsEntity;
import com.mylike.his.entity.TokenEntity;
import com.mylike.his.entity.TriageInfoEntity;
import com.mylike.his.entity.UserIntentionEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.SListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mylike.his.activity.consultant.ShoppingCartActivity.CART_TAG;

/**
 * Created by zhengluping on 2018/2/5.
 * 填写订单
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener {
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.section_text)
    TextView sectionText;
    @BindView(R.id.doctor_text)
    TextView doctorText;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.project_list)
    SListView projectList;
    @BindView(R.id.intention_text)
    TextView intentionText;
    @BindView(R.id.add_intention_text)
    TextView addIntentionText;
    @BindView(R.id.tag_ll)
    LinearLayout tagLl;
    @BindView(R.id.sum_text)
    TextView sumText;
    @BindView(R.id.save_charge_btn)
    TextView saveChargeBtn;
    @BindView(R.id.add_charge_btn)
    TextView addChargeBtn;
    @BindView(R.id.integral_text)
    TextView integralText;
    @BindView(R.id.update_btn)
    TextView updateBtn;
    @BindView(R.id.subscription_btn)
    TextView subscriptionBtn;
    @BindView(R.id.time_text)
    TextView timeText;
    @BindView(R.id.time_ll)
    LinearLayout timeLl;
    @BindView(R.id.money_text)
    EditText moneyText;
    @BindView(R.id.money_ll)
    LinearLayout moneyLl;
    @BindView(R.id.oa_box)
    CheckBox oaBox;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.remark_edit)
    EditText remarkEdit;
    @BindView(R.id.integral_edit)
    EditText integralEdit;
    @BindView(R.id.jian_text)
    TextView jianText;
    @BindView(R.id.integral_money_text)
    TextView integralMoneyText;
    @BindView(R.id.identity_edit)
    EditText identityEdit;
    @BindView(R.id.clear_btn)
    TextView clearBtn;

    private OptionsPickerView optionsPickerView;

    //产品数据
    private CommonAdapter commonAdapter;
    private List<UserIntentionEntity> userIntentionEntityList = new ArrayList<>();//客戶意向
    private List<ProductDetailsEntity> accountList = new ArrayList<>();//挑选的产品

    //所有意向数据
    private List<IntentionEntity> intentionEntities1 = new ArrayList<>();
    private List<List<IntentionEntity>> intentionEntities2 = new ArrayList<>();
    private List<List<List<IntentionEntity>>> intentionEntities3 = new ArrayList<>();

    //科室医生数据
    private DepartmentDEntity departmentDEntity = new DepartmentDEntity();

    private List<String> tcId = new ArrayList<>();//查询套餐id
    private String[] Intention;//意向数据
    private double moneySum;//实付款
    private String custId;//客戶id
    private String intentionId;//意向id
    private String doctorDepartment;//医生部门id
    private String doctorId;//医生id
    private String chargeId = "";//收费单id
    private int integralValue;//客户可用积分
    private String zxsid;
    private String chargeTag;//是否读取暂存数据，如果为空，则是新单；

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        getIntentionAllData();//获取全部意向信息

        chargeTag = getIntent().getStringExtra("chargeTag");
        initView();
        initDepartmentDData();
        getCustData();

    }


    private void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setEnableLoadMore(false);
        if (TextUtils.isEmpty(chargeTag)) {//购物车过来的产品
            accountList.addAll((List<ProductDetailsEntity>) getIntent().getSerializableExtra("accountList"));
            tcId.clear();
            for (ProductDetailsEntity p : accountList) {
                if (!TextUtils.isEmpty(p.getPkgid())) {
                    tcId.add(p.getPkgid());
                }
            }
            getPackageProductData();
        }
        commonAdapter = new CommonAdapter<ProductDetailsEntity>(this, R.layout.item_charge_package_list, accountList) {
            @Override
            protected void convert(ViewHolder viewHolder, ProductDetailsEntity item, final int position) {
                switch (item.getItemLx()) {
                    case "1":
                        viewHolder.setText(R.id.product_name, item.getPkgname());
                        break;
                    case "2":
                        viewHolder.setText(R.id.product_name, item.getPname());
                        break;
                    case "3":
                        viewHolder.setText(R.id.product_name, item.getItemName());
                        break;
                }

                final TextView textView = viewHolder.getView(R.id.price_text);
                textView.setText(CommonUtil.setTwoNumber(item.getPrice()));
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                viewHolder.setText(R.id.money_text, setDecimalFormat(item.getPrice2()));

                if (TextUtils.isEmpty(item.getPrice1())) {
                    viewHolder.setText(R.id.money_count_text, new DecimalFormat("0.00").format(Double.parseDouble(item.getPrice()) * Integer.parseInt(item.getCount())));
                } else {
                    viewHolder.setText(R.id.money_count_text, new DecimalFormat("0.00").format(Double.parseDouble(item.getPrice1())));
                }
                viewHolder.setText(R.id.count_text, "x" + item.getCount());

                ListView listView = viewHolder.getView(R.id.project_list);
                listView.setAdapter(new CommonAdapter<PackageEntity.product>(OrderActivity.this, R.layout.item_charge_product_list, item.getSubItems()) {
                    @Override
                    protected void convert(ViewHolder viewHolder, final PackageEntity.product item, int position) {
                        viewHolder.setText(R.id.name, item.getPname());
                        viewHolder.setText(R.id.num, "x" + item.getNum());
                        viewHolder.setText(R.id.money, item.getPrice());

                        //医生下拉选项
                        final List<DepartmentDEntity.deptDocker> deptDockerSpr = new ArrayList<>();
                        final CommonAdapter sprAdapter = new CommonAdapter<DepartmentDEntity.deptDocker>(OrderActivity.this, R.layout.common_item_text, deptDockerSpr) {
                            @Override
                            protected void convert(ViewHolder viewHolder, DepartmentDEntity.deptDocker item, int position) {

                                TextView textView = viewHolder.getView(R.id.text);
                                //设置第一项“请选择颜色为灰色”
                                if (TextUtils.isEmpty(item.getEmpId())) {
                                    textView.setTextColor(getResources().getColor(R.color.gray_49));
                                } else {
                                    textView.setTextColor(getResources().getColor(R.color.black_50));
                                }
                                textView.setGravity(Gravity.LEFT);
                                textView.setPadding(20, 30, 20, 30);
                                viewHolder.setText(R.id.text, item.getEmpName());
                            }
                        };

                        final Spinner spinner2 = viewHolder.getView(R.id.doctor_spr);

                        //科室下拉选项
                        final Spinner spinner = viewHolder.getView(R.id.department_spr);
                        spinner.setAdapter(new CommonAdapter<DepartmentDEntity.department>(OrderActivity.this, R.layout.common_item_text, departmentDEntity.getDepartments()) {
                            @Override
                            protected void convert(ViewHolder viewHolder, DepartmentDEntity.department dItem, int position) {
                                TextView textView = viewHolder.getView(R.id.text);
                                //设置第一项“请选择颜色为灰色”
                                if (TextUtils.isEmpty(dItem.getDeptid()) || (!TextUtils.isEmpty(item.getDepartment()))) {
                                    textView.setTextColor(getResources().getColor(R.color.gray_49));
                                } else {
                                    textView.setTextColor(getResources().getColor(R.color.black_50));
                                }
                                textView.setGravity(Gravity.LEFT);
                                textView.setPadding(20, 30, 20, 30);
                                viewHolder.setText(R.id.text, dItem.getDeptname());
                            }
                        });
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                deptDockerSpr.clear();
                                deptDockerSpr.add(new DepartmentDEntity.deptDocker("请选择"));
                                if (!TextUtils.isEmpty(departmentDEntity.getDepartments().get(i).getDeptid()))
                                    deptDockerSpr.addAll(departmentDEntity.getDeptDockers().get(departmentDEntity.getDepartments().get(i).getDeptid()));
                                item.setDepartment(departmentDEntity.getDepartments().get(i).getDeptid());
                                sprAdapter.notifyDataSetChanged();

                                if (!TextUtils.isEmpty(item.getFdoctorid())) {
                                    for (int j = 0; j < deptDockerSpr.size(); j++) {
                                        if (item.getFdoctorid().equals(deptDockerSpr.get(j).getEmpId())) {
                                            spinner2.setSelection(j, true);
                                            break;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                        if (TextUtils.isEmpty(item.getDepartment())) {
                            spinner.setEnabled(true);
                        } else {
                            for (int i = 0; i < departmentDEntity.getDepartments().size(); i++) {
                                if (item.getDepartment().equals(departmentDEntity.getDepartments().get(i).getDeptid())) {
                                    spinner.setSelection(i, true);
                                    break;
                                }
                            }
                            spinner.setEnabled(false);
                        }

                        spinner2.setAdapter(sprAdapter);
                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                item.setFdoctorid(deptDockerSpr.get(i).getEmpId());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });


                    }
                });
            }
        };
        projectList.setAdapter(commonAdapter);


        integralEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
                if (!TextUtils.isEmpty(value) && !value.equals("null")) {
                    if (s.toString().equals("0")) {
                        integralEdit.setText("");
                    } else {
                        if (Integer.parseInt(s.toString()) > integralValue) {
                            integralEdit.setText(integralValue + "");
                            integralEdit.setSelection(integralEdit.getText().toString().length());//将光标移至文字末尾
                        }
                        jianText.setVisibility(View.VISIBLE);
                        integralMoneyText.setVisibility(View.VISIBLE);
                        integralMoneyText.setText(integralEdit.getText());
                    }
                } else {
                    jianText.setVisibility(View.GONE);
                    integralMoneyText.setVisibility(View.GONE);
                }
                sumData();
            }
        });
    }

    private void initDepartmentDData() {

        //收费单客戶信息
        HttpClient.getHttpApi().getDepartmentDoctor().enqueue(new BaseBack<DepartmentDEntity>() {
            @Override
            protected void onSuccess(DepartmentDEntity dDEntity) {
                departmentDEntity.getDepartments().add(new DepartmentDEntity.department("请选择"));
                departmentDEntity.getDepartments().addAll(dDEntity.getDepartments());
                departmentDEntity.getDeptDockers().putAll(dDEntity.getDeptDockers());
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    private void getCustData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Triageid", SPUtils.getCache(SPUtils.FILE_RECEPTION, SPUtils.RECEPTION_ID));

        //收费单客戶信息
        HttpClient.getHttpApi().getChargeUserInfo(HttpClient.getRequestBody(map)).enqueue(new BaseBack<ChargeUserInfoEntity>() {
            @Override
            protected void onSuccess(ChargeUserInfoEntity chargeUserInfoEntity) {
                //产品
                /*if (chargeUserInfoEntity.getProductData().getCp() != null && chargeUserInfoEntity.getProductData().getCp().size() > 0) {
                    for (ChargeUserInfoEntity.PInfo pInfo : chargeUserInfoEntity.getProductData().getCp()) {
                        pde = new ProductDetailsEntity();
                        pde.setPname(pInfo.getCfprojectname());
                        pde.setProductid(pInfo.getFprojectid());
                        pde.setCount(pInfo.getCfnumbers());
                        pde.setPrice(pInfo.getFproductprice());
                        pde.setPrice1(pInfo.getFsummoney());
                        pde.setPrice2(pInfo.getUnitprice());
                        pde.setItemLx("2");
                        pde.setDiscount(pInfo.getBillrebate());
                        accountList.add(pde);
                    }
                }
                //套餐
                if (chargeUserInfoEntity.getProductData().getTc() != null && chargeUserInfoEntity.getProductData().getTc().size() > 0) {
                    for (ChargeUserInfoEntity.PInfo pInfo : chargeUserInfoEntity.getProductData().getTc()) {
                        pde = new ProductDetailsEntity();
                        pde.setPkgname(pInfo.getCfprojectname());
                        pde.setPkgid(pInfo.getFprojectid());
                        pde.setCount(pInfo.getCfnumbers());
                        pde.setPrice(pInfo.getFproductprice());
                        pde.setPrice1(pInfo.getFsummoney());
                        pde.setPrice2(pInfo.getUnitprice());
                        pde.setItemLx("1");
                        pde.setDiscount(pInfo.getBillrebate());
                        accountList.add(pde);
                    }
                }
                //细目
                if (chargeUserInfoEntity.getProductData().getXm() != null && chargeUserInfoEntity.getProductData().getXm().size() > 0) {
                    for (ChargeUserInfoEntity.PInfo pInfo : chargeUserInfoEntity.getProductData().getXm()) {
                        pde = new ProductDetailsEntity();
                        pde.setItemName(pInfo.getCfprojectname());
                        pde.setChaitemCd(pInfo.getFprojectid());
                        pde.setCount(pInfo.getCfnumbers());
                        pde.setPrice(pInfo.getFproductprice());
                        pde.setPrice1(pInfo.getFsummoney());
                        pde.setPrice2(pInfo.getUnitprice());
                        pde.setItemLx("3");
                        pde.setDiscount(pInfo.getBillrebate());
                        accountList.add(pde);
                    }
                }*/
                if (chargeUserInfoEntity.getProductData().getData() != null) {
                    ProductDetailsEntity pde;
                    List<PackageEntity.product> list = new ArrayList<>();
                    for (ChargeUserInfoEntity.PInfo pInfo : chargeUserInfoEntity.getProductData().getData()) {
                        pde = new ProductDetailsEntity();
                        if ("1".equals(pInfo.getItemLx())) {//套餐
                            pde = new ProductDetailsEntity();
                            pde.setPkgname(pInfo.getCfprojectname());
                        } else if ("2".equals(pInfo.getItemLx())) {//产品
                            pde.setPname(pInfo.getCfprojectname());
                            pde.setProductid(pInfo.getFprojectid());
                        } else if ("3".equals(pInfo.getItemLx())) {//细目
                            pde.setItemName(pInfo.getCfprojectname());
                            pde.setChaitemCd(pInfo.getFprojectid());
                        }
                        pde.setCount(pInfo.getCfnumbers());
                        pde.setPrice(pInfo.getFproductprice());
                        pde.setPrice1(pInfo.getFsummoney());
                        pde.setPrice2(pInfo.getUnitprice());
                        pde.setItemLx(pInfo.getItemLx());
                        pde.setDiscount(pInfo.getBillrebate());
                        if (pInfo.getItemLx().equals("1")) {
                            PackageEntity.product product;
                            for (ChargeUserInfoEntity.PInfo pInfo2 : pInfo.getProduct()) {
                                product = new PackageEntity.product();
                                product.setPname(pInfo2.getCfprojectname());//产品名
                                product.setNum(pInfo2.getCfnumbers());//数量
                                product.setPrice(pInfo2.getFproductprice());//原价
                                product.setDepartment(pInfo2.getDepartment());//科室
                                product.setFdoctorid(pInfo2.getFdoctorid());//医生
                                list.add(product);
                            }
                            pde.setSubItems(list);
                        }
                        accountList.add(pde);
                    }
                    commonAdapter.notifyDataSetChanged();
                }

                integralValue = chargeUserInfoEntity.getTriageData().getJIFEN();//积分

                userName.setText(chargeUserInfoEntity.getTriageData().getKHXM());//姓名
                userPhone.setText(chargeUserInfoEntity.getTriageData().getKHDH());//电话
                sectionText.setText(chargeUserInfoEntity.getTriageData().getKSMC());//科室
                doctorText.setText(chargeUserInfoEntity.getTriageData().getYSMC());//医生
                integralText.setText("共" + chargeUserInfoEntity.getTriageData().getJIFEN() + "积分");//积分
//              sintegralEdit.setText(chargeUserInfoEntity.getTriageData().getScore() + "");//填写的积分

                identityEdit.setText(chargeUserInfoEntity.getTriageData().getSFZH());//身份证
                remarkEdit.setText(chargeUserInfoEntity.getTriageData().getChargebillcfremark());//备注

                if (!TextUtils.isEmpty(chargeUserInfoEntity.getTriageData().getYXID())) {
                    intentionText.setText(chargeUserInfoEntity.getTriageData().getYX());
                    intentionId = chargeUserInfoEntity.getTriageData().getYXID();
                }

                if (!TextUtils.isEmpty(chargeUserInfoEntity.getProductData().getCb()))
                    chargeId = chargeUserInfoEntity.getProductData().getCb();//收费单id
                custId = chargeUserInfoEntity.getTriageData().getKHID();//客户id
                doctorId = chargeUserInfoEntity.getTriageData().getYSID();//医生id
                doctorDepartment = chargeUserInfoEntity.getTriageData().getKSID();//科室id
                zxsid = chargeUserInfoEntity.getTriageData().getZXSID();//科室id

                tcId.clear();
                for (ProductDetailsEntity p : accountList) {
                    if (!TextUtils.isEmpty(p.getPkgid())) {
                        tcId.add(p.getPkgid());
                    }
                }
                getPackageProductData();


                sumData();
                initIntentionData();//获取意向数据
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });

        moneyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    sumText.setText(moneySum + "");
                } else {
                    sumText.setText(CommonUtil.setTwoNumber(editable.toString()));
                }

            }
        });

    }

    //客戶所有意向
    private void initIntentionData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("custId", custId);
        HttpClient.getHttpApi().getUserIntentionInfo(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<UserIntentionEntity>>() {
            @Override
            protected void onSuccess(List<UserIntentionEntity> userIntentionEntities) {
                userIntentionEntityList.clear();
                userIntentionEntityList.addAll(userIntentionEntities);
//                getIntentionAllData();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //获取意向数据
    private void getIntentionAllData() {
        HttpClient.getHttpApi().getIntentionAll().enqueue(new BaseBack<List<IntentionEntity>>() {
            @Override
            protected void onSuccess(List<IntentionEntity> intentionEntities) {
                intentionEntities1.addAll(intentionEntities);
                //初始化意向数据
                initViewData();
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    //获取套餐下的产品数据
    private void getPackageProductData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pkgIds", tcId);

        HttpClient.getHttpApi().getPackageProduct(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<PackageEntity>>() {
            @Override
            protected void onSuccess(List<PackageEntity> packageEntities) {
                for (ProductDetailsEntity p : accountList) {
                    for (PackageEntity packageEntity : packageEntities) {
                        if ((!TextUtils.isEmpty(p.getPkgid())) && p.getPkgid().equals(packageEntity.getPackageInfo().getPkgid())) {
                            p.getSubItems().clear();
                            p.getSubItems().addAll(packageEntity.getProducts());
                        }
                    }
                }
                commonAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    private PopupWindow projectPW;

    @OnClick({R.id.return_btn, R.id.intention_text, R.id.add_intention_text, R.id.save_charge_btn, R.id.add_charge_btn, R.id.update_btn, R.id.subscription_btn, R.id.time_text, R.id.clear_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intention_text://意向
                final View view = getLayoutInflater().inflate(R.layout.common_item_list, null);
                projectPW = new PopupWindow(view, intentionText.getWidth(), 400, true);
                projectPW.setBackgroundDrawable(getResources().getDrawable(R.color.white));
                projectPW.showAsDropDown(intentionText);
                final ListView listView = view.findViewById(R.id.common_list);
                listView.setAdapter(new CommonAdapter<UserIntentionEntity>(OrderActivity.this, R.layout.common_item_text, userIntentionEntityList) {
                    @Override
                    protected void convert(ViewHolder viewHolder, UserIntentionEntity item, int position) {
                        final TextView textView = viewHolder.getView(R.id.text);
                        textView.setText(item.getItemData());
                        textView.setPadding(5, 30, 5, 30);
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        intentionText.setText(userIntentionEntityList.get(position).getItemData());
                        intentionId = userIntentionEntityList.get(position).getId();
                        projectPW.dismiss();
                    }
                });
                break;
            case R.id.add_intention_text:
                optionsPickerView.show();
                break;
            case R.id.save_charge_btn://保存
                if (!TextUtils.isEmpty(moneyText.getText())) {
                    CommonUtil.showToast("预约金订单不能保存");
                } else {
                    saveCharge();
                }
                break;
            case R.id.add_charge_btn://提交
                if (TextUtils.isEmpty(intentionId)) {
                    CommonUtil.showToast("请先选择意向，再提交订单");
                } else if (TextUtils.isEmpty(doctorText.getText().toString())) {
                    CommonUtil.showToast("未分配医生不能提交订单，请通知前台分配医生并下拉刷新");
                } else {
                    if (!TextUtils.isEmpty(moneyText.getText()) || !TextUtils.isEmpty(timeText.getText())) {
                        if (TextUtils.isEmpty(moneyText.getText())) {
                            CommonUtil.showToast("你选择了预约金，请填写预约金额");
                        } else {
                            DialogUtil.hintDialog(OrderActivity.this, "是否确认下单？").setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    addCharge();
                                    DialogUtil.dismissDialog();
                                }
                            });
                        }
                    } else {
                        DialogUtil.hintDialog(OrderActivity.this, "是否确认下单？").setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addCharge();
                                DialogUtil.dismissDialog();
                            }
                        });
                    }
                }
                break;
            case R.id.subscription_btn://预约金
                if (timeLl.getVisibility() == View.GONE) {
                    Drawable nav_up = getResources().getDrawable(R.mipmap.top_green_icon);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    subscriptionBtn.setCompoundDrawables(null, null, nav_up, null);
                    timeLl.setVisibility(View.VISIBLE);
                    moneyLl.setVisibility(View.VISIBLE);
                } else {
                    Drawable nav_up = getResources().getDrawable(R.mipmap.below_green_icon);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    subscriptionBtn.setCompoundDrawables(null, null, nav_up, null);

                    timeLl.setVisibility(View.GONE);
                    moneyLl.setVisibility(View.GONE);
                    timeText.setText("");
                    moneyText.setText("");
                }
                break;
            case R.id.time_text://预约金选择时间
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(OrderActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        timeText.setText(getTime(date));
                    }
                }).setType(new boolean[]{true, true, true, true, true, false})//隐藏秒
                        .setContentTextSize(16)
                        .setSubmitColor(getResources().getColor(R.color.green_50))
                        .setCancelColor(getResources().getColor(R.color.gray_50))
                        .build();
                pvTime.show();
                break;
            case R.id.clear_btn:
                timeText.setText("");
                break;
            case R.id.update_btn://更改
                if (!TextUtils.isEmpty(chargeTag)) {//从暂存进来进购物车不再是finish，需要传递数据给购物车
                    Intent intent = new Intent();
                    intent.setClass(OrderActivity.this, ShoppingCartActivity.class);
                    intent.putExtra(CART_TAG, "1");
                    intent.putExtra("accountList", (Serializable) accountList);
                    startActivityForResult(intent, 1);
                } else {
                    finish();
                }
                break;
            case R.id.return_btn://返回
                finish();
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
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

        //弹框初始化
        optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {//选择项
                Intention = new String[]{intentionEntities1.get(options1).getPid(), intentionEntities2.get(options1).get(options2).getPid(), intentionEntities3.get(options1).get(options2).get(options3).getPid()};
                String intentionValue = "";
                if (!TextUtils.isEmpty(intentionEntities1.get(options1).getPid())) {
                    intentionValue = intentionValue + intentionEntities1.get(options1).getPname();
                }
                if (!TextUtils.isEmpty(intentionEntities2.get(options1).get(options2).getPid())) {
                    intentionValue = intentionValue + "/" + intentionEntities2.get(options1).get(options2).getPname();
                }
                if (!TextUtils.isEmpty(intentionEntities3.get(options1).get(options2).get(options3).getPid())) {
                    intentionValue = intentionValue + "/" + intentionEntities3.get(options1).get(options2).get(options3).getPname();
                }
                intentionText.setText(intentionValue);
                intentionSubmit();
                optionsPickerView.dismiss();

            }
        }).setLayoutRes(R.layout.dialog_again_consult_not, new CustomListener() {//自定义布局
            @Override
            public void customLayout(View v) {
                final Button submitBtn = v.findViewById(R.id.submit_btn);

                //意向提交
                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsPickerView.returnData();
                    }
                });
            }
        }).setContentTextSize(14).setDividerColor(getResources().getColor(R.color.green_50)).setLineSpacingMultiplier((float) 2.5).isDialog(true).build();
        optionsPickerView.setPicker(intentionEntities1, intentionEntities2, intentionEntities3);//设置数据
    }

    private void intentionSubmit() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("customId", custId);
        map.put("itemFirst", Intention[0]);
        map.put("itemSecond", Intention[1]);
        map.put("itemThird", Intention[2]);

        //添加意向
        HttpClient.getHttpApi().addIntention(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {

            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                intentionId = stringStringMap.get("id");//意向id
                initIntentionData();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //保存订单
    private void saveCharge() {

        //客户信息
        HashMap<String, Object> kehushuju = new HashMap<>();
        kehushuju.put("khid", custId);
        kehushuju.put("sfzh", identityEdit.getText().toString());

        //分诊数据
        HashMap<String, Object> fenzhenshuju = new HashMap<>();
        fenzhenshuju.put("fenzhenid", SPUtils.getCache(SPUtils.FILE_RECEPTION, SPUtils.RECEPTION_ID));
        fenzhenshuju.put("yixiangId", intentionId);
        fenzhenshuju.put("doctorDepartment", doctorDepartment);
        fenzhenshuju.put("doctorId", doctorId);
        fenzhenshuju.put("chargebillcfremark", remarkEdit.getText().toString());
        fenzhenshuju.put("score", integralEdit.getText().toString());//积分

        //产品数据(后台说要这样的数据，给后台循环一层)
        HashMap<String, Object> productshuju = new HashMap<>();
        for (int i = 0; i < accountList.size(); i++) {
            productshuju.put(i + "", accountList.get(i));
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("kehushuju", kehushuju);
        map.put("fenzhenshuju", fenzhenshuju);
        map.put("productshuju", productshuju);
        map.put("totalMoney", sumText.getText().toString());
        map.put("zxsid", zxsid);
        map.put("billId", chargeId);

        //保存收费单
        HttpClient.getHttpApi().saveCharge(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {

            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                CommonUtil.showToast("保存成功");
                startActivity(CMainActivity.class, CMainActivity.GO_CHARGE, CMainActivity.GO_CHARGE);
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }


    //提交订单
    private void addCharge() {
        //客户信息
        HashMap<String, Object> kehushuju = new HashMap<>();
        kehushuju.put("khid", custId);//客户id
        kehushuju.put("yuyueTime", timeText.getText().toString());//预约时间
        kehushuju.put("yuyueprice", moneyText.getText().toString());//预约金额


        //分诊数据
        HashMap<String, Object> fenzhenshuju = new HashMap<>();
        fenzhenshuju.put("fenzhenid", SPUtils.getCache(SPUtils.FILE_RECEPTION, SPUtils.RECEPTION_ID));//分诊id
        fenzhenshuju.put("yixiangId", intentionId);//意向id
        fenzhenshuju.put("doctorDepartment", doctorDepartment);//医生部门id
        fenzhenshuju.put("doctorId", doctorId);//医生id
        fenzhenshuju.put("score", integralEdit.getText().toString());//积分
        fenzhenshuju.put("chargebillcfremark", remarkEdit.getText().toString());//消费开单备注


        HashMap<String, Object> productshuju = new HashMap<>();
        for (int i = 0; i < accountList.size(); i++) {
            productshuju.put(i + "", accountList.get(i));
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("kehushuju", kehushuju);
        map.put("fenzhenshuju", fenzhenshuju);
        map.put("productshuju", productshuju);
        map.put("totalMoney", sumText.getText().toString());
        map.put("zxsid", zxsid);
        map.put("billId", chargeId);

        if (TextUtils.isEmpty(moneyText.getText().toString())) {
            map.put("isAppointmentMoney", "0");//是否为预约金(0-不是，1-是)
        } else {
            map.put("isAppointmentMoney", "1");//是否为预约金(0-不是，1-是)
        }
        map.put("totalRealMoney", sumText.getText().toString());//实付金额 （减去积分后的实际需要支付的金额，预约金情况下不能使用积分）

        map.put("remark", remarkEdit.getText().toString());//预约金备注
        if (oaBox.isChecked()) {
            map.put("isOA", "1");//oa：0-不走oa，1-走oa
        } else {
            map.put("isOA", "0");//oa：0-不走oa，1-走oa
        }

        //提交订单
        HttpClient.getHttpApi().addCharge(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                CommonUtil.showToast("提交成功");
                if (oaBox.isChecked()) {//选择了OA选项
                    //跳转OA
                    startActivity(CMainActivity.class, CMainActivity.GO_OA, stringStringMap.get("billId"));
//                    startActivity(OAActivity.class, "fid", stringStringMap.get("billId"));
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(CMainActivity.GO_PAYMENT, stringStringMap.get("billId"));
                    intent.putExtra("money", moneySum);
                    intent.setClass(OrderActivity.this, CMainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            protected void onFailed(String code, String msg) {
                CommonUtil.showToast("提交失败");
            }
        });
    }

    private void sumData() {
        moneySum = 0;
        for (ProductDetailsEntity productDetailsEntity : accountList) {
            if (TextUtils.isEmpty(productDetailsEntity.getPrice1())) {
                moneySum += (Double.parseDouble(productDetailsEntity.getPrice()) * Integer.parseInt(productDetailsEntity.getCount()));
            } else {
                moneySum += Double.parseDouble(productDetailsEntity.getPrice1());
            }
        }
        if (!TextUtils.isEmpty(integralEdit.getText().toString()) && !integralEdit.getText().toString().equals("null")) {
            int integral = Integer.parseInt(integralEdit.getText().toString());
            if (moneySum >= integral) {
                moneySum -= integral;
            } else {
                integralEdit.setText((int) Math.floor(moneySum) + "");
                integralEdit.setSelection(integralEdit.getText().toString().length());
            }
        }
        sumText.setText(setDecimalFormat(moneySum + ""));
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
        getDoctorData();
    }

    private void getDoctorData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Triageid", SPUtils.getCache(SPUtils.FILE_RECEPTION, SPUtils.RECEPTION_ID));

        //收费单客戶信息
        HttpClient.getHttpApi().getTriageInfo(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<TriageInfoEntity>>() {
            @Override
            protected void onSuccess(List<TriageInfoEntity> triageInfoEntities) {
                doctorText.setText(triageInfoEntities.get(0).getYSMC());
                doctorId = triageInfoEntities.get(0).getYSID();
                refreshLayout.finishRefresh();
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            accountList.clear();
            accountList.addAll((List<ProductDetailsEntity>) data.getExtras().get("accountList"));
            tcId.clear();
            for (ProductDetailsEntity p : accountList) {
                if (!TextUtils.isEmpty(p.getPkgid())) {
                    tcId.add(p.getPkgid());
                }
            }
            getPackageProductData();
            sumData();
            commonAdapter.notifyDataSetChanged();
        }
    }
}
