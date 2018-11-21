package com.mylike.his.activity.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
 */

public class MedicalShowActivity extends BaseActivity {

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.medical_list)
    ListView medicalList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_show);
        ButterKnife.bind(this);

        medicalList.setAdapter(new CommonAdapter<String>(this, R.layout.item_medical_list, DataUtil.getData(10)) {
            @Override
            protected void convert(final ViewHolder viewHolder, String item, int position) {

                //意向列表
                ListView listView = viewHolder.getView(R.id.intention_list);
                listView.setAdapter(new CommonAdapter<String>(MedicalShowActivity.this, R.layout.item_intention_list, DataUtil.getData(3)) {
                    @Override
                    protected void convert(ViewHolder viewHolder, String item, int position) {
                    }
                });
                //意向列表高度
                setListViewHeightBasedOnChildren(listView);
                //解决点击内嵌listview无反应
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(MedicalDetailsActivity.class);
                    }
                });
            }
        });

        medicalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(MedicalDetailsActivity.class);
            }
        });
    }

    //设置列表高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        int listItemHeight = listItem.getMeasuredHeight();
        int totalHeight = listItemHeight * listAdapter.getCount();

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (1 * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
