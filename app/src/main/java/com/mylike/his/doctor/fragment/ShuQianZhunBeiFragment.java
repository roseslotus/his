package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;

/**
 * Created by thl on 2018/12/31.
 */

public class ShuQianZhunBeiFragment extends BaseFragment {

    public static ShuQianZhunBeiFragment newInstance(){
        ShuQianZhunBeiFragment fragment= new ShuQianZhunBeiFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shuqian_zhunbei,null,false);
        return rootView;
    }
}
