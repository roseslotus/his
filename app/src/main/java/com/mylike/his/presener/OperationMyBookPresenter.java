package com.mylike.his.presener;

import android.content.Context;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.OperationMyBookingListResp;
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

public class OperationMyBookPresenter extends BasePagePresenter<OperationMyBookingListResp> {

    private String userId;
    private String searchName;

    private Calendar selectCalendar;
    public String formatDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(selectCalendar.getTime());
    }

    public void goPreDay(){
        selectCalendar.add(Calendar.DAY_OF_MONTH,-1);
    }
    public void goNexDay(){
        selectCalendar.add(Calendar.DATE,1);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public OperationMyBookPresenter(Context context){
        super(context);
        selectCalendar = Calendar.getInstance();
    }

    @Override
    public void loadData(ResponseListener<OperationMyBookingListResp> listener) {
        getOperationMyBookList(listener);
    }


    public void getOperationMyBookList(final ResponseListener<OperationMyBookingListResp> listener){
        CommonUtil.showLoadProgress(mContext);
        Calendar calendar = Calendar.getInstance();

        HttpClient.getHttpApi().getOperationMyBookList(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
                BaseApplication.getLoginEntity().getUserId(),pageIndex,pageSize,
               formatDate(),
                "",searchName,"",BaseApplication.getLoginEntity().getToken()

        ).enqueue(new Callback<OperationMyBookingListResp>() {
            @Override
            public void onResponse(Call<OperationMyBookingListResp> call, Response<OperationMyBookingListResp> response) {
                CommonUtil.dismissLoadProgress();
                if (listener!=null){
                    listener.onResponse(response.body());
                }

            }

            @Override
            public void onFailure(Call<OperationMyBookingListResp> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
                listener.onError(t.getMessage(),-1);
            }
        });
    }
}
