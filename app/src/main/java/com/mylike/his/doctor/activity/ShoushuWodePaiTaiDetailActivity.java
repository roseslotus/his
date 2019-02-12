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
import com.mylike.his.doctor.fragment.CustomerFilesJianchajiluFragment;
import com.mylike.his.doctor.fragment.CustomerFilesKehuzhaopianFragment;
import com.mylike.his.doctor.fragment.JianKangPingGuFragment;
import com.mylike.his.doctor.fragment.ShouShuPaiTaiJinChengFragment;
import com.mylike.his.doctor.fragment.ShouShuWenShuFragment;
import com.mylike.his.doctor.fragment.ShoushujiluFragment;
import com.mylike.his.doctor.fragment.YiZhuFragment;
import com.mylike.his.entity.OperationMyArrangementListBean;
import com.mylike.his.utils.Constacts;
import com.mylike.his.utils.CustomerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    OperationMyArrangementListBean data;
    @BindView(R.id.ll_detail_header_info)
    LinearLayout mLlDetailHeaderInfo;
    private Fragment[] mFragments = new Fragment[7];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoushu_wode_paitai_detail);
        ButterKnife.bind(this);
        data = (OperationMyArrangementListBean) getIntent().getSerializableExtra(Constacts.CONTENT_DATA);
        CustomerUtil.setCustomerDetailHeaderInfo(mLlDetailHeaderInfo,data.getCustomer());
        setFragment();

        mVpCustomer.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTbTitle.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpCustomer));
        mVpCustomer.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTbTitle));
    }


    private void setFragment() {
        mFragments[0] = ShoushujiluFragment.newInstance(data.getRegistId());
        mFragments[1] = ShouShuPaiTaiJinChengFragment.newInstance();//客户fragment
        mFragments[2] = ShouShuWenShuFragment.newInstance();//统计fragment
        mFragments[3] = CustomerFilesJianchajiluFragment.newInstance();
        mFragments[4] = YiZhuFragment.newInstance();
        mFragments[5] = JianKangPingGuFragment.newInstance();
        mFragments[6] = CustomerFilesKehuzhaopianFragment.newInstance();

    }

    @OnClick(R.id.ll_detail_header_info)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_detail_header_info:
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
