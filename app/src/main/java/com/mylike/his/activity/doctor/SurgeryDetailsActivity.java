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
import com.mylike.his.fragment.doctor.HospitalDetailsFragment;
import com.mylike.his.fragment.doctor.MedicalRecordsFragment;
import com.mylike.his.fragment.doctor.SurgeryExamineFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/3/13.
 */

public class SurgeryDetailsActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.surgery_tablayout)
    TabLayout surgeryTablayout;
    @Bind(R.id.surgery_viewpager)
    ViewPager surgeryViewpager;

    private Fragment[] mFragments = new Fragment[6];
    private String[] tabTitles;

    private String tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgery_details);
        ButterKnife.bind(this);

        setFragment();

        surgeryViewpager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager()));
        surgeryTablayout.setupWithViewPager(surgeryViewpager);
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

    private void setFragment() {
        tabTitles = new String[]{"住院详情", "检查记录", "病历信息", "手术记录", "诊疗记录"};
        mFragments[0] = HospitalDetailsFragment.newInstance();//住院详情fragment
        mFragments[1] = CheckRecordFragment.newInstance();//检查记录fragment
        mFragments[2] = MedicalRecordsFragment.newInstance();//病历信息Fragment
        mFragments[3] = SurgeryExamineFragment.newInstance();//手术记录Fragment
        mFragments[4] = TaskFragment.newInstance();//诊疗记录Fragment
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
}
