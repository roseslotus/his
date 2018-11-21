package com.mylike.his.activity.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.utils.DataUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/31.
 * 已开立处方
 */

public class PrescriptionActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.prescription_list)
    ListView prescription_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        ButterKnife.bind(this);

        prescription_list.setAdapter(new CommonAdapter<String>(this, R.layout.item_prescription_list, DataUtil.getData(5)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
            }
        });
    }

    @OnClick({R.id.return_btn})
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
