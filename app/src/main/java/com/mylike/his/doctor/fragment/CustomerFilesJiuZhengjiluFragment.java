package com.mylike.his.doctor.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.CustomerTreatRecordListBean;
import com.mylike.his.entity.InspectRecordListBean;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.Constacts;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2018/12/30.
 */

public class CustomerFilesJiuZhengjiluFragment extends BaseFragment {

    @BindView(R.id.list_view)
    ListView mListView;
    private View view;
    private Unbinder unbinder;

    private CommonAdapter<CustomerTreatRecordListBean> commonAdapter;
    private List<CustomerTreatRecordListBean> mDatas =  new ArrayList<>();
    private String cusId;

    public static CustomerFilesJiuZhengjiluFragment newInstance(String cusId){
        CustomerFilesJiuZhengjiluFragment fragment= new CustomerFilesJiuZhengjiluFragment();
        Bundle bundle = new Bundle();
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
            cusId= getArguments().getString(Constacts.CUSID);
        }
        commonAdapter = new CommonAdapter<CustomerTreatRecordListBean>(getActivity(),R.layout.item_customer_files_jiuzhengjilu,mDatas) {
            @Override
            protected void convert(ViewHolder holder, CustomerTreatRecordListBean item, int position) {
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

                Date date=null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfMonthDay = new SimpleDateFormat("MM月dd日");
                try {
                    date= sdf.parse(item.getRegistDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    holder.setText(R.id.tv_register_month_day,sdfMonthDay.format(date));
                    holder.setText(R.id.tv_register_year,date.getYear()+"");
                }

                holder.setText(R.id.tv_register_time,item.getRegistTime());
                holder.setText(R.id.tv_dialog_status,item.getStatus());
                holder.setText(R.id.tv_product_name,item.getProductsName());
                holder.setText(R.id.tv_product_type,item.getType());
                holder.setText(R.id.tv_doctor_name,item.getDoctor());
                holder.setText(R.id.tv_depart_name,item.getDepName());
                holder.setText(R.id.tv_remark,item.getRemark());
                holder.setText(R.id.tv_create_time,item.getCreater()+"创建于:"+item.getCreateTime());

            }
        };

        mListView.setAdapter(commonAdapter);
        getCustomerTreatRecordList();
        return rootView;
    }


    public void getCustomerTreatRecordList() {
//        CommonUtil.showLoadProgress(getActivity());
        HttpClient.getHttpApi().getCustomerTreatRecordList(BaseApplication.getLoginEntity().getTenantId(),BaseApplication.getLoginEntity().getDefaultDepId(),cusId,1,50,BaseApplication.getLoginEntity().getToken())
                .enqueue(new Callback<List<CustomerTreatRecordListBean>>() {
                    @Override
                    public void onResponse(Call<List<CustomerTreatRecordListBean>> call, Response<List<CustomerTreatRecordListBean>> response) {
//                CommonUtil.dismissLoadProgress();
                        mDatas.clear();
                        if (response!=null&&response.body()!=null){
                            mDatas.addAll(response.body());
                        }
                        commonAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<List<CustomerTreatRecordListBean>> call, Throwable t) {
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
