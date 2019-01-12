package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手术 检查记录 血常规 或者肝功能
 * Created by thl on 2019/1/1.
 */

public class JianChajiluXueChangGuiOrGanGongNengDetailActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private int type;


    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas = new ArrayList<>();
    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianchajilu_xuechanggui_or_gangongneng_detail);
        ButterKnife.bind(this);
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");

        type=getIntent().getIntExtra("type",1);
        if (type == 1) {
            mTvTitle.setText("血常规");
        }else {
            mTvTitle.setText("肝功能四项");
        }
        commonAdapter = new CommonAdapter<String>(this, R.layout.item_jianchajilu_xuechanggui, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {

            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(MenZhenDetailActivity.class);
            }
        });
    }


}
