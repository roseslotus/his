package com.mylike.his.doctor.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.mylike.his.entity.InHospitalDetailResp;
import com.mylike.his.entity.InHospitalSortResp;
import com.mylike.his.entity.OperationMySchedulingItemBean;
import com.mylike.his.entity.OperationMySchedulingListResp;
import com.mylike.his.http.HttpClient;
import com.mylike.his.presener.OperationMySchedulePresenter;
import com.mylike.his.utils.CustomerUtil;
import com.mylike.his.view.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 手术 我的排期
 * Created by thl on 2018/12/31.
 */

public class OperationMyScheduleActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_shuaixuan_panel)
    LinearLayout mLlShuaixuanPanel;
    @BindView(R.id.tv_left_title)
    TextView mTvLeftTitle;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.tv_right_title)
    TextView mTvRightTitle;
    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.search_edit)
    ClearEditText mSearchEdit;
    @BindView(R.id.tv_go_pre_day)
    TextView mTvGoPreDay;
    @BindView(R.id.tv_current_day)
    TextView mTvCurrentDay;
    @BindView(R.id.tv_go_next_day)
    TextView mTvGoNextDay;


    private CommonAdapter<OperationMySchedulingItemBean> commonAdapter;
    private List<OperationMySchedulingItemBean> mDatas = new ArrayList<>();
    private String mName;

    OperationMySchedulePresenter mPresenter;
    InHospitalSortResp inHospitalSortResp;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", -1);
        setContentView(R.layout.activity_operation_my_schedule);
        ButterKnife.bind(this);
        mPresenter = new OperationMySchedulePresenter(this);
        if (type == 1) {
            mPresenter.setUserId(BaseApplication.getLoginEntity().getUserId());
        }

        commonAdapter = new CommonAdapter<OperationMySchedulingItemBean>(this, R.layout.item_operation_my_scheduling_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, OperationMySchedulingItemBean item, int position) {
                CustomerUtil.setCustomerInfo(holder, item.getCustomer());
                TextView tvBookTime = holder.getView(R.id.tv_booking_time);
                tvBookTime.setVisibility(View.VISIBLE);
                tvBookTime.setText(item.getAppTime());

//                TextView tvStatus = holder.getView(R.id.tv_book_status);
//                BusnessUtil.setSchedulingStatus(tvStatus,item.getAnesthesia());

                holder.setText(R.id.tv_project_name, item.getProductsName());
                holder.setText(R.id.tv_anesthesia_method, item.getAnesthesia());
                holder.setText(R.id.tv_doctor_name, item.getDoctor());

                TextView tvProsthesisStatus = holder.getView(R.id.tv_prosthesis_status);
                TextView tvSkinTestStatus = holder.getView(R.id.tv_skintest_status);
                TextView tvMedicalexamStatus = holder.getView(R.id.tv_medicalexam_status);

                boolean isProsthesisNotOk = "未到货".equals(item.getProsthesis() == null ? "" : item.getProsthesis().trim());
                boolean isSkinTestNotOk = "不通过".equals(item.getSkinTest() == null ? "" : item.getSkinTest().trim());
                boolean isMedicalExamNotOk = "不通过".equals(item.getMedicalExam() == null ? "" : item.getMedicalExam().trim());

                int okColor = getResources().getColor(R.color.doctor_green_color);
                int notOkColor = Color.parseColor("#FE4E4E");

                tvProsthesisStatus.setText(item.getProsthesis());
                tvProsthesisStatus.setTextColor(isProsthesisNotOk ? notOkColor : okColor);

                tvSkinTestStatus.setText(item.getSkinTest());
                tvSkinTestStatus.setTextColor(isSkinTestNotOk ? notOkColor : okColor);

                tvMedicalexamStatus.setText(item.getMedicalExam());
                tvMedicalexamStatus.setTextColor(isMedicalExamNotOk ? notOkColor : okColor);

                holder.setImageResource(R.id.iv_test_status, !isProsthesisNotOk && !isSkinTestNotOk && !isMedicalExamNotOk ? R.mipmap.icon_shoushu_wodepaiqi_tongguo : R.mipmap.icon_shoushu_wodepaiqi_weitongguo);


            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OperationMyScheduleActivity.this, OperationMySchedulingDetailActivity.class);
                intent.putExtra("data", mDatas.get(i));
                startActivity(intent);
            }
        });

        if (type == 1) {
            mTvLeftTitle.setVisibility(View.VISIBLE);
            mTvCenterTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            mTvLeftTitle.setVisibility(View.GONE);
            mTvCenterTitle.setVisibility(View.VISIBLE);
            mTvRightTitle.setVisibility(View.GONE);
            mTvCenterTitle.setText("已接诊：10");
        }

        mRefreshLayout.autoRefresh();
        getScheduingSort();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
               refreshLoadData();
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore(new ResponseListener<OperationMySchedulingListResp>() {
                    @Override
                    public void onResponse(OperationMySchedulingListResp myBookingListResp) {
                        mDatas.addAll(myBookingListResp.getDataList());
                        commonAdapter.notifyDataSetChanged();
                        bindData(myBookingListResp.getList());
                        stopOver(mRefreshLayout);
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver(mRefreshLayout);
                    }
                });
            }
        });

        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_SEND) {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    mPresenter.setSearchName(mSearchEdit.getText().toString());
                    refreshLoadData();

                    return true;
                }
                return false;
            }
        });
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    mPresenter.setSearchName("");
                    refreshLoadData();
                }
            }
        });

    }

    public void refreshLoadData(){
        mPresenter.refresh(new ResponseListener<OperationMySchedulingListResp>() {
            @Override
            public void onResponse(OperationMySchedulingListResp myBookingListResp) {
                stopOver(mRefreshLayout);
                mDatas.clear();
                mDatas.addAll(myBookingListResp.getDataList());
                bindData(myBookingListResp.getList());
                commonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message, int errorCode) {
                stopOver(mRefreshLayout);
            }
        });
    }

    private void bindData(List<OperationMySchedulingListResp.ListBean> list) {
        if (list != null) {
            for (OperationMySchedulingListResp.ListBean listBean : list) {
                if (listBean.getName().equals("排台总数")) {
                    mTvLeftTitle.setText("排台总数:" + listBean.getValue());
                } else if (listBean.getName().equals("已排台")) {
                    mTvCenterTitle.setText("已排台:" + listBean.getValue());
                } else if (listBean.getName().equals("已完成")) {
                    mTvRightTitle.setText("已完成:" + listBean.getValue());
                }
            }
        }
    }


    @OnClick({R.id.ll_shuaixuan_panel, R.id.return_btn, R.id.search_edit, R.id.tv_go_pre_day, R.id.tv_current_day, R.id.tv_go_next_day})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_shuaixuan_panel:
                if (inHospitalSortResp != null) {
                    new ChoiceZhuyuanShuaixuanPopupMenu().setDatas(inHospitalSortResp.getDropdowns()).show(getFragmentManager(), "ChoiceZhuyuanShuaixuanPopupMenu");
                }
                break;
            case R.id.tv_left_title:
                break;
            case R.id.tv_center_title:
                break;
            case R.id.tv_right_title:
                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.search_edit:
                break;
            case R.id.tv_go_pre_day:
                mPresenter.goPreDay();
                mTvCurrentDay.setText(mPresenter.formatDate());
                refreshLoadData();
                break;
            case R.id.tv_current_day:
                break;
            case R.id.tv_go_next_day:
                mPresenter.goNexDay();
                mTvCurrentDay.setText(mPresenter.formatDate());
                refreshLoadData();
                break;
        }
    }

    public void getScheduingSort() {
//        CommonUtil.showLoadProgress(this);
        HttpClient.getHttpApi().getScheduingSort(BaseApplication.getLoginEntity().getToken()).enqueue(new Callback<InHospitalSortResp>() {
            @Override
            public void onResponse(Call<InHospitalSortResp> call, Response<InHospitalSortResp> response) {
//                CommonUtil.dismissLoadProgress();
                inHospitalSortResp = response.body();

            }

            @Override
            public void onFailure(Call<InHospitalSortResp> call, Throwable t) {
//                CommonUtil.dismissLoadProgress();
            }
        });
    }
}
