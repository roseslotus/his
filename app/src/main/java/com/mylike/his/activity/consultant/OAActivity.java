package com.mylike.his.activity.consultant;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zhengluping on 2018/1/24.
 * OA审批
 */
public class OAActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.prog)
    ProgressBar prog;

    private String token;
    private String fid;

    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadCallbackBelow;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oa);
        ButterKnife.bind(this);

//        onPermissionRequests(Manifest.permission.CAMERA);
//        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
//        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
//        imageUri = Uri.fromFile(file);

        token = SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN);//获取token
        fid = getIntent().getStringExtra("fid");//获取fid

        initView();
    }

    private void initView() {
        //支持js
        webView.getSettings().setJavaScriptEnabled(true);

        //设置可以访问文件
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        //访问地址
        String ipString = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED);
        webView.loadUrl("http://" + ipString + "/mylike-crm/app360/OA/application.html?_ijt=qpg5ue95dekpqgpgbstgant1tv");

        webView.setWebViewClient(new WebViewClient() {
            //拦截url，禁止用第三方浏览器
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


        webView.setWebChromeClient(new WebChromeClient() {
            //设置alert的弹框
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(OAActivity.this);
                b.setTitle("提示");//标题
                b.setMessage(message);//内容
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

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                //16(Android 4.1.2) <= API <= 20(Android 4.4W.2)回调此方法
                mUploadCallbackBelow = uploadMsg;
                takePhoto();
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                // 该方法回调时说明版本API >= 21，此时将结果赋值给 mUploadCallbackAboveL，使之 != null
                mUploadCallbackAboveL = filePathCallback;
                takePhoto();
                return true;
            }
        });

        webView.addJavascriptInterface(new JsOperation(), "clients");

    }

    //弹出上传文件选项
    private void takePhoto() {

        View view = DialogUtil.commomDialog(this, R.layout.dialog_image_selector, DialogUtil.BOTTOM);
        TextView camera_btn = view.findViewById(R.id.camera_btn);
        TextView othe_btn = view.findViewById(R.id.othe_btn);
        camera_btn.setOnClickListener(this);
        othe_btn.setOnClickListener(this);

        //弹框被取消，清空mUploadCallbackAboveL，解决第二次不能回调的问题
        Dialog dialog = DialogUtil.getDialog();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (mUploadCallbackAboveL != null) {
                    mUploadCallbackAboveL.onReceiveValue(null);
                    mUploadCallbackAboveL = null;
                } else if (mUploadCallbackBelow != null) {
                    mUploadCallbackBelow.onReceiveValue(null);
                    mUploadCallbackBelow = null;
                }
            }
        });
    }

    //初始化js数据（h5需要）
    private void initJs() {
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:appoa('" + token + "','" + fid + "')");
            }
        });
    }

    class JsOperation {
        //提交成功返回
        @JavascriptInterface
        public void succeed() {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //针对5.0以上, 以下区分处理方法
            if (mUploadCallbackBelow != null) {
                chooseBelow(resultCode, data);
            } else if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                CommonUtil.showToast("发生错误");
            }
        }
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseAbove(int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            updatePhotos();
            if (data != null) {
                // 这里是针对从文件中选图片的处理, 区别是一个返回的URI, 一个是URI[]
                Uri[] results;
                Uri uriData = data.getData();
                if (uriData != null) {
                    results = new Uri[]{uriData};
                    mUploadCallbackAboveL.onReceiveValue(results);
                } else {
                    mUploadCallbackAboveL.onReceiveValue(null);
                }
            } else {
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
            }
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        mUploadCallbackAboveL = null;
    }

    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }

    /**
     * Android API < 21(Android 5.0)版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseBelow(int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            updatePhotos();
            if (data != null) {
                // 这里是针对文件路径处理
                Uri uri = data.getData();
                if (uri != null) {
                    mUploadCallbackBelow.onReceiveValue(uri);
                } else {
                    mUploadCallbackBelow.onReceiveValue(null);
                }
            } else {
                // 以指定图像存储路径的方式调起相机，成功后返回data为空
                mUploadCallbackBelow.onReceiveValue(imageUri);
            }
        } else {
            mUploadCallbackBelow.onReceiveValue(null);
        }
        mUploadCallbackBelow = null;
    }


    @OnClick({R.id.return_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_btn://返回
                finish();
                break;

            case R.id.camera_btn://拍照
                onPermissionRequests(Manifest.permission.CAMERA);
                break;

            case R.id.othe_btn://其他方式
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "Image Chooser"), 100);
                DialogUtil.dismissDialog();
                break;
        }
    }

    private void Camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/his/photo/");

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        //是否是文件夹，不是就创建文件夹
        if (!file.exists()) file.mkdirs();
        File imageFile = new File(Environment.getExternalStorageDirectory().getPath() + "/his/photo/" + timeStampFormat.format(new Date()) + ".jpg");
        //创建一个图片保存的Uri
        imageUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", imageFile);

        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 100);

        DialogUtil.dismissDialog();
    }

    //权限检查
    public void onPermissionRequests(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                //权限已有
                Camera();
            } else {
                //没有权限，申请一下
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
            }
        } else {
            Camera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限通过
                Camera();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
