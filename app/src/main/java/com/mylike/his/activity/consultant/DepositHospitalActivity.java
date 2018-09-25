package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.HDepositEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.SPUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    private String clientId;//客户id
    private TagAdapter mAdapter;//快捷金额
    private List<String> moneyData = new ArrayList<>();//快捷金额值

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_hospital);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        mAdapter = new TagAdapter(moneyData) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                View view = LayoutInflater.from(DepositHospitalActivity.this).inflate(R.layout.common_item_text, null);
                TextView textView = view.findViewById(R.id.text);
                textView.setPadding(40, 20, 40, 20);
                textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_label_green));
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setText(moneyData.get(position));
                return textView;
            }
        };
        flowlayout.setAdapter(mAdapter);
        flowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (selectPosSet.isEmpty()) {
                    moneyEdit.setText("");//选项为空清空编辑框
                } else {
                    for (Integer i : selectPosSet)
                        moneyEdit.setText(moneyData.get(i));
                    moneyEdit.setSelection(moneyEdit.getText().toString().length());//将光标移至文字末尾
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
                //判断编辑框里的值是否快捷金额，如是快捷金额需高亮快捷选项
                if (s.length() > 2 && s.length() < 5) {//快捷金额大于2 位数，小于5 位数
                    for (int i = 0; i < moneyData.size(); i++) {
                        if (moneyData.get(i).equals(s.toString())) {
                            mAdapter.setSelectedList(i);//选中快捷金额
                            return;
                        }
                    }
                    mAdapter.setSelectedList(new HashSet<Integer>());
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
        HttpClient.getHttpApi().getBalance(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, Double>>() {
            @Override
            protected void onSuccess(Map<String, Double> stringIntegerMap) {
                //预交金余额
                balanceText.setText(CommonUtil.setTwoNumber(stringIntegerMap.get("AMOUNT")));
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });

        //填充快捷金额值
        moneyData.add("500");
        moneyData.add("1000");
        moneyData.add("1500");
        moneyData.add("2000");
        mAdapter.notifyDataChanged();
    }

    @OnClick({R.id.return_btn, R.id.submit_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn://提交
                if (moneyEdit.getText().toString().isEmpty())
                    CommonUtil.showToast("充值金额不能为空");
                else
                    submit();
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

        HashMap<String, Map<String, Object>> map1 = new HashMap<>();
        map1.put("chargeBill", map);
        HttpClient.getHttpApi().setHospitalDeposit(HttpClient.getRequestBody(map)).enqueue(new BaseBack<HDepositEntity>() {
            @Override
            protected void onSuccess(HDepositEntity hDepositEntity) {
                CommonUtil.showToast("提交成功");
                startActivity(CMainActivity.class, CMainActivity.GO_PAYMENT, hDepositEntity.getBillId());
                finish();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

}

