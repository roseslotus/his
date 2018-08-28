package com.mylike.his.fragment.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.ProductChildrenEntity;
import com.mylike.his.entity.ProductsThreeLevelEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.DataUtil;
import com.mylike.his.utils.SPUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.verticaltablayout.VerticalTabLayout;


/**
 * Created by zhengluping on 2018/1/2.
 * 活动-产品
 */
public class ProductFragment<T> extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.reception_tablayout)
    VerticalTabLayout receptionTablayout;
    @Bind(R.id.reception_viewpager)
    ViewPager receptionViewpager;
    @Bind(R.id.combo_btn)
    LinearLayout comboBtn;
    @Bind(R.id.product_btn)
    LinearLayout productBtn;
    @Bind(R.id.detail_btn)
    LinearLayout detailBtn;
    @Bind(R.id.screen_ll)
    LinearLayout screenLl;

    private PopupWindow projectPW;
    private String typeValue = "COMBO";

    public static ProductFragment newInstance() {
        Bundle args = new Bundle();
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }




    private ListView setPopupWindow(LinearLayout ll) {
        View view = getLayoutInflater().inflate(R.layout.common_item_list, null);
        projectPW = new PopupWindow(view, ll.getWidth(), ll.getWidth() / 2, true);
        projectPW.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white_box_10));
        projectPW.showAsDropDown(ll);
        ListView listView = view.findViewById(R.id.common_list);
//        listView.setAdapter(adapter);
        return listView;
    }


    ProductsThreeLevelEntity productsThreeLevel;
    List<ProductChildrenEntity> productChildrenEntities = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        HttpClient.getHttpApi().getProductAll().enqueue(new BaseBack<ProductsThreeLevelEntity>() {
            @Override
            protected void onSuccess(ProductsThreeLevelEntity productsThreeLevelEntity) {
                productsThreeLevel = productsThreeLevelEntity;
                productChildrenEntities = productsThreeLevel.getComboType().get(0).getChildren().get(0).getChildren();
                receptionViewpager.setAdapter(new FragmentPageAdapter(getChildFragmentManager()));
                receptionTablayout.setupWithViewPager(receptionViewpager);
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }


    @OnClick({R.id.combo_btn, R.id.product_btn, R.id.detail_btn})
    @Override
    public void onClick(View v) {
        ListView listView = null;
        switch (v.getId()) {
            case R.id.combo_btn://套餐
                listView = setPopupWindow(screenLl);

                List<ProductChildrenEntity> comboEntityList = productsThreeLevel.getComboType();
                listView.setAdapter(new CommonAdapter<ProductChildrenEntity>(getActivity(), R.layout.item_filtrate_product_list, comboEntityList) {
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
                                typeValue = "COMBO";
                                productChildrenEntities = productChildrenEntity.get(position).getChildren();
                                receptionViewpager.getAdapter().notifyDataSetChanged();

                                projectPW.dismiss();
                                return true;
                            }
                        });
                    }
                });
                break;
            case R.id.product_btn://产品
                listView = setPopupWindow(screenLl);
                List<ProductChildrenEntity> subjectProductEntityList = productsThreeLevel.getSubjectProductType();

                listView.setAdapter(new CommonAdapter<ProductChildrenEntity>(getActivity(), R.layout.item_filtrate_product_list, subjectProductEntityList) {
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
                                typeValue = "SUBJECT";
                                productChildrenEntities = productChildrenEntity.get(position).getChildren();
                                receptionViewpager.setAdapter(new FragmentPageAdapter(getChildFragmentManager()));
                                receptionTablayout.setupWithViewPager(receptionViewpager);
//                                receptionViewpager.getAdapter().notifyDataSetChanged();
//                                receptionTablayout.setupWithViewPager(receptionViewpager);

                                projectPW.dismiss();
                                return true;
                            }
                        });
                    }
                });
                break;
            case R.id.detail_btn://细目
                listView = setPopupWindow(screenLl);
                List<ProductChildrenEntity> minutiaEntityList = productsThreeLevel.getMinutiaType();
                listView.setAdapter(new CommonAdapter<ProductChildrenEntity>(getActivity(), R.layout.item_filtrate_product_list, minutiaEntityList) {
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
                                typeValue = "SUBJECT";
                                productChildrenEntities = productChildrenEntity.get(position).getChildren();
                                receptionViewpager.getAdapter().notifyDataSetChanged();
                                projectPW.dismiss();
                                return true;
                            }
                        });
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        projectSpinner.setText(listData.get(position).getName());
                        projectPW.dismiss();
                    }
                });
                break;
        }
    }

    class FragmentPageAdapter extends FragmentPagerAdapter {
        public FragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ProductDetailsFragment.newInstance(typeValue, productChildrenEntities.get(position).getPid());
        }

        @Override
        public int getCount() {
            return productChildrenEntities.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return productChildrenEntities.get(position).getPname();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
