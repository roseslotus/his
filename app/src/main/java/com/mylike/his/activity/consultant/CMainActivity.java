package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.WindowManager;

import com.mylike.his.R;
import com.mylike.his.activity.LoginActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.fragment.consultant.ActivityFragment;
import com.mylike.his.fragment.consultant.CHomeFragment;
import com.mylike.his.fragment.consultant.CustomerFragment;
import com.mylike.his.fragment.consultant.SalesFragment;
import com.mylike.his.fragment.consultant.StatisticsFragment;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.BanSlidViewPager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 咨询师主页
 */
public class CMainActivity extends BaseActivity {

    @Bind(R.id.tb_title)
    TabLayout tbTitle;
    @Bind(R.id.vp_content)
    BanSlidViewPager vpContent;

    private Fragment[] mFragments = new Fragment[5];

    public static String GO_CHARGE = "go_charge";
    public static String GO_OA = "go_oa";
    public static String GO_PAYMENT = "go_payment";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_c_main);
        ButterKnife.bind(this);

        if (TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))) {
            startActivity(LoginActivity.class);
            finish();
        }

        if (!TextUtils.isEmpty(getIntent().getStringExtra(GO_CHARGE))) {//跳转收费单
            startActivity(ChargeShowActivity.class);

        } else if (!TextUtils.isEmpty(getIntent().getStringExtra(GO_OA))) {//跳转收费单，再跳转OA
            startActivity(ChargeShowActivity.class, GO_OA, getIntent().getStringExtra(GO_OA));

        } else if (!TextUtils.isEmpty(getIntent().getStringExtra(GO_PAYMENT))) {//跳转收费单，再跳转支付
            Intent intent = new Intent();
            intent.putExtra(GO_PAYMENT, getIntent().getStringExtra(GO_PAYMENT));
            intent.putExtra("money", getIntent().getStringExtra("money"));
            intent.setClass(CMainActivity.this, ChargeShowActivity.class);
            startActivity(intent);
        }

        setFragment();
        vpContent.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tbTitle.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpContent));
        vpContent.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbTitle));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))) {
            startActivity(LoginActivity.class);
            finish();
        }

        if (!TextUtils.isEmpty(getIntent().getStringExtra(GO_CHARGE))) {//跳转收费单
            startActivity(ChargeShowActivity.class);

        } else if (!TextUtils.isEmpty(getIntent().getStringExtra(GO_OA))) {//跳转收费单，再跳转OA
            startActivity(ChargeShowActivity.class, GO_OA, getIntent().getStringExtra(GO_OA));

        } else if (!TextUtils.isEmpty(getIntent().getStringExtra(GO_PAYMENT))) {//跳转收费单，再跳转支付
            intent.setClass(CMainActivity.this, ChargeShowActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CHomeFragment cHomeFragments = (CHomeFragment) mFragments[0];
        cHomeFragments.initData();

    }

    private void setFragment() {
        mFragments[0] = CHomeFragment.newInstance();//首页fragment
        mFragments[1] = CustomerFragment.newInstance();//客户fragment
        mFragments[2] = StatisticsFragment.newInstance();//统计fragment
        mFragments[3] = SalesFragment.newInstance();//销售Fragment
        mFragments[4] = ActivityFragment.newInstance();//活动Fragment
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
