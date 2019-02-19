package com.mylike.his.doctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.doctor.activity.CustomerFilesDetailActivity;
import com.mylike.his.entity.CustomerListBean;
import com.mylike.his.entity.DaiZhenResp;
import com.mylike.his.presener.CustomerListPresenter;
import com.mylike.his.utils.Constacts;
import com.mylike.his.utils.CustomerUtil;
import com.mylike.his.view.ClearEditText;
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

    private CommonAdapter<CustomerListBean> commonAdapter;
    private List<CustomerListBean> mDatas;
    private List<CustomerListBean> mOriglDatas;
    private CustomerListPresenter mPresenter;

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
        mPresenter =new CustomerListPresenter(getActivity());
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh(new ResponseListener<List<CustomerListBean>>() {
                    @Override
                    public void onResponse(List<CustomerListBean> list) {
                        mOriglDatas.clear();
                        mOriglDatas.addAll(list);
                        searchByKey();
                        commonAdapter.notifyDataSetChanged();
                        stopOver();
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver();
                    }
                });
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore(new ResponseListener<List<CustomerListBean>>() {
                    @Override
                    public void onResponse(List<CustomerListBean> list) {

                        mOriglDatas.addAll(list);
                        searchByKey();
                        commonAdapter.notifyDataSetChanged();
                        stopOver();
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver();
                    }
                });
            }
        });
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchByKey();
                commonAdapter.notifyDataSetChanged();
            }
        });


        return rootView;
    }

    private void stopOver(){
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
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
        mOriglDatas =new ArrayList<>();
        searchByKey();
        commonAdapter = new CommonAdapter<CustomerListBean>(getActivity(),R.layout.item_customer_base_info,mDatas) {
            @Override
            protected void convert(ViewHolder holder, CustomerListBean item, int position) {
                CustomerUtil.setCustomerInfo(holder,item.getCustomer());
                TextView tvTime = holder.getView(R.id.tv_booking_time);
                tvTime.setVisibility(View.VISIBLE);
                tvTime.setText(item.getRecentDate());
            }
        };

        mCustomerList.setAdapter(commonAdapter);

        mCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),CustomerFilesDetailActivity.class);
                intent.putExtra(Constacts.CONTENT_DATA,mDatas.get(i));
                startActivity(intent);
            }
        });

    }

    private void searchByKey(){
        String keyword= mSearchEdit.getText().toString();
        mDatas.clear();
        if (TextUtils.isEmpty(keyword)){
            mDatas.addAll(mOriglDatas);
        }else {
            for (CustomerListBean bean : mOriglDatas) {
                if (bean.getCustomer().getCaseNo().contains(keyword)||bean.getCustomer().getCusName().contains(keyword)||bean.getCustomer().getTel().contains(keyword)){
                    mDatas.add(bean);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
