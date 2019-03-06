package com.mylike.his.presener;

import android.content.Context;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.CustomerListBean;
import com.mylike.his.entity.MyInHostpitalListResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/3.
 */

public class CustomerListPresenter extends BasePagePresenter<List<CustomerListBean>> {

    private int status=1;


    public void setStatus(int status) {
        this.status = status;
    }

    public CustomerListPresenter(Context context){
        super(context);
    }

    @Override
    public void loadData(ResponseListener<List<CustomerListBean>> listener) {
        getMyInHospital(listener);
    }


    public void getMyInHospital(final ResponseListener<List<CustomerListBean>> listener){
        CommonUtil.showLoadProgress(mContext);
        HttpClient.getHttpApi().getCustomerList(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
                "85100954",pageIndex,pageSize,BaseApplication.getLoginEntity().getToken()

        ).enqueue(new Callback<List<CustomerListBean>>() {
            @Override
            public void onResponse(Call<List<CustomerListBean>> call, Response<List<CustomerListBean>> response) {
                CommonUtil.dismissLoadProgress();
                if (listener!=null){
                    listener.onResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<CustomerListBean>> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
                listener.onError(t.getMessage(),-1);
            }
        });
    }
}
