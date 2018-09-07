package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.ToastUtils;
import com.mylike.his.view.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.search_btn)
    Button searchBtn;
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.search_edit)
    ClearEditText searchEdit;
    @Bind(R.id.text_hint)
    TextView textHint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.search_btn,R.id.return_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_btn:
                if (CommonUtil.isChinaPhoneLegal(searchEdit.getText().toString())) {
                    searchData();
                } else {
                    ToastUtils.showToast("手机号输入有误");
                }
                break;
            case R.id.return_btn:
                finish();
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
                    Intent intent = new Intent();
                    intent.putExtra("phone", searchEdit.getText().toString());
                    intent.putExtra("time", stringStringMap.get("buildTime"));
                    intent.setClass(SearchActivity.this, BookbuildingActivity.class);
                    startActivity(intent);
                } else {//如果订单存在
                    textHint.setText("由" + stringStringMap.get("creatorName") + " " + stringStringMap.get("createTime") + "已建档");
                }
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }
}
