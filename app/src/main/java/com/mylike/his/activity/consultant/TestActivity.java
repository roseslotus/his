package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.BaseEntity;
import com.mylike.his.entity.SVProjectEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengluping on 2018/7/18.
 */

public class TestActivity extends BaseActivity {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        ButterKnife.bind(this);

    }

    private void initData() {

    }
}
