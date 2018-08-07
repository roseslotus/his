package com.mylike.his.fragment.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;


/**
 * Created by zhengluping on 2018/1/2.
 */

public class SurgeryExamineFragment extends BaseFragment {


    public static SurgeryExamineFragment newInstance() {
        Bundle args = new Bundle();
        SurgeryExamineFragment fragment = new SurgeryExamineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_surgery_examine, container, false);
    }
}
