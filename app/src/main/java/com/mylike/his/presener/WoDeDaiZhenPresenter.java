package com.mylike.his.presener;

import android.content.Context;
import android.text.TextUtils;

import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.DaiZhenResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
    private String searchName="";
    private String triageTime;
    private String waitingTime;

    private Calendar selectCalendar;

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void setTriageTime(String triageTime) {
        this.triageTime = triageTime;
    }

    public void setWaitingTime(String waitingTime) {
        this.waitingTime = waitingTime;
    }

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
        selectCalendar = Calendar.getInstance();
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

    @Override
    public void loadData(ResponseListener<DaiZhenResp> listener) {
        getWoDeDaiZhenList(listener);
    }

    public void getWoDeDaiZhenList(final ResponseListener<DaiZhenResp> listener){
        CommonUtil.showLoadProgress(mContext);
        String search="";
        try {
            search = URLEncoder.encode(searchName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpClient.getHttpApi().getWoDeDaiZhenList(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
               userId,pageIndex,pageSize,
               formatDate(),
                status,triageTime,waitingTime,search,BaseApplication.getLoginEntity().getToken()

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
