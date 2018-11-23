package com.mylike.his.activity.consultant;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.CustomerDetailsActivity;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.VisitInfoEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/6/22.
 */

public class VisitDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.name_text)
    TextView nameText;
    @BindView(R.id.phone_text)
    TextView phoneText;
    @BindView(R.id.card_text)
    TextView cardText;
    @BindView(R.id.tag_rl)
    RelativeLayout tagRl;
    @BindView(R.id.visit_name_tv)
    TextView visitNameTv;
    @BindView(R.id.grade_tv)
    TextView gradeTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.type_tv)
    TextView typeTv;
    @BindView(R.id.node_tv)
    TextView nodeTv;
    @BindView(R.id.purpose_tv)
    TextView purposeTv;
    @BindView(R.id.require_tv)
    TextView requireTv;
    @BindView(R.id.visit_not_ll)
    LinearLayout visitNotLl;
    @BindView(R.id.visit_name_text)
    TextView visitNameText;
    @BindView(R.id.time_text)
    TextView timeText;
    @BindView(R.id.type_text)
    TextView typeText;
    @BindView(R.id.result_text)
    TextView resultText;
    @BindView(R.id.feedback_text)
    TextView feedbackText;
    @BindView(R.id.note_text)
    TextView noteText;
    @BindView(R.id.visit_has_ll)
    LinearLayout visitHasLl;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.wx_text1)
    TextView wxText1;
    @BindView(R.id.copy_btn1)
    TextView copyBtn1;
    @BindView(R.id.wx_text)
    TextView wxText;
    @BindView(R.id.copy_btn)
    TextView copyBtn;

    private int visitValue;
    private String bpdId;
    private String customerId;

    private Drawable sex_g_icon;
    private Drawable sex_b_icon;

    private String wxh = "123456";//存储微信号

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_details);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        sex_g_icon = getResources().getDrawable(R.mipmap.sex_g_icon);
        sex_g_icon.setBounds(0, 0, sex_g_icon.getMinimumWidth(), sex_g_icon.getMinimumHeight());
        sex_b_icon = getResources().getDrawable(R.mipmap.sex_b_icon);
        sex_b_icon.setBounds(0, 0, sex_b_icon.getMinimumWidth(), sex_b_icon.getMinimumHeight());


        visitValue = getIntent().getIntExtra("visit_tag", 0);
        bpdId = getIntent().getStringExtra("bpd_id");
        if (visitValue == 0) {
            timeText.setText("未回访");
        } else {
            timeText.setText("已回访");
        }
        initData();
    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("bpdId", bpdId);

        HttpClient.getHttpApi().getVisitListDetails(HttpClient.getRequestBody(map)).enqueue(new BaseBack<VisitInfoEntity>() {
            @Override
            protected void onSuccess(VisitInfoEntity visitInfoEntity) {
                customerId = visitInfoEntity.getCustomerId();
                if (visitInfoEntity.getCustomerGender().equals("1")) {
                    nameText.setCompoundDrawables(sex_b_icon, null, null, null);
                } else {
                    nameText.setCompoundDrawables(sex_g_icon, null, null, null);
                }
                nameText.setText(visitInfoEntity.getCustomerName());//客户名称
                phoneText.setText(visitInfoEntity.getCustomerPhone());//客户手机号
                cardText.setText(visitInfoEntity.getCardName());

                if (TextUtils.isEmpty(visitInfoEntity.getWx())) {
                    copyBtn.setVisibility(View.GONE);
                    copyBtn1.setVisibility(View.GONE);
                }

                if (visitValue == 0) {//未回访详情
                    visitNotLl.setVisibility(View.VISIBLE);
                    visitHasLl.setVisibility(View.GONE);

                    visitNameTv.setText(visitInfoEntity.getKeyWord());//回访名称
                    gradeTv.setText(visitInfoEntity.getTaskLevel());//回访等级
                    timeTv.setText(visitInfoEntity.getPlanTime().substring(0, 11));//计划时间
                    typeTv.setText(visitInfoEntity.getObType());//回访类型
                    nodeTv.setText(visitInfoEntity.getInvokeNode());//触发节点
                    purposeTv.setText(visitInfoEntity.getVisitAims());//回访目的
                    requireTv.setText(visitInfoEntity.getVisitRequire());//回访要求
                    wxText1.setText(visitInfoEntity.getWx());

                } else {//已回访详情
                    visitNotLl.setVisibility(View.GONE);
                    visitHasLl.setVisibility(View.VISIBLE);

                    timeText.setText(visitInfoEntity.getVisitTime().substring(0, 11));//回访日期
                    typeText.setText(visitInfoEntity.getObType());//回访类型
                    resultText.setText(visitInfoEntity.getVisitResult());//回访结果
                    feedbackText.setText(visitInfoEntity.getResultText());//回访反馈
                    noteText.setText("暂无");//短信内容
                    wxText.setText(visitInfoEntity.getWx());
                }
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }


    @OnClick({R.id.return_btn, R.id.tag_rl, R.id.copy_btn, R.id.copy_btn1})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tag_rl:
                startActivity(CustomerDetailsActivity.class, "clientId", customerId);
                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.copy_btn:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(wxh);
                CommonUtil.showToast("复制成功");
                break;
            case R.id.copy_btn1:
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm1.setText(wxh);
                CommonUtil.showToast("复制成功");
                break;
        }
    }
}
