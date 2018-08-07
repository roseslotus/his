package com.mylike.his.activity.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
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

/**
 * Created by zhengluping on 2018/4/18.
 */

public class VisitShowActivity extends BaseActivity {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.screening_text)
    TextView screeningText;
    @Bind(R.id.visit_list)
    ListView visitList;

    private List<String> date = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_show);
        ButterKnife.bind(this);

        visitList.setAdapter(new CommonAdapter<String>(this, R.layout.item_visit_list, DataUtil.getData(10)) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        });

        visitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
