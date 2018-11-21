package com.mylike.his.activity.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/3/15.
 */

public class SurgeryDynamicDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.surgery_dynamic_list)
    ListView surgeryDynamicList;
    private List<String> date = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgery_dynamic_details);
        ButterKnife.bind(this);

        setDate();

        surgeryDynamicList.setAdapter(new CommonAdapter<String>(this, R.layout.item_surgery_dynamic_details_list, date) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        });
    }

    private void setDate() {
        for (int i = 1; i < 10; i++) {
            date.add("手术间" + i);
        }
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
