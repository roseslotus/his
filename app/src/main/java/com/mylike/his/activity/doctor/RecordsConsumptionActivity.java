package com.mylike.his.activity.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.activity.consultant.ChargeDetailsActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.utils.DataUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhengluping on 2018/1/31.
 */

public class RecordsConsumptionActivity extends BaseActivity {

    @Bind(R.id.charge_list)
    ListView chargeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_list);
        ButterKnife.bind(this);

        chargeList.setAdapter(new CommonAdapter<String>(this, R.layout.item_records_consumption_list, DataUtil.getData(10)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        });

        chargeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ChargeDetailsActivity.class);
            }
        });
    }

}
