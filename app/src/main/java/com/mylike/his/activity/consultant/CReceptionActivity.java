//package com.mylike.his.activity.consultant;
//
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.TypedValue;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.mylike.his.R;
//import com.mylike.his.core.BaseActivity;
//import com.mylike.his.fragment.consultant.CReceptionFragment;
//
//import java.lang.reflect.Field;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by zhengluping on 2018/1/23.
// * 接诊列表
// **/
//public class CReceptionActivity extends BaseActivity implements View.OnClickListener {
//    @BindView(R.id.reception_tablayout)
//    TabLayout receptionTablayout;
//    @BindView(R.id.reception_viewpager)
//    ViewPager receptionViewpager;
//    @BindView(R.id.return_btn)
//    ImageView returnBtn;
//
//    private String tabTitles[] = new String[]{"未接诊", "已接诊"};
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reception_consultant);
//        ButterKnife.bind(this);
//
//        receptionViewpager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager()));
//        receptionTablayout.setupWithViewPager(receptionViewpager);
//    }
//
//    @OnClick({R.id.return_btn})
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.return_btn://返回
//                finish();
//                break;
////            case R.id.search_btn://搜索
////                break;
//        }
//    }
//
//    class FragmentPageAdapter extends FragmentPagerAdapter {
//
//        public FragmentPageAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return CReceptionFragment.newInstance(tabTitles[position]);
//        }
//
//        @Override
//        public int getCount() {
//            return tabTitles.length;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabTitles[position];
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        receptionTablayout.post(new Runnable() {
//            @Override
//            public void run() {
//                setIndicator(receptionTablayout, 60, 60);
//            }
//        });
//    }
//
//    //修改下划线的宽度
//    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
//        Class<?> tabLayout = tabs.getClass();
//        Field tabStrip = null;
//        try {
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        tabStrip.setAccessible(true);
//        LinearLayout llTab = null;
//        try {
//            llTab = (LinearLayout) tabStrip.get(tabs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
//        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
//
//        for (int i = 0; i < llTab.getChildCount(); i++) {
//            View child = llTab.getChildAt(i);
//            child.setPadding(0, 0, 0, 0);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.leftMargin = left;
//            params.rightMargin = right;
//            child.setLayoutParams(params);
//            child.invalidate();
//        }
//    }
//}
