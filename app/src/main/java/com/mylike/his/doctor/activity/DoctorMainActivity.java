package com.mylike.his.doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.WindowManager;

import com.mylike.his.R;
import com.mylike.his.activity.consultant.CMainActivity;
import com.mylike.his.activity.consultant.ChargeShowActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.fragment.DoctorCustomerFragment;
import com.mylike.his.doctor.fragment.DoctorMessageFragment;
import com.mylike.his.doctor.fragment.MineFragment;
import com.mylike.his.doctor.fragment.WorkbenchFragment;
import com.mylike.his.fragment.consultant.ActivityFragment;
import com.mylike.his.fragment.consultant.CHomeFragment;
import com.mylike.his.fragment.consultant.CustomerFragment;
import com.mylike.his.fragment.consultant.SalesFragment;
import com.mylike.his.fragment.consultant.StatisticsFragment;
import com.mylike.his.view.BanSlidViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thl on 2018/12/29.
 */

public class DoctorMainActivity extends BaseActivity {

    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    @BindView(R.id.vp_content)
    BanSlidViewPager vpContent;

    private Fragment[] mFragments = new Fragment[4];
    @BindView(R.id.DrawerLayout)
    android.support.v4.widget.DrawerLayout drawerLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        setStatusBarColor(true);
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode
//                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
//                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        setContentView(R.layout.activity_d_doctor_main);

        ButterKnife.bind(this);

        //禁止筛选侧滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        setFragment();
        vpContent.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tbTitle.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpContent));
        vpContent.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbTitle));
    }


    private void setFragment() {
        mFragments[0] = WorkbenchFragment.newInstance();//首页fragment
        mFragments[1] = DoctorMessageFragment.newInstance();//客户fragment
        mFragments[2] = DoctorCustomerFragment.newInstance();//统计fragment
        mFragments[3] = MineFragment.newInstance();
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
            return tbTitle.getTabCount();
        }
    }


}
