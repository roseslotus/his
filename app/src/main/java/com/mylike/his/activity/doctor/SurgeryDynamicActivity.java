package com.mylike.his.activity.doctor;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/3/15.
 */

public class SurgeryDynamicActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.surgery_dynamic_grid)
    ListView surgeryDynamicGrid;
    @Bind(R.id.screening_text)
    TextView screeningText;


    private List<String> date = new ArrayList<>();
    private List<String> date2 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surgery_dynamic);
        ButterKnife.bind(this);

        setDate();

        surgeryDynamicGrid.setAdapter(new CommonAdapter<String>(this, R.layout.item_surgery_dynamic_grid, date) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.surgery_room, date.get(position));
            }
        });

        surgeryDynamicGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SurgeryDynamicActivity.this, SurgeryDynamicDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setDate() {
        for (int i = 1; i < 10; i++) {
            date.add("手术间" + i);
        }

        date2.add("全部");
        date2.add("进行中");
        date2.add("未开始");
        date2.add("空置");

    }

    private PopupWindow projectPW;

    @OnClick({R.id.return_btn, R.id.screening_text})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.screening_text:
                final View view = getLayoutInflater().inflate(R.layout.common_item_list, null);
                projectPW = new PopupWindow(view, screeningText.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
                projectPW.setBackgroundDrawable(getResources().getDrawable(R.color.black_100_99));
                projectPW.showAsDropDown(screeningText);
                final ListView listView = view.findViewById(R.id.common_list);
                listView.setDivider(new ColorDrawable(Color.WHITE));
                listView.setDividerHeight(1);
                listView.setAdapter(new CommonAdapter<String>(SurgeryDynamicActivity.this, R.layout.common_item_text, date2) {
                    @Override
                    protected void convert(ViewHolder viewHolder, String item, int position) {
                        final TextView textView = viewHolder.getView(R.id.text);
                        textView.setText(item);
                        textView.setTextColor(getResources().getColor(R.color.white));
                        textView.setPadding(5, 20, 5, 20);
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        screeningText.setText(date2.get(position));
                        projectPW.dismiss();
                    }
                });

                break;
            case R.id.return_btn:
                finish();
                break;

        }
    }


}
