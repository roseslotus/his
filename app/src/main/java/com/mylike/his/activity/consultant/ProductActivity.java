package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.ProductChildrenEntity;
import com.mylike.his.entity.ProductDetailsEntity;
import com.mylike.his.entity.ProductEntity;
import com.mylike.his.entity.ProductsThreeLevelEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.paradoxie.shopanimlibrary.AniManager;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/2/5.
 * 产品选择
 */
public class ProductActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.combo_btn)
    LinearLayout comboBtn;
    @Bind(R.id.product_btn)
    LinearLayout productBtn;
    @Bind(R.id.detail_btn)
    LinearLayout detailBtn;
    @Bind(R.id.screen_ll)
    LinearLayout screenLl;
    @Bind(R.id.menu_list)
    ListView menuList;
    @Bind(R.id.sublevel_list)
    ListView sublevelList;
    @Bind(R.id.search_edit)
    EditText searchEdit;
    @Bind(R.id.money_text)
    TextView moneyText;
    @Bind(R.id.submit_btn)
    TextView submitBtn;
    @Bind(R.id.search_btn)
    Button searchBtn;
    @Bind(R.id.combo_text)
    TextView comboText;
    @Bind(R.id.product_text)
    TextView productText;
    @Bind(R.id.detail_text)
    TextView detailText;

    private PopupWindow projectPW;
    private String typeValue = "COMBO";

    private CommonAdapter commonAdapter;
    private CommonAdapter commonAdapter2;

    private double moneySum;
    private boolean clickTag = true;

    private ProductsThreeLevelEntity productsThreeLevel = new ProductsThreeLevelEntity();//头部数据（包括左侧数据）
    private List<ProductChildrenEntity> productChildrenEntities = new ArrayList<>();//左侧菜单数据
    private List<ProductDetailsEntity> productDetailsEntityList = new ArrayList<>();//右侧菜单数据
    private List<ProductDetailsEntity> accountList = new ArrayList<>();//挑选的产品


    @Override
    protected void onStart() {
        super.onStart();

        //判断是否从购物车过来，不为空则把购物车数据带过来
        if (!TextUtils.isEmpty(getIntent().getStringExtra(ShoppingCartActivity.CART_TAG))) {
            accountList.addAll((List<ProductDetailsEntity>) getIntent().getSerializableExtra("accountList"));
            if (accountList.size() > 0) {
                sumData();
            }
        } else {
            accountList.clear();
            moneyText.setText("0.00");
            clickTag = true;
        }
        //判断“选好了”是否能点击
        setBtnClick();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void setBtnClick() {
        if (accountList.size() <= 0) {
            submitBtn.setClickable(false);
            submitBtn.setText("选好了");
            submitBtn.setBackgroundColor(Color.LTGRAY);
        } else {
            submitBtn.setClickable(true);
            submitBtn.setBackgroundColor(Color.RED);
        }
    }

    private void initView() {
        //左侧菜单列表
        commonAdapter = new CommonAdapter<ProductChildrenEntity>(this, R.layout.item_menu_list, productChildrenEntities) {
            @Override
            protected void convert(ViewHolder viewHolder, ProductChildrenEntity item, int position) {
                TextView textView = viewHolder.getView(R.id.text);
                textView.setPadding(20, 40, 20, 40);
                textView.setText(item.getPname());
            }
        };
        menuList.setAdapter(commonAdapter);
        menuList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        menuList.setItemChecked(0, true);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setSublevelData(productChildrenEntities.get(position).getPid());
            }
        });
        //右侧菜单列表
        commonAdapter2 = new CommonAdapter<ProductDetailsEntity>(this, R.layout.item_product_details_list, productDetailsEntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, ProductDetailsEntity item, int position) {
                if (!TextUtils.isEmpty(searchEdit.getText().toString())) {
                    typeValue = productDetailsEntityList.get(position).getItemLx();
                }
                switch (typeValue) {
                    case "COMBO"://套餐
                        viewHolder.setText(R.id.product_name, item.getPkgname());
                        break;
                    case "SUBJECT"://产品
                        viewHolder.setText(R.id.product_name, item.getPname());
                        break;
                    case "MINUTIA"://细目
                        viewHolder.setText(R.id.product_name, item.getItemName());
                        break;
                }
                viewHolder.setText(R.id.money_text, "" + item.getPrice());
            }
        };
        sublevelList.setAdapter(commonAdapter2);
        sublevelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                double money = 0;
