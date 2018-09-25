package com.mylike.his.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mylike.his.R;
import com.mylike.his.activity.consultant.MedicineActivity;
import com.mylike.his.activity.consultant.OAActivity;
import com.mylike.his.activity.consultant.OrderActivity;
import com.mylike.his.activity.consultant.PaymentActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.SPUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zhengluping on 2018/1/24.
 * 客户详情
 */
public class CustomerDetailsActivity extends BaseActivity {
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.prog)
    ProgressBar prog;

    private String token;
    private String customId;

    private String fidValue;
    private String remarkValue;
    private String[] Intention;
    private OptionsPickerView optionsPickerView;
    private List<IntentionEntity> intentionEntities1 = new ArrayList<>();
    private List<List<IntentionEntity>> intentionEntities2 = new ArrayList<>();
    private List<List<List<IntentionEntity>>> intentionEntities3 = new ArrayList<>();

    private Handler handler = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        ButterKnife.bind(this);
        token = SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN);
        customId = getIntent().getStringExtra("clientId");

        //创建属于主线程的handler
        handler = new Handler();

        initView();
        initData();
    }

    private void initView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        String ipString = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED);


        webView.loadUrl("http://" + ipString + "/mylike-crm/app360/index.html#/information");
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
                AlertDialog.Builder b = new AlertDialog.Builder(CustomerDetailsActivity.this);
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
                    CommonUtil.dismissLoadProgress();
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

    class JsOperation {
        //测试
        @JavascriptInterface
        public void RepeatConsult() {
            CommonUtil.showToast("成功");
        }

        /**
         * 编辑
         *
         * @param receptionId 分诊id
         */
        @JavascriptInterface
        public void compile(String receptionId) {
            CommonUtil.showToast("分诊" + receptionId);
            SPUtils.setCache(SPUtils.FILE_RECEPTION, SPUtils.RECEPTION_ID, receptionId);
            startActivity(OrderActivity.class, "chargeTag", "1");
        }

        /**
         * 支付
         *
         * @param fid 收费单id
         */
        @JavascriptInterface
        public void payment(String fid) {
//            CommonUtil.showToast("支付" + fid);

            Intent intent = new Intent();
            intent.putExtra("fid", fid);
            intent.setClass(CustomerDetailsActivity.this, PaymentActivity.class);
            startActivity(intent);
        }

        /**
         * 重咨
         *
         * @param fid 收费单id
         */
        @JavascriptInterface
        public void consult(String fid) {
//            CommonUtil.showToast("重咨" + fid);
            fidValue = fid;
            new Thread() {
                public void run() {
                    handler.post(runnableUi);
                }
            }.start();

        }

        /**
         * 跨科
         *
         * @param fid 收费单id
         */
        @JavascriptInterface
        public void section(String fid) {
//            CommonUtil.showToast("跨科" + fid);

            startActivity(MedicineActivity.class, "fid", fid);
        }

        /**
         * OA申请
         *
         * @param fid
         */
        @JavascriptInterface
        public void oa(String fid) {
            CommonUtil.showToast("OA申请" + fid);

            startActivity(OAActivity.class, "fid", fid);
        }

        /**
         * 返回
         */
        @JavascriptInterface
        public void finsh() {
            finish();
        }
    }

    private void initJs() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:app('" + token + "','" + customId + "')");
            }
        });
    }

    private void initData() {
        HttpClient.getHttpApi().getIntentionAll().enqueue(new BaseBack<List<IntentionEntity>>() {
            @Override
            protected void onSuccess(List<IntentionEntity> intentionEntities) {
                intentionEntities1.add(new IntentionEntity("请选择"));
                intentionEntities1.addAll(intentionEntities);
                //初始化意向数据
                initViewData();
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    private void initViewData() {
        for (int i = 0; i < intentionEntities1.size(); i++) {
            List<IntentionEntity> intentionEntityList2 = new ArrayList<>();//二级意向
            List<List<IntentionEntity>> intentionEntityList3 = new ArrayList<>();//三级意向
            //在第一项添加空意向，如果选择“请选择”则代表此级意向为空
            intentionEntityList2.add(new IntentionEntity("请选择"));
            //如果无意向，添加空对象，防止数据为null 导致三个选项长度不匹配造成崩溃
            if (intentionEntities1.get(i).getChildren().size() == 0) {
                intentionEntityList3.add(intentionEntityList2);
            }
            for (int j = 0; j < intentionEntities1.get(i).getChildren().size(); j++) {
                //添加二级意向
                intentionEntityList2.add(intentionEntities1.get(i).getChildren().get(j));

                //如果二级意向循环第一次，这为三级意向添加一个空对象，对应二级意向的“请选择”
                if (j == 0) {
                    List<IntentionEntity> IList = new ArrayList<>();
                    IList.add(new IntentionEntity("请选择"));
                    intentionEntityList3.add(IList);
                }

                //添加三级意向
                List<IntentionEntity> IList3 = new ArrayList<>();
                IList3.add(new IntentionEntity("请选择"));
                if (intentionEntities1.get(i).getChildren().get(j).getChildren() != null || intentionEntities1.get(i).getChildren().get(j).getChildren().size() != 0) {
                    IList3.addAll(intentionEntities1.get(i).getChildren().get(j).getChildren());
                }
                intentionEntityList3.add(IList3);
            }

            intentionEntities2.add(intentionEntityList2);
            intentionEntities3.add(intentionEntityList3);
        }

        //重咨弹框初始化
        optionsPickerView = new OptionsPickerBuilder(CustomerDetailsActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {//选择项
                Intention = new String[]{intentionEntities1.get(options1).getPbtid(), intentionEntities2.get(options1).get(options2).getPbtid(), intentionEntities3.get(options1).get(options2).get(options3).getPbtid()};
                submitData();
            }
        }).setLayoutRes(R.layout.dialog_again_consult, new CustomListener() {//自定义布局
            @Override
            public void customLayout(View v) {
                final Button submitBtn = v.findViewById(R.id.submit_btn);
                final EditText remarkEdit = v.findViewById(R.id.remark_edit);

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remarkValue = remarkEdit.getText().toString();
                        optionsPickerView.returnData();
                    }
                });

            }
        }).setContentTextSize(14)
                .setSelectOptions(0, 0, 0)
                .setDividerColor(getResources().getColor(R.color.green_50))
                .setLineSpacingMultiplier((float) 2.5)
                .isDialog(true)
                .build();
        optionsPickerView.setPicker(intentionEntities1, intentionEntities2, intentionEntities3);//设置数据
    }


    private void submitData() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("fid", fidValue);
        map.put("CustomerOpinion", remarkValue);
        map.put("CustomerIntention", Intention);

        //获取收费单
        HttpClient.getHttpApi().setIntention(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                optionsPickerView.dismiss();
                CommonUtil.showToast("提交成功");
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    // 构建Runnable对象，在runnable中更新界面
    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            //更新界面
//            textView.setText("the Content is:"+content);
            optionsPickerView.show();
        }

    };
}
