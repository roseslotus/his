package com.mylike.his.fragment.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mylike.his.R;
import com.mylike.his.activity.doctor.AdviceActivity;
import com.mylike.his.core.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/3/12.
 */

public class HospitalDetailsFragment extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.advice_query_btn)
    Button adviceQueryBtn;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hospital_details, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public static HospitalDetailsFragment newInstance() {
        Bundle args = new Bundle();
        HospitalDetailsFragment fragment = new HospitalDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.advice_query_btn})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.advice_query_btn:
                startActivity(AdviceActivity.class);
                break;
//            case R.id.advance_money_btn:
//                break;
        }
    }
}
