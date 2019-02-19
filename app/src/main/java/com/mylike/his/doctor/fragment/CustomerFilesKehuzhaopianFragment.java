package com.mylike.his.doctor.fragment;

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
import com.mylike.his.doctor.activity.XiaofeijiluDetailActivity;
import com.mylike.his.entity.DoctorAdviceListBean;
import com.mylike.his.entity.OperationPhotoBean;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.Constacts;
import com.mylike.his.utils.ImagePreviewUtils;
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
 * 治疗登记
 * Created by thl on 2018/12/30.
 */

public class CustomerFilesKehuzhaopianFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private CommonAdapter<OperationPhotoBean> commonAdapter;
    private List<OperationPhotoBean> mDatas =  new ArrayList<>();
    private String registId;
    private String cusId;

    public static CustomerFilesKehuzhaopianFragment newInstance(String registId,String cusId){
        CustomerFilesKehuzhaopianFragment fragment= new CustomerFilesKehuzhaopianFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constacts.CONTENT_DATA,registId);
        bundle.putString(Constacts.CUSID,cusId);
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
            cusId =getArguments().getString(Constacts.CUSID);
        }
        commonAdapter = new CommonAdapter<OperationPhotoBean>(getActivity(),R.layout.item_customer_files_kehuzhaopian,mDatas) {
            @Override
            protected void convert(ViewHolder holder, final OperationPhotoBean item, int position) {
                holder.setText(R.id.tv_product_name,item.getItemName());
                holder.setText(R.id.tv_operating_time,item.getItemName());
                holder.setText(R.id.tv_preparation_num,"["+item.getPreoperative().size()+"张]");
                holder.setText(R.id.tv_operating_num,"["+item.getIntranperative().size()+"张]");
                holder.setText(R.id.tv_operated_num,"["+item.getPostoperative().size()+"张]");

                holder.getView(R.id.tv_preparation_look).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> photos = new ArrayList<>();
                        for (OperationPhotoBean.PhotoBean photoBean : item.getPreoperative()) {
                            photos.add(photoBean.getPath());
                        }
                        ImagePreviewUtils.lookBigMutilImage(getActivity(),photos);
                    }
                });

                holder.getView(R.id.tv_operating_look).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> photos = new ArrayList<>();
                        for (OperationPhotoBean.PhotoBean photoBean : item.getIntranperative()) {
                            photos.add(photoBean.getPath());
                        }
                        ImagePreviewUtils.lookBigMutilImage(getActivity(),photos);
                    }
                });

                holder.getView(R.id.tv_operated_look).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<String> photos = new ArrayList<>();
                        for (OperationPhotoBean.PhotoBean photoBean : item.getPostoperative()) {
                            photos.add(photoBean.getPath());
                        }
                        ImagePreviewUtils.lookBigMutilImage(getActivity(),photos);
                    }
                });
            }
        };

        mListView.setAdapter(commonAdapter);

        getCustomerPhotoList();
        return rootView;
    }
    public void getCustomerPhotoList() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getCustomerPhotoList(BaseApplication.getLoginEntity().getTenantId(),
                cusId,registId)
                .enqueue(new Callback<List<OperationPhotoBean>>() {
                    @Override
                    public void onResponse(Call<List<OperationPhotoBean>> call, Response<List<OperationPhotoBean>> resp) {
                        CommonUtil.dismissLoadProgress();
//                        bindData(resp.body());
                        if (resp != null&&resp.body()!=null) {
                            mDatas.addAll(resp.body());
                        }

                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<OperationPhotoBean>> call, Throwable t) {
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
