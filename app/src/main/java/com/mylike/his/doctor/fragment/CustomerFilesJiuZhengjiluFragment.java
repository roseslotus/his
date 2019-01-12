package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by thl on 2018/12/30.
 */

public class CustomerFilesJiuZhengjiluFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas =  new ArrayList<>();

    public static CustomerFilesJiuZhengjiluFragment newInstance(){
        CustomerFilesJiuZhengjiluFragment fragment= new CustomerFilesJiuZhengjiluFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_files_jiuzhengjilu, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        commonAdapter = new CommonAdapter<String>(getActivity(),R.layout.item_customer_files_jiuzhengjilu,mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                if (position==0){
                    holder.getView(R.id.line1).setVisibility(View.INVISIBLE);
                }else {
                    holder.getView(R.id.line1).setVisibility(View.VISIBLE);
                }
                if (position==mDatas.size()-1){
                    holder.getView(R.id.line2).setVisibility(View.INVISIBLE);
                }else {
                    holder.getView(R.id.line2).setVisibility(View.VISIBLE);
                }
            }
        };

        mListView.setAdapter(commonAdapter);
        return rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
