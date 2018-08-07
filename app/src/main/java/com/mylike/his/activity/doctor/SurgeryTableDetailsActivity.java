package com.mylike.his.activity.doctor;

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
import com.mylike.his.fragment.consultant.TaskFragment;
import com.mylike.his.fragment.doctor.CheckRecordFragment;
import com.mylike.his.fragment.doctor.DiagnosticInformationFragment;
import com.mylike.his.fragment.doctor.SurgeryCourseFragment;
import com.mylike.his.fragment.doctor.SurgeryDetailsFragment;
import com.mylike.his.fragment.doctor.SurgeryExamineFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/3/31.
 */

public class SurgeryTableDetailsActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.surgery_tablayout)
    TabLayout surgeryTablayout;
    @Bind(R.id.surgery_viewpager)
    ViewPager surgeryViewpager;


    private String[] tabTitles = new String[]{"手术详情", "手术进程", "手术文书", "检查记录", "临时医嘱", "诊断信息", "会诊记录",};
    private Fragment[] mFragments = new Fragment[7];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgery_table_details);
        ButterKnife.bind(this);
        setFragment();
        surgeryViewpager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager()));
        surgeryTablayout.setupWithViewPager(surgeryViewpager);
    }

    private void setFragment() {
        mFragments[0] = SurgeryDetailsFragment.newInstance();//手术详情
        mFragments[1] = SurgeryCourseFragment.newInstance();//手术进程
        mFragments[2] = SurgeryExamineFragment.newInstance();//手术文书
        mFragments[3] = CheckRecordFragment.newInstance();//检查记录
        mFragments[4] = TaskFragment.newInstance();//临时医嘱
        mFragments[5] = DiagnosticInformationFragment.newInstance();//诊断信息
        mFragments[6] = TaskFragment.newInstance();//会诊记录
    }


    class FragmentPageAdapter extends FragmentPagerAdapter {

        public FragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    @OnClick({R.id.return_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
