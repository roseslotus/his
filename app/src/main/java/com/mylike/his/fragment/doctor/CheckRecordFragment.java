package com.mylike.his.fragment.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.activity.doctor.CheckRecordDetailsActivity;
import com.mylike.his.core.BaseFragment;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhengluping on 2018/3/15.
 * 检查记录
 */
public class CheckRecordFragment extends BaseFragment {
    Unbinder unbinder;

    @BindView(R.id.check_record_list)
    ListView checkRecordList;

    private List<String> date = new ArrayList<>();

    public static CheckRecordFragment newInstance() {
        Bundle args = new Bundle();
        CheckRecordFragment fragment = new CheckRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_record, container, false);
        unbinder = ButterKnife.bind(this, view);
        setDate();

        checkRecordList.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_check_record_list, date) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
            }
        });

        checkRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(CheckRecordDetailsActivity.class);
            }
        });

        return view;
    }

    private void setDate() {
        for (int i = 0; i < 10; i++) {
            date.add("aaa" + i);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
