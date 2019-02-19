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
import com.mylike.his.doctor.fragment.CustomerFilesBinlixinxiFragment;
import com.mylike.his.doctor.fragment.CustomerFilesChufangjiluFragment;
import com.mylike.his.doctor.fragment.CustomerFilesZhiliaodengjiFragment;
import com.mylike.his.doctor.fragment.MenZhenFenZhenInfoFragment;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.WoDeDaiZhenItemBean;
import com.mylike.his.utils.CustomerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thl on 2018/12/30.
 */

public class MenZhenDetailActivity extends BaseActivity  {
    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.tb_title)
    TabLayout mTbTitle;
    @BindView(R.id.vp_customer)
    ViewPager mVpCustomer;
    @BindView(R.id.ll_detail_header_info)
    LinearLayout mLlDetailHeaderInfo;

    WoDeDaiZhenItemBean woDeDaiZhenItemBean;

    private Fragment[] mFragments = new Fragment[7];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menzhen_detail);
        ButterKnife.bind(this);

        woDeDaiZhenItemBean = (WoDeDaiZhenItemBean) getIntent().getSerializableExtra("WoDeDaiZhenItemBean");
        bindData();
        setFragment();
        mVpCustomer.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTbTitle.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpCustomer));
        mVpCustomer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTbTitle));
    }

    private void bindData() {
        CustomerMenZhenBean customer = woDeDaiZhenItemBean.getCustomer();
        CustomerUtil.setCustomerDetailHeaderInfo(mLlDetailHeaderInfo,customer);
    }


    private void setFragment() {
        mFragments[0] = MenZhenFenZhenInfoFragment.newInstance(woDeDaiZhenItemBean.getRegistId());//首页fragment
        mFragments[1] = CustomerFilesBinlixinxiFragment.newInstance(woDeDaiZhenItemBean.getRegistId(),"");//客户fragment
        mFragments[2] = CustomerFilesZhiliaodengjiFragment.newInstance(woDeDaiZhenItemBean.getRegistId(),"");//统计fragment
        mFragments[3] = CustomerFilesChufangjiluFragment.newInstance(woDeDaiZhenItemBean.getRegistId(),"");

    }

    @OnClick({R.id.return_btn, R.id.iv_customer_head_image, R.id.tv_customer_name, R.id.tv_customer_sex, R.id.tv_customer_vip_level, R.id.tv_customer_vip_flag, R.id.tv_customer_bingli_no, R.id.tv_customer_year_and_birth, R.id.tv_customer_mobile_no, R.id.tb_title, R.id.vp_customer})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.iv_customer_head_image:
                break;
            case R.id.tv_customer_name:
                break;
            case R.id.tv_customer_sex:
                break;
            case R.id.tv_customer_vip_level:
                break;
            case R.id.tv_customer_vip_flag:
                break;
            case R.id.tv_customer_bingli_no:
                break;
            case R.id.tv_customer_year_and_birth:
                break;
            case R.id.tv_customer_mobile_no:
                break;
            case R.id.tb_title:
                break;
            case R.id.vp_customer:
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
