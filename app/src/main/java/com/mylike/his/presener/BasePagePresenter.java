package com.mylike.his.presener;

import android.content.Context;

import com.mylike.his.doctor.ResponseListener;

/**
 * Created by thl on 2019/1/3.
 */

public abstract class BasePagePresenter<T> {

    protected Context mContext;
    protected int pageIndex=1;
    protected int pageSize= 10;

    public  void refresh(ResponseListener<T> listener){
        pageIndex=1;
        loadData(listener);
    }

    public  void loadMore(ResponseListener<T> listener){
        pageIndex= pageIndex+1;
        loadData(listener);
    }

    public BasePagePresenter(Context context){
        this.mContext= context;
    }

    public abstract void loadData(ResponseListener<T> listener);


}
