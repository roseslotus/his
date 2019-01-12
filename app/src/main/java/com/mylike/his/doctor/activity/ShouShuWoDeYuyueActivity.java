package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手术 我的预约
 * Created by thl on 2018/12/31.
 */

public class ShouShuWoDeYuyueActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_shuaixuan_panel)
    LinearLayout mLlShuaixuanPanel;
    @BindView(R.id.tv_left_title)
    TextView mTvLeftTitle;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.tv_right_title)
    TextView mTvRightTitle;


    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas = new ArrayList<>();
    private String mName;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", -1);
        setContentView(R.layout.activity_wode_yuyue);
        ButterKnife.bind(this);
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");

        commonAdapter = new CommonAdapter<String>(this, R.layout.item_shoushu_wode_yuyue_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {

            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ShouShuYuyueDetailActivity.class);
            }
        });

        if (type==1){
            mTvLeftTitle.setVisibility(View.VISIBLE);
            mTvCenterTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setVisibility(View.VISIBLE);
        }else if (type==2){
            mTvLeftTitle.setVisibility(View.GONE);
            mTvCenterTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setVisibility(View.GONE);
            mTvCenterTitle.setText("已接诊：10");
        }
    }


    @OnClick({R.id.ll_shuaixuan_panel})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_shuaixuan_panel:
                List<String> choiceList = new ArrayList<>();
                if (type==1){
                    choiceList.add("未缴费");
                    choiceList.add("全款");
                    choiceList.add("预约金");
                }else {
                    choiceList.add("接诊中");
                    choiceList.add("已诊");
                    choiceList.add("已取消");
                }

                new ChoiceZhuyuanShuaixuanPopupMenu().setDatas(choiceList).show(getFragmentManager(), "ChoiceZhuyuanShuaixuanPopupMenu");

                break;
            case R.id.tv_left_title:
                break;
            case R.id.tv_center_title:
                break;
            case R.id.tv_right_title:
                break;
        }
    }
}
