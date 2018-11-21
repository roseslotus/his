package com.mylike.his.fragment.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhengluping on 2018/3/12.
 */

public class SurgeryDetailsFragment extends BaseFragment implements View.OnClickListener {
    Unbinder unbinder;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_surgery_details, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public static SurgeryDetailsFragment newInstance() {
        Bundle args = new Bundle();
        SurgeryDetailsFragment fragment = new SurgeryDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
