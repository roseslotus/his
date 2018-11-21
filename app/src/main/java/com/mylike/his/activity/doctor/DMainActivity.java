package com.mylike.his.activity.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.fragment.doctor.DHomeFragment;
import com.mylike.his.fragment.doctor.HospitalFragment;
import com.mylike.his.fragment.doctor.MessageFragment;
import com.mylike.his.fragment.doctor.OutpatientServiceFragment;
import com.mylike.his.fragment.doctor.SurgeryFragment;
import com.mylike.his.view.BanSlidViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 医生端主页
 */
public class DMainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    @BindView(R.id.vp_content)
    BanSlidViewPager vpContent;

    private Fragment[] mFragments = new Fragment[5];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_main);
        ButterKnife.bind(this);

        setFragment();

        vpContent.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tbTitle.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpContent));
        vpContent.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbTitle));
    }

    private void setFragment() {
        mFragments[0] = DHomeFragment.newInstance();//首页fragment
        mFragments[1] = OutpatientServiceFragment.newInstance();//门诊fragment
        mFragments[2] = SurgeryFragment.newInstance();//手术Fragment
        mFragments[3] = HospitalFragment.newInstance();//住院Fragment
        mFragments[4] = MessageFragment.newInstance();//消息Fragment
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


}
