package com.mylike.his.presener;

import android.content.Context;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.OperationMySchedulingListResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/3.
 */

public class OperationMySchedulePresenter extends BasePagePresenter<OperationMySchedulingListResp> {

    public OperationMySchedulePresenter(Context context){
        super(context);
    }

    @Override
    public void loadData(ResponseListener<OperationMySchedulingListResp> listener) {
        getOperationMySchedulingList(listener);
    }


    public void getOperationMySchedulingList(final ResponseListener<OperationMySchedulingListResp> listener){
        CommonUtil.showLoadProgress(mContext);

        HttpClient.getHttpApi().getOperationMySchedulingList(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
                "85101137",pageIndex,pageSize,
               "2019-01-23",
                "",""

        ).enqueue(new Callback<OperationMySchedulingListResp>() {
            @Override
            public void onResponse(Call<OperationMySchedulingListResp> call, Response<OperationMySchedulingListResp> response) {
                CommonUtil.dismissLoadProgress();
                if (listener!=null){
                    listener.onResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<OperationMySchedulingListResp> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
                listener.onError(t.getMessage(),-1);
            }
        });
    }
}
