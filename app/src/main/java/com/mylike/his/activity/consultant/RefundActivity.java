package com.mylike.his.activity.consultant;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.adapter.ChargeListAdapter;
import com.mylike.his.core.BaseActivity;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/2/7.
 * 退款
 */

public class RefundActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.refund_list)
    ListView refundList;
    @Bind(R.id.refund_reason_btn)
    TextView refundReasonBtn;
    private List<String> date = new ArrayList<>();

    private ChargeListAdapter chargeListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        ButterKnife.bind(this);

        setDate();
        chargeListAdapter = new ChargeListAdapter(date, this);
        refundList.setAdapter(chargeListAdapter);
    }


    private void setDate() {
        for (int i = 0; i < 3; i++) {
            date.add("aaa" + i);
        }
    }

    @OnClick({R.id.refund_reason_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refund_reason_btn:
                View itemView = LayoutInflater.from(this).inflate(R.layout.dialog_refund_reason, null);
                WheelView wheelView = itemView.findViewById(R.id.wheelView);
                wheelView.setWheelAdapter(new ArrayWheelAdapter(this));
                WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
                style.selectedTextColor = Color.parseColor("#333333");
                style.textColor = Color.GRAY;
                style.selectedTextSize = 20;
                wheelView.setStyle(style);
                wheelView.setSkin(WheelView.Skin.Holo);

                wheelView.setWheelData(date);

                Dialog dialog = new Dialog(this, R.style.DialogSelectStyle);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.DialogAnimation);
                window.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp = window.getAttributes();
                WindowManager wm = window.getWindowManager();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
                dialog.setContentView(itemView);
                dialog.show();
                break;
        }
    }
}
