package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.IpEntiyt;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.ClearEditText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengluping on 2018/7/4.
 * 添加ip地址
 */
public class AddIPActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.ip_edit)
    ClearEditText ipEdit;
    @BindView(R.id.port_edit)
    ClearEditText portEdit;
    @BindView(R.id.remark_edit)
    ClearEditText remarkEdit;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    private String json;
    private Gson gson = new Gson();
    private List<IpEntiyt> ipEntiytList = new ArrayList<>();
    private IpEntiyt ipEntiyt = new IpEntiyt();

    private String position;//编辑需要的数值
    private String ipValue;
    private boolean Checked = false;//新添加的ip默认为未选中

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ip);
        ButterKnife.bind(this);

        json = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_List);
        if (!TextUtils.isEmpty(json)) {
            ipEntiytList.addAll((Collection<? extends IpEntiyt>) gson.fromJson(json, new TypeToken<List<IpEntiyt>>() {
            }.getType()));
        } else {//如果是添加的第一条ip，默认选中
            Checked = true;
        }

        position = getIntent().getStringExtra("position");
        //position不为空表示是编辑过来的，将旧数据展示出来
        if (!TextUtils.isEmpty(position)) {
            ipEntiyt = ipEntiytList.get(Integer.parseInt(position));
            ipEdit.setText(ipEntiyt.getIp());
            portEdit.setText(ipEntiyt.getPort());
            remarkEdit.setText(ipEntiyt.getRemark());
            Checked = ipEntiyt.isChecked();
        }
    }

    @OnClick({R.id.submit_btn, R.id.return_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                String ipStr = ipEdit.getText().toString().replace(" ", "");//ip,去除所有空格
                String portStr = portEdit.getText().toString();//端口

                if (TextUtils.isEmpty(ipStr)) {
                    CommonUtil.showToast("ip地址不能为空");
                } else {
                    //拼接完整ip的值
                    if (TextUtils.isEmpty(portStr)) {
                        ipValue = ipStr;
                    } else {
                        ipValue = ipStr + ":" + portStr;
                    }
                    submit(ipStr, portStr);
                }
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

    //接口验证
    private void submit(final String ipStr, final String portStr) {
        try {
            //替换http地址
            RetrofitUrlManager.getInstance().setGlobalDomain("https://" + ipValue);
            CommonUtil.showLoadProgress(AddIPActivity.this);

            //验证接口是否用
            HttpClient.getPingHttpApi().pingAPI().enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    Map<String, String> map = response.body();
                    if (map != null && map.get("code").equals("1000")) {//成功
                        //设置值
                        ipEntiyt.setIp(ipStr);
                        ipEntiyt.setPort(portStr);
                        ipEntiyt.setRemark(remarkEdit.getText().toString());
                        ipEntiyt.setIpValue(ipValue);
                        ipEntiyt.setChecked(Checked);
                        //新添加的数据需要add
                        if (TextUtils.isEmpty(position)) {
                            ipEntiytList.add(ipEntiyt);
                        }
                        SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_List, gson.toJson(ipEntiytList));
                        finish();
                    } else {//失败
                        CommonUtil.dismissLoadProgress();
                        CommonUtil.showToast("ip或端口错误，请查正后再输入");
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    CommonUtil.dismissLoadProgress();
                    CommonUtil.showToast("ip或端口错误，请查正后再输入");
                }
            });
        } catch (Exception e) {
            CommonUtil.showToast("ip或端口错误，请查正后再输入");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String ipChecked = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED);
        if (!TextUtils.isEmpty(ipChecked))
            RetrofitUrlManager.getInstance().setGlobalDomain("https://" + ipChecked);
    }
}
