package com.mylike.his.http;


import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.mylike.his.activity.consultant.CMainActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.BaseEntity;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.SPUtils;
import com.orhanobut.logger.Logger;

import java.net.ConnectException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseNewBack<T> implements Callback<BaseEntity<T>> {
    protected abstract void onSuccess(T t);

    protected abstract void onFailed(String code, String msg);


    @Override
    public void onResponse(Call<BaseEntity<T>> call, Response<BaseEntity<T>> response) {
        Log.i(this.getClass().getSimpleName(),response + "\n" + new Gson().toJson(response.body()));
        BaseEntity<T> baseEntity = response.body();
        if (response.isSuccessful() && baseEntity != null) {
            if (baseEntity.getCode().equals("1000")) {//成功
                onSuccess(baseEntity.getData());

            } else if (baseEntity.getCode().equals("4001")) {//token失效
                CommonUtil.showToast("登录失效，请重新登录");
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.TOKEN, "");
                Intent intent = new Intent();
                intent.setClass(BaseApplication.getContext(), CMainActivity.class);
                BaseApplication.getContext().startActivity(intent);

            } else if (baseEntity.getCode().equals("4002")) {//踢出登录
                CommonUtil.showToast(baseEntity.getMsg());
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.TOKEN, "");
                Intent intent = new Intent();
                intent.setClass(BaseApplication.getContext(), CMainActivity.class);
                BaseApplication.getContext().startActivity(intent);

        } else if (baseEntity.getCode().equals("4000")) {//后台异常
            CommonUtil.showToast(baseEntity.getMsg());
        } else {
            onFailed(baseEntity.getCode(), baseEntity.getMsg());
        }
        } else {
            onFailed(response.code() + "", response.message());
        }

        CommonUtil.dismissLoadProgress();
    }

    @Override
    public void onFailure(Call<BaseEntity<T>> call, Throwable t) {
        Logger.d(t.getMessage());
        if (t instanceof ConnectException) {//网络连接失败
            CommonUtil.showToast("网络开小差了，请检查网络");
        } else {
            CommonUtil.showToast("喔噢~请求失败，请稍后再试");
        }
        CommonUtil.dismissLoadProgress();
        onFailed("", "");
    }
}
