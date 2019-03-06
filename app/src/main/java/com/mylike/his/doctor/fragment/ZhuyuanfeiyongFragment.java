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
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.doctor.activity.XiaofeijiluDetailActivity;
import com.mylike.his.doctor.activity.ZhuyuanFeiyongDetailActivity;
import com.mylike.his.entity.InHospitalCostListBean;
import com.mylike.his.entity.MyInHospitalCostResp;
import com.mylike.his.entity.MyInHospitalDetailResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.Constacts;
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

public class ZhuyuanfeiyongFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;
    private String registId;

    private CommonAdapter<InHospitalCostListBean> commonAdapter;
    private List<InHospitalCostListBean> mDatas =  new ArrayList<>();

    public static ZhuyuanfeiyongFragment newInstance(String registId){
        ZhuyuanfeiyongFragment fragment= new ZhuyuanfeiyongFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constacts.CONTENT_DATA,registId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_files_jiuzhengjilu, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            registId = getArguments().getString(Constacts.CONTENT_DATA);
        }

        commonAdapter = new CommonAdapter<InHospitalCostListBean>(getActivity(),R.layout.item_zhuyuan_feiyong,mDatas) {
            @Override
            protected void convert(ViewHolder holder, InHospitalCostListBean item, int position) {
                holder.setText(R.id.tv_depart_name,item.getDepName());
                holder.setText(R.id.tv_cost_status,item.getStatus());
                holder.setText(R.id.tv_ennter_hospital_date,item.getEnterDate());
                holder.setText(R.id.tv_enter_days,item.getDays());
                holder.setText(R.id.tv_deposit_amount,item.getDeposit());
                holder.setText(R.id.tv_hospital_amount,item.getFee());
                holder.setText(R.id.tv_free_amount,item.getFree());
                holder.setText(R.id.tv_actual_amount,item.getActualFee());
            }
        };

        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ZhuyuanFeiyongDetailActivity.class);
            }
        });
        getInHospitalCost();

        return rootView;
    }


    public void getInHospitalCost() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getInHospitalCost(BaseApplication.getLoginEntity().getTenantId(), registId,1,10,BaseApplication.getLoginEntity().getToken())
                .enqueue(new Callback<MyInHospitalCostResp>() {
                    @Override
                    public void onResponse(Call<MyInHospitalCostResp> call, Response<MyInHospitalCostResp> resp) {
//                CommonUtil.dismissLoadProgress();
                        mDatas.addAll(resp.body().getDataList());
                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<MyInHospitalCostResp> call, Throwable t) {
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
