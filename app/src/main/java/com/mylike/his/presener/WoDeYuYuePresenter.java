package com.mylike.his.presener;

import android.content.Context;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.MyBookingListResp;
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

public class WoDeYuYuePresenter extends BasePagePresenter<MyBookingListResp> {


    private String searchName;
    private String userId;
    private Calendar selectCalendar;


    public WoDeYuYuePresenter(Context context){
        super(context);
        selectCalendar = Calendar.getInstance();
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @Override
    public void loadData(ResponseListener<MyBookingListResp> listener) {
        getMyBookingList(listener);
    }


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




    public void getMyBookingList(final ResponseListener<MyBookingListResp> listener){
        CommonUtil.showLoadProgress(mContext);

        HttpClient.getHttpApi().getMyBookingList(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
                userId,pageIndex,pageSize,
               formatDate(),
                "",searchName

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
