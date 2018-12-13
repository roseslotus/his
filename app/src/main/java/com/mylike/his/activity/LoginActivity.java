package com.mylike.his.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.mylike.his.R;
import com.mylike.his.activity.consultant.CMainActivity;
import com.mylike.his.activity.consultant.SearchActivity;
import com.mylike.his.activity.consultant.SettingIPActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.Constant;
import com.mylike.his.entity.TokenEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.ClearEditText;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/31.
 * 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.account_edit)
    ClearEditText accountEdit;
    @BindView(R.id.password_edit)
    ClearEditText passwordEdit;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.setting_btn)
    ImageView settingBtn;

    private TokenEntity tokenEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setStatusBarColor(false);//取消状态栏的颜色变更（注：一定要写在setContentView前面）
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucent(this);//设置状态栏透明
        ButterKnife.bind(this);

        jumpActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onPermissionRequests(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    //判断跳转界面
    private void jumpActivity() {
        //如果token不为空（已登录），则判断角色跳转页面
        if (!TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))) {
            //解决重启app会打开首页的问题
            if (!isTaskRoot()) {
                finish();
                return;
            }
            //角色判断
            judgeJob(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.USER_JOB), false);

        } else {//token为空初始登录界面
            initView();

        }
    }

    //初始化控件
    private void initView() {
        //读取最后一次登录的账户名和密码
        if (!TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_ACCOUNT, SPUtils.ACCOUNT))) {
            accountEdit.setText(SPUtils.getCache(SPUtils.FILE_ACCOUNT, SPUtils.ACCOUNT));
            passwordEdit.setText(SPUtils.getCache(SPUtils.FILE_ACCOUNT, SPUtils.PASSWORD));
            accountEdit.setSelection(accountEdit.getText().toString().length());//将光标移至文字末尾
        }

        //编辑框有内容是更改左边图标
        setDrawableleft(accountEdit, getResources().getDrawable(R.mipmap.account_icon_true), getResources().getDrawable(R.mipmap.account_icon_false));
        setDrawableleft(passwordEdit, getResources().getDrawable(R.mipmap.password_icon_true), getResources().getDrawable(R.mipmap.password_icon_false));

        //初始化ip地址
        String json = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_List);//地址列表为空
        if (TextUtils.isEmpty(json)) {
            SettingIPActivity settingIPActivity = new SettingIPActivity();
            settingIPActivity.initData();
        }
    }


    @OnClick({R.id.login_btn, R.id.setting_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn://登录
                String accountStr = accountEdit.getText().toString();//账户
                String passwordStr = passwordEdit.getText().toString();//密码
                String ip = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED);//ip地址

                if (TextUtils.isEmpty(accountStr) || TextUtils.isEmpty(passwordStr)) {
                    CommonUtil.showToast("账户名或密码不能为空，请重新输入");
                } else if (TextUtils.isEmpty(ip)) {
                    CommonUtil.showToast("请设置服务器地址");
                } else {//登录
                    longin(accountStr, passwordStr);
                }
                break;

            case R.id.setting_btn://设置
                startActivity(SettingIPActivity.class);
                break;

            //医生权限未开放
            /*case R.id.doctor_btn://医生
                startActivity(DMainActivity.class);
                break;*/
        }
    }

    private void longin(String accountStr, String passwordStr) {
        CommonUtil.showLoadProgress(LoginActivity.this);

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
                LoginActivity.this.tokenEntity = tokenEntity;
                verifyJob(tokenEntity.getSpecial_role());// 角色判断/选择
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //用户角色权限验证
    private void verifyJob(final List<Map<String, String>> job) {
        if (job.isEmpty()) {
            CommonUtil.showToast("未检查出匹配的权限");
        } else {
            if (job.size() > 1) {//用户角色多余1个，就弹框选择跳转
                View itemView = DialogUtil.commomDialog(LoginActivity.this, R.layout.dialog_job, DialogUtil.CENTER);
                ListView listView = itemView.findViewById(R.id.job_list);
                listView.setAdapter(new CommonAdapter<Map<String, String>>(LoginActivity.this, R.layout.common_item_text, job) {
                    @Override
                    protected void convert(ViewHolder viewHolder, Map<String, String> item, int position) {
                        TextView textView = viewHolder.getView(R.id.text);
                        textView.setPadding(40, 40, 40, 40);
                        textView.setText(item.get("name"));
                    }

                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        judgeJob(job.get(i).get("id"), true);
                    }
                });
            } else {//角色只有一个时直接跳转
                judgeJob(job.get(0).get("id"), true);
            }
        }
    }

    /**
     * 角色判断
     *
     * @param job 角色
     * @param tag 是否需要存储数据
     */
    public void judgeJob(String job, boolean tag) {
        if (tag) {
            SPUtils.setCache(SPUtils.FILE_ACCOUNT, SPUtils.ACCOUNT, accountEdit.getText().toString());//账户
            SPUtils.setCache(SPUtils.FILE_ACCOUNT, SPUtils.PASSWORD, passwordEdit.getText().toString());//密码

            SPUtils.setCache(SPUtils.FILE_USER, SPUtils.TOKEN, tokenEntity.getToken());//token
            SPUtils.setCache(SPUtils.FILE_USER, SPUtils.EMP_ID, tokenEntity.getUserInfo().getEmpid());//咨询师id
            SPUtils.setCache(SPUtils.FILE_USER, SPUtils.USER_NAME, tokenEntity.getUserInfo().getUsername());//咨询师姓名
            SPUtils.setCache(SPUtils.FILE_USER, SPUtils.HOSPITAL_NAME, tokenEntity.getUserInfo().getTenantName());//咨询师所在医院
            SPUtils.setCache(SPUtils.FILE_USER, SPUtils.USER_JOB, job);//存储咨询师角色
            SPUtils.setCache(SPUtils.FILE_USER, SPUtils.USER_JD, new Gson().toJson(tokenEntity.getAuthList()));
        }
        switch (job) {
            case Constant.JOB_XC_COUNSELOR://现场咨询师
                startActivity(CMainActivity.class);
                break;
            case Constant.JOB_WD_COUNSELOR://网电咨询师
                startActivity(SearchActivity.class, "tag", Constant.JOB_WD_COUNSELOR);
                break;
            default:
                //3 社交咨询，4 电商咨询，5 线下合作咨询，6 传统媒体咨询
                //3456角色权限和网电咨询师权限操作一致，在角色操作有变更之前都跳建档
                startActivity(SearchActivity.class, "tag", Constant.JOB_WD_COUNSELOR);
                break;
        }
        finish();
    }

    /**
     * 输入框有内容变化时，更改左边的图标
     *
     * @param editText  编辑框
     * @param tDrawable 有内容时的图标
     * @param fDrawable 无内容时的图标
     */
    public static void setDrawableleft(final EditText editText, final Drawable tDrawable, final Drawable fDrawable) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tDrawable.setBounds(0, 0, tDrawable.getIntrinsicWidth(), tDrawable.getIntrinsicHeight());
                fDrawable.setBounds(0, 0, fDrawable.getIntrinsicWidth(), fDrawable.getIntrinsicHeight());

                if (s.length() > 0) {
                    editText.setCompoundDrawables(tDrawable, editText.getCompoundDrawables()[1], editText.getCompoundDrawables()[2], editText.getCompoundDrawables()[3]);
                } else {
                    editText.setCompoundDrawables(fDrawable, editText.getCompoundDrawables()[1], editText.getCompoundDrawables()[2], editText.getCompoundDrawables()[3]);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //检查存储权限
    public void onPermissionRequests(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //权限已有
                CommonUtil.updataApp(this, false);
            } else {
                //没有权限，申请一下
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
            }
        } else {
            //权限已有
            CommonUtil.updataApp(this, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限通过
                CommonUtil.updataApp(this, false);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
