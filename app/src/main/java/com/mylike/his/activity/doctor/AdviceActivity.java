package com.mylike.his.activity.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.utils.DataUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/31.
 * 医嘱查询
 */

public class AdviceActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.return_btn)
    ImageView returnBtn;

    @Bind(R.id.advice_list)
    ListView adviceList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ButterKnife.bind(this);

        adviceList.setAdapter(new CommonAdapter<String>(this, R.layout.item_advice_list, DataUtil.getData(5)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
            }
        });

        adviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(AdviceDetailsActivity.class);
            }
        });
    }

    @OnClick({R.id.return_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
