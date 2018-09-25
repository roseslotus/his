package com.mylike.his.fragment.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mylike.his.R;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zhengluping on 2018/1/2.
 * 任务
 */
public class StatisticsFragment extends BaseFragment {
    @Bind(R.id.webView)
    WebView webView;

    public static StatisticsFragment newInstance() {
        Bundle args = new Bundle();
        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        initData();
        return view;
    }

    private void initData() {
        HttpClient.getHttpApi().getStatistics().enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                String cookieValue = stringStringMap.get("sessionId") + "=" + stringStringMap.get("sessionValue");
                synCookie(stringStringMap.get("redirectUrl"), cookieValue);
                if (StatisticsFragment.this != null && StatisticsFragment.this.isAdded()) {
                    webView.loadUrl(stringStringMap.get("redirectUrl"));
                }
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    //初始化
    public void synCookie(String url, String cookie) {
        CookieSyncManager.createInstance(getActivity());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, cookie);
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
