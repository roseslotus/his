package com.mylike.his.presener;

import android.content.Context;
import android.text.TextUtils;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.DaiZhenResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/3.
 */

public class WoDeDaiZhenPresenter extends BasePagePresenter<DaiZhenResp> {

    private int status=1;

    private String userId;
    private String departId;


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public WoDeDaiZhenPresenter(Context context){
       super(context);
    }

    @Override
    public void loadData(ResponseListener<DaiZhenResp> listener) {
        getWoDeDaiZhenList(listener);
    }

    public void getWoDeDaiZhenList(final ResponseListener<DaiZhenResp> listener){
        CommonUtil.showLoadProgress(mContext);
        Calendar calendar = Calendar.getInstance();

        HttpClient.getHttpApi().getWoDeDaiZhenList(BaseApplication.getLoginEntity().getTenantId(), TextUtils.isEmpty(departId)?BaseApplication.getLoginEntity().getDefaultDepId():departId,
                TextUtils.isEmpty(userId)?BaseApplication.getLoginEntity().getUserId():userId,pageIndex,pageSize,
               "2019-01-23",
                status,"desc","desc",""

        ).enqueue(new Callback<DaiZhenResp>() {
            @Override
            public void onResponse(Call<DaiZhenResp> call, Response<DaiZhenResp> response) {
                CommonUtil.dismissLoadProgress();
                if (listener!=null){
                    listener.onResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<DaiZhenResp> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
                listener.onError(t.getMessage(),-1);
            }
        });
    }
}
