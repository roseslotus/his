package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.Constant;
import com.mylike.his.entity.DCProductEntity;
import com.mylike.his.entity.DiscountCouponEntity;
import com.mylike.his.entity.ProductDetailsEntity;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.NumberPickerView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/2/6.
 * 消费/预约金
 */

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener {
    public static String CART_TAG = "ShoppingCart";

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.add_btn)
    TextView addBtn;
    @BindView(R.id.project_list)
    ListView projectList;
    @BindView(R.id.money_text)
    TextView moneyText;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.dc_btn)
    LinearLayout dcBtn;
    @BindView(R.id.activity_name)
    TextView activityName;
    @BindView(R.id.discounts_text)
    TextView discountsText;
    @BindView(R.id.clear_btn)
    ImageView clearBtn;
    @BindView(R.id.activity_ll)
    LinearLayout activityLl;

    private String departmentId;//科室id

    private double moneySum;//合计金额
    private double moneyActivity;//活动总金额
    private CommonAdapter commonAdapter;
    private List<ProductDetailsEntity> accountList = new ArrayList<>();//挑选的产品
    private List<ProductDetailsEntity> newAccountList = new ArrayList<>();//挑选的产品

    //优惠券数据
    DiscountCouponEntity discountCouponEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        accountList.addAll((List<ProductDetailsEntity>) getIntent().getSerializableExtra("accountList"));
        discountCouponEntity = (DiscountCouponEntity) getIntent().getSerializableExtra("discountCouponEntity");
        departmentId = getIntent().getStringExtra("deptId");
        initView();
        if (discountCouponEntity != null)
            setDC();

    }

    private void initView() {
        commonAdapter = new CommonAdapter<ProductDetailsEntity>(this, R.layout.item_product_list, accountList) {
            @Override
            protected void convert(final ViewHolder viewHolder, final ProductDetailsEntity item, final int position) {
                //给赠品添加不可点击样式
                if ("1".equals(item.getIsgive())) {
                    viewHolder.setVisible(R.id.hide, true);
                } else {
                    viewHolder.setVisible(R.id.hide, false);
                }
                viewHolder.setOnClickListener(R.id.hide, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

                //项目名
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

                //划除的原价单价
                final TextView textView = viewHolder.getView(R.id.price_text);
                textView.setText(CommonUtil.setTwoNumber(item.getPrice()));
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                if (TextUtils.isEmpty(item.getPrice2())) {
                    item.setPrice2(item.getPrice());
                    accountList.set(position, item);
                }
                viewHolder.setText(R.id.money_one_text, CommonUtil.setTwoNumber(item.getPrice2()));//单价

                if (TextUtils.isEmpty(item.getPrice1())) {
                    String sumberStr = (Double.parseDouble(item.getPrice2()) * Integer.parseInt(item.getCount())) + "";
                    item.setPrice1(sumberStr);
                    accountList.set(position, item);
                }
                //编辑后的单价乘以数量，小计的总价
                viewHolder.setText(R.id.money_count_text, CommonUtil.setTwoNumber(item.getPrice1()));//小计
//              viewHolder.setText(R.id.discounts_text, accountList.get(position).getDiscount());//手动输入折扣
                viewHolder.setText(R.id.discounts_text, CommonUtil.setTwoNumber((Double.parseDouble(item.getPrice2()) / Double.parseDouble(item.getPrice())) + ""));//手动输入折扣

                //单价编辑
                viewHolder.setOnClickListener(R.id.money_one_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = DialogUtil.inputDialog(ShoppingCartActivity.this, R.layout.dialog_input);
                        Button confirmBtn = view.findViewById(R.id.confirm_btn);
                        final EditText contentText = view.findViewById(R.id.content_text);
                        contentText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        contentText.setHint("输入修改金额");
                        confirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!contentText.getText().toString().isEmpty()) {
                                    //修改数据
                                    item.setPrice2(CommonUtil.setTwoNumber(contentText.getText().toString()));//单价
                                    item.setPrice1((Double.parseDouble(item.getPrice2()) * Integer.parseInt(item.getCount())) + "");//小计
                                    item.setDiscount(CommonUtil.setTwoNumber((Double.parseDouble(item.getPrice2()) / Double.parseDouble(item.getPrice())) + ""));//手动输入折扣
                                    //展示
                                    viewHolder.setText(R.id.money_one_text, CommonUtil.setTwoNumber(item.getPrice2()));
                                    viewHolder.setText(R.id.money_count_text, CommonUtil.setTwoNumber(item.getPrice1()));
                                    viewHolder.setText(R.id.discounts_text, item.getDiscount());//手动输入折扣
                                    accountList.set(position, item);
                                    //计算总价

                                    //清空活动数据
                                    if (discountCouponEntity != null && Constant.DC_ZS.equals(discountCouponEntity.getActivityType())) {
                                        clearAccountList();
                                    }
                                    discountCouponEntity = null;
                                    activityLl.setVisibility(View.GONE);

                                    sumData(null);
                                }
                                DialogUtil.dismissDialog();
                            }
                        });
                    }
                });

                //小计编辑
                viewHolder.setOnClickListener(R.id.money_count_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = DialogUtil.inputDialog(ShoppingCartActivity.this, R.layout.dialog_input);
                        Button confirmBtn = view.findViewById(R.id.confirm_btn);
                        final EditText contentText = view.findViewById(R.id.content_text);
                        contentText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        contentText.setHint("输入修改金额");
                        confirmBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if (!contentText.getText().toString().isEmpty()) {
                                    //清空活动数据
                                    if (discountCouponEntity != null && Constant.DC_ZS.equals(discountCouponEntity.getActivityType())) {
                                        clearAccountList();
                                    }
                                    discountCouponEntity = null;
                                    activityLl.setVisibility(View.GONE);

                                    item.setPrice1(contentText.getText().toString());
                                    item.setPrice2(CommonUtil.setTwoNumber((Double.parseDouble(item.getPrice1()) / Integer.parseInt(item.getCount())) + ""));
                                    item.setDiscount(CommonUtil.setTwoNumber((Double.parseDouble(item.getPrice2()) / Double.parseDouble(item.getPrice())) + ""));

                                    viewHolder.setText(R.id.money_count_text, CommonUtil.setTwoNumber(item.getPrice1()));
                                    viewHolder.setText(R.id.money_one_text, CommonUtil.setTwoNumber(item.getPrice2()));
                                    viewHolder.setText(R.id.discounts_text, item.getDiscount());//折扣
                                    accountList.set(position, item);
                                    sumData(null);
                                }
                                DialogUtil.dismissDialog();
                            }
                        });
                    }
                });

                //折扣编辑
                viewHolder.setOnClickListener(R.id.discounts, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = DialogUtil.inputDialog(ShoppingCartActivity.this, R.layout.dialog_input);
                        Button confirmBtn = view.findViewById(R.id.confirm_btn);
                        final EditText contentText = view.findViewById(R.id.content_text);
                        contentText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        contentText.setHint("输入折扣值");
                        confirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!contentText.getText().toString().isEmpty()) {
                                    //清空活动数据
                                    if (discountCouponEntity != null && Constant.DC_ZS.equals(discountCouponEntity.getActivityType())) {
                                        clearAccountList();
                                    }
                                    discountCouponEntity = null;
                                    activityLl.setVisibility(View.GONE);

                                    item.setPrice2((Double.parseDouble(item.getPrice()) * Double.parseDouble(CommonUtil.setTwoNumber(contentText.getText().toString()))) + "");//单价(原价*折扣，折扣永远保持两位数)
                                    item.setPrice1((Double.parseDouble(item.getPrice2()) * Integer.parseInt(item.getCount())) + "");//小计
                                    item.setDiscount(CommonUtil.setTwoNumber(contentText.getText().toString()));//折扣

                                    viewHolder.setText(R.id.discounts_text, item.getDiscount());//折扣
                                    viewHolder.setText(R.id.money_count_text, CommonUtil.setTwoNumber(item.getPrice1()));//显示小计
                                    viewHolder.setText(R.id.money_one_text, CommonUtil.setTwoNumber(item.getPrice2()));//显示总价
                                    accountList.set(position, item);
                                    sumData(null);
                                }
                                DialogUtil.dismissDialog();
                            }
                        });
                    }
                });

                //数量
                viewHolder.setTag(R.id.number, position);
                final NumberPickerView numberPickerView = viewHolder.getView(R.id.number);
                numberPickerView.setCurrentNum(Integer.parseInt(item.getCount()));
                numberPickerView.setMinDefaultNum(1);
                numberPickerView.setMaxValue(1000);
                numberPickerView.setmOnClickBtnListener(new NumberPickerView.OnClickBtnListener() {
                    @Override
                    public void textChanged(int inputText) {
                        int positionTag = (Integer) numberPickerView.getTag();
                        if (!accountList.get(positionTag).getCount().equals(inputText + "")) {
                            //清空活动数据
                            if (discountCouponEntity != null && Constant.DC_ZS.equals(discountCouponEntity.getActivityType())) {
                                clearAccountList();
                            }
                            discountCouponEntity = null;
                            activityLl.setVisibility(View.GONE);

                        }
                        accountList.get(positionTag).setCount(inputText + "");
                        accountList.get(positionTag).setPrice1((Double.parseDouble(accountList.get(positionTag).getPrice2()) * inputText) + "");
                        viewHolder.setText(R.id.money_count_text, CommonUtil.setTwoNumber(accountList.get(positionTag).getPrice1()));

                        if (discountCouponEntity == null)
                            sumData(null);

                    }
                });
                numberPickerView.setmOnClickInputListener(new NumberPickerView.OnClickInputListener() {
                    @Override
                    public void onWarningForInventory(int inventory) {
                    }

                    @Override
                    public void onWarningMinInput(int minValue) {//监听数量最小值弹框提示
                        CommonUtil.showToast("亲，不能再少啦，可左滑删除哦~");
                    }

                    @Override
                    public void onWarningMaxInput(int maxValue) {
                        int positionTag = (Integer) numberPickerView.getTag();
                        accountList.get(positionTag).setCount(maxValue + "");
                        accountList.get(positionTag).setPrice1((Double.parseDouble(accountList.get(positionTag).getPrice2()) * maxValue) + "");
                        viewHolder.setText(R.id.money_count_text, CommonUtil.setTwoNumber(accountList.get(positionTag).getPrice1()));
                        sumData(null);
                    }
                });

                //删除
                viewHolder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("1".equals(accountList.get(position).getIsgive())) {
                            CommonUtil.showToast("赠品不能删除哦~");
                        } else {
                            //清空活动数据
                            if (discountCouponEntity != null && Constant.DC_ZS.equals(discountCouponEntity.getActivityType())) {
                                Iterator<ProductDetailsEntity> iterator = accountList.iterator();
                                while (iterator.hasNext()) {
                                    ProductDetailsEntity pdeList = iterator.next();
                                    if ("1".equals(pdeList.getIsgive()))
                                        iterator.remove();
                                }
                            }
                            discountCouponEntity = null;
                            activityLl.setVisibility(View.GONE);
                            accountList.remove(position);

                            sumData(null);
                            ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
                            commonAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        };
        projectList.setAdapter(commonAdapter);
        sumData(null);
    }

    @OnClick({R.id.return_btn, R.id.add_btn, R.id.submit_btn, R.id.dc_btn, R.id.clear_btn})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.return_btn:
                View view = DialogUtil.hintDialog(this, "返回将清空产品，是否确认返回？");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.dismissDialog();
                        finish();
                    }
                });
                break;
            case R.id.add_btn:
                intent = new Intent();
                intent.setClass(ShoppingCartActivity.this, ProductActivity.class);
                intent.putExtra(CART_TAG, "1");
                intent.putExtra("deptId", departmentId);
                intent.putExtra("accountList", (Serializable) accountList);
                startActivityForResult(intent, 1);
                break;
            case R.id.submit_btn://结算
                if (!TextUtils.isEmpty(getIntent().getStringExtra(ShoppingCartActivity.CART_TAG))) {//更改标识，需要传递编辑过的商品集合
                    intent = new Intent();
                    intent.putExtra("accountList", (Serializable) accountList);
                    intent.putExtra("discountCouponEntity", discountCouponEntity);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (accountList.size() > 0) {
                        intent = new Intent();
                        intent.setClass(ShoppingCartActivity.this, OrderActivity.class);
                        intent.putExtra("accountList", (Serializable) accountList);
                        intent.putExtra("discountCouponEntity", discountCouponEntity);
                        startActivity(intent);
                    } else {
                        CommonUtil.showToast("暂无商品可结算，请先添加商品");
                    }
                }
                break;

            case R.id.dc_btn://可享优惠
                DCProductEntity dcProductEntity = new DCProductEntity();
                DCProductEntity.product product;//产品数据
                double totalPrices = 0;
                for (ProductDetailsEntity pde : accountList) {
                    product = new DCProductEntity.product();
                    //项目名
                    switch (pde.getItemLx()) {
                        case "1":
                            product.setProductId(pde.getPkgid());
                            product.setProductName(pde.getPkgname());
                            break;
                        case "2":
                            product.setProductId(pde.getProductid());
                            product.setProductName(pde.getPname());
                            break;
                        case "3":
                            product.setProductId(pde.getChaitemCd());
                            product.setProductName(pde.getItemName());
                            break;
                    }
                    totalPrices += Double.parseDouble(pde.getPrice());
                    product.setNum(pde.getCount());
                    dcProductEntity.getProductList().add(product);
                }
                dcProductEntity.setConsumeMoney(totalPrices + "");
                dcProductEntity.setCustId(SPUtils.getCache(SPUtils.FILE_PASS, SPUtils.CLIENT_ID));
                intent = new Intent();
                intent.setClass(ShoppingCartActivity.this, DiscountCouponActivity.class);
                intent.putExtra("dcProductEntity", (Serializable) dcProductEntity);
                startActivityForResult(intent, 1);
                break;
            case R.id.clear_btn:
                //清空活动数据
                activityLl.setVisibility(View.GONE);
                if (discountCouponEntity != null && Constant.DC_ZS.equals(discountCouponEntity.getActivityType())) {
                    clearAccountList();
                    commonAdapter.notifyDataSetChanged();
                }
                for (ProductDetailsEntity pde : accountList) {
                    pde.setPrice1(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice())));
                    pde.setPrice2(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice1()) / Integer.parseInt(pde.getCount())));
                    pde.setDiscount(CommonUtil.setTwoNumber("1"));
                }
                discountCouponEntity = null;
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            accountList.clear();
            accountList.addAll((List<ProductDetailsEntity>) data.getExtras().get("accountList"));
            clearAccountList();
            commonAdapter.notifyDataSetChanged();

            //清空活动数据
            discountCouponEntity = null;
            activityLl.setVisibility(View.GONE);

            sumData(null);
        } else if (resultCode == Constant.RESULT_DC) {
            //清空赠品
            clearAccountList();
            //获取优惠券数据
            discountCouponEntity = (DiscountCouponEntity) data.getExtras().get("discountCouponEntity");
            if (discountCouponEntity != null)
                setDC();
        }
    }

    //清空赠品
    private void clearAccountList() {
        Iterator<ProductDetailsEntity> iterator = accountList.iterator();
        while (iterator.hasNext()) {
            ProductDetailsEntity pdeList = iterator.next();
            if ("1".equals(pdeList.getIsgive()))
                iterator.remove();
        }
    }

    //优惠活动
    private void setDC() {
        activityLl.setVisibility(View.VISIBLE);
        activityName.setText(discountCouponEntity.getActivityName());

        switch (discountCouponEntity.getActivityType()) {
            case Constant.DC_ZK://折扣
                if ("2".equals(discountCouponEntity.getIsOrderly())) {
                    for (ProductDetailsEntity pde : accountList) {
                        pde.setPrice2(pde.getPrice());
                        pde.setPrice1(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice()) * Integer.parseInt(pde.getCount())));
                        pde.setDiscount(CommonUtil.setTwoNumber("1"));
                    }
                    sumData(Constant.DC_ZK);
                } else {
                    for (DiscountCouponEntity.presentItems presentItem : discountCouponEntity.getItems()) {
                        for (ProductDetailsEntity pde : accountList) {
                            switch (pde.getItemLx()) {
                                case "1":
                                    if (presentItem.getId().equals(pde.getPkgid())) {
                                        pde.setPrice1(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice()) * Double.parseDouble(discountCouponEntity.getDiscount())));
                                        pde.setPrice2(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice1()) / Integer.parseInt(pde.getCount())));
                                        pde.setDiscount(CommonUtil.setTwoNumber(discountCouponEntity.getDiscount()));
                                    }
                                    break;
                                case "2":
                                    if (presentItem.getId().equals(pde.getProductid())) {
                                        pde.setPrice1(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice()) * Double.parseDouble(discountCouponEntity.getDiscount())));
                                        pde.setPrice2(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice1()) / Integer.parseInt(pde.getCount())));
                                        pde.setDiscount(CommonUtil.setTwoNumber(discountCouponEntity.getDiscount()));
                                    }
                                    break;
                                case "3":
                                    if (presentItem.getId().equals(pde.getChaitemCd())) {
                                        pde.setPrice1(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice()) * Double.parseDouble(discountCouponEntity.getDiscount())));
                                        pde.setPrice2(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice1()) / Integer.parseInt(pde.getCount())));
                                        pde.setDiscount(CommonUtil.setTwoNumber(discountCouponEntity.getDiscount()));
                                    }
                                    break;
                            }
                        }
                    }
                    sumData(null);
                }
                break;
            case Constant.DC_MJ://满减
                if ("2".equals(discountCouponEntity.getIsOrderly())) {//整单
                    for (ProductDetailsEntity pde : accountList) {
                        pde.setPrice2(pde.getPrice());
                        pde.setPrice1(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice()) * Integer.parseInt(pde.getCount())));
                        pde.setDiscount(CommonUtil.setTwoNumber("1"));
                    }
                    sumData(Constant.DC_MJ);
                } else {//非整单
                    for (DiscountCouponEntity.presentItems presentItem : discountCouponEntity.getItems()) {
                        for (ProductDetailsEntity pde : accountList) {
                            switch (pde.getItemLx()) {
                                case "1":
                                    if (presentItem.getId().equals(pde.getPkgid())) {
                                        pde.setPrice1(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice()) - Double.parseDouble(discountCouponEntity.getLowerMoney())));
                                        pde.setPrice2(CommonUtil.setTwoNumber((Double.parseDouble(pde.getPrice1()) / Integer.parseInt(pde.getCount())) + ""));
                                        pde.setDiscount(CommonUtil.setTwoNumber((Double.parseDouble(pde.getPrice2()) / Double.parseDouble(pde.getPrice())) + ""));
                                    }
                                    break;
                                case "2":
                                    if (presentItem.getId().equals(pde.getProductid())) {
                                        pde.setPrice1(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice()) - Double.parseDouble(discountCouponEntity.getLowerMoney())));
                                        pde.setPrice2(CommonUtil.setTwoNumber((Double.parseDouble(pde.getPrice1()) / Integer.parseInt(pde.getCount())) + ""));
                                        pde.setDiscount(CommonUtil.setTwoNumber((Double.parseDouble(pde.getPrice2()) / Double.parseDouble(pde.getPrice())) + ""));
                                    }
                                    break;
                                case "3":
                                    if (presentItem.getId().equals(pde.getChaitemCd())) {
                                        pde.setPrice1(CommonUtil.setTwoNumber(Double.parseDouble(pde.getPrice()) - Double.parseDouble(discountCouponEntity.getLowerMoney())));
                                        pde.setPrice2(CommonUtil.setTwoNumber((Double.parseDouble(pde.getPrice1()) / Integer.parseInt(pde.getCount())) + ""));
                                        pde.setDiscount(CommonUtil.setTwoNumber((Double.parseDouble(pde.getPrice2()) / Double.parseDouble(pde.getPrice())) + ""));
                                    }
                                    break;
                            }
                        }
                    }
                    sumData(null);
                }
                break;
            case Constant.DC_ZS://赠送
                List<DiscountCouponEntity.presentItems> presentItems = discountCouponEntity.getPresentItems();
                for (DiscountCouponEntity.presentItems presentItem : presentItems) {
                    ProductDetailsEntity pde = new ProductDetailsEntity();
                    switch (presentItem.getType()) {
                        case "1":
                            pde.setItemLx("1");
                            pde.setPkgid(presentItem.getId());
                            pde.setPkgname(presentItem.getName());
                            break;
                        case "2":
                            pde.setItemLx("2");
                            pde.setProductid(presentItem.getId());
                            pde.setPname(presentItem.getName());
                            break;
                        case "3":
                            pde.setItemLx("3");
                            pde.setChaitemCd(presentItem.getId());
                            pde.setItemName(presentItem.getName());
                            break;
                    }
                    pde.setPrice(presentItem.getPrice());
                    pde.setPrice1("0");
                    pde.setPrice2("0");
                    pde.setDiscount("0");
                    pde.setCount(presentItem.getNum());
                    pde.setIsgive("1");
                    accountList.add(pde);
                }
                sumData(null);
                break;
            case Constant.DC_XX://赠送
                sumData(null);
                break;
        }
        commonAdapter.notifyDataSetChanged();
    }

