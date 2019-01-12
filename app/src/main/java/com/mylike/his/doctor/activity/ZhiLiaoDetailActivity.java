package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thl on 2019/1/1.
 */

public class ZhiLiaoDetailActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;


    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas = new ArrayList<>();
    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhiliao_detail);
        ButterKnife.bind(this);
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        commonAdapter = new CommonAdapter<String>(this, R.layout.item_zhiliao_detail, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                LinearLayout llChildPanel = holder.getView(R.id.ll_child_panel);
                View childview1 = LayoutInflater.from(ZhiLiaoDetailActivity.this).inflate(R.layout.item_zhiliao_child_detail,null,false);
                llChildPanel.addView(childview1);
                View childview2 = LayoutInflater.from(ZhiLiaoDetailActivity.this).inflate(R.layout.item_zhiliao_child_detail,null,false);
                llChildPanel.addView(childview2);
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
