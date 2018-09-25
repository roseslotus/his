package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.HDepositEntity;
import com.mylike.his.entity.SVProjectEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/29.
 * 储值
 */
public class StoredValueActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.cash_text)
    EditText cashText;
    @Bind(R.id.presenter_text)
    EditText presenterText;
    @Bind(R.id.total_text)
    TextView totalText;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    @Bind(R.id.remark_edit)
    EditText remarkEdit;
    @Bind(R.id.project_spinner)
    Spinner projectSpinner;
    @Bind(R.id.type_spinner)
    Spinner typeSpinner;

    private String clientId;//客户id
    private String projectId;//储值项目id

    private CommonAdapter projectAdapter;//储值项目
    private CommonAdapter typeAdapter;//储值类型
    private List<SVProjectEntity> svProjectEntityList = new ArrayList<>();//储值项目
    private List<SVProjectEntity> svTypeEntityList = new ArrayList<>();//储值类型

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored_value);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {

        //监听文本变化，计算储值金额 + 赠送金额
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double total = 0.0;//初始化
                //储值和赠送押金都为空
                if (TextUtils.isEmpty(cashText.getText().toString()) && TextUtils.isEmpty(presenterText.getText().toString())) {
                    total = 0.0;

                    //如果储值金额为空
                } else if (TextUtils.isEmpty(cashText.getText().toString())) {
                    if (s.toString().equals("."))//禁止第一位输入“.”
                        presenterText.setText("");
                    else
                        total = 0 + Double.parseDouble(presenterText.getText().toString());

                    //如果赠送金额为空
                } else if (TextUtils.isEmpty(presenterText.getText().toString())) {
                    if (s.toString().equals("."))//禁止第一位输入“.”
                        cashText.setText("");
                    else
                        total = 0 + Double.parseDouble(cashText.getText().toString());

                    //储值和赠送押金都不为空
                } else {
                    total = Double.parseDouble(cashText.getText().toString()) + Double.parseDouble(presenterText.getText().toString());
                }
                //保留两位数展示
                totalText.setText(CommonUtil.setTwoNumber(total));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        //现金以及赠送金额发生改变时
        cashText.addTextChangedListener(watcher);
        presenterText.addTextChangedListener(watcher);

        //初始化适配器
        projectAdapter = setAdapter(projectSpinner, projectAdapter, svProjectEntityList);
        typeAdapter = setAdapter(typeSpinner, typeAdapter, svTypeEntityList);

    }

    private void initData() {
        //获取客户id
        clientId = getIntent().getStringExtra("clientId");

        //获取储值项目
        HttpClient.getHttpApi().getSVProject().enqueue(new BaseBack<List<SVProjectEntity>>() {
            @Override
            protected void onSuccess(List<SVProjectEntity> svProjectEntities) {
                svProjectEntityList.addAll(svProjectEntities);
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //设置下拉选项
    private CommonAdapter setAdapter(final Spinner spinner, CommonAdapter commonAdapter, final List<SVProjectEntity> svList) {
        commonAdapter = new CommonAdapter<SVProjectEntity>(this, R.layout.common_item_text, svList) {
            @Override
            protected void convert(ViewHolder viewHolder, SVProjectEntity item, int position) {
                TextView textView = viewHolder.getView(R.id.text);
                textView.setTextColor(getResources().getColor(R.color.black_50));
                textView.setGravity(Gravity.LEFT);
                textView.setPadding(15, 30, 30, 30);

                if (spinner == projectSpinner) {//储值项目下拉框
                    textView.setText(item.getACCOUNT());
                }
                if (spinner == typeSpinner) {//储值类型下拉框
                    textView.setText(item.getREMARK());
                }
            }
        };
        spinner.setAdapter(commonAdapter);

        //获取下拉框里的值
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner == projectSpinner) {//储值项目下拉框
                    projectId = svProjectEntityList.get(i).getACCOUNTID();//获取项目id
                    setTypeData();//刷新储值类型下拉数据
                } else if (spinner == typeSpinner) {//储值类型下拉框
                    setSelectSpinner(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return commonAdapter;
    }

    @OnClick({R.id.submit_btn, R.id.return_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_btn:
                if (cashText.getText().toString().isEmpty()) {
                    CommonUtil.showToast("储值现金不能为空");
                } else {
                    submit();
                }
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

    //获取储值类型
    private void setTypeData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", projectId);//项目id
        HttpClient.getHttpApi().getSVType(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<SVProjectEntity>>() {
            @Override
            protected void onSuccess(List<SVProjectEntity> svProjectEntities) {
                svTypeEntityList.clear();
                svTypeEntityList.addAll(svProjectEntities);
                svTypeEntityList.add(new SVProjectEntity("其他"));//没给储值类型添加“其他”项，作为可随便编辑金额选项

                //默认选中第一项
                typeSpinner.setSelection(0, true);
                setSelectSpinner(0);

                typeAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //设置储值类型的默认选项（解决默认第一条变更项目不变值的bug）
    private void setSelectSpinner(int position) {
        if (svTypeEntityList.get(position).getREMARK().equals("其他")) {
            //金额设置可编辑
            cashText.setEnabled(true);
            presenterText.setEnabled(true);
            //清空金额编辑框
            cashText.setText("");
            presenterText.setText("");
        } else {
            //金额设置不可编辑
            cashText.setEnabled(false);
            presenterText.setEnabled(false);
            cashText.setText(svTypeEntityList.get(position).getCASH() + "");
            presenterText.setText(svTypeEntityList.get(position).getPRESENTMONEY() + "");
        }
    }

    //提交储值
    private void submit() {
        //基础数据
        HashMap<String, Object> ctFinCardrecord = new HashMap<>();
        ctFinCardrecord.put("memberid", clientId);//客户id
        ctFinCardrecord.put("accountid", projectId);//项目id
        ctFinCardrecord.put("cash", cashText.getText().toString());//储值现金金额
        if (presenterText.getText().toString().isEmpty()) {//如果赠送金额为空（注：后台没处理，不能传空）
            ctFinCardrecord.put("present", "0.00");//储值赠送金额
        } else {
            ctFinCardrecord.put("present", presenterText.getText().toString());//储值赠送金额
        }
        ctFinCardrecord.put("remark", remarkEdit.getText().toString());//备注

        HashMap<String, Object> map = new HashMap<>();
        map.put("ctFinCardrecord", ctFinCardrecord);//基础数据
        map.put("fConsultorId", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.EMP_ID));//咨询Id

        HttpClient.getHttpApi().setSV(HttpClient.getRequestBody(map)).enqueue(new BaseBack<HDepositEntity>() {
            @Override
            protected void onSuccess(HDepositEntity hDepositEntity) {
                startActivity(CMainActivity.class, CMainActivity.GO_PAYMENT, hDepositEntity.getBillId());
                CommonUtil.showToast("提交成功");
                finish();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }
}
