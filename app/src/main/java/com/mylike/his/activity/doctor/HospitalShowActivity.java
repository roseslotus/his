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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/3/13.
 * 今日住院顾客列表
 */
public class HospitalShowActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.hospital_list)
    ListView hospitalList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_show);
        ButterKnife.bind(this);

        hospitalList.setAdapter(new CommonAdapter<String>(this, R.layout.item_hospital_list, DataUtil.getData(10)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        });

        hospitalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(SurgeryDetailsActivity.class);
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
