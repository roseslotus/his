package com.mylike.his.presener;

import android.content.Context;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.MyBookingListResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/3.
 */

public class WoDeYuYuePresenter extends BasePagePresenter<MyBookingListResp> {

    public WoDeYuYuePresenter(Context context){
        super(context);
    }

    @Override
    public void loadData(ResponseListener<MyBookingListResp> listener) {
        getMyBookingList(listener);
    }


    public void getMyBookingList(final ResponseListener<MyBookingListResp> listener){
        CommonUtil.showLoadProgress(mContext);
        Calendar calendar = Calendar.getInstance();

        HttpClient.getHttpApi().getMyBookingList(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
                BaseApplication.getLoginEntity().getUserId(),pageIndex,pageSize,
               "2019-01-24",
                "",""

        ).enqueue(new Callback<MyBookingListResp>() {
            @Override
            public void onResponse(Call<MyBookingListResp> call, Response<MyBookingListResp> response) {
                CommonUtil.dismissLoadProgress();
                if (listener!=null){
                    listener.onResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<MyBookingListResp> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
                listener.onError(t.getMessage(),-1);
            }
        });
    }
}
