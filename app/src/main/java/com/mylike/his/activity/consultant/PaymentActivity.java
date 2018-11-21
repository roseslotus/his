package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/7/13.
 */
public class PaymentActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.ali_image)
    ImageView aliImage;
    @BindView(R.id.wx_image)
    ImageView wxImage;
    @BindView(R.id.money_text)
    TextView moneyText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
//        moneyText.setText(setDecimalFormat(getIntent().getStringExtra("money")));
        initData();
    }

    private void initData() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("businessType", "1");
        map.put("businessId", getIntent().getStringExtra("fid"));

        //添加意向
        HttpClient.getHttpApi().getPayment(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {

            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                moneyText.setText(setDecimalFormat(stringStringMap.get("totalFee")));
                Glide.with(PaymentActivity.this).load(stringStringMap.get("ali")).into(aliImage);
                Glide.with(PaymentActivity.this).load(stringStringMap.get("wx")).into(wxImage);
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }


    private String setDecimalFormat(String numberStr) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (TextUtils.isEmpty(numberStr)) {
            numberStr = "0";
        }
        Double number = Double.parseDouble(numberStr);
        decimalFormat.format(number);
        return decimalFormat.format(number);
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
