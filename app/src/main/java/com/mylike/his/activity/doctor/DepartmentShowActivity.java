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

/**
 * Created by zhengluping on 2018/4/18.
 * 科室预约查询
 */

public class DepartmentShowActivity extends BaseActivity {

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.department_list)
    ListView departmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_show);
        ButterKnife.bind(this);

        departmentList.setAdapter(new CommonAdapter<String>(this, R.layout.item_department_list, DataUtil.getData(10)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        });

    }
}
