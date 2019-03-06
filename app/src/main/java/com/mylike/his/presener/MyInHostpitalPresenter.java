package com.mylike.his.presener;

import android.content.Context;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.MyInHostpitalListResp;
import com.mylike.his.entity.OperationMyBookingListResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/3.
 */

public class MyInHostpitalPresenter extends BasePagePresenter<MyInHostpitalListResp> {

    private int status=1;

    private String userId;
    private String searchName;
    private String code;
    private String bednoId;
    private String inhosptimeId;


    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBednoId(String bednoId) {
        this.bednoId = bednoId;
    }

    public void setInhosptimeId(String inhosptimeId) {
        this.inhosptimeId = inhosptimeId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MyInHostpitalPresenter(Context context){
        super(context);
    }

    @Override
    public void loadData(ResponseListener<MyInHostpitalListResp> listener) {
        getMyInHospital(listener);
    }


    public void getMyInHospital(final ResponseListener<MyInHostpitalListResp> listener){
        CommonUtil.showLoadProgress(mContext);
        HttpClient.getHttpApi().getMyInHospital(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
                userId,status,pageIndex,pageSize,
               searchName,
                code,bednoId,inhosptimeId,BaseApplication.getLoginEntity().getToken()

        ).enqueue(new Callback<MyInHostpitalListResp>() {
            @Override
            public void onResponse(Call<MyInHostpitalListResp> call, Response<MyInHostpitalListResp> response) {
                CommonUtil.dismissLoadProgress();
                if (listener!=null){
                    listener.onResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<MyInHostpitalListResp> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
                listener.onError(t.getMessage(),-1);
            }
        });
    }
}
