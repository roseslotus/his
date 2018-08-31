package com.mylike.his.activity.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.HDepositEntity;
import com.mylike.his.entity.SVProjectEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.utils.ToastUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/1/29.
 */

public class StoredValueActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.project_spinner)
    TextView projectSpinner;
    @Bind(R.id.type_spinner)
    TextView typeSpinner;
    @Bind(R.id.cash_text)
    EditText cashText;
    @Bind(R.id.presenter_text)
    EditText presenterText;
    @Bind(R.id.total_text)
    TextView totalText;
    @Bind(R.id.submit_btn)
    Button submitBtn;
//    @Bind(R.id.not_move)
//    RadioButton notMove;
//    @Bind(R.id.move)
//    RadioButton move;
//    @Bind(R.id.patternPayment)
//    RadioGroup patternPayment;
    @Bind(R.id.remark_edit)
    EditText remarkEdit;

//    private String ppValue;//支付方式
    private String clientId;//客户id
    private String projectId;//储值项目id


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored_value);
        ButterKnife.bind(this);
        setData();
        initView();
    }

    private void initView() {
//        patternPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (notMove.getId() == checkedId) {
//                    ppValue = "0";
//                } else {
//                    ppValue = "1";
//                }
//            }
//        });

        //获取客户id
        clientId = getIntent().getStringExtra("clientId");
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
                Long total = null;
                if (TextUtils.isEmpty(cashText.getText().toString()) && TextUtils.isEmpty(presenterText.getText().toString())) {
                    total = 0L;
                } else if (TextUtils.isEmpty(cashText.getText().toString())) {
                    total = 0 + Long.parseLong(presenterText.getText().toString());
                } else if (TextUtils.isEmpty(presenterText.getText().toString())) {
                    total = 0 + Long.parseLong(cashText.getText().toString());
                } else {
                    total = Long.parseLong(cashText.getText().toString()) + Long.parseLong(presenterText.getText().toString());
                }
                totalText.setText("￥" + total);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
            }

            @Override
            public void afterTextChanged(Editable s) {
                //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
            }
        };

        cashText.addTextChangedListener(watcher);
        presenterText.addTextChangedListener(watcher);
    }


    private PopupWindow projectPW;

    @OnClick({R.id.project_spinner, R.id.type_spinner, R.id.submit_btn, R.id.return_btn})
    @Override
    public void onClick(View v) {
        ListView listView = null;
        switch (v.getId()) {
            case R.id.project_spinner://项目选择
                CommonAdapter commonAdapter = new CommonAdapter<SVProjectEntity>(this, R.layout.common_item_text, svProjectEntityList) {
                    @Override
                    protected void convert(ViewHolder viewHolder, SVProjectEntity item, int position) {
                        TextView textView = viewHolder.getView(R.id.text);
                        textView.setPadding(30, 30, 30, 30);
                        textView.setText(item.getACCOUNT());
                    }
                };
                listView = setPopupWindow(commonAdapter, projectSpinner);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        projectId = svProjectEntityList.get(position).getACCOUNTID();
                        projectSpinner.setText(svProjectEntityList.get(position).getACCOUNT());
                        setTypeData(svProjectEntityList.get(position).getACCOUNTID());
                        projectPW.dismiss();
                    }
                });
                break;
            case R.id.type_spinner://类型选择
                CommonAdapter commonAdapter2 = new CommonAdapter<SVProjectEntity>(this, R.layout.common_item_text, svTypeEntityList) {
                    @Override
                    protected void convert(ViewHolder viewHolder, SVProjectEntity item, int position) {
                        TextView textView = viewHolder.getView(R.id.text);
                        textView.setPadding(30, 30, 30, 30);
                        textView.setText(item.getREMARK());
                    }
                };
                listView = setPopupWindow(commonAdapter2, typeSpinner);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        typeSpinner.setText(svTypeEntityList.get(position).getREMARK());
                        if (svTypeEntityList.get(position).getREMARK().equals("其他")) {
                            cashText.setEnabled(true);
                            presenterText.setEnabled(true);
                            cashText.setText("");
                            presenterText.setText("");
                        } else {
                            cashText.setEnabled(false);
                            presenterText.setEnabled(false);
                            cashText.setText(svTypeEntityList.get(position).getCASH() + "");
                            presenterText.setText(svTypeEntityList.get(position).getPRESENTMONEY() + "");
                        }
                        projectPW.dismiss();
                    }
                });
                break;
            case R.id.submit_btn:
                submit();

                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

    //wrap_content
    private ListView setPopupWindow(BaseAdapter adapter, TextView textView) {
        View view = getLayoutInflater().inflate(R.layout.common_item_list, null);
        projectPW = new PopupWindow(view, projectSpinner.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        projectPW.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_white_line_box_gray_5));
        projectPW.showAsDropDown(textView);
        ListView listView = view.findViewById(R.id.common_list);
        listView.setAdapter(adapter);
        return listView;
    }


    private List<SVProjectEntity> svProjectEntityList = new ArrayList<>();

    private void setData() {
        HttpClient.getHttpApi().getSVProject().enqueue(new BaseBack<List<SVProjectEntity>>() {
            @Override
            protected void onSuccess(List<SVProjectEntity> svProjectEntities) {
                svProjectEntityList = svProjectEntities;
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    private List<SVProjectEntity> svTypeEntityList = new ArrayList<>();

    private void setTypeData(String projectValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", projectValue);
        HttpClient.getHttpApi().getSVType(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<SVProjectEntity>>() {
            @Override
            protected void onSuccess(List<SVProjectEntity> svProjectEntities) {
                svTypeEntityList = svProjectEntities;
                svTypeEntityList.add(new SVProjectEntity("其他"));
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    private void submit() {

        HashMap<String, Object> ctFinCardrecord = new HashMap<>();
        ctFinCardrecord.put("memberid", clientId);//客户id
        ctFinCardrecord.put("accountid", projectId);//项目id
        ctFinCardrecord.put("cash", cashText.getText().toString());//储值现金金额
        ctFinCardrecord.put("present", presenterText.getText().toString());//储值赠送金额
        ctFinCardrecord.put("remark", remarkEdit.getText().toString());//备注

        HashMap<String, Object> map = new HashMap<>();
        map.put("ctFinCardrecord", ctFinCardrecord);
        map.put("fConsultorId", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.EMP_ID));//咨询Id
//        map.put("payType", ppValue);//支付方式；0—非移动；1—移动支付

//        HashMap<String, Object> map1 = new HashMap<>();
//        map1.put("ctFinCardrecord", map);
        HttpClient.getHttpApi().setSV(HttpClient.getRequestBody(map)).enqueue(new BaseBack<HDepositEntity>() {
            @Override
            protected void onSuccess(HDepositEntity hDepositEntity) {
//                Intent intent = new Intent();
//                intent.putExtra(CMainActivity.GO_PAYMENT, stringStringMap.get("billId"));
//                intent.putExtra("money", moneySum);
//                intent.setClass(OrderActivity.this, CMainActivity.class);
//                startActivity(intent);
                startActivity(CMainActivity.class, CMainActivity.GO_PAYMENT, hDepositEntity.getBillId());
                ToastUtils.showToast("提交成功");
                finish();
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }
}
