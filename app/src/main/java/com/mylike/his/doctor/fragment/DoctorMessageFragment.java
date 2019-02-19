package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.CustomerTreatRecordListBean;
import com.mylike.his.entity.MessageItemListBean;
import com.mylike.his.http.HttpClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private int pageIndex=1;


    private CommonAdapter<MessageItemListBean> commonAdapter;
    private List<MessageItemListBean> mDatas;

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
        commonAdapter = new CommonAdapter<MessageItemListBean>(getActivity(),R.layout.item_doctor_message,mDatas) {
            @Override
            protected void convert(ViewHolder holder, MessageItemListBean item, int position) {
                holder.setText(R.id.tv_title,item.getMsgType());
                holder.setText(R.id.tv_time,item.getCreateDate());
                holder.setText(R.id.tv_message_content,item.getMsgContent());

            }
        };

        mMessageList.setAdapter(commonAdapter);

        setListener();
    }

    public void getMessageList() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getMessageList(BaseApplication.getLoginEntity().getTenantId(),BaseApplication.getLoginEntity().getDefaultDepId(),pageIndex,10)
                .enqueue(new Callback<List<MessageItemListBean>>() {
                    @Override
                    public void onResponse(Call<List<MessageItemListBean>> call, Response<List<MessageItemListBean>> response) {
//                CommonUtil.dismissLoadProgress();
                        if (pageIndex==1){
                            mDatas.clear();
                        }

                        if (response!=null&&response.body()!=null){
                            mDatas.addAll(response.body());
                        }
                        commonAdapter.notifyDataSetChanged();
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();

                    }

                    @Override
                    public void onFailure(Call<List<MessageItemListBean>> call, Throwable t) {
//                CommonUtil.dismissLoadProgress();
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadMore();
                    }
                });
    }

    private void loadData(){
        getMessageList();
    }

    private void setListener() {
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex =1;
                loadData();
                commonAdapter.notifyDataSetChanged();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex =1+pageIndex;
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
