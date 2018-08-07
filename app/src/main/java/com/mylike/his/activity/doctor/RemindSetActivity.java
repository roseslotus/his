package com.mylike.his.activity.doctor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/31.
 */

public class RemindSetActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.label_1)
    TagFlowLayout label1;
    @Bind(R.id.label_2)
    TagFlowLayout label2;
    @Bind(R.id.label_3)
    TagFlowLayout label3;
    private List<String> date = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_set);
        ButterKnife.bind(this);
        final LayoutInflater mInflater = LayoutInflater.from(this);

        setDate();

        label1.setAdapter(new TagAdapter<String>(date) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) mInflater.inflate(R.layout.item_text_label, label1, false);
                textView.setText(s);
                return textView;
            }
        });

        label2.setAdapter(new TagAdapter<String>(date) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) mInflater.inflate(R.layout.item_text_label, label1, false);
                textView.setText(s);
                return textView;
            }
        });

        label3.setAdapter(new TagAdapter<String>(date) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView textView = (TextView) mInflater.inflate(R.layout.item_text_label, label1, false);
                textView.setText(s);
                return textView;
            }
        });

    }

    private void setDate() {
        for (int i = 0; i < 10; i++) {
            date.add("aaa" + i);
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
