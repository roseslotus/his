package com.mylike.his.utils;

import android.os.Build;
import android.text.TextUtils;

import com.mylike.his.core.BaseApplication;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by thl on 2018/10/30.
 */

public class LogInterceptor implements Interceptor {
    public static String TAG = "LogInterceptor";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException, NullPointerException {
        long id = System.currentTimeMillis();
        Request original = chain.request();
        if (original.method().toLowerCase().contains("get")) {
            log(id, "method=" + original.method() + " url=" + original.url());
        } else {
            RequestBody rb = original.body();
            if (rb != null) {
                okio.Buffer buffer = new okio.Buffer();
                rb.writeTo(buffer);
                if(rb instanceof MultipartBody){
                    log(id, "method=" + original.method() + " url=" + original.url() );

                } else {
                    log(id, "method=" + original.method() + " url=" + original.url() + " \nbody=" + buffer.readUtf8());

                }
                buffer.clear();
            } else {
                log(id, "method=" + original.method() + " url=" + original.url());
            }
        }


        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            original = original.newBuilder().addHeader("Connection", "close").build();
        }
        //表示第一次登陆还没拉取过token
        if (!TextUtils.isEmpty(BaseApplication.getLoginEntity().getToken())) {
            original=original.newBuilder().header("token", BaseApplication.getLoginEntity().getToken()).build();//执行登陆的操作
        }

        original = original.newBuilder()
                .header("Content-type", "application/json;charset=UTF-8")
//                .header("token", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))
                .build();
        Headers headers = original.headers();
        for (String name : headers.names()) {
            log(id,"header " + name + "=" + headers.get(name));
        }
        okhttp3.Response response = chain.proceed(original);
        String content = response.body().string();
        log(id, (System.currentTimeMillis() - id) + "ms << " + content);
        okhttp3.MediaType mediaType = response.body().contentType();
        return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();
    }

    private static void log(long id, String msg) {
        LogUtil.info(LogInterceptor.class, "id=" + id + " " + msg);
    }

}
