package com.mylike.his.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.mylike.his.utils.SPUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private static String BASE_URL = "http://172.16.61.222:8280/mylike-crm/";//服务器
//    private static String BASE_URL = "http://172.16.63.230:8085/mylike-crm/";//春雷
//    private static String BASE_URL = "http://172.16.63.217:8080/mylike-crm/";//李哲
//    private static String BASE_URL = "http://172.16.63.52:8080/mylike-crm/";//收费
//    private static String BASE_URL = "http://172.16.63.75:8080/mylike-crm/";//鲁钿
//    private static String BASE_URL = "http://172.16.63.136:8080/mylike-crm/";//良学

    private static ServersApi serversApi;
//    private static final int DEFAULT_TIMEOUT = 60000;

    public static ServersApi getHttpApi() {
        if (serversApi == null) {
            //初始化请求头（满足ip变更后统一修改的需求）
            OkHttpClient.Builder httpClientBuiler = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder());
//            OkHttpClient.Builder httpClientBuiler = new OkHttpClient.Builder();
            //设置超时时间
            httpClientBuiler.connectTimeout(3, TimeUnit.SECONDS);//连接超时
            httpClientBuiler.readTimeout(60, TimeUnit.SECONDS);//读取数据超时
            //对所有请求添加请求头
            httpClientBuiler.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    if (TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))) {//表示第一次登陆还没拉取过token
//                        originalRequest.newBuilder().header("Content-type", "application/json;charset=UTF-8").build();
                        return chain.proceed(originalRequest.newBuilder().header("Content-type", "application/json;charset=UTF-8").build());//执行登陆的操作
                    }
                    Request authorised = originalRequest.newBuilder()
                            .header("Content-type", "application/json;charset=UTF-8")
                            .header("token", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))
                            .build();
                    Response response = chain.proceed(authorised);//执行此次请求
                    //----------此处为了处理统计的jsonp---------------------------
//                    ResponseBody responseBody = response.body();
//                    MediaType mediaType = responseBody.contentType();
//                    String content = responseBody.string();
//                    int index = content.indexOf("(");
//                    if (/*content.startsWith("jsonp") &&*/ index != -1) {
//                        content = content.substring(index + 1, content.length() - 1);
//                    }
//
//                    if (!response.headers("Set-Cookie").isEmpty()) {
//                        //解析Cookie
//                        for (String header : response.headers("Set-Cookie")) {
//                            if (header.contains("JSESSIONID")) {
//                                String a = header.substring(header.indexOf("JSESSIONID"), header.indexOf(";"));
//                                SPUtils.setCache(SPUtils.FILE_WEB, SPUtils.COOKIES, a);
//                            }
//                        }
//                    }
//                    return response.newBuilder()
//                            .body(responseBody.create(mediaType, content))
//                            .build();
                    //----------------------------------------------------------
                    return response;
                }
            });
            serversApi = new Retrofit.Builder()
                    .client(httpClientBuiler.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(ServersApi.class);
        }
        return serversApi;
    }

    public static RequestBody getRequestBody(Map<String, Object> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        Logger.d(jsonStr);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonStr);
    }

}
