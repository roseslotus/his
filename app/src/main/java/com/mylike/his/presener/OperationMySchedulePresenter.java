package com.mylike.his.presener;

import android.content.Context;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.OperationMySchedulingListResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/3.
 */

public class OperationMySchedulePresenter extends BasePagePresenter<OperationMySchedulingListResp> {


    private String userId;
    private String searchName;

    private Calendar selectCalendar;
    public String formatDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(selectCalendar.getTime());
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void goPreDay(){
        selectCalendar.add(Calendar.DAY_OF_MONTH,-1);
    }
    public void goNexDay(){
        selectCalendar.add(Calendar.DATE,1);
    }

    public OperationMySchedulePresenter(Context context){
        super(context);
        selectCalendar =Calendar.getInstance();
    }

    @Override
    public void loadData(ResponseListener<OperationMySchedulingListResp> listener) {
        getOperationMySchedulingList(listener);
    }


    public void getOperationMySchedulingList(final ResponseListener<OperationMySchedulingListResp> listener){
        CommonUtil.showLoadProgress(mContext);

        HttpClient.getHttpApi().getOperationMySchedulingList(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
                userId,pageIndex,pageSize,
               formatDate(), searchName,"",BaseApplication.getLoginEntity().getToken()

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
