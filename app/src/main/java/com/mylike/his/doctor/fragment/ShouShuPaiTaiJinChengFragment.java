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
import com.mylike.his.entity.OperationMySchedulingDetailResp;
import com.mylike.his.entity.OperationProcessResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.Constacts;

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

public class ShouShuPaiTaiJinChengFragment extends BaseFragment {

    @BindView(R.id.tv_preparation_time)
    TextView mTvPreparationTime;
    @BindView(R.id.tv_ennter_opera_time)
    TextView mTvEnnterOperaTime;
    @BindView(R.id.tv_anesthesia_start_time)
    TextView mTvAnesthesiaStartTime;
    @BindView(R.id.tv_opera_start_time)
    TextView mTvOperaStartTime;
    @BindView(R.id.tv_opera_end_time)
    TextView mTvOperaEndTime;
    @BindView(R.id.tv_anesthesia_end_time)
    TextView mTvAnesthesiaEndTime;
    @BindView(R.id.tv_out_opera_room_time)
    TextView mTvOutOperaRoomTime;
    private View view;
    private Unbinder unbinder;
    private String registId;

    public static ShouShuPaiTaiJinChengFragment newInstance(String registId) {
        ShouShuPaiTaiJinChengFragment fragment = new ShouShuPaiTaiJinChengFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constacts.CONTENT_DATA,registId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shoushu_paitai_jincheng, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            registId = getArguments().getString(Constacts.CONTENT_DATA);
        }
        getOperationProcess();
        return rootView;
    }

    public void getOperationProcess() {
        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getOperationProcess(BaseApplication.getLoginEntity().getTenantId(), registId)
                .enqueue(new Callback<OperationProcessResp>() {
                    @Override
                    public void onResponse(Call<OperationProcessResp> call, Response<OperationProcessResp> response) {
                        CommonUtil.dismissLoadProgress();
                        bindData(response.body());

                    }

                    @Override
                    public void onFailure(Call<OperationProcessResp> call, Throwable t) {
                        CommonUtil.dismissLoadProgress();
                    }
                });
    }

    private void bindData(OperationProcessResp body) {
        mTvPreparationTime.setText(body.getPreparationTime());
        mTvEnnterOperaTime.setText(body.getEnterOperaRoom());
        mTvAnesthesiaStartTime.setText(body.getAnesthesiaStartTime());
        mTvOperaStartTime.setText(body.getOperaStartTime());
        mTvOperaEndTime.setText(body.getOperaEndTime());
        mTvAnesthesiaEndTime.setText(body.getAnesthesiaEndTime());
        mTvOutOperaRoomTime.setText(body.getOutOperaRoom());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
