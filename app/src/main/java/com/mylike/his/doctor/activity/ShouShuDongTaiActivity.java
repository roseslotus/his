package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

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
 * Created by thl on 2019/1/2.
 */

public class ShouShuDongTaiActivity  extends BaseActivity{
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas = new ArrayList<>();
    private String mName;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", -1);
        setContentView(R.layout.activity_shoushu_dongtai);
        ButterKnife.bind(this);
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");

        commonAdapter = new CommonAdapter<String>(this, R.layout.item_shoushu_wode_paitai_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {

            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ShoushuWodePaiTaiDetailActivity.class);
            }
        });

    }


}
