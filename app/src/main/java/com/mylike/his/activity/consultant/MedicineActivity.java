package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.DepartmentDoctorEntity;
import com.mylike.his.entity.DepartmentEntity;
import com.mylike.his.entity.IntentionAddEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.entity.SVProjectEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.ViewUtil;
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
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.intention_btn)
    TextView intentionBtn;
    @Bind(R.id.remark_edit)
    EditText remarkEdit;
    @Bind(R.id.submit_btn)
    Button submitBtn;
    @Bind(R.id.department_spinner)
    Spinner departmentSpinner;
    @Bind(R.id.doctor_spinner)
    Spinner doctorSpinner;

    private OptionsPickerView IntentionPV;
    private ViewUtil viewUtil = new ViewUtil();

    private CommonAdapter departmentAdapter;//科室适配器
    private CommonAdapter doctorAdapter;//医生适配器
    private List<DepartmentEntity> departmentEntitieList = new ArrayList<>();//科室数据
    private List<DepartmentDoctorEntity> departmentDoctorEntityList = new ArrayList<>();//科室医生数据

    //意向数据
    private List<IntentionEntity> intentionEntities1 = new ArrayList<>();

    private String fidValue;//收费单id
    private String[] Intention = new String[]{};//意向数据
    private String departmentId;//科室id
    private String departmentValue;//科室id
    private String doctorId;//医生id
    private String doctorValue;//医生id

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    //初始化控件
    private void initView() {
        //收费单id
        fidValue = getIntent().getStringExtra("fid");

        //下拉框适配器
        departmentAdapter = setAdapter(departmentSpinner, departmentAdapter, departmentEntitieList);
        doctorAdapter = setAdapter(doctorSpinner, doctorAdapter, departmentDoctorEntityList);

        //选中意向的值
        viewUtil.setIntentionListener(new ViewUtil.OnIntentionListener() {
            @Override
            public void onOptionsSelect(IntentionAddEntity intentionAddEntity) {
                //选中意向数据
                Intention = new String[]{intentionAddEntity.getItemFirst(), intentionAddEntity.getItemSecond(), intentionAddEntity.getItemThird()};
                if (intentionAddEntity.getIntentionStr().isEmpty()) {
                    intentionBtn.setText("请选择");
                    intentionBtn.setTextColor(getResources().getColor(R.color.gray_49));
                } else {
                    intentionBtn.setTextColor(getResources().getColor(R.color.black_50));
                    intentionBtn.setText(intentionAddEntity.getIntentionStr());
                }
            }
        });
    }

    //初始化数据
    private void initData() {
        //获取科室列表
        HttpClient.getHttpApi().getDepartmentList().enqueue(new BaseBack<List<DepartmentEntity>>() {
            @Override
            protected void onSuccess(List<DepartmentEntity> departmentEntities) {
                departmentEntitieList.addAll(departmentEntities);
                departmentAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });

        //获取意向数据
        HttpClient.getHttpApi().getIntentionAll().enqueue(new BaseBack<List<IntentionEntity>>() {
            @Override
            protected void onSuccess(List<IntentionEntity> intentionEntities) {
                intentionEntities1.add(new IntentionEntity("请选择"));
                intentionEntities1.addAll(intentionEntities);
                IntentionPV = viewUtil.initIntention(MedicineActivity.this, IntentionPV, intentionEntities1);
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //获取医生数据
    private void getDoctorData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("deptid", departmentId);//科室id

        //获取科室列表
        HttpClient.getHttpApi().getDepartmentDoctorList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<DepartmentDoctorEntity>>() {

            @Override
            protected void onSuccess(List<DepartmentDoctorEntity> departmentDoctorEntities) {
                departmentDoctorEntityList.clear();
                if (departmentDoctorEntities.isEmpty()) {
                    departmentDoctorEntityList.add(new DepartmentDoctorEntity("此科室暂无医生"));
                } else {
                    departmentDoctorEntityList.add(new DepartmentDoctorEntity("请选择"));
                }
                departmentDoctorEntityList.addAll(departmentDoctorEntities);
                doctorSpinner.setSelection(0, true);
                doctorId = "";
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    @OnClick({R.id.return_btn, R.id.intention_btn, R.id.submit_btn})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.intention_btn://选择意向
                if (IntentionPV == null)
                    CommonUtil.showToast("意向数据获取失败，请稍后再试");
                else
                    IntentionPV.show();
                break;

            case R.id.submit_btn://提交
                if (TextUtils.isEmpty(departmentId))
                    CommonUtil.showToast("提交数据失败，请稍后再试");
                else
                    submitData();
                break;

            case R.id.return_btn://返回
                finish();
                break;
        }
    }


    private void submitData() {
        CommonUtil.showLoadProgress(this);

        HashMap<String, Object> map = new HashMap<>();
        map.put("doctorDepartment", departmentId);//科室id
        map.put("doctorId", doctorId);//医生id
        map.put("remark", remarkEdit.getText().toString());//备注id

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("fid", fidValue);//收费单id
        map1.put("CustomerIntention", Intention);//意向
        map1.put("triage", map);//科室数据

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

    //设置下拉选项
    private CommonAdapter setAdapter(final Spinner spinner, CommonAdapter commonAdapter, final Object object) {
        commonAdapter = new CommonAdapter(this, R.layout.common_item_text, (List) object) {
            @Override
            protected void convert(ViewHolder viewHolder, Object item, int position) {
                TextView textView = viewHolder.getView(R.id.text);
                textView.setTextColor(getResources().getColor(R.color.black_50));
                textView.setGravity(Gravity.LEFT);
                textView.setPadding(30, 30, 30, 30);

                if (spinner == departmentSpinner) {//科室显示的值
                    textView.setText(((DepartmentEntity) item).getDeptname());
                }
                if (spinner == doctorSpinner) {//医生显示的值
                    //设置请选择项为灰色
                    if (TextUtils.isEmpty(departmentDoctorEntityList.get(position).getEmpId())) {
                        textView.setTextColor(getResources().getColor(R.color.gray_49));
                    } else {
                        textView.setTextColor(getResources().getColor(R.color.black_50));
                    }
                    textView.setText(((DepartmentDoctorEntity) item).getEmpName());
                }
            }
        };
        spinner.setAdapter(commonAdapter);
        //获取下拉框里的值
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner == departmentSpinner) {//科室
                    departmentId = departmentEntitieList.get(i).getDeptid();//获取科室id
                    departmentValue = "科室:" + departmentEntitieList.get(i).getDeptname();//获取科室名称（分诊需要）
                    doctorValue = "";
                    getDoctorData();//刷新医生数据

                } else if (spinner == doctorSpinner) {//医生
                    doctorId = departmentDoctorEntityList.get(i).getEmpId();//医生id

                    if (!doctorId.isEmpty())//“请选择”选项id为空
                        doctorValue = "推荐医生:" + departmentDoctorEntityList.get(i).getEmpName();
                    else
                        doctorValue = "";
                }

                //更改分诊备注的值
                remarkEdit.setText(departmentValue);
                //有医生拼接医生的值
                if (!TextUtils.isEmpty(doctorValue))
                    remarkEdit.setText(departmentValue + " " + doctorValue);
                //将光标移至文字末尾
                remarkEdit.setSelection(remarkEdit.getText().toString().length());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return commonAdapter;
    }
}
