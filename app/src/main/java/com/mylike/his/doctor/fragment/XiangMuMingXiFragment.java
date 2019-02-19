package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.doctor.activity.ZhiLiaoDetailActivity;
import com.mylike.his.entity.ProjectDetailListBean;
import com.mylike.his.http.HttpClient;
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
 * 项目明细
 * Created by thl on 2018/12/30.
 */

public class XiangMuMingXiFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private CommonAdapter<ProjectDetailListBean> commonAdapter;
    private List<ProjectDetailListBean> mDatas =  new ArrayList<>();
    private String registId;
    public static XiangMuMingXiFragment newInstance(String registId){
        XiangMuMingXiFragment fragment= new XiangMuMingXiFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constacts.CONTENT_DATA,registId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_files_jiuzhengjilu, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            registId = getArguments().getString(Constacts.CONTENT_DATA);
        }
        commonAdapter = new CommonAdapter<ProjectDetailListBean>(getActivity(),R.layout.item_xiangmu_mingxi,mDatas) {
            @Override
            protected void convert(ViewHolder holder, ProjectDetailListBean item, int position) {
                holder.setText(R.id.tv_project_name,item.getProductsName());
                holder.setText(R.id.tv_project_num,"项目数量:"+item.getNum());
                LinearLayout llChildPanel = holder.getView(R.id.ll_child_panel);
                llChildPanel.removeAllViews();
                if (item.getDtls() != null) {
                    for (ProjectDetailListBean.DtlsBean dtlsBean : item.getDtls()) {
                        View childView = LayoutInflater.from(getActivity()).inflate(R.layout.item_child_project_info,null,false);
                        TextView tvChildProjectName = childView.findViewById(R.id.tv_child_project_name);
                        tvChildProjectName.setText(dtlsBean.getDtlName());
                        llChildPanel.addView(childView);
                    }
                }
            }
        };

        mListView.setAdapter(commonAdapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                startActivity(ZhiLiaoDetailActivity.class);
//            }
//        });
        getProjectDetailList();
        return rootView;
    }


    public void getProjectDetailList() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getProjectDetailList(BaseApplication.getLoginEntity().getTenantId(),registId)
                .enqueue(new Callback<List<ProjectDetailListBean>>() {
                    @Override
                    public void onResponse(Call<List<ProjectDetailListBean>> call, Response<List<ProjectDetailListBean>> response) {
//                CommonUtil.dismissLoadProgress();
                        mDatas.clear();
                        if (response!=null&&response.body()!=null){
                            mDatas.addAll(response.body());
                        }
                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<ProjectDetailListBean>> call, Throwable t) {
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