//                int countValue = 0;
                ProductDetailsEntity p = productDetailsEntityList.get(position);

                boolean not_equally_tag = true;
                //循环查询是否有同种产品
                for (ProductDetailsEntity productDetailsEntity : accountList) {
                    int count = Integer.parseInt(productDetailsEntity.getCount());
                    switch (typeValue) {
                        case "COMBO"://套餐
                            if (p.getPkgid().equals(productDetailsEntity.getPkgid())) {
                                not_equally_tag = false;
//                                if (!TextUtils.isEmpty(getIntent().getStringExtra(ShoppingCartActivity.CART_TAG))) {
//                                    //如果是购物车过来的数据，同种产品不能做添加（为防止购物车编辑过后的价格发生改变）
//                                    showToast("产品已存在，不能做添加");
//                                } else {
                                count += 1;
                                productDetailsEntity.setCount(count + "");
//                                }
                            }
                            break;
                        case "SUBJECT"://产品
                            if (p.getProductid().equals(productDetailsEntity.getProductid())) {
                                not_equally_tag = false;
//                                if (!TextUtils.isEmpty(getIntent().getStringExtra(ShoppingCartActivity.CART_TAG))) {
//                                    //如果是购物车过来的数据，同种产品不能做添加（为防止购物车编辑过后的价格发生改变）
//                                    showToast("产品已存在，不能做添加");
//                                } else {
                                count += 1;
                                productDetailsEntity.setCount(count + "");
//                                }
                            }
                            break;
                        case "MINUTIA"://细目
                            if (p.getChaitemCd().equals(productDetailsEntity.getChaitemCd())) {
                                not_equally_tag = false;
//                                if (!TextUtils.isEmpty(getIntent().getStringExtra(ShoppingCartActivity.CART_TAG))) {
//                                    //如果是购物车过来的数据，同种产品不能做添加（为防止购物车编辑过后的价格发生改变）
//                                    showToast("产品已存在，不能做添加");
//                                } else {
                                count += 1;
                                productDetailsEntity.setCount(count + "");
//                                }
                            }
                            break;
                    }
//                    countValue += count;
//                    money += (Double.parseDouble(productDetailsEntity.getPrice()) * count);
                }
                //没有同种产品做添加操作
                if (not_equally_tag) {
                    switch (typeValue) {
                        case "COMBO"://套餐
                            p.setItemLx("套餐");
                            break;
                        case "SUBJECT"://产品
                            p.setItemLx("产品");
                            break;
                        case "MINUTIA"://细目
                            p.setItemLx("细目");
                            break;
                    }

//                    countValue += 1;
//                    money += Double.parseDouble(p.getPrice());
                    p.setCount("1");
                    accountList.add(p);
                }
                if (clickTag) {
                    clickTag = false;
                    setBtnClick();
                }
//                startAnim(view, countValue, money);
                startAnim(view);
            }
        });

    }

    private ImageView buyImg;
    private AniManager mAniManager = new AniManager();

    //启动动画，购物车飞入效果
