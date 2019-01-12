package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.activity.presener.WoDeDaiZhenPresenter;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.doctor.popup.ChoiceSortPopupMenu;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.mylike.his.entity.DaiZhenResp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的待诊
 * Created by thl on 2018/12/31.
 */

public class WoDeDaizhenActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas = new ArrayList<>();
    private String mName;
    private WoDeDaiZhenPresenter daiZhenPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_daizhen);
        ButterKnife.bind(this);
        daiZhenPresenter = new WoDeDaiZhenPresenter(this);
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        commonAdapter = new CommonAdapter<String>(this, R.layout.item_wode_daizhen_info, mDatas) {
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

        daiZhenPresenter.refresh(new ResponseListener<DaiZhenResp>() {
            @Override
            public void onResponse(DaiZhenResp daiZhenResp) {

            }

            @Override
            public void onError(String message, int errorCode) {

            }
        });

//        daiZhenPresenter.loadMore(new ResponseListener<DaiZhenResp>() {
//            @Override
//            public void onResponse(DaiZhenResp daiZhenResp) {
//
//            }
//
//            @Override
//            public void onError(String message, int errorCode) {
//
//            }
//        });
    }



}
