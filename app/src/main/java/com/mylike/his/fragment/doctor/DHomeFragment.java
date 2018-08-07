package com.mylike.his.fragment.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.utils.DataUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhengluping on 2018/1/2.
 * 医生端-首页
 */

public class DHomeFragment extends BaseFragment {

    @Bind(R.id.list_view)
    ListView listView;

    public static DHomeFragment newInstance() {
        Bundle args = new Bundle();
        DHomeFragment fragment = new DHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_home, container, false);
        ButterKnife.bind(this, view);

        listView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_work_list, DataUtil.getData(10)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
