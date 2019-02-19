package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.doctor.activity.XiaofeijiluDetailActivity;
import com.mylike.his.entity.BinLiJiLuBean;
import com.mylike.his.entity.BinLiJiLuResp;
import com.mylike.his.entity.FenZhenInfoResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.BusnessUtil;
import com.mylike.his.utils.CommonUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 治疗登记
 * Created by thl on 2018/12/30.
 */

public class CustomerFilesBinlixinxiFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private CommonAdapter<BinLiJiLuBean> commonAdapter;
    private List<BinLiJiLuBean> mDatas =  new ArrayList<>();

    public static CustomerFilesBinlixinxiFragment newInstance(String registId,String cusId){
        CustomerFilesBinlixinxiFragment fragment= new CustomerFilesBinlixinxiFragment();
        Bundle bundle = new Bundle();
        bundle.putString("registId",registId);
        bundle.putString("cusId",cusId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_files_jiuzhengjilu, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        getBinLiJiLu(getArguments().getString("registId"),getArguments().getString("cusId"));

        commonAdapter = new CommonAdapter<BinLiJiLuBean>(getActivity(),R.layout.item_customer_files_binlixinxi,mDatas) {
            @Override
            protected void convert(ViewHolder holder, BinLiJiLuBean item, int position) {
                holder.setText(R.id.tv_project_name,item.getEmrSummary());
                TextView tvDocumentFileStatus = holder.getView(R.id.tv_document_file_status);
                BusnessUtil.setBinLiRecordStatus(tvDocumentFileStatus,item.getStatus());
                holder.setText(R.id.tv_binren_name,item.getCreater());
                holder.setText(R.id.tv_create_time,item.getCreateTime());
                holder.setText(R.id.tv_depart_name,item.getDepName());
                holder.setText(R.id.tv_update_time,item.getUpdateTime());
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

    public void getBinLiJiLu(String registId,String cusId) {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getBinLiJiLu(BaseApplication.getLoginEntity().getTenantId(),BaseApplication.getLoginEntity().getDefaultDepId(), registId,cusId)
                .enqueue(new Callback<List<BinLiJiLuBean>>() {
            @Override
            public void onResponse(Call<List<BinLiJiLuBean>> call, Response<List<BinLiJiLuBean>> response) {
//                CommonUtil.dismissLoadProgress();
                mDatas.clear();
                if (response!=null&&response.body()!=null){
                    mDatas.addAll(response.body());
                }
                commonAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<BinLiJiLuBean>> call, Throwable t) {
//                CommonUtil.dismissLoadProgress();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
