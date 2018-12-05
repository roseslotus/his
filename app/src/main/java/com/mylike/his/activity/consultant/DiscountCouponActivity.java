package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.Constant;
import com.mylike.his.entity.DCProductEntity;
import com.mylike.his.entity.DiscountCouponEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.view.ClearEditText;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/11/24
 * 优惠券
 */
public class DiscountCouponActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.search_edit)
    ClearEditText searchEdit;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.tag_ll)
    LinearLayout tagLl;
    @BindView(R.id.list_view)
    ListView listView;

    //优惠券数据
    private CommonAdapter commonAdapter;
    private List<DiscountCouponEntity> discountCouponEntityList = new ArrayList<>();

    //购买的产品
    private DCProductEntity dcProductEntity = new DCProductEntity();

    //回传
    private DiscountCouponEntity discountCouponEntity = new DiscountCouponEntity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_coupon);
        ButterKnife.bind(this);
        dcProductEntity = (DCProductEntity) getIntent().getSerializableExtra("dcProductEntity");
        initView();
        initData();
    }

    //初始化优惠券数据
    private void initView() {
        commonAdapter = new CommonAdapter<DiscountCouponEntity>(this, R.layout.item_discount_coupon, discountCouponEntityList) {
            @Override
            protected void convert(ViewHolder viewHolder, DiscountCouponEntity item, int position) {
//                LinearLayout item_ll = viewHolder.getView(R.id.item);

                viewHolder.setText(R.id.activity_name, item.getActivityName());//活动名
                viewHolder.setText(R.id.time, item.getStartTime() + " 至 " + item.getEndTime());//开始时间至结束时间

                switch (item.getActivityType()) {
                    case Constant.DC_ZK://折扣
                        viewHolder.setBackgroundColor(R.id.dc_text, getResources().getColor(R.color.blue_53));
                        viewHolder.setText(R.id.dc_text, "折扣");

                        break;
                    case Constant.DC_MJ://满减
                        viewHolder.setBackgroundColor(R.id.dc_text, getResources().getColor(R.color.pink_50));
                        viewHolder.setText(R.id.dc_text, "满减");

                        break;
                    case Constant.DC_ZS://赠送
                        viewHolder.setBackgroundColor(R.id.dc_text, getResources().getColor(R.color.purple_40));
                        viewHolder.setText(R.id.dc_text, "赠送");

                        break;
                    case Constant.DC_XX://签呈
                        viewHolder.setBackgroundColor(R.id.dc_text, getResources().getColor(R.color.blue_30));
                        viewHolder.setText(R.id.dc_text, "签呈");

                        break;
                }

                //初始化隐藏显示的控件
                /*viewHolder.setVisible(R.id.money1, false);//赠送价
                viewHolder.setVisible(R.id.money2, false);//满减价
                viewHolder.setVisible(R.id.discount_ll, false);//折扣布局
                viewHolder.setVisible(R.id.gift_img, false);//礼物图标

                //优惠券类型
                switch (item.getActivityType()) {
                    case Constant.DC_ZK://折扣
                        item_ll.setBackgroundResource(R.mipmap.dc_blue_bg);
                        viewHolder.setTextColor(R.id.money2, getResources().getColor(R.color.blue_53));
                        viewHolder.setText(R.id.dc_text, "折扣");
                        viewHolder.setBackgroundColor(R.id.dc_text, getResources().getColor(R.color.blue_53));

                        viewHolder.setVisible(R.id.discount_ll, true);
                        viewHolder.setText(R.id.discount_text, (Double.parseDouble(item.getDiscount()) * 10) + "");
                        break;
                    case Constant.DC_MJ://满减
                        item_ll.setBackgroundResource(R.mipmap.dc_pink_bg);
                        viewHolder.setTextColor(R.id.money2, getResources().getColor(R.color.pink_50));
                        viewHolder.setText(R.id.dc_text, "满减");
                        viewHolder.setBackgroundColor(R.id.dc_text, getResources().getColor(R.color.pink_50));

                        viewHolder.setVisible(R.id.money1, true);
                        viewHolder.setText(R.id.money1, item.getLowerMoney());
                        break;
                    case Constant.DC_ZS://赠送
                        item_ll.setBackgroundResource(R.mipmap.dc_purple_bg);
                        viewHolder.setTextColor(R.id.money2, getResources().getColor(R.color.purple_40));
                        viewHolder.setText(R.id.dc_text, "赠送");
                        viewHolder.setBackgroundColor(R.id.dc_text, getResources().getColor(R.color.purple_40));

                        viewHolder.setVisible(R.id.gift_img, true);
                        break;
                }

                if (!TextUtils.isEmpty(item.getPackedMoney())) {
                    viewHolder.setVisible(R.id.money2, true);
                    viewHolder.setText(R.id.money2, "满" + item.getPackedMoney() + "元可用");
                }*/


            }
        };
        listView.setAdapter(commonAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                discountCouponEntity = discountCouponEntityList.get(i);
                Intent intent = new Intent();
                intent.putExtra("discountCouponEntity", (Serializable) discountCouponEntity);
                setResult(Constant.RESULT_DC, intent);
                finish();
            }
        });
    }

    private void initData() {
        //优惠券
        HttpClient.getHttpApi().getDiscountCoupon(HttpClient.getRequestBody(dcProductEntity)).enqueue(new BaseBack<List<DiscountCouponEntity>>() {
            @Override
            protected void onSuccess(List<DiscountCouponEntity> discountCouponEntities) {
                discountCouponEntityList.addAll(discountCouponEntities);
                commonAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    @OnClick({R.id.return_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
