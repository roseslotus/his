package com.mylike.his.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.mylike.his.R;
import com.mylike.his.activity.consultant.CMainActivity;
import com.mylike.his.activity.consultant.SettingActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.TokenEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.EditTextUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.utils.ToastUtils;
import com.mylike.his.view.ClearEditText;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import retrofit2.http.HTTP;

/**
 * Created by zhengluping on 2018/1/31.
 * 登录
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.account_edit)
    ClearEditText accountEdit;
    @Bind(R.id.password_edit)
    ClearEditText passwordEdit;
    @Bind(R.id.login_btn)
    TextView loginBtn;
    @Bind(R.id.setting_btn)
    ImageView settingBtn;

//    @Bind(R.id.doctor_btn)
//    TextView doctorBtn;
//    @Bind(R.id.account_edit)
//    ClearEditText accountEdit;
//    @Bind(R.id.password_edit)
//    ClearEditText passwordEdit;
//    @Bind(R.id.login_btn)
//    TextView loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(false);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucent(this);//设置状态栏透明
        ButterKnife.bind(this);


        if (!TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))) {
            if (!isTaskRoot()) {//解决重启app会打开首页的问题bbbbb
                finish();
                return;
            }
            startActivity(CMainActivity.class);
            finish();
        }

        if (!TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.ACCOUNT))) {
            accountEdit.setText(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.ACCOUNT));
            passwordEdit.setText(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.PASSWORD));
            accountEdit.setSelection(accountEdit.getText().toString().length());//将光标移至文字末尾
        }


        EditTextUtil.setDrawableleft(accountEdit, getResources().getDrawable(R.mipmap.account_icon_true), getResources().getDrawable(R.mipmap.account_icon_false));
        EditTextUtil.setDrawableleft(passwordEdit, getResources().getDrawable(R.mipmap.password_icon_true), getResources().getDrawable(R.mipmap.password_icon_false));
    }

    //    @OnClick({R.id.consultant_btn, R.id.doctor_btn, R.id.account_clear, R.id.password_clear, R.id.login_btn})
    @OnClick({R.id.login_btn, R.id.setting_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn://登录按钮
                String accountStr = accountEdit.getText().toString();
                String passwordStr = passwordEdit.getText().toString();
                String ip = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED);
                if (TextUtils.isEmpty(accountStr) || TextUtils.isEmpty(passwordStr)) {
                    ToastUtils.showToast("账户名或密码不能为空，请重新输入");
                } else if (TextUtils.isEmpty(ip)) {
                    ToastUtils.showToast("请设置服务器地址");
                } else {//登录
                    RetrofitUrlManager.getInstance().setGlobalDomain("http://" + ip);
                    longin(accountStr, passwordStr);
                }
                break;
            case R.id.setting_btn:
                startActivity(SettingActivity.class);
                break;
//            case R.id.consultant_btn://咨询师
//                startActivity(CMainActivity.class);
//                break;
//            case R.id.doctor_btn://医生
//                startActivity(DMainActivity.class);
//                break;
//            case R.id.account_clear://账号清除
//                accountEdit.setText("");
//                break;
//            case R.id.password_clear://密码清除
//                passwordEdit.setText("");
//                break;
        }
    }

    private void longin(String accountStr, String passwordStr) {
        //获取设备信息
        String brand = Build.BRAND;//品牌
        String model = Build.MODEL;//手机型号
        String release = Build.VERSION.RELEASE;//系统版本
        String phoneInfo = "品牌:" + brand + "；手机型号:" + model + "；系统版本:" + release;

        HashMap<String, Object> map = new HashMap<>();
        map.put("mobileNo", accountStr);//账号
        map.put("password", passwordStr);//密码
        map.put("registrationid", SPUtils.getCache(SPUtils.FILE_JP, SPUtils.JP_REGISTER_ID));//极光id
        map.put("phoneType", "android");//手机类型
        map.put("deviceInfo", phoneInfo);//手机设备信息

        HttpClient.getHttpApi().getLongin(HttpClient.getRequestBody(map)).enqueue(new BaseBack<TokenEntity>() {
            @Override
            protected void onSuccess(TokenEntity tokenEntity) {
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.TOKEN, tokenEntity.getToken());//token
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.ACCOUNT, accountEdit.getText().toString());//账户
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.PASSWORD, passwordEdit.getText().toString());//密码
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.EMP_ID, tokenEntity.getUserInfo().getEmpid());//咨询师id
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.USER_NAME, tokenEntity.getUserInfo().getUsername());//咨询师姓名
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.HOSPITAL_NAME, tokenEntity.getUserInfo().getTenantName());//咨询师所在医院

                startActivity(CMainActivity.class);
                finish();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

}