//    public void startAnim(View v, final int countValue, final double money) {
    public void startAnim(View v) {
        int[] end_location = new int[2];
        int[] start_location = new int[2];
        v.getLocationInWindow(start_location);// 获取购买按钮的在屏幕的X、Y坐标（动画开始的坐标）
        submitBtn.getLocationInWindow(end_location);// 这是用来存储动画结束位置，也就是购物车图标的X、Y坐标
        buyImg = new ImageView(this);// buyImg是动画的图片
        buyImg.setImageResource(R.mipmap.round_red_icon);// 设置buyImg的图片

        mAniManager.setTime(300);//自定义时间
        mAniManager.setAnim(this, buyImg, start_location, end_location);// 开始执行动画
        mAniManager.setOnAnimListener(new AniManager.AnimListener() {
            @Override
            public void setAnimBegin(AniManager a) {
                //动画开始时的监听
            }

            @Override
            public void setAnimEnd(AniManager a) {
                //动画结束后的监听
                sumData();
//                submitBtn.setText("选好了(" + countValue + ")");
//                moneyText.setText(new DecimalFormat("0.00").format(money) + "");
            }
        });
    }


    private void initData() {
        HttpClient.getHttpApi().getProductAll().enqueue(new BaseBack<ProductsThreeLevelEntity>() {
            @Override
            protected void onSuccess(ProductsThreeLevelEntity productsThreeLevelEntity) {
                productsThreeLevel = productsThreeLevelEntity;
                productChildrenEntities.clear();
                productChildrenEntities.addAll(productsThreeLevel.getComboType().get(0).getChildren().get(0).getChildren());
                setSublevelData(productChildrenEntities.get(0).getPid());
                commonAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }


    @OnClick({R.id.combo_btn, R.id.product_btn, R.id.detail_btn, R.id.submit_btn, R.id.return_btn, R.id.search_btn})
    @Override
    public void onClick(View v) {
        Drawable nav_up = getResources().getDrawable(R.mipmap.up_arrow_icon);
        switch (v.getId()) {
            case R.id.combo_btn://套餐
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                comboText.setCompoundDrawables(null, null, nav_up, null);
                typeValue = "COMBO";
                pop_up();
                break;
            case R.id.product_btn://产品
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                productText.setCompoundDrawables(null, null, nav_up, null);
                typeValue = "SUBJECT";
                pop_up();
                break;
            case R.id.detail_btn://细目
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                detailText.setCompoundDrawables(null, null, nav_up, null);
                typeValue = "MINUTIA";
                pop_up();
                break;
            case R.id.search_btn:
                if (TextUtils.isEmpty(searchEdit.getText().toString())) {
                    typeValue = "COMBO";
                    initData();
                } else {
                    search();
                }
                break;
            case R.id.submit_btn:
                if (!TextUtils.isEmpty(getIntent().getStringExtra(ShoppingCartActivity.CART_TAG))) {//商品添加标识，需要传递编辑过的商品集合
                    Intent intent = new Intent();
                    intent.putExtra("accountList", (Serializable) accountList);
                    ProductActivity.this.setResult(RESULT_OK, intent);
                    ProductActivity.this.finish();
                } else {
                    if (accountList.size() <= 0) {
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(ProductActivity.this, ShoppingCartActivity.class);
                        intent.putExtra("accountList", (Serializable) accountList);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

    private void pop_up() {
        ListView listView = setPopupWindow(screenLl);
        listView.setDividerHeight(0);
        listView.setPadding(20, 0, 20, 0);
        List<ProductChildrenEntity> EntityList = new ArrayList<>();

        switch (typeValue) {
            case "COMBO"://套餐
                EntityList = productsThreeLevel.getComboType();
                break;
            case "SUBJECT"://产品
                EntityList = productsThreeLevel.getSubjectProductType();
                break;
            case "MINUTIA"://细目
                EntityList = productsThreeLevel.getMinutiaType();
                break;
        }

        listView.setAdapter(new CommonAdapter<ProductChildrenEntity>(this, R.layout.item_filtrate_product_list, EntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, ProductChildrenEntity item, int position) {
                viewHolder.setText(R.id.cover_name, item.getPname());
                final List<ProductChildrenEntity> productChildrenEntity = item.getChildren();
                TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
                tagFlowLayout.setAdapter(new TagAdapter(productChildrenEntity) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        View view = LayoutInflater.from(ProductActivity.this).inflate(R.layout.common_item_text, null);
                        TextView textView = view.findViewById(R.id.text);
                        textView.setPadding(40, 20, 40, 20);
                        textView.setBackgroundColor(getResources().getColor(R.color.gray_47));
                        textView.setText(productChildrenEntity.get(position).getPname());

                        return textView;
                    }
                });
                tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        productChildrenEntities.clear();
                        productChildrenEntities.addAll(productChildrenEntity.get(position).getChildren());

                        menuList.setItemChecked(0, true);
                        setSublevelData(productChildrenEntities.get(0).getPid());

                        commonAdapter.notifyDataSetChanged();
                        projectPW.dismiss();
                        return true;
                    }
                });
            }
        });


    }

    private ListView setPopupWindow(LinearLayout ll) {
        View view = getLayoutInflater().inflate(R.layout.common_item_list, null);
        projectPW = new PopupWindow(view, ll.getWidth(), 700, true);
        projectPW.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white_box_10));
        projectPW.showAsDropDown(ll);
        projectPW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Drawable nav_below = getResources().getDrawable(R.mipmap.below_arrow_icon);
                switch (typeValue) {
                    case "COMBO"://套餐
                        nav_below.setBounds(0, 0, nav_below.getMinimumWidth(), nav_below.getMinimumHeight());
                        comboText.setCompoundDrawables(null, null, nav_below, null);
                        break;
                    case "SUBJECT"://产品
                        nav_below.setBounds(0, 0, nav_below.getMinimumWidth(), nav_below.getMinimumHeight());
                        productText.setCompoundDrawables(null, null, nav_below, null);
                        break;
                    case "MINUTIA"://细目
                        nav_below.setBounds(0, 0, nav_below.getMinimumWidth(), nav_below.getMinimumHeight());
                        detailText.setCompoundDrawables(null, null, nav_below, null);
                        break;
                }

            }
        });
        ListView listView = view.findViewById(R.id.common_list);
        return listView;
    }

    private void setSublevelData(String pid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", pid);
        map.put("type", typeValue);

        HttpClient.getHttpApi().getFindProduct(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<ProductDetailsEntity>>() {
            @Override
            protected void onSuccess(final List<ProductDetailsEntity> productDetailsEntities) {
                productDetailsEntityList.clear();
                productDetailsEntityList.addAll(productDetailsEntities);
                commonAdapter2.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    private void search() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("searchContent", searchEdit.getText().toString());

        HttpClient.getHttpApi().getSearchProduct(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<ProductEntity>>() {
            @Override
            protected void onSuccess(List<ProductEntity> productEntities) {
                productDetailsEntityList.clear();
                ProductDetailsEntity productDetailsEntity;
                for (ProductEntity productEntity : productEntities) {
                    switch (productEntity.getType()) {
                        case "COMBO"://套餐"
                            productDetailsEntity = new ProductDetailsEntity();
                            productDetailsEntity.setPkgid(productEntity.getPid());
                            productDetailsEntity.setPkgname(productEntity.getPname());
                            productDetailsEntity.setPrice(productEntity.getPrice());
                            productDetailsEntity.setItemLx(productEntity.getType());
                            productDetailsEntityList.add(productDetailsEntity);
                            break;
                        case "SUBJECT"://产品
                            productDetailsEntity = new ProductDetailsEntity();
                            productDetailsEntity.setProductid(productEntity.getPid());
                            productDetailsEntity.setPname(productEntity.getPname());
                            productDetailsEntity.setPrice(productEntity.getPrice());
                            productDetailsEntity.setItemLx(productEntity.getType());
                            productDetailsEntityList.add(productDetailsEntity);
                            break;
                        case "MINUTIA"://细目
                            productDetailsEntity = new ProductDetailsEntity();
                            productDetailsEntity.setChaitemCd(productEntity.getPid());
                            productDetailsEntity.setItemName(productEntity.getPname());
                            productDetailsEntity.setPrice(productEntity.getPrice());
                            productDetailsEntity.setItemLx(productEntity.getType());
                            productDetailsEntityList.add(productDetailsEntity);
                            break;
                    }
                }
                commonAdapter2.notifyDataSetChanged();
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

    private void sumData() {
        moneySum = 0;
        int count = 0;
        Logger.d(accountList);
        for (ProductDetailsEntity productDetailsEntity : accountList) {
            if (TextUtils.isEmpty(productDetailsEntity.getPrice2())) {
                moneySum += (Double.parseDouble(productDetailsEntity.getPrice()) * Integer.parseInt(productDetailsEntity.getCount()));
            } else {
                moneySum += (Double.parseDouble(productDetailsEntity.getPrice2()) * Integer.parseInt(productDetailsEntity.getCount()));
            }
            count += Integer.parseInt(productDetailsEntity.getCount());
        }
        moneyText.setText(setDecimalFormat(moneySum + ""));
        submitBtn.setText("选好了(" + count + ")");
    }
}
