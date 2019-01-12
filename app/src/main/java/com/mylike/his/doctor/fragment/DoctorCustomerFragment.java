package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.doctor.activity.CustomerFilesDetailActivity;
import com.mylike.his.view.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by thl on 2018/12/30.
 */

public class DoctorCustomerFragment extends BaseFragment {
    @BindView(R.id.search_edit)
    ClearEditText mSearchEdit;
    @BindView(R.id.tag_ll)
    LinearLayout mTagLl;
    @BindView(R.id.customer_list)
    ListView mCustomerList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private Unbinder unbinder;

    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas;

    public static DoctorCustomerFragment newInstance() {
        Bundle args = new Bundle();
        DoctorCustomerFragment fragment = new DoctorCustomerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_doctor_customer, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.search_edit, R.id.tag_ll})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.search_edit:
                break;
            case R.id.tag_ll:
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatas =new ArrayList<>();
        loadData();
        commonAdapter = new CommonAdapter<String>(getActivity(),R.layout.item_customer_hone_page,mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {

            }
        };

        mCustomerList.setAdapter(commonAdapter);

        mCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(CustomerFilesDetailActivity.class);
            }
        });

    }

    private void loadData(){
        mDatas.add("您有2个门诊消息点击查看详情");
        mDatas.add("您有2个手术消息点击查看详情");
        mDatas.add("您有2个住院消息点击查看详情");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
