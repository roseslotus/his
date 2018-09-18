package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.DepartmentDoctorEntity;
import com.mylike.his.entity.DepartmentEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.orhanobut.logger.Logger;
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
 * Created by zhengluping on 2018/2/7.
 * 跨科
 */
public class MedicineActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.department_btn)
    TextView departmentBtn;
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.doctor_btn)
    TextView doctorBtn;
    @Bind(R.id.intention_btn)
    TextView intentionBtn;
    @Bind(R.id.remark_edit)
    EditText remarkEdit;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    private OptionsPickerView optionsPickerView;

    private List<DepartmentEntity> departmentEntitieList = new ArrayList<>();//科室数据
    private List<DepartmentDoctorEntity> departmentDoctorEntityList = new ArrayList<>();//科室医生数据
    //意向数据
    private List<IntentionEntity> intentionEntities1 = new ArrayList<>();
    private List<List<IntentionEntity>> intentionEntities2 = new ArrayList<>();
    private List<List<List<IntentionEntity>>> intentionEntities3 = new ArrayList<>();

    private String fidValue;//收费单id
    private String[] Intention;//意向数据
    private String doctorDepartment;//科室id
    private String doctorId;//医生id

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        fidValue = getIntent().getStringExtra("fid");

        //获取科室列表
        HttpClient.getHttpApi().getDepartmentList().enqueue(new BaseBack<List<DepartmentEntity>>() {
            @Override
            protected void onSuccess(List<DepartmentEntity> departmentEntities) {
                departmentEntitieList.addAll(departmentEntities);
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });

        //获取意向数据
        HttpClient.getHttpApi().getIntentionAll().enqueue(new BaseBack<List<IntentionEntity>>() {
            @Override
            protected void onSuccess(List<IntentionEntity> intentionEntities) {
                intentionEntities1.addAll(intentionEntities);
                //初始化意向数据
                initViewData();
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });
    }

    private void initViewData() {
        for (int i = 0; i < intentionEntities1.size(); i++) {
            List<IntentionEntity> intentionEntityList2 = new ArrayList<>();//二级意向
            List<List<IntentionEntity>> intentionEntityList3 = new ArrayList<>();//三级意向
            //在第一项添加空意向，如果选择“请选择”则代表此级意向为空
            intentionEntityList2.add(new IntentionEntity("请选择"));
            //如果无意向，添加空对象，防止数据为null 导致三个选项长度不匹配造成崩溃
            if (intentionEntities1.get(i).getChildren().size() == 0) {
                intentionEntityList3.add(intentionEntityList2);
            }
            for (int j = 0; j < intentionEntities1.get(i).getChildren().size(); j++) {
                //添加二级意向
                intentionEntityList2.add(intentionEntities1.get(i).getChildren().get(j));

                //如果二级意向循环第一次，这为三级意向添加一个空对象，对应二级意向的“请选择”
                if (j == 0) {
                    List<IntentionEntity> IList = new ArrayList<>();
                    IList.add(new IntentionEntity("请选择"));
                    intentionEntityList3.add(IList);
                }

                //添加三级意向
                List<IntentionEntity> IList3 = new ArrayList<>();
                IList3.add(new IntentionEntity("请选择"));
                if (intentionEntities1.get(i).getChildren().get(j).getChildren() != null || intentionEntities1.get(i).getChildren().get(j).getChildren().size() != 0) {
                    IList3.addAll(intentionEntities1.get(i).getChildren().get(j).getChildren());
                }
                intentionEntityList3.add(IList3);
            }

            intentionEntities2.add(intentionEntityList2);
            intentionEntities3.add(intentionEntityList3);
        }

        //重咨弹框初始化
        optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {//选择项
                Intention = new String[]{intentionEntities1.get(options1).getPbtid(), intentionEntities2.get(options1).get(options2).getPbtid(), intentionEntities3.get(options1).get(options2).get(options3).getPbtid()};
                Logger.d(Intention);
                String intentionText = "";
                if (intentionEntities1.get(options1).getPbtid() != null) {
                    intentionText = intentionText + intentionEntities1.get(options1).getPbtname();
                }
                if (intentionEntities2.get(options1).get(options2).getPbtid() != null) {
                    intentionText = intentionText + "/" + intentionEntities2.get(options1).get(options2).getPbtname();
                }
                if (intentionEntities3.get(options1).get(options2).get(options3).getPbtid() != null) {
                    intentionText = intentionText + "/" + intentionEntities3.get(options1).get(options2).get(options3).getPbtname();
                }
                intentionBtn.setText(intentionText);
            }
        }).setLayoutRes(R.layout.dialog_again_consult_not, new CustomListener() {//自定义布局
            @Override
            public void customLayout(View v) {
                final Button submitBtn = v.findViewById(R.id.submit_btn);

                //意向提交
                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionsPickerView.returnData();
                        optionsPickerView.dismiss();
                    }
                });
            }
        }).setContentTextSize(14).setDividerColor(getResources().getColor(R.color.green_50)).setLineSpacingMultiplier((float) 2.5).isDialog(true).build();
        optionsPickerView.setPicker(intentionEntities1, intentionEntities2, intentionEntities3);//设置数据
    }

    @OnClick({R.id.department_btn, R.id.return_btn, R.id.doctor_btn, R.id.intention_btn, R.id.submit_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.department_btn://咨询科室
                View itemView = DialogUtil.commomDialog(MedicineActivity.this, R.layout.common_item_list, 0);
                ListView listView = itemView.findViewById(R.id.common_list);
                listView.setBackgroundResource(R.drawable.bg_white_box_10);
                listView.setAdapter(new CommonAdapter<DepartmentEntity>(this, R.layout.common_item_text, departmentEntitieList) {
                    @Override
                    protected void convert(ViewHolder viewHolder, DepartmentEntity item, int position) {
                        TextView textView = viewHolder.getView(R.id.text);
                        textView.setPadding(20, 30, 20, 30);
                        textView.setText(item.getDeptname());
                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        departmentBtn.setText(departmentEntitieList.get(position).getDeptname());
                        doctorDepartment = departmentEntitieList.get(position).getDeptid();
                        DialogUtil.dismissDialog();
                        departmentDoctorEntityList.clear();
                        getDoctorData(departmentEntitieList.get(position).getDeptid());
                    }
                });
                break;
            case R.id.doctor_btn://推荐医生
//                if (departmentDoctorEntityList.isEmpty()) {
//                    CommonUtil.showToast("请先选择科室");
//                } else {
                if (doctorBtn.getText().toString().equals("请选择")) {
                    if (departmentDoctorEntityList.isEmpty()) {
                        CommonUtil.showToast("请先选择科室");
                    } else {
                        View itemView2 = DialogUtil.commomDialog(MedicineActivity.this, R.layout.common_item_list, 0);
                        ListView listView2 = itemView2.findViewById(R.id.common_list);
                        listView2.setBackgroundResource(R.drawable.bg_white_box_10);
                        listView2.setAdapter(new CommonAdapter<DepartmentDoctorEntity>(this, R.layout.common_item_text, departmentDoctorEntityList) {
                            @Override
                            protected void convert(ViewHolder viewHolder, DepartmentDoctorEntity item, int position) {
                                TextView textView = viewHolder.getView(R.id.text);
                                textView.setPadding(20, 30, 20, 30);
                                textView.setText(item.getEmpName());
                            }
                        });
                        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                doctorBtn.setText(departmentDoctorEntityList.get(position).getEmpName());
                                doctorId = departmentDoctorEntityList.get(position).getEmpId();
                                DialogUtil.dismissDialog();
                            }
                        });
                    }
                }
                break;
            case R.id.intention_btn://意向
                optionsPickerView.show();
                break;
            case R.id.submit_btn://提交
                if (TextUtils.isEmpty(doctorDepartment)) {
                    CommonUtil.showToast("科室不能为空，请选择科室");
                } else if (Intention == null || Intention.length == 0) {
                    CommonUtil.showToast("意向不能为空，请选择意向");
                } else {
                    submitData();
                }
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }

    private void getDoctorData(String deptid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("deptid", deptid);

        //获取科室列表
        HttpClient.getHttpApi().getDepartmentDoctorList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<DepartmentDoctorEntity>>() {

            @Override
            protected void onSuccess(List<DepartmentDoctorEntity> departmentDoctorEntities) {
                if (departmentDoctorEntities.isEmpty()) {
                    doctorBtn.setText("此科室暂无医生选择");
                } else {
                    doctorBtn.setText("请选择");
                }
                departmentDoctorEntityList.addAll(departmentDoctorEntities);


            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });

    }

    private void submitData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("doctorDepartment", doctorDepartment);
        map.put("doctorId", doctorId);
        map.put("remark", remarkEdit.getText().toString());

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("fid", fidValue);
        map1.put("CustomerIntention", Intention);
        map1.put("triage", map);

        //提交跨科
        HttpClient.getHttpApi().setMedicine(HttpClient.getRequestBody(map1)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                CommonUtil.showToast("提交成功");
                finish();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }


}
