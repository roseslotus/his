package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/7/18.
 */

public class TestActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.table1)
    TableLayout table1;
    @Bind(R.id.table2)
    TableLayout table2;
    @Bind(R.id.table3)
    TableLayout table3;
    @Bind(R.id.table4)
    TableLayout table4;
    @Bind(R.id.table5)
    TableLayout table5;
    @Bind(R.id.table6)
    TableLayout table6;
    @Bind(R.id.ll_tag1)
    LinearLayout llTag1;
    @Bind(R.id.ll_tag2)
    LinearLayout llTag2;
    @Bind(R.id.ll_tag3)
    LinearLayout llTag3;
    @Bind(R.id.ll_tag4)
    LinearLayout llTag4;
    @Bind(R.id.ll_tag5)
    LinearLayout llTag5;
    @Bind(R.id.ll_tag6)
    LinearLayout llTag6;
//    @Bind(R.id.return_btn)
//    ImageView returnBtn;
//    @Bind(R.id.webView)
//    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.ll_tag3, R.id.ll_tag4, R.id.ll_tag5, R.id.ll_tag6})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tag3:
                if (table3.getVisibility() == View.GONE) {
                    table3.setVisibility(View.VISIBLE);
                } else {
                    table3.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_tag4:
                if (table4.getVisibility() == View.GONE) {
                    table4.setVisibility(View.VISIBLE);
                } else {
                    table4.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_tag5:
                if (table5.getVisibility() == View.GONE) {
                    table5.setVisibility(View.VISIBLE);
                } else {
                    table5.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_tag6:
                if (table6.getVisibility() == View.GONE) {
                    table6.setVisibility(View.VISIBLE);
                } else {
                    table6.setVisibility(View.GONE);
                }
                break;
        }
    }
}