//    private String CommonUtil.setTwoNumber(String numberStr) {
//        DecimalFormat decimalFormat = new DecimalFormat("0.00");
//        if (TextUtils.isEmpty(numberStr)) {
//            numberStr = "0";
//        }
//        Double number = Double.parseDouble(numberStr);
//        decimalFormat.format(number);
//        return decimalFormat.format(number);
//    }

    private void sumData(String tag) {
        moneySum = 0;
        moneyActivity = 0;
        for (ProductDetailsEntity productDetailsEntity : accountList) {
            if (!"1".equals(productDetailsEntity.getIsgive())) {//除赠品外的产品价格累加
                if (TextUtils.isEmpty(productDetailsEntity.getPrice2())) {
                    moneySum += (Double.parseDouble(productDetailsEntity.getPrice()) * Integer.parseInt(productDetailsEntity.getCount()));
                } else {
                    moneySum += (Double.parseDouble(productDetailsEntity.getPrice2()) * Integer.parseInt(productDetailsEntity.getCount()));
//                    moneySum += Double.parseDouble(productDetailsEntity.getPrice1());
                }
                //活动价
                moneyActivity += (Double.parseDouble(productDetailsEntity.getPrice()) * Integer.parseInt(productDetailsEntity.getCount()));
            }
        }
        if (Constant.DC_MJ.equals(tag)) {
            moneySum = moneySum - Double.parseDouble((discountCouponEntity.getLowerMoney() != null ? discountCouponEntity.getLowerMoney() : "0"));
        } else if (Constant.DC_ZK.equals(tag)) {
            moneySum = moneySum * Double.parseDouble(discountCouponEntity.getDiscount() != null ? discountCouponEntity.getDiscount() : "0");
        }
        moneyActivity = moneyActivity - moneySum;
        discountsText.setText(CommonUtil.setTwoNumber(moneyActivity));
        moneyText.setText(CommonUtil.setTwoNumber(moneySum + ""));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            View view = DialogUtil.hintDialog(this, "返回将清空产品，是否确认返回？");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.dismissDialog();
                    finish();
                }
            });
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
