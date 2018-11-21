package com.mylike.his.activity.consultant;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.VisitCDEntity;
import com.mylike.his.entity.VisitTypeEntity;
import com.mylike.his.fragment.consultant.VisitFragment;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.view.SListView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/6/21.
 */
public class VisitActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.reception_tablayout)
    TabLayout receptionTablayout;
    @BindView(R.id.reception_viewpager)
    ViewPager receptionViewpager;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.filtrate_btn)
    ImageView filtrateBtn;
    @BindView(R.id.filtrate_list)
    SListView filtrateList;
    @BindView(R.id.clear_text)
    TextView clearText;
    @BindView(R.id.start_time_text)
    TextView startTimeText;
    @BindView(R.id.start_time_btn)
    LinearLayout startTimeBtn;
    @BindView(R.id.end_time_text)
    TextView endTimeText;
    @BindView(R.id.end_time_btn)
    LinearLayout endTimeBtn;
    @BindView(R.id.reset_btn)
    Button resetBtn;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.filtrate_menu)
    LinearLayout filtrateMenu;
    @BindView(R.id.DrawerLayout)
    android.support.v4.widget.DrawerLayout DrawerLayout;
    @BindView(R.id.clear_text2)
    TextView clearText2;
    @BindView(R.id.start_time_text2)
    TextView startTimeText2;
    @BindView(R.id.start_time_btn2)
    LinearLayout startTimeBtn2;
    @BindView(R.id.end_time_text2)
    TextView endTimeText2;
    @BindView(R.id.end_time_btn2)
    LinearLayout endTimeBtn2;

    private String tabTitles[] = new String[]{"待回访", "已回访"};

    //筛选数据
    private TagAdapter tagAdapter;
    private CommonAdapter commonAdapter1;
    private List<VisitTypeEntity> visitTypeEntities = new ArrayList<>();

    //标签存储数据
    Map<String, String> selectedValue = new HashMap<>();
    Map<Integer, Set<Integer>> selectedMap = new HashMap<Integer, Set<Integer>>();

    //时间选择器
    private TimePickerView TimePV1;
    private TimePickerView TimePV2;
    private TimePickerView TimePV3;
    private TimePickerView TimePV4;
    Calendar startDate = Calendar.getInstance();//选择器开始时间
    Calendar endDate = Calendar.getInstance();//选择结束时间
    Calendar today = Calendar.getInstance();//当天

    private VisitCDEntity visitCDEntity = new VisitCDEntity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        receptionViewpager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager()));
        receptionTablayout.setupWithViewPager(receptionViewpager);

        //初始化适配器
        initAdapter2();

        //时间初始化
        startDate.set(1900, 0, 1);
        endDate.set(2200, 11, 31);

        //时间器初始化
        initTimeView1();
        initTimeView2();
        initTimeView3();
        initTimeView4();
    }

    private void initData() {
        HttpClient.getHttpApi().getVisitType().enqueue(new BaseBack<VisitTypeEntity>() {
            @Override
            protected void onSuccess(VisitTypeEntity visitTypeEntity) {
                visitTypeEntities.addAll(visitTypeEntity.getList());
                commonAdapter1.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //筛选适配器
    private void initAdapter2() {
        final float scale = this.getResources().getDisplayMetrics().density;
        commonAdapter1 = new CommonAdapter<VisitTypeEntity>(VisitActivity.this, R.layout.item_filtrate_product_list, visitTypeEntities) {
            @Override
            protected void convert(final ViewHolder viewHolder, final VisitTypeEntity item, int position) {
                viewHolder.setText(R.id.cover_name, item.getDomainText());
                TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
                tagAdapter = new TagAdapter(item.getSub()) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) LayoutInflater.from(VisitActivity.this).inflate(R.layout.item_text_label, null);
                        textView.setTextSize(12);
                        textView.setWidth((filtrateMenu.getWidth() / 3) - (int) (10 * scale + 0.5f));
                        textView.setPadding(0, 30, 0, 30);
                        textView.setGravity(Gravity.CENTER);

                        textView.setText(item.getSub().get(position).getDomainText());
                        return textView;
                    }
                };
                tagFlowLayout.setAdapter(tagAdapter);

                tagAdapter.setSelectedList(selectedMap.get(viewHolder.getItemPosition()));
                tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        selectedMap.put(viewHolder.getItemPosition(), selectPosSet);
                        String str = "";
                        for (int i : selectPosSet) {
                            if (str.isEmpty())
                                str += item.getSub().get(i).getDomainValue();
                            else
                                str += "," + item.getSub().get(i).getDomainValue();
                        }
                        selectedValue.put(item.getDomainValue(), str);
                    }
                });
            }
        };
        filtrateList.setAdapter(commonAdapter1);
    }

    //开始时间选择器
    private void initTimeView1() {
        TimePV1 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                startTimeText.setText(CommonUtil.getYMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(today)//默认数据
                .build();
    }

    //结束时间选择器
    private void initTimeView2() {
        TimePV2 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                endTimeText.setText(CommonUtil.getYMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(today)//默认数据
                .build();
    }

    //开始时间选择器
    private void initTimeView3() {
        TimePV3 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                startTimeText2.setText(CommonUtil.getYMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(today)//默认数据
                .build();
    }

    //结束时间选择器
    private void initTimeView4() {
        TimePV4 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                endTimeText2.setText(CommonUtil.getYMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(today)//默认数据
                .build();
    }


    @OnClick({R.id.return_btn, R.id.filtrate_btn, R.id.reset_btn, R.id.confirm_btn, R.id.start_time_btn, R.id.end_time_btn, R.id.clear_text, R.id.start_time_btn2, R.id.end_time_btn2, R.id.clear_text2})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_btn://返回
                finish();
                break;
            case R.id.reset_btn://筛选重置
                selectedMap.clear();
                selectedValue.clear();
                startTimeText.setText("");
                endTimeText.setText("");
                startTimeText2.setText("");
                endTimeText2.setText("");
                commonAdapter1.notifyDataSetChanged();
                break;
            case R.id.confirm_btn://筛选确认
                DrawerLayout.closeDrawer(filtrateMenu);
                visitCDEntity.setPlanTimeStart(startTimeText.getText().toString());
                visitCDEntity.setPlanTimeEnd(endTimeText.getText().toString());
                visitCDEntity.setVisitTimeStart(startTimeText2.getText().toString());
                visitCDEntity.setVisitTimeEnd(startTimeText2.getText().toString());
                String str = "";
                for (String key : selectedValue.keySet()) {
                    if (!TextUtils.isEmpty(str))
                        str += ",";
                    str += selectedValue.get(key);
                }
                visitCDEntity.setVisitType(str);
                EventBus.getDefault().post(visitCDEntity);
                break;
            case R.id.filtrate_btn://消息筛选
                DrawerLayout.openDrawer(filtrateMenu);
                break;
            case R.id.start_time_btn://开始时间
                TimePV1.show();
                break;
            case R.id.end_time_btn://结束时间
                TimePV2.show();
                break;
            case R.id.start_time_btn2://开始时间
                TimePV3.show();
                break;
            case R.id.end_time_btn2://结束时间
                TimePV4.show();
                break;
            case R.id.clear_text://清除时间
                startTimeText.setText("");
                endTimeText.setText("");
                break;
            case R.id.clear_text2://清除时间
                startTimeText2.setText("");
                endTimeText2.setText("");
                break;
        }
    }

    class FragmentPageAdapter extends FragmentPagerAdapter {

        public FragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return VisitFragment.newInstance(tabTitles[position]);
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

    @Override
    protected void onStart() {
        super.onStart();
        receptionTablayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(receptionTablayout, 60, 60);
            }
        });
    }

    //修改下划线的宽度
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
