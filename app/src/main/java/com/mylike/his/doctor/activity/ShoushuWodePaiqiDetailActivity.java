package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.fragment.CustomerFilesBinlixinxiFragment;
import com.mylike.his.doctor.fragment.CustomerFilesChufangjiluFragment;
import com.mylike.his.doctor.fragment.CustomerFilesJianchajiluFragment;
import com.mylike.his.doctor.fragment.CustomerFilesZhiliaodengjiFragment;
import com.mylike.his.doctor.fragment.MenZhenFenZhenInfoFragment;
import com.mylike.his.doctor.fragment.ShouShuPaiQiDetailFragment;
import com.mylike.his.doctor.fragment.ShuQianZhunBeiFragment;
import com.mylike.his.doctor.fragment.XiangMuMingXiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thl on 2018/12/30.
 */

public class ShoushuWodePaiqiDetailActivity extends BaseActivity {
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
        setContentView(R.layout.activity_shoushu_wode_paiqi_detail);
        ButterKnife.bind(this); setFragment();
        mVpCustomer.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTbTitle.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpCustomer));
        mVpCustomer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTbTitle));
    }


    private void setFragment() {
        mFragments[0] = ShouShuPaiQiDetailFragment.newInstance();//首页fragment
        mFragments[1] = CustomerFilesJianchajiluFragment.newInstance();//客户fragment
        mFragments[2] = XiangMuMingXiFragment.newInstance();//统计fragment
        mFragments[3] = ShuQianZhunBeiFragment.newInstance();

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
