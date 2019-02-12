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

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.doctor.activity.JianChajiluBChaoDetailActivity;
import com.mylike.his.doctor.activity.JianChajiluDrDetailActivity;
import com.mylike.his.doctor.activity.JianChajiluXinDianTuDetailActivity;
import com.mylike.his.doctor.activity.JianChajiluXueChangGuiOrGanGongNengDetailActivity;
import com.mylike.his.entity.InspectRecordListBean;
import com.mylike.his.entity.MenZhenZhiLiaoDengJiBean;
import com.mylike.his.http.HttpClient;
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
 *
 * Created by thl on 2018/12/30.
 */

public class CustomerFilesJianchajiluFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private CommonAdapter<InspectRecordListBean> commonAdapter;
    private List<InspectRecordListBean> mDatas =  new ArrayList<>();

    public static CustomerFilesJianchajiluFragment newInstance(){
        CustomerFilesJianchajiluFragment fragment= new CustomerFilesJianchajiluFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_files_jianchajilu, null, false);
        unbinder = ButterKnife.bind(this, rootView);

        commonAdapter = new CommonAdapter<InspectRecordListBean>(getActivity(),R.layout.item_customer_files_jianchajilu,mDatas) {
            @Override
            protected void convert(ViewHolder holder, InspectRecordListBean item, int position) {

            }
        };

        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    startActivity(JianChajiluBChaoDetailActivity.class);
                }else if (i==1){
                    startActivity(JianChajiluDrDetailActivity.class);
                }else if (i==2){
                    startActivity(JianChajiluXinDianTuDetailActivity.class);
                }else if (i==3){
                    Intent intent =new Intent(getActivity(),JianChajiluXueChangGuiOrGanGongNengDetailActivity.class);
                    intent.putExtra("type",1);
                    startActivity(intent);
                }else if (i==4){
                    Intent intent =new Intent(getActivity(),JianChajiluXueChangGuiOrGanGongNengDetailActivity.class);
                    intent.putExtra("type",2);
                    startActivity(intent);
                }

            }
        });
        getInspectRecordList();
        return rootView;
    }


    public void getInspectRecordList() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getInspectRecordList(BaseApplication.getLoginEntity().getTenantId(),"66461e438c824fa1adf852be9b5369a5","")
                .enqueue(new Callback<List<InspectRecordListBean>>() {
                    @Override
                    public void onResponse(Call<List<InspectRecordListBean>> call, Response<List<InspectRecordListBean>> response) {
//                CommonUtil.dismissLoadProgress();
                        mDatas.clear();
                        if (response!=null&&response.body()!=null){
                            mDatas.addAll(response.body());
                        }
                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<InspectRecordListBean>> call, Throwable t) {
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
