package com.mylike.his.activity.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
 * Created by zhengluping on 2018/1/31.
 */

public class RemindManagementActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.remind_management_list)
    ListView remindManagementList;

    private List<String> date = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_management);
        ButterKnife.bind(this);

        setDate();

        remindManagementList.setAdapter(new CommonAdapter<String>(this, R.layout.item_remind_management_list, date) {
            @Override
            protected void convert(final ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.remind_naem_text, item);

                viewHolder.setOnClickListener(R.id.state_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = viewHolder.getView(v.getId());
                        textView.setText("已重新订阅");
                        textView.setTextColor(getResources().getColor(R.color.gray_50));
                        textView.setEnabled(false);
                    }
                });
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

    private void setDate() {
        date.add("住院提醒");
        date.add("治疗提醒");
        date.add("手术提醒");
        date.add("住院提醒");
        date.add("治疗提醒");
        date.add("手术提醒");
    }
}
