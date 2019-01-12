package com.mylike.his.doctor;

/**
 * Created by thl on 2019/1/3.
 */

public interface ResponseListener<T> {
    void onResponse(T t);
    void onError(String message,int errorCode);
}
