package com.mylike.his.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.google.gson.Gson;
import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.entity.BaseEntity;
import com.mylike.his.entity.VersionsEntity;
import com.mylike.his.entity.VisitEntity;
import com.mylike.his.http.HttpClient;
import com.mylike.his.http.ServersApi;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.ConnectException;
import java.text.DecimalFormat;
import java.util.Map;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.allenliu.versionchecklib.v2.ui.VersionService.builder;

/**
 * 工具类
 */
public class CommonUtil {

    //-----------------------------------------------------------toast提示-------------------------------------------------------
    private static Toast toast;

    /**
     * 解决Toast重复弹出 长时间不消失的问题
     *
     * @param message
     */
    public static void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    //-----------------------------------------------------------加载框-------------------------------------------------------
    public static Dialog dialog;

    /**
     * 加载动态旋转
     *
     * @param context
     * @return
     */
    public static void showLoadProgress(final Context context) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_load, null);
        dialog = new Dialog(context, R.style.LoadDialog);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        dialog.setContentView(itemView);
        dialog.show();
    }

    //取消弹框
    public static void dismissLoadProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    //-------------------------------------------------------手机号正则表达式-------------------------------------------------------
    /**
     * 判断字符串是否符合手机号码格式
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     * "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
     */
    private static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";

    /**
     * 判断是否是手机号
     *
     * @param mobileNums
     * @return 是手机号true
     */
    public static boolean isChinaPhoneLegal(String mobileNums) {
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(REGEX_MOBILE);
    }

    //-------------------------------------------------------保留后两位小数-------------------------------------------------------
    public static String setTwoNumber(String numberStr) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (TextUtils.isEmpty(numberStr)) {
            numberStr = "0";
        }
        Double number = Double.parseDouble(numberStr);
        decimalFormat.format(number);
        return decimalFormat.format(number);
    }

    public static String setTwoNumber(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.format(number);
        return decimalFormat.format(number);
    }

    //-------------------------------------------------------键盘的显示隐藏-------------------------------------------------------

    //根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
    public static boolean isShouldHideInput(View v, MotionEvent event, int a) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                if (a == 0) {
                    isShouldHideInput(v, event, 1);
                }
                return true;
            }
        }
        return false;
    }

    //多种隐藏软件盘方法的其中一种
    public static void hideSoftInput(IBinder token, Context context) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //收起键盘
    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //-------------------------------------------------------App检查更新-------------------------------------------------------

    public static void updataApp(final Context context) {
        HttpClient.getHttpApi().updataApp().enqueue(new Callback<VersionsEntity>() {
            @Override
            public void onResponse(Call<VersionsEntity> call, Response<VersionsEntity> response) {
                Logger.d(response + "\n" + new Gson().toJson(response.body()));
                VersionsEntity versionsEntity = response.body();
                if (versionsEntity == null) {
                    return;
                }
                int lowVersion = Integer.parseInt(versionsEntity.getLowVersion());//最低支持版本号
                int serverVersion = Integer.parseInt(versionsEntity.getVersion());//服务器版本号
                int localVersion = getLocalVersion(context);//应用本地版本号

                //提示框
                UIData uiData = UIData.create();
                uiData.setDownloadUrl(versionsEntity.getFilePath());
                uiData.setTitle("更新提示");
                uiData.setContent("发现新版本，是否立即更新?");

                //初始化
                DownloadBuilder builder = AllenVersionChecker
                        .getInstance()
                        .downloadOnly(uiData)
                        .setForceRedownload(true);

                //判断是否强制更新(app低于要求的最低版本强制更新)
                if (lowVersion > localVersion) {
                    builder.setForceUpdateListener(new ForceUpdateListener() {
                        @Override
                        public void onShouldForceUpdate() {
                            Logger.d("强制更新");
                        }
                    });
                    builder.executeMission(context);

                } else if (serverVersion > localVersion) { //有版本需要更新
                    builder.executeMission(context);

                } else {//没有版本更新
                    showToast("已是最新版本，无需更新");
                }
            }

            @Override
            public void onFailure(Call<VersionsEntity> call, Throwable t) {
                Logger.d(t.getMessage());
                if (t instanceof ConnectException) {//网络连接失败
                    showToast("网络开小差了，请检查网络");
                } else {
                    showToast("喔噢~请求失败，请稍后再试");
                }
            }
        });
    }

    //获取应用版本号
    private static int getLocalVersion(Context context) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

}
