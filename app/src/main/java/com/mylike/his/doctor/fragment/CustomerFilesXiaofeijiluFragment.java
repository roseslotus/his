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
import com.mylike.his.doctor.activity.XiaofeijiluDetailActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by thl on 2018/12/30.
 */

public class CustomerFilesXiaofeijiluFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas =  new ArrayList<>();

    public static CustomerFilesXiaofeijiluFragment newInstance(){
        CustomerFilesXiaofeijiluFragment fragment= new CustomerFilesXiaofeijiluFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_files_jiuzhengjilu, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        commonAdapter = new CommonAdapter<String>(getActivity(),R.layout.item_customer_files_xiaofeijilu,mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {

            }
        };

        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(XiaofeijiluDetailActivity.class);
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
