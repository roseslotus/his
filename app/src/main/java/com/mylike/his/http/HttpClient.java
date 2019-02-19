package com.mylike.his.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.mylike.his.utils.LogInterceptor;
import com.mylike.his.utils.SPUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private static String ip = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED);//选中的ip
    private static String BASE_URL = "http://" + ip + "/mylike-crm/";//统一地址
    private static ServersApi serversApi;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static ServersApi getHttpApi() {
        //防止在没有选中ip时修改统一ip出现修改错位的现象
        if (TextUtils.isEmpty(ip)) {
            BASE_URL = "http://172.16.61.222:8280/mylike-crm/";
        }
        BASE_URL = "https://api.mylikesh.cn/his-api/";
//        BASE_URL = "https://uat8280.mylikesh.cn:80/his-api/";
        if (serversApi == null) {
            //OkHttpClient.Builder httpClientBuiler = new OkHttpClient.Builder();
            //初始化请求头（满足ip变更后统一修改的需求）
            OkHttpClient.Builder httpClientBuiler = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder());

            //设置超时时间
            httpClientBuiler.connectTimeout(5, TimeUnit.SECONDS);//连接超时,5秒
            httpClientBuiler.readTimeout(30, TimeUnit.SECONDS);//读取数据超时，30秒

            //对所有请求添加请求头
            httpClientBuiler.addInterceptor(new LogInterceptor());
//                    new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
//
//                    //表示第一次登陆还没拉取过token
//                    if (TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))) {
//                        return chain.proceed(originalRequest.newBuilder().header("Content-type", "application/json;charset=UTF-8").build());//执行登陆的操作
//                    }
//
//                    Request authorised = originalRequest.newBuilder()
//                            .header("Content-type", "application/json;charset=UTF-8")
//                            .header("token", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))
//                            .build();
//                    Response response = chain.proceed(authorised);//执行此次请求
//                    return response;
//                }
//            });

            serversApi = new Retrofit.Builder()
                    .client(httpClientBuiler.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(ServersApi.class);
        }
        return serversApi;
    }

    //map参数请求数据
    public static RequestBody getRequestBody(Map<String, Object> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        Logger.d(jsonStr);//打印map数据日志
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);
    }

    //对象参数请求数据
    public static RequestBody getRequestBody(Object o) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(o);
        Logger.d(jsonStr);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);
    }


  /*  public static void aa() {
        Call call = new BaseBack<TokenEntity>() {
            @Override
            protected void onSuccess(TokenEntity tokenEntity) {
                LoginActivity.this.tokenEntity = tokenEntity;
                verifyJob(tokenEntity.getSpecial_role());// 角色判断/选择
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        }
    }*/

}

