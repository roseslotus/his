package com.mylike.his.doctor.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.Constacts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.prog)
    ProgressBar prog;
    private int type;
    private String registId;
    private String tenantId;

    private String url= "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        ButterKnife.bind(this);
        registId = getIntent().getStringExtra("registId");
        tenantId = BaseApplication.getLoginEntity().getTenantId();
        type = getIntent().getIntExtra("type",0);

        if (type==1){//1.超声、DR:
            url= HttpClient.getBaseUrl() +"/static/hisapplication.html";
        }else if (type==2){//2.心电图:
            url= HttpClient.getBaseUrl() +"/static/hisappxintimg.html";
        }else if (type==3){
            url= HttpClient.getBaseUrl() +"/static/hisapprequirent.html";
        }
        initView();


    }

    private void initView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.loadUrl(url);
//        webView.loadUrl("http://172.16.61.222:8280/mylike-crm/app360/index.html#/information");

        webView.setWebViewClient(new WebViewClient() {
            //拦截url
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //加载完成后调用js
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                initJs();
            }
        });

        webView.addJavascriptInterface(new JsOperation(), "clients");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    prog.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    // 加载中
                    prog.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    prog.setProgress(newProgress);//设置进度值
                }
            }
        });


    }

    private void initJs() {
        webView.post(new Runnable() {
            @Override
            public void run() {
//                if (TextUtils.isEmpty(getIntent().getStringExtra("tag"))) {
                    webView.loadUrl("javascript:appxdt('" + registId + "','" + BaseApplication.getLoginEntity().getTenantId() + "')");
//                } else {
//                    webView.loadUrl("javascript:appinquiry('" + token + "','" + customId + "','" + fId + "')");
//                }
            }
        });
    }

    class JsOperation {
        //测试
        @JavascriptInterface
        public void RepeatConsult() {
            CommonUtil.showToast("成功");
        }


        /**
         * 返回
         */
        @JavascriptInterface
        public void finsh() {
            finish();
        }
    }

    @OnClick({R.id.webView, R.id.prog})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.webView:
                break;
            case R.id.prog:
                break;
        }
    }
}
