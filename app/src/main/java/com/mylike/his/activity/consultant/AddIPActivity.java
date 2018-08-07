package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.BaseEntity;
import com.mylike.his.entity.IpEntiyt;
import com.mylike.his.entity.TokenEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.http.ServersApi;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.utils.ToastUtils;
import com.mylike.his.view.ClearEditText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengluping on 2018/7/4.
 */

public class AddIPActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.ip_edit)
    ClearEditText ipEdit;
    @Bind(R.id.port_edit)
    ClearEditText portEdit;
    @Bind(R.id.remark_edit)
    ClearEditText remarkEdit;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    private String json;
    private String position;//编辑需要的数值
    private Gson gson = new Gson();
    private List<IpEntiyt> ipEntiytList = new ArrayList<>();
    private IpEntiyt ipEntiyt = new IpEntiyt();

    private boolean Checked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ip);
        ButterKnife.bind(this);

        json = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_TEXT);
        if (!TextUtils.isEmpty(json)) {
            ipEntiytList.addAll((Collection<? extends IpEntiyt>) gson.fromJson(json, new TypeToken<List<IpEntiyt>>() {
            }.getType()));
        } else {
            Checked = true;
        }

        position = getIntent().getStringExtra("position");
        if (!TextUtils.isEmpty(position)) {
            ipEntiyt = ipEntiytList.get(Integer.parseInt(position));
            ipEdit.setText(ipEntiyt.getIp());
            portEdit.setText(ipEntiyt.getPort());
            remarkEdit.setText(ipEntiyt.getRemark());
        }
    }

    @OnClick({R.id.submit_btn, R.id.return_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                String ipStr = ipEdit.getText().toString();
                String portStr = portEdit.getText().toString();
                if (TextUtils.isEmpty(ipStr) || TextUtils.isEmpty(portStr)) {
                    ToastUtils.showToast("ip地址或端口不能为空");
                } else {
                    ipEntiyt.setIp(ipStr);
                    ipEntiyt.setPort(portStr);
                    ipEntiyt.setRemark(remarkEdit.getText().toString());
                    RetrofitUrlManager.getInstance().setGlobalDomain("http://" + ipStr + ":" + portStr);

                    HttpClient.getHttpApi().pingAPI().enqueue(new Callback<Map<String, String>>() {
                        @Override
                        public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                            Map<String, String> map = response.body();
                            if (map.get("code").equals("1000")) {
                                if (TextUtils.isEmpty(position)) {
                                    ipEntiyt.setChecked(Checked);
                                    ipEntiytList.add(ipEntiyt);
                                    if (Checked) {
                                        SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED, ipEntiyt.getIp() + ":" + ipEntiyt.getPort());
                                    }
                                } else {
                                    if (ipEntiyt.isChecked()) {
                                        SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED, ipEntiyt.getIp() + ":" + ipEntiyt.getPort());
                                    }
                                }
                                SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_TEXT, gson.toJson(ipEntiytList));
                                finish();
                            } else {
                                ToastUtils.showToast(map.get("msg"));
                            }
                        }

                        @Override
                        public void onFailure(Call<Map<String, String>> call, Throwable t) {
                            ToastUtils.showToast("ip或端口错误，请查正后再输入");
                        }
                    });

                }
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
