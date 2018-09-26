package com.mylike.his.utils;

import android.content.SharedPreferences;

import com.mylike.his.core.BaseApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zhengluping on 2017/12/28.
 * 缓存
 */
public class SPUtils {

    //用户账号文件
    public static final String FILE_ACCOUNT = "cache_account";
    public static final String ACCOUNT = "account"; //用户账户
    public static final String PASSWORD = "password"; //用户密码

    //用户信息文件
    public static final String FILE_USER = "cache_user";
    public static final String TOKEN = "token"; //用户token
    public static final String EMP_ID = "empId"; //咨询师ID
    public static final String USER_NAME = "userName"; //咨询师姓名
    public static final String USER_JOB = "userJob"; //咨询师角色
    public static final String HOSPITAL_NAME = "hospitalName"; //咨询师所在医院

//    //用户名
//    public static final String USER_NAME = "user_naem";
//    //用户登陆名
//    public static final String USER_LOGIN_NAME = "user_login_naem";
//    //用户tenantId
//    public static final String USER_TENANT_ID = "user_tenant_id";
//    //用户拒绝更新版本日期
//    public static final String APP_UPDATE_DATE = "app_update_date ";

    //ip地址文件
    public static final String FILE_IP = "cache_ip";
    public static final String IP_List = "ipList";//ip列表
    public static final String IP_CHECKED = "ipChecked";//选中的ip

    //分诊文件
    public static final String FILE_RECEPTION = "cache_reception";
    public static final String RECEPTION_ID = "reception_id";//分诊id

    //极光文件
    public static final String FILE_JP = "cache_jp";
    public static final String JP_REGISTER_ID = "jp_register_id"; //极光registerId

    //webview文件
    public static final String FILE_WEB = "cache_web";
    public static final String COOKIES = "cookies"; //极光registerId

    /**
     * 用于存储String类型的数据
     *
     * @param fileNaem 缓存的文件名
     * @param spNaem   缓存数据的key
     * @param spValue  缓存数据的值
     */
    public static void setCache(String fileNaem, String spNaem, String spValue) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(spNaem, spValue);
        editor.commit();
    }

    /**
     * 用于存储String类型的数据
     *
     * @param fileNaem 缓存的文件名
     * @param spNaem   缓存数据的key
     * @param spValue  缓存数据的值
     */
    public static void setCache(String fileNaem, String[] spNaem, String[] spValue) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < spNaem.length; i++) {
            editor.putString(spNaem[i], spValue[i]);
        }
        editor.commit();
    }

    /**
     * 获取缓存值
     *
     * @param fileNaem
     * @param spNaem
     * @return 缓存数据的值
     */
    public static String getCache(String fileNaem, String spNaem) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        String spValue = sp.getString(spNaem, "");
        return spValue;
    }


    public static void clearCache(String fileName) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


}
