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
import com.mylike.his.doctor.DoctorAdviceAdapter;
import com.mylike.his.doctor.activity.DoctorAdviceDetailActivity;
import com.mylike.his.entity.DoctorAdviceListBean;
import com.mylike.his.entity.DoctorAdviceOrderMain;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.Constacts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 医嘱
 * Created by thl on 2018/12/30.
 */

public class DoctorAdviceFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private DoctorAdviceAdapter doctorAdviceAdapter;
    private List<DoctorAdviceOrderMain> mDatas =  new ArrayList<>();
    private String registId;

    public static DoctorAdviceFragment newInstance(String registId){
        DoctorAdviceFragment fragment= new DoctorAdviceFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constacts.CONTENT_DATA,registId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yizhu, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        registId = getArguments().getString(Constacts.CONTENT_DATA);
        doctorAdviceAdapter =new DoctorAdviceAdapter(getActivity(),mDatas);

        mListView.setAdapter(doctorAdviceAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),DoctorAdviceDetailActivity.class);
                intent.putExtra(Constacts.CONTENT_DATA,mDatas.get(i).getOrderId());
                startActivity(intent);
            }
        });
        getDoctorAdviceList();
        return rootView;
    }

    public void getDoctorAdviceList() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getDoctorAdviceList(BaseApplication.getLoginEntity().getTenantId(), registId,BaseApplication.getLoginEntity().getToken())
                .enqueue(new Callback<List<DoctorAdviceListBean>>() {
                    @Override
                    public void onResponse(Call<List<DoctorAdviceListBean>> call, Response<List<DoctorAdviceListBean>> resp) {
                        CommonUtil.dismissLoadProgress();
                        bindData(resp.body());

                    }

                    @Override
                    public void onFailure(Call<List<DoctorAdviceListBean>> call, Throwable t) {
                        CommonUtil.dismissLoadProgress();
                    }
                });
    }

    private void bindData(List<DoctorAdviceListBean> body) {
        if (body != null) {
            for (DoctorAdviceListBean bean : body) {
                DoctorAdviceOrderMain doctorAdviceOrderMain = new DoctorAdviceOrderMain();
                doctorAdviceOrderMain.setType(0);
                doctorAdviceOrderMain.setOrderDate(bean.getOrderDate());
                mDatas.add(doctorAdviceOrderMain);
                for (DoctorAdviceOrderMain adviceOrderMain : bean.getOrderMains()) {
                    adviceOrderMain.setType(1);
                    mDatas.add(adviceOrderMain);
                }
            }
        }
        doctorAdviceAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
