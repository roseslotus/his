package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.fragment.CustomerFilesInspectRecordFragment;
import com.mylike.his.doctor.fragment.CustomerFilesJianchajiluFragment;
import com.mylike.his.doctor.fragment.OperationMySchedulingDetailFragment;
import com.mylike.his.doctor.fragment.ShuQianZhunBeiFragment;
import com.mylike.his.doctor.fragment.XiangMuMingXiFragment;
import com.mylike.his.entity.OperationMySchedulingItemBean;
import com.mylike.his.utils.CustomerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thl on 2018/12/30.
 */

public class OperationMySchedulingDetailActivity extends BaseActivity {
    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.tb_title)
    TabLayout mTbTitle;
    @BindView(R.id.vp_customer)
    ViewPager mVpCustomer;
    @BindView(R.id.ll_detail_header_info)
    LinearLayout mLlDetailHeaderInfo;

    private Fragment[] mFragments = new Fragment[4];
    private OperationMySchedulingItemBean data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_my_scheduling_detail);
        ButterKnife.bind(this);

        data = (OperationMySchedulingItemBean) getIntent().getSerializableExtra("data");
        CustomerUtil.setCustomerDetailHeaderInfo(mLlDetailHeaderInfo, data.getCustomer());

        setFragment();
        mVpCustomer.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTbTitle.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpCustomer));
        mVpCustomer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTbTitle));

    }


    private void setFragment() {
        mFragments[0] = OperationMySchedulingDetailFragment.newInstance(data.getRegistId());//首页fragment
        mFragments[1] = CustomerFilesInspectRecordFragment.newInstance(data.getRegistId(),"");//客户fragment
        mFragments[2] = XiangMuMingXiFragment.newInstance(data.getRegistId());//统计fragment
        mFragments[3] = ShuQianZhunBeiFragment.newInstance(data.getRegistId());

    }

    @OnClick(R.id.return_btn)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mTbTitle.getTabCount();
        }
    }
}
