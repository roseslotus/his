package com.mylike.his.doctor.activity;

import android.content.Context;
import android.content.Intent;
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
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.DaiZhenResp;
import com.mylike.his.entity.InHospitalSortResp;
import com.mylike.his.entity.WoDeDaiZhenItemBean;
import com.mylike.his.http.HttpClient;
import com.mylike.his.presener.WoDeDaiZhenPresenter;
import com.mylike.his.utils.BusnessUtil;
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
 * 门诊 我的预约
 * Created by thl on 2018/12/31.
 */

public class WoDeJieZhenActivity extends BaseActivity {

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


    private WoDeDaiZhenPresenter mPresenter;
    private CommonAdapter<WoDeDaiZhenItemBean> commonAdapter;
    private List<WoDeDaiZhenItemBean> mDatas = new ArrayList<>();
    private String mName;
    private int type;
    private InHospitalSortResp inHospitalSortResp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_yuyue);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        mPresenter = new WoDeDaiZhenPresenter(this);
        mTvCurrentDay.setText(mPresenter.formatDate());
        mPresenter.setStatus(2);
        if (type == 1) {
            mPresenter.setUserId(BaseApplication.getLoginEntity().getUserId());
        }

        commonAdapter = new CommonAdapter<WoDeDaiZhenItemBean>(this, R.layout.item_wode_yuyue_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, WoDeDaiZhenItemBean item, int position) {
                CustomerMenZhenBean customer = item.getCustomer();
                CustomerUtil.setCustomerInfo(holder, customer);

                TextView tvStatus = holder.getView(R.id.tv_book_status);
                BusnessUtil.setMenZhenJieZhenStatus(tvStatus, item.getStatus());

                holder.setText(R.id.tv_project_name, item.getProductsName());
                holder.setText(R.id.tv_book_status, item.getStatus());
                holder.setText(R.id.tv_booking_time, item.getTriageTime());
                holder.setText(R.id.tv_zhengdan_type, "是".equals(item.getType()) ? "[初诊]" : "[复诊]");


                holder.setText(R.id.tv_doctor_name, item.getDoctor());

            }
        };
        mListView.setAdapter(commonAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WoDeDaiZhenItemBean data = mDatas.get(i);
                Intent intent = new Intent(WoDeJieZhenActivity.this, MenZhenDetailActivity.class);
                intent.putExtra("WoDeDaiZhenItemBean", data);
                startActivity(intent);
            }
        });

        mTvLeftTitle.setVisibility(View.GONE);
        mTvCenterTitle.setVisibility(View.VISIBLE);
        mTvRightTitle.setVisibility(View.GONE);
        mTvCenterTitle.setText("已接诊：0");
        setListeners();
        mRefreshLayout.autoRefresh();
        getMyWaitingRegistSort();
    }

    private void setListeners() {

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore(new ResponseListener<DaiZhenResp>() {
                    @Override
                    public void onResponse(DaiZhenResp daiZhenResp) {
                        mDatas.addAll(daiZhenResp.getDataList());
                        commonAdapter.notifyDataSetChanged();
                        bindData(daiZhenResp.getList());
                        stopOver();
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver();
                    }
                });
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLoadData();
            }
        });

        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE||actionId == EditorInfo.IME_ACTION_SEARCH||actionId == EditorInfo.IME_ACTION_SEND) {
                    InputMethodManager imm = (InputMethodManager)v.getContext()
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
                if (TextUtils.isEmpty(s.toString())){
                    mPresenter.setSearchName("");
                    refreshLoadData();
                }
            }
        });
    }

    private void refreshLoadData(){
        mPresenter.refresh(new ResponseListener<DaiZhenResp>() {
            @Override
            public void onResponse(DaiZhenResp daiZhenResp) {
                mDatas.clear();
                mDatas.addAll(daiZhenResp.getDataList());
                commonAdapter.notifyDataSetChanged();
                bindData(daiZhenResp.getList());
                stopOver();
            }

            @Override
            public void onError(String message, int errorCode) {
                stopOver();
            }
        });
    }

    private void bindData(List<DaiZhenResp.ListBean> list) {
        if (list != null) {
            for (DaiZhenResp.ListBean listBean : list) {
                if (listBean.getName().equals("总计数")) {
                    mTvCenterTitle.setText("已接诊:" + listBean.getValue());
                }
            }
        }
    }

    private void stopOver() {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

    public void getMyWaitingRegistSort() {
//        CommonUtil.showLoadProgress(this);
        HttpClient.getHttpApi().getMyWaitingRegistSort(BaseApplication.getLoginEntity().getToken()).enqueue(new Callback<InHospitalSortResp>() {
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
}
