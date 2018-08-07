package com.mylike.his.activity.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
 * 已开立处方
 */

public class TreatmentRecordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.treatment_record_list)
    ListView treatmentRecordList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_record);
        ButterKnife.bind(this);

        View view = View.inflate(this, R.layout.common_item_text, null);
        TextView textView = view.findViewById(R.id.text);
        textView.setText("张三 136****123");
        textView.setPadding(0, 10, 0, 0);

        textView.setTextColor(getResources().getColor(R.color.black_50));
        treatmentRecordList.addHeaderView(textView);
        treatmentRecordList.setAdapter(new CommonAdapter<String>(this, R.layout.item_treatment_record_list, DataUtil.getData(5)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
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
