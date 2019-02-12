package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.FenZhenInfoResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2018/12/31.
 */

public class MenZhenFenZhenInfoFragment extends BaseFragment {

    @BindView(R.id.tv_fenzhen_time)
    TextView mTvFenzhenTime;
    @BindView(R.id.tv_project_name)
    TextView mTvProjectName;
    @BindView(R.id.tv_zhuzhi_yisheng)
    TextView mTvZhuzhiYisheng;
    @BindView(R.id.tv_fenzhen_yisheng)
    TextView mTvFenzhenYisheng;
    @BindView(R.id.tv_zixunshi)
    TextView mTvZixunshi;
    @BindView(R.id.tv_chuzhen_fuzhen)
    TextView mTvChuzhenFuzhen;
    private View view;
    private Unbinder unbinder;

    public static MenZhenFenZhenInfoFragment newInstance(String registId) {
        MenZhenFenZhenInfoFragment fragment = new MenZhenFenZhenInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("registId",registId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menzhen_fenzhen_info, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        getFenZhenInfo(getArguments().getString("registId"));
        return rootView;
    }

    public void getFenZhenInfo(String registId) {
        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getFenZhenInfo(BaseApplication.getLoginEntity().getTenantId(), registId).enqueue(new Callback<FenZhenInfoResp>() {
            @Override
            public void onResponse(Call<FenZhenInfoResp> call, Response<FenZhenInfoResp> response) {
                CommonUtil.dismissLoadProgress();
                FenZhenInfoResp data =response.body();
                mTvFenzhenTime.setText(data.getTriageTime());
                mTvProjectName.setText(data.getProductsName());
                mTvZhuzhiYisheng.setText(data.getDoctor());
                mTvFenzhenYisheng.setText(data.getTriageDoc());
                mTvZixunshi.setText(data.getConsultant());
                mTvChuzhenFuzhen.setText(data.getType());

            }

            @Override
            public void onFailure(Call<FenZhenInfoResp> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
