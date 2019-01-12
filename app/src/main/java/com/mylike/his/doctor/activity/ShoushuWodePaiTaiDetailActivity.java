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
import com.mylike.his.doctor.fragment.CustomerFilesJianchajiluFragment;
import com.mylike.his.doctor.fragment.CustomerFilesKehuzhaopianFragment;
import com.mylike.his.doctor.fragment.JianKangPingGuFragment;
import com.mylike.his.doctor.fragment.ShouShuPaiQiDetailFragment;
import com.mylike.his.doctor.fragment.ShouShuPaiTaiJinChengFragment;
import com.mylike.his.doctor.fragment.ShouShuWenShuFragment;
import com.mylike.his.doctor.fragment.ShoushujiluFragment;
import com.mylike.his.doctor.fragment.ShuQianZhunBeiFragment;
import com.mylike.his.doctor.fragment.XiangMuMingXiFragment;
import com.mylike.his.doctor.fragment.YiZhuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thl on 2018/12/30.
 */

public class ShoushuWodePaiTaiDetailActivity extends BaseActivity {
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
        setContentView(R.layout.activity_shoushu_wode_paitai_detail);
        ButterKnife.bind(this); setFragment();
        mVpCustomer.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTbTitle.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpCustomer));
        mVpCustomer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTbTitle));
    }


    private void setFragment() {
        mFragments[0] = ShoushujiluFragment.newInstance();
        mFragments[1] = ShouShuPaiTaiJinChengFragment.newInstance();//客户fragment
        mFragments[2] = ShouShuWenShuFragment.newInstance();//统计fragment
        mFragments[3] = CustomerFilesJianchajiluFragment.newInstance();
        mFragments[4] = YiZhuFragment.newInstance();
        mFragments[5] = JianKangPingGuFragment.newInstance();
        mFragments[6] = CustomerFilesKehuzhaopianFragment.newInstance();

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
