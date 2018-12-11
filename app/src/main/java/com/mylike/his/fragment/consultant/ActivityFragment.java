package com.mylike.his.fragment.consultant;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.consultant.ProductActivity;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.ProductChildrenEntity;
import com.mylike.his.entity.ProductDetailsEntity;
import com.mylike.his.entity.ProductEntity;
import com.mylike.his.entity.ProductsThreeLevelEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by zhengluping on 2018/1/2.
 * 活动-产品
 */
public class ActivityFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.combo_btn)
    LinearLayout comboBtn;
    @BindView(R.id.product_btn)
    LinearLayout productBtn;
    @BindView(R.id.detail_btn)
    LinearLayout detailBtn;
    @BindView(R.id.screen_ll)
    LinearLayout screenLl;
    @BindView(R.id.menu_list)
    ListView menuList;
    @BindView(R.id.sublevel_list)
    ListView sublevelList;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.combo_text)
    TextView comboText;
    @BindView(R.id.product_text)
    TextView productText;
    @BindView(R.id.detail_text)
    TextView detailText;

    Unbinder unbinder;
    @BindView(R.id.tag_ll)
    LinearLayout tagLl;
    @BindView(R.id.product_list)
    ListView productList;

    private PopupWindow projectPW;
    private String typeValue = "COMBO";

    private CommonAdapter commonAdapter;
    private CommonAdapter commonAdapter2;
    private CommonAdapter commonAdapter3;


    ProductsThreeLevelEntity productsThreeLevel = new ProductsThreeLevelEntity();//头部数据（包括左侧数据）
    List<ProductChildrenEntity> productChildrenEntities = new ArrayList<>();//左侧菜单数据
    private List<ProductDetailsEntity> productDetailsEntityList = new ArrayList<>();//右侧菜单数据
    private List<ProductEntity> productEntityList = new ArrayList<>();//搜索出来的产品数据


    public static ActivityFragment newInstance() {
        Bundle args = new Bundle();
        ActivityFragment fragment = new ActivityFragment();
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
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        unbinder = ButterKnife.bind(this, view);
        typeValue = "COMBO";
        initView();
        initData();
        return view;
    }

    private void initView() {
        //左侧菜单列表
        commonAdapter = new CommonAdapter<ProductChildrenEntity>(getActivity(), R.layout.item_menu_list, productChildrenEntities) {
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
        commonAdapter2 = new CommonAdapter<ProductDetailsEntity>(getActivity(), R.layout.item_product_details_list, productDetailsEntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, ProductDetailsEntity item, int position) {
                viewHolder.setVisible(R.id.add_img, false);
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
                viewHolder.setText(R.id.money_text, item.getPrice() != null ? item.getPrice().toString() : "0");
            }
        };
        sublevelList.setAdapter(commonAdapter2);

        //搜索产品适配器
        commonAdapter3 = new CommonAdapter<ProductEntity>(getActivity(), R.layout.item_product_details_list, productEntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, ProductEntity item, int position) {
                viewHolder.setVisible(R.id.add_img, false);
                switch (item.getType()) {
                    case "COMBO"://套餐
                        viewHolder.setText(R.id.product_name, "[套餐] " + item.getPname());
                        break;
                    case "SUBJECT"://产品
                        viewHolder.setText(R.id.product_name, "[产品] " + item.getPname());
                        break;
                    case "MINUTIA"://细目
                        viewHolder.setText(R.id.product_name, "[细目] " + item.getPname());
                        break;
                }
                viewHolder.setText(R.id.money_text, item.getPrice() != null ? item.getPrice().toString() : "0");
            }
        };
        productList.setAdapter(commonAdapter3);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    productList.setVisibility(View.GONE);
                }
            }
        });
    }


    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("deptId", "");//筛选

        HttpClient.getHttpApi().getProductAll(HttpClient.getRequestBody(map)).enqueue(new BaseBack<ProductsThreeLevelEntity>() {
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


    @OnClick({R.id.combo_btn, R.id.product_btn, R.id.detail_btn, R.id.search_btn})
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

        listView.setAdapter(new CommonAdapter<ProductChildrenEntity>(getActivity(), R.layout.item_filtrate_product_list, EntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, ProductChildrenEntity item, int position) {
                viewHolder.setText(R.id.cover_name, item.getPname());
                final List<ProductChildrenEntity> productChildrenEntity = item.getChildren();
                TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
                tagFlowLayout.setAdapter(new TagAdapter(productChildrenEntity) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.common_item_text, null);
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
                productList.setVisibility(View.VISIBLE);
                productEntityList.clear();
                productEntityList.addAll(productEntities);
                commonAdapter3.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

   /* private void search() {
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
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
