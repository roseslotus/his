package com.mylike.his.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.mylike.his.utils.EditTextUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.ClearEditText;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

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

    private TokenEntity tokenEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setStatusBarColor(false);//取消状态栏的颜色变更（注：一定要写在setContentView前面）
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucent(this);//设置状态栏透明
        ButterKnife.bind(this);

        jumpActivity();
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
        EditTextUtil.setDrawableleft(accountEdit, getResources().getDrawable(R.mipmap.account_icon_true), getResources().getDrawable(R.mipmap.account_icon_false));
        EditTextUtil.setDrawableleft(passwordEdit, getResources().getDrawable(R.mipmap.password_icon_true), getResources().getDrawable(R.mipmap.password_icon_false));
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
    private void verifyJob(final List<String> job) {
        if (job.isEmpty()) {
            CommonUtil.showToast("未检查出匹配的权限");
        } else {
            if (job.size() > 1) {//用户角色多余1个，就弹框选择跳转
                View itemView = DialogUtil.commomDialog(LoginActivity.this, R.layout.dialog_job, DialogUtil.CENTER);
                ListView listView = itemView.findViewById(R.id.job_list);
                listView.setAdapter(new CommonAdapter<String>(LoginActivity.this, R.layout.common_item_text, job) {
                    @Override
                    protected void convert(ViewHolder viewHolder, String item, int position) {
                        TextView textView = viewHolder.getView(R.id.text);
                        textView.setPadding(40, 40, 40, 40);

                        if (Constant.JOB_XC_COUNSELOR.equals(item)) {//现场咨询师
                            textView.setText("现场咨询师");
                        } else if (Constant.JOB_WD_COUNSELOR.equals(item)) {//网电咨询师
                            textView.setText("网电咨询师");
                        }
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        judgeJob(job.get(i), true);
                    }
                });
            } else {//角色只有一个时直接跳转
                judgeJob(job.get(0), true);
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
        }
        switch (job) {
            case Constant.JOB_XC_COUNSELOR://现场咨询师
                startActivity(CMainActivity.class);
                break;
            case Constant.JOB_WD_COUNSELOR://网电咨询师
                startActivity(SearchActivity.class);
                break;
        }
        finish();
    }
}
