package com.mylike.his.presener;

import android.content.Context;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.OperationMyArrangementListResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/3.
 */

public class OperationMyArrangementPresenter extends BasePagePresenter<OperationMyArrangementListResp> {

    public OperationMyArrangementPresenter(Context context){
        super(context);
    }

    @Override
    public void loadData(ResponseListener<OperationMyArrangementListResp> listener) {
        getOperationMyBookList(listener);
    }


    public void getOperationMyBookList(final ResponseListener<OperationMyArrangementListResp> listener){
        CommonUtil.showLoadProgress(mContext);
        Calendar calendar = Calendar.getInstance();

        HttpClient.getHttpApi().getOperationMyArrangementList(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
                "85101137","2019-01-23",pageIndex,pageSize,
                2,"","",""

        ).enqueue(new Callback<OperationMyArrangementListResp>() {
            @Override
            public void onResponse(Call<OperationMyArrangementListResp> call, Response<OperationMyArrangementListResp> response) {
                CommonUtil.dismissLoadProgress();
                if (listener!=null){
                    listener.onResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<OperationMyArrangementListResp> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
                listener.onError(t.getMessage(),-1);
            }
        });
    }
}
