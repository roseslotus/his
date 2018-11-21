package com.mylike.his.fragment.consultant;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.ProductChildrenEntity;
import com.mylike.his.entity.ProductDetailsEntity;
import com.mylike.his.entity.ProductsThreeLevelEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.SPUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by zhengluping on 2018/1/2.
 * 任务
 */
public class ProductDetailsFragment extends BaseFragment {
    Unbinder unbinder;

    @BindView(R.id.product_details_list)
    ListView productDetailsList;

    //        private List<String> content;
    private List<ProductChildrenEntity> productChildrenEntities;

    //        public static ProductDetailsFragment newInstance(List<String> content) {
//        Bundle args = new Bundle();
//        args.putStringArrayList("content", (ArrayList<String>) content);
//        ProductDetailsFragment fragment = new ProductDetailsFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
    public static ProductDetailsFragment newInstance(String type, String pid) {
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("pid", pid);
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();


//        content = getArguments().getStringArrayList("content");

//        productDetailsList.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_product_details_list, content) {
//            @Override
//            protected void convert(ViewHolder viewHolder, String item, int position) {
//                viewHolder.setText(R.id.product_name, item);
//            }
//        });

        return view;
    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", getArguments().getString("pid"));
        map.put("type", getArguments().getString("type"));

        HttpClient.getHttpApi().getFindProduct(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<ProductDetailsEntity>>() {
            @Override
            protected void onSuccess(final List<ProductDetailsEntity> productDetailsEntities) {
                productDetailsList.setAdapter(new CommonAdapter<ProductDetailsEntity>(getActivity(), R.layout.item_product_details_list, productDetailsEntities) {
                    @Override
                    protected void convert(ViewHolder viewHolder, ProductDetailsEntity item, int position) {
                        viewHolder.setText(R.id.product_name, item.getPkgname());
                        viewHolder.setText(R.id.money_text, "￥" + item.getPrice());
                    }
                });
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
