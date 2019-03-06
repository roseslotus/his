package com.mylike.his.doctor.fragment;

import android.content.Intent;
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
import com.mylike.his.doctor.activity.ZhiLiaoDetailActivity;
import com.mylike.his.entity.BinLiJiLuBean;
import com.mylike.his.entity.MenZhenZhiLiaoDengJiBean;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.BusnessUtil;
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

public class CustomerFilesZhiliaodengjiFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private CommonAdapter<MenZhenZhiLiaoDengJiBean> commonAdapter;
    private List<MenZhenZhiLiaoDengJiBean> mDatas =  new ArrayList<>();
    private String registId;
    private String cusId;

    public static CustomerFilesZhiliaodengjiFragment newInstance(String registId,String cusId){
        CustomerFilesZhiliaodengjiFragment fragment= new CustomerFilesZhiliaodengjiFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constacts.CONTENT_DATA,registId);
        bundle.putString(Constacts.CUSID,cusId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_files_jiuzhengjilu, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            registId = getArguments().getString(Constacts.CONTENT_DATA);
            cusId =getArguments().getString(Constacts.CUSID);
        }
        commonAdapter = new CommonAdapter<MenZhenZhiLiaoDengJiBean>(getActivity(),R.layout.item_customer_files_zhiliaodengji,mDatas) {
            @Override
            protected void convert(ViewHolder holder, MenZhenZhiLiaoDengJiBean item, int position) {
                holder.setText(R.id.tv_treat_time,item.getTreatTime());
                holder.setText(R.id.tv_zhixing_yisheng,item.getExeDoc());
                holder.setText(R.id.tv_zhuzhi_yisheng,item.getDoctor());
                holder.setText(R.id.tv_depart_name,item.getDepName());
                TextView tvTreatStatus = holder.getView(R.id.tv_treat_status);
                BusnessUtil.setZhiLiaoDengJiStatus(tvTreatStatus,item.getStatus());
            }
        };

        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MenZhenZhiLiaoDengJiBean bean = mDatas.get(position);
                Intent intent = new Intent(getActivity(),ZhiLiaoDetailActivity.class);
                intent.putExtra("treatId",bean.getTreatId());
                startActivity(intent);
            }
        });
        getZhiLiaoDengJi();

        return rootView;
    }


    public void getZhiLiaoDengJi() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getZhiLiaoDengJi(BaseApplication.getLoginEntity().getTenantId(),BaseApplication.getLoginEntity().getDefaultDepId(), registId,cusId,BaseApplication.getLoginEntity().getToken())
                .enqueue(new Callback<List<MenZhenZhiLiaoDengJiBean>>() {
                    @Override
                    public void onResponse(Call<List<MenZhenZhiLiaoDengJiBean>> call, Response<List<MenZhenZhiLiaoDengJiBean>> response) {
//                CommonUtil.dismissLoadProgress();
                        mDatas.clear();
                        if (response!=null&&response.body()!=null){
                            mDatas.addAll(response.body());
                        }
                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<MenZhenZhiLiaoDengJiBean>> call, Throwable t) {
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
