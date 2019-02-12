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

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.fragment.CustomerFilesBinlixinxiFragment;
import com.mylike.his.doctor.fragment.CustomerFilesChufangjiluFragment;
import com.mylike.his.doctor.fragment.CustomerFilesJianchajiluFragment;
import com.mylike.his.doctor.fragment.CustomerFilesJiuZhengjiluFragment;
import com.mylike.his.doctor.fragment.CustomerFilesKehuzhaopianFragment;
import com.mylike.his.doctor.fragment.CustomerFilesXiaofeijiluFragment;
import com.mylike.his.doctor.fragment.CustomerFilesZhiliaodengjiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thl on 2018/12/30.
 */

public class CustomerFilesDetailActivity extends BaseActivity {
    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.tb_title)
    TabLayout mTbTitle;
    @BindView(R.id.vp_customer)
    ViewPager mVpCustomer;

    private Fragment[] mFragments = new Fragment[7];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_files_detail);
        ButterKnife.bind(this); setFragment();
        mVpCustomer.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTbTitle.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpCustomer));
        mVpCustomer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTbTitle));
    }


    private void setFragment() {
        mFragments[0] = CustomerFilesJiuZhengjiluFragment.newInstance();//首页fragment
        mFragments[1] = CustomerFilesZhiliaodengjiFragment.newInstance();//客户fragment
        mFragments[2] = CustomerFilesBinlixinxiFragment.newInstance("","");//统计fragment
        mFragments[3] = CustomerFilesChufangjiluFragment.newInstance();
        mFragments[4] = CustomerFilesXiaofeijiluFragment.newInstance();
        mFragments[5] = CustomerFilesKehuzhaopianFragment.newInstance();
        mFragments[6] = CustomerFilesJianchajiluFragment.newInstance();
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
