package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
 * Created by thl on 2018/12/31.
 */

public class YiZhuDetailActivity extends BaseActivity {


    @BindView(R.id.list_view)
    ListView mListView;
    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yizhu_detail);
        ButterKnife.bind(this);
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        commonAdapter = new CommonAdapter<String>(this, R.layout.item_yizhu_zhixing_detail, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {

            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ZhuyuanDetailActivity.class);
            }
        });
    }


}
