package com.mylike.his.activity.presener;

import android.content.Context;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.DaiZhenResp;
import com.mylike.his.entity.DepCountEntity;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2019/1/3.
 */

public class WoDeDaiZhenPresenter {

    private Context mContext;
    private int pageIndex=1;
    private int pageSize= 10;

    public  void refresh(ResponseListener<DaiZhenResp> listener){
        pageIndex=1;
        getWoDeDaiZhenList(listener);
    }

    public  void loadMore(ResponseListener<DaiZhenResp> listener){
        pageIndex= pageIndex+1;
        getWoDeDaiZhenList(listener);
    }

    public WoDeDaiZhenPresenter(Context context){
        this.mContext= context;
    }

    public boolean isBoy(String sex){
       return  "男".equals(sex);
    }

    public int getVipLevelIcon(String levelName){
        if ("金卡".equals(levelName)){
            return R.mipmap.icon_d_vip1;
        }else if ("银卡".equals(levelName)){
            return R.mipmap.icon_d_vip2;
        } else if ("时尚卡".equals(levelName)){
            return R.mipmap.icon_d_vip3;
        }else {
            return R.mipmap.icon_d_vip4;
        }
    }

    public void getWoDeDaiZhenList(final ResponseListener<DaiZhenResp> listener){
        CommonUtil.showLoadProgress(mContext);
        Calendar calendar = Calendar.getInstance();

        HttpClient.getHttpApi().getWoDeDaiZhenList(BaseApplication.getLoginEntity().getTenantId(), BaseApplication.getLoginEntity().getDefaultDepId(),
                BaseApplication.getLoginEntity().getUserId(),pageIndex,pageSize,
               "2019-01-23",
                1,"desc","desc",""

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
