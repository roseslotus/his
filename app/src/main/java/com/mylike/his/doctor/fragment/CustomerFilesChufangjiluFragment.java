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
import com.mylike.his.doctor.activity.ChuFangDetailActivity;
import com.mylike.his.doctor.activity.XiaofeijiluDetailActivity;
import com.mylike.his.entity.MenZhenChuFangJiLuBean;
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

public class CustomerFilesChufangjiluFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;


    private CommonAdapter<MenZhenChuFangJiLuBean> commonAdapter;
    private List<MenZhenChuFangJiLuBean> mDatas =  new ArrayList<>();
    private String registId;
    private String cusId;
    public static CustomerFilesChufangjiluFragment newInstance(String registId,String cusId){
        CustomerFilesChufangjiluFragment fragment= new CustomerFilesChufangjiluFragment();
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
            cusId = getArguments().getString(Constacts.CUSID);
        }
        commonAdapter = new CommonAdapter<MenZhenChuFangJiLuBean>(getActivity(),R.layout.item_customer_files_chufangjilu,mDatas) {
            @Override
            protected void convert(ViewHolder holder, MenZhenChuFangJiLuBean item, int position) {
                holder.setText(R.id.tv_chufang_time,item.getPresTime());
                holder.setText(R.id.tv_chufang_leixing,"处方类型:"+item.getType());
                holder.setText(R.id.tv_chufang_yisheng,item.getDoctor());
                holder.setText(R.id.tv_zhenduan_wenti,item.getDiagnosis());
                holder.setText(R.id.tv_chufang_status,item.getStatus());
                TextView tvChuFangStatus = holder.getView(R.id.tv_chufang_status);
                BusnessUtil.setChuFangJiLuStatus(tvChuFangStatus,item.getStatus());
            }
        };

        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Intent intent =  new Intent(getActivity(),ChuFangDetailActivity.class);
              intent.putExtra(Constacts.CONTENT_DATA,mDatas.get(i).getPresId());
              startActivity(intent);
            }
        });
        getChuFangJiLu();
        return rootView;
    }

    public void getChuFangJiLu() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getChuFangJiLu(BaseApplication.getLoginEntity().getTenantId(),BaseApplication.getLoginEntity().getDefaultDepId(), registId,cusId)
                .enqueue(new Callback<List<MenZhenChuFangJiLuBean>>() {
                    @Override
                    public void onResponse(Call<List<MenZhenChuFangJiLuBean>> call, Response<List<MenZhenChuFangJiLuBean>> response) {
//                CommonUtil.dismissLoadProgress();
                        mDatas.clear();
                        if (response!=null&&response.body()!=null){
                            mDatas.addAll(response.body());
                        }
                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<MenZhenChuFangJiLuBean>> call, Throwable t) {
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
