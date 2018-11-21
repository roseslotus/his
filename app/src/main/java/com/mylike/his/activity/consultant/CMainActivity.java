package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.mylike.his.R;
import com.mylike.his.activity.LoginActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.fragment.consultant.ActivityFragment;
import com.mylike.his.fragment.consultant.CHomeFragment;
import com.mylike.his.fragment.consultant.CustomerFragment;
import com.mylike.his.fragment.consultant.SalesFragment;
import com.mylike.his.fragment.consultant.StatisticsFragment;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.BanSlidViewPager;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 咨询师主页
 */
public class CMainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tb_title)
    TabLayout tbTitle;
    @BindView(R.id.vp_content)
    BanSlidViewPager vpContent;
    @BindView(R.id.update_btn)
    Button updateBtn;
    @BindView(R.id.exit_btn)
    Button exitBtn;
    @BindView(R.id.filtrate_menu)
    LinearLayout filtrateMenu;
    @BindView(R.id.DrawerLayout)
    android.support.v4.widget.DrawerLayout DrawerLayout;

    private Fragment[] mFragments = new Fragment[5];

    public static String GO_CHARGE = "go_charge";//跳转收费单
    public static String GO_OA = "go_oa";//跳转OA
    public static String GO_PAYMENT = "go_payment";//跳转支付

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_c_main);
        ButterKnife.bind(this);

        //禁止筛选侧滑动
        DrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

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
    protected void onStart() {
        super.onStart();
        CommonUtil.updataApp(this, false);
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

    //打开菜单
    public void openMenu() {
        DrawerLayout.openDrawer(filtrateMenu);
    }

    @OnClick({R.id.exit_btn, R.id.update_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_btn://版本更新
                CommonUtil.updataApp(CMainActivity.this, true);
                break;
            case R.id.exit_btn://退出
                DrawerLayout.closeDrawer(filtrateMenu);
                exitLogin();
               /* View view = DialogUtil.hintDialog(this, "是否确认退出？");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitLogin();
                    }
                });*/
                break;
        }
    }

    //退出登录
    private void exitLogin() {
//        DialogUtil.dismissDialog();//关闭 退出提示弹框
        CommonUtil.showLoadProgress(this);

        HttpClient.getHttpApi().exitLongin().enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                SPUtils.clearCache(SPUtils.FILE_USER);//清空账户信息缓存
                startActivity(LoginActivity.class);
                finish();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                SPUtils.clearCache(SPUtils.FILE_USER);//清空账户信息缓存
                startActivity(LoginActivity.class);
                finish();
            }
        });
    }


}
