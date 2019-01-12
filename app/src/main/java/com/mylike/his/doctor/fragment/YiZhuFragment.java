package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.doctor.YiZhuAdapter;
import com.mylike.his.doctor.YiZhuBean;
import com.mylike.his.doctor.activity.YiZhuDetailActivity;
import com.mylike.his.doctor.activity.ZhuyuanFeiyongDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 治疗登记
 * Created by thl on 2018/12/30.
 */

public class YiZhuFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private YiZhuAdapter yiZhuAdapter;
    private List<YiZhuBean> mDatas =  new ArrayList<>();

    public static YiZhuFragment newInstance(){
        YiZhuFragment fragment= new YiZhuFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yizhu, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        YiZhuBean bean = new YiZhuBean();
        bean.setType(0);
        mDatas.add(bean);
        bean = new YiZhuBean();
        bean.setType(1);
        mDatas.add(bean);

        bean = new YiZhuBean();
        bean.setType(1);
        mDatas.add(bean);

        bean = new YiZhuBean();
        bean.setType(1);
        mDatas.add(bean);

        bean = new YiZhuBean();
        bean.setType(1);
        mDatas.add(bean);

        yiZhuAdapter =new YiZhuAdapter(getActivity(),mDatas);

        mListView.setAdapter(yiZhuAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(YiZhuDetailActivity.class);
            }
        });

        return rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
