package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by thl on 2018/12/29.
 */

public class DoctorMessageFragment extends BaseFragment {

    @BindView(R.id.message_list)
    ListView mMessageList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private View view;
    private Unbinder unbinder;

    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas;

    public static  DoctorMessageFragment newInstance(){
        Bundle args = new Bundle();
        DoctorMessageFragment fragment = new DoctorMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_doctor_message, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatas =new ArrayList<>();
        loadData();
        commonAdapter = new CommonAdapter<String>(getActivity(),R.layout.item_doctor_message,mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                holder.setText(R.id.tv_title,item);
                if (position%3==0){
                    holder.setImageResource(R.id.iv_message_type,R.mipmap.icon_doctor_menzhen);
                }else if(position%3==1){
                    holder.setImageResource(R.id.iv_message_type,R.mipmap.icon_doctor_shoushu);
                }else if(position%3==2){
                    holder.setImageResource(R.id.iv_message_type,R.mipmap.icon_doctor_zhuyuan);
                }

            }
        };

        mMessageList.setAdapter(commonAdapter);

        setListener();
    }

    private void loadData(){
        mDatas.add("您有2个门诊消息点击查看详情");
        mDatas.add("您有2个手术消息点击查看详情");
        mDatas.add("您有2个住院消息点击查看详情");
    }

    private void setListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshLayout.finishRefresh();
                loadData();
                commonAdapter.notifyDataSetChanged();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
