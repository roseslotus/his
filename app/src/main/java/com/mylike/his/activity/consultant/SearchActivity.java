package com.mylike.his.activity.consultant;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mylike.his.R;
import com.mylike.his.activity.LoginActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.Constant;
import com.mylike.his.entity.TokenEntity;
import com.mylike.his.entity.UserInfoEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DataUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.ClearEditText;
import com.pgyersdk.update.PgyUpdateManager;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.search_edit)
    ClearEditText searchEdit;
    @BindView(R.id.text_hint)
    TextView textHint;
    @BindView(R.id.tag_ll)
    LinearLayout tagLl;
    @BindView(R.id.update_btn)
    Button updateBtn;
    @BindView(R.id.exit_btn)
    Button exitBtn;
    @BindView(R.id.filtrate_menu)
    LinearLayout filtrateMenu;
    @BindView(R.id.DrawerLayout)
    android.support.v4.widget.DrawerLayout DrawerLayout;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.menu_img)
    ImageView menuImg;

    //建档
    private CommonAdapter commonAdapter;
    private List<TokenEntity.authList> authLists = new ArrayList<>();

    //后台返回的时间，防止手机时间不准确
    private String time;

    public boolean updataAppTag;//app是否弹无更新提示


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        if (TextUtils.isEmpty(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN))) {
            startActivity(LoginActivity.class);
            finish();
        }
        //禁止筛选侧滑动
        DrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updataAppTag = false;
        onPermissionRequests(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void initView() {
        if (getIntent().getStringExtra("tag").equals(Constant.JOB_WD_COUNSELOR)) {
            menuImg.setVisibility(View.VISIBLE);
        } else if (getIntent().getStringExtra("tag").equals(Constant.JOB_XC_COUNSELOR)) {
            returnBtn.setVisibility(View.VISIBLE);
        }

        commonAdapter = new CommonAdapter<TokenEntity.authList>(SearchActivity.this, R.layout.common_item_text, authLists) {
            @Override
            protected void convert(ViewHolder viewHolder, TokenEntity.authList item, int position) {
                TextView textView = viewHolder.getView(R.id.text);
                textView.setPadding(0, 50, 0, 50);
                textView.setText(item.getText());
            }
        };
        gridView.setAdapter(commonAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("phone", searchEdit.getText().toString());//手机号
                bundle.putString("time", time);//时间
                bundle.putString("tag", authLists.get(i).getAuth());//标识
                bundle.putString("tagText", authLists.get(i).getText());//标识
                startActivity(BookbuildingActivity.class, bundle);
            }
        });

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                textHint.setVisibility(View.GONE);
                gridView.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        String value = SPUtils.getCache(SPUtils.FILE_USER, SPUtils.USER_JD);
        Gson gson = new Gson();
        authLists = gson.fromJson(value, new TypeToken<List<TokenEntity.authList>>() {
        }.getType());

        //删除 6 和 9 ，不属于建档
//        for (TokenEntity.authList v : authLists) {
//            if ("6".equals(v.getAuth()) || "9".equals(v.getAuth()))
//                authLists.remove(v);
//        }
        Iterator<TokenEntity.authList> iterator = authLists.iterator();
        while (iterator.hasNext()) {
            TokenEntity.authList authList = iterator.next();
            if ("6".equals(authList.getAuth()) || "9".equals(authList.getAuth()))
                iterator.remove();
        }

    }

    @OnClick({R.id.search_btn, R.id.return_btn, R.id.exit_btn, R.id.update_btn, R.id.menu_img})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_btn:
                if (CommonUtil.isChinaPhoneLegal(searchEdit.getText().toString())) {
                    searchData();
                } else {
                    CommonUtil.showToast("手机号输入有误");
                }
                break;
            case R.id.return_btn://打开左滑菜单
                finish();
                break;
            case R.id.menu_img://打开左滑菜单
                DrawerLayout.openDrawer(filtrateMenu);
                break;
            case R.id.update_btn://版本更新
                updataAppTag = true;
                onPermissionRequests(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.exit_btn://退出
                DrawerLayout.closeDrawer(filtrateMenu);
                exitLogin();

                break;
        }
    }

    public void searchData() {
        //搜索建档
        Map<String, Object> map = new HashMap<>();
        map.put("phone", searchEdit.getText().toString());
        HttpClient.getHttpApi().getSearchData(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                if ("0".equals(stringStringMap.get("isExist"))) {//如果订单不存在
                    time = stringStringMap.get("buildTime");
                    gridView.setVisibility(View.VISIBLE);
                    textHint.setVisibility(View.GONE);
                } else {//如果订单存在
                    gridView.setVisibility(View.GONE);
                    textHint.setText("由" + stringStringMap.get("creatorName") + " " + stringStringMap.get("createTime") + "已建档");
                    textHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    private void exitLogin() {
        CommonUtil.showLoadProgress(this);

        HttpClient.getHttpApi().exitLongin().enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                DialogUtil.dismissDialog();
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.TOKEN, "");
                startActivity(LoginActivity.class);
                finish();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                DialogUtil.dismissDialog();
                SPUtils.setCache(SPUtils.FILE_USER, SPUtils.TOKEN, "");
                startActivity(LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        PgyUpdateManager.unregister();
    }

    //检查存储权限
    public void onPermissionRequests(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //权限已有
                CommonUtil.updataApp(this, updataAppTag);
            } else {
                //没有权限，申请一下
                ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
            }
        } else {
            //权限已有
            CommonUtil.updataApp(this, updataAppTag);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限通过
                CommonUtil.updataApp(this, updataAppTag);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
