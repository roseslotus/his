package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.ChargeEntity;
import com.mylike.his.entity.HDepositEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.utils.ToastUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zhengluping on 2018/1/30.
 * 住院押金
 */
public class DepositHospitalActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @Bind(R.id.money_edit)
    EditText moneyEdit;
    @Bind(R.id.remark_edit)
    EditText remarkEdit;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    @Bind(R.id.balance_text)
    TextView balanceText;
    @Bind(R.id.not_move)
    RadioButton notMove;
    @Bind(R.id.move)
    RadioButton move;
    @Bind(R.id.patternPayment)
    RadioGroup patternPayment;

    private List<String> data = new ArrayList<>();//快捷金额值
    private String ppValue;//支付方式
    private String clientId;//客户id

    private TagAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_hospital);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    private void initView() {
        mAdapter = new TagAdapter(data) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View view = LayoutInflater.from(DepositHospitalActivity.this).inflate(R.layout.common_item_text, null);
                TextView textView = view.findViewById(R.id.text);
                textView.setPadding(40, 20, 40, 20);
                textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_label_green));
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setText(data.get(position));
                return textView;
            }
        };
        flowlayout.setAdapter(mAdapter);
        flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                     if (selectPosSet.isEmpty()) {
                    moneyEdit.setText("");
                } else {
                    for (Integer i : selectPosSet)
                        moneyEdit.setText(data.get(i));
                    moneyEdit.setSelection(moneyEdit.getText().toString().length());//将光标移至文字末尾
                }
            }
        });

        patternPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (notMove.getId() == checkedId) {
                    ppValue = "0";
                } else {
                    ppValue = "1";
                }
            }
        });
        moneyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3 && s.length() <= 4) {
                    int i = 0;
                    boolean tag = true;
                    for (String value : data) {
                        if (value.equals(s.toString())) {
                            tag = false;
                            mAdapter.setSelectedList(i);
                            break;
                        }
                        i++;
                    }
                    if (tag) {
                        mAdapter.setSelectedList(new HashSet<Integer>());
                    }
                } else {
                    mAdapter.setSelectedList(new HashSet<Integer>());
                }
            }
        });
    }

    private void initData() {
        //获取客户id
        clientId = getIntent().getStringExtra("clientId");

        //获取住院押金余额的值
        HashMap<String, Object> map = new HashMap<>();
        map.put("cusId", clientId);//客户id
        HttpClient.getHttpApi().getBalance(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, Integer>>() {
            @Override
            protected void onSuccess(Map<String, Integer> stringIntegerMap) {
                balanceText.setText(stringIntegerMap.get("AMOUNT") + "");
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });

        //填充快捷金额值
        data.add("500");
        data.add("1000");
        data.add("1500");
        data.add("2000");
    }

    @OnClick({R.id.return_btn, R.id.submit_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn://提交
                if (TextUtils.isEmpty(moneyEdit.getText())) {
                    ToastUtils.showToast("请输入充值金额");
                } else if (ppValue == null) {
                    ToastUtils.showToast("请选择支付方式");
                } else {
                    submit();
                }
                break;
            case R.id.return_btn://返回
                finish();
                break;
        }
    }

    //提交
    private void submit() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cfCutomeriId", clientId);//客户id
        map.put("fShouldMoney", moneyEdit.getText() + "");//押金金额
        map.put("cfRemark", remarkEdit.getText() + "");//备注
        map.put("fConsultorId", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.EMP_ID));//咨询Id
        map.put("patternPayment", ppValue);//支付方式；0—非移动；1—移动支付

        HashMap<String, Map<String, Object>> map1 = new HashMap<>();
        map1.put("chargeBill", map);
        HttpClient.getHttpApi().setHospitalDeposit(HttpClient.getRequestBody(map)).enqueue(new BaseBack<HDepositEntity>() {
            @Override
            protected void onSuccess(HDepositEntity hDepositEntity) {
                ToastUtils.showToast("提交成功");
                finish();
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

}

