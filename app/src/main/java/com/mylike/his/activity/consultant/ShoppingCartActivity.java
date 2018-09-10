package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuBuilder;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.ProductDetailsEntity;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.ToastUtils;
import com.mylike.his.view.NumberPickerView;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.lang.annotation.IncompleteAnnotationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/2/6.
 * 消费/预约金
 */

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener {
    public static String CART_TAG = "ShoppingCart";

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.add_btn)
    TextView addBtn;
    @Bind(R.id.project_list)
    ListView projectList;
    @Bind(R.id.money_text)
    TextView moneyText;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    private double moneySum;//合计金额
    private CommonAdapter commonAdapter;
    private List<ProductDetailsEntity> accountList = new ArrayList<>();//挑选的产品
    private List<ProductDetailsEntity> newAccountList = new ArrayList<>();//挑选的产品

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        accountList.addAll((List<ProductDetailsEntity>) getIntent().getSerializableExtra("accountList"));
        initView();
    }

    private void initView() {
        commonAdapter = new CommonAdapter<ProductDetailsEntity>(this, R.layout.item_product_list, accountList) {
            @Override
            protected void convert(final ViewHolder viewHolder, final ProductDetailsEntity item, final int position) {
                //项目名
                switch (item.getItemLx()) {
                    case "套餐":
                        viewHolder.setText(R.id.product_name, item.getPkgname());
                        break;
                    case "产品":
                        viewHolder.setText(R.id.product_name, item.getPname());
                        break;
                    case "细目":
                        viewHolder.setText(R.id.product_name, item.getItemName());
                        break;
                }

                //划除的原价单价
                TextView textView = viewHolder.getView(R.id.price_text);
                textView.setText(setDecimalFormat(item.getPrice()));
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                if (TextUtils.isEmpty(item.getPrice2())) {
                    item.setPrice2(item.getPrice());
                    accountList.set(position, item);
                }
                viewHolder.setText(R.id.money_one_text, setDecimalFormat(item.getPrice2()));//单价

                if (TextUtils.isEmpty(item.getPrice1())) {
                    String sumberStr = (Double.parseDouble(item.getPrice2()) * Integer.parseInt(item.getCount())) + "";
                    item.setPrice1(sumberStr);
                    accountList.set(position, item);
                }
                //编辑后的单价乘以数量，小计的总价
                viewHolder.setText(R.id.money_count_text, setDecimalFormat(item.getPrice1()));//小计

                //单价编辑
                viewHolder.setOnClickListener(R.id.money_one_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View view = DialogUtil.inputDialog(ShoppingCartActivity.this, R.layout.dialog_input);
                        Button confirmBtn = view.findViewById(R.id.confirm_btn);
                        final EditText contentText = view.findViewById(R.id.content_text);
                        contentText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        contentText.setHint("请输入修改金额");
                        confirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                item.setPrice2(contentText.getText().toString());
                                item.setPrice1((Double.parseDouble(item.getPrice2()) * Integer.parseInt(item.getCount())) + "");
                                viewHolder.setText(R.id.money_one_text, setDecimalFormat(item.getPrice2()));
                                viewHolder.setText(R.id.money_count_text, setDecimalFormat(item.getPrice1()));
                                viewHolder.setText(R.id.discounts_text,setDecimalFormat((Double.parseDouble(item.getPrice2())/Double.parseDouble(item.getPrice()))+""));//折扣
                                accountList.set(position, item);
                                sumData();
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
                        contentText.setHint("请输入修改金额");
                        confirmBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                item.setPrice1(contentText.getText().toString());
                                item.setPrice2(setDecimalFormat((Double.parseDouble(item.getPrice1()) / Integer.parseInt(item.getCount())) + ""));
                                viewHolder.setText(R.id.money_count_text, setDecimalFormat(item.getPrice1()));
                                viewHolder.setText(R.id.money_one_text, setDecimalFormat(item.getPrice2()));
                                viewHolder.setText(R.id.discounts_text,setDecimalFormat((Double.parseDouble(item.getPrice2())/Double.parseDouble(item.getPrice()))+""));//折扣
                                accountList.set(position, item);
                                sumData();
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
                        contentText.setHint("请输入折扣值");
                        confirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                item.setPrice2((Double.parseDouble(item.getPrice()) * Double.parseDouble(setDecimalFormat(contentText.getText().toString()))) + "");//单价(原价*折扣，折扣永远保持两位数)
                                item.setPrice1((Double.parseDouble(item.getPrice2()) * Integer.parseInt(item.getCount())) + "");//小计
                                viewHolder.setText(R.id.discounts_text,setDecimalFormat(contentText.getText().toString()));//折扣
                                viewHolder.setText(R.id.money_count_text, setDecimalFormat(item.getPrice1()));//显示小计
                                viewHolder.setText(R.id.money_one_text, setDecimalFormat(item.getPrice2()));//显示总价
                                accountList.set(position, item);
                                sumData();
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
                numberPickerView.setmOnClickBtnListener(new NumberPickerView.OnClickBtnListener() {
                    @Override
                    public void textChanged(int inputText) {
                        int positionTag = (Integer) numberPickerView.getTag();
                        accountList.get(positionTag).setCount(inputText + "");
                        accountList.get(positionTag).setPrice1((Double.parseDouble(accountList.get(positionTag).getPrice2()) * inputText) + "");
                        viewHolder.setText(R.id.money_count_text, setDecimalFormat(accountList.get(positionTag).getPrice1()));
                        sumData();
                    }
                });

                //删除
                viewHolder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        accountList.remove(position);
                        sumData();
                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
                        commonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        projectList.setAdapter(commonAdapter);
        sumData();
    }

    @OnClick({R.id.return_btn, R.id.add_btn, R.id.submit_btn})
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
                intent.putExtra("accountList", (Serializable) accountList);
                startActivityForResult(intent, 1);
                break;
            case R.id.submit_btn://结算
                if (!TextUtils.isEmpty(getIntent().getStringExtra(ShoppingCartActivity.CART_TAG))) {//商品添加标识，需要传递编辑过的商品集合
                    intent = new Intent();
                    intent.putExtra("accountList", (Serializable) accountList);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (accountList.size() > 0) {
                        intent = new Intent();
                        intent.setClass(ShoppingCartActivity.this, OrderActivity.class);
                        intent.putExtra("accountList", (Serializable) accountList);
                        startActivity(intent);
                    } else {
                        ToastUtils.showToast("暂无商品可结算，请先添加商品");
                    }
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            accountList.clear();
            accountList.addAll((List<ProductDetailsEntity>) data.getExtras().get("accountList"));
            commonAdapter.notifyDataSetChanged();
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

    private void sumData() {
        moneySum = 0;
        for (ProductDetailsEntity productDetailsEntity : accountList) {
            if (TextUtils.isEmpty(productDetailsEntity.getPrice1())) {
                moneySum += (Double.parseDouble(productDetailsEntity.getPrice()) * Integer.parseInt(productDetailsEntity.getCount()));
            } else {
                moneySum += Double.parseDouble(productDetailsEntity.getPrice1());
            }
        }
        moneyText.setText(setDecimalFormat(moneySum + ""));
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
