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
import android.widget.Toast;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.doctor.popup.ChoiceSortPopupMenu;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.DaiZhenResp;
import com.mylike.his.entity.InHospitalSortResp;
import com.mylike.his.entity.MyInHostpitalListResp;
import com.mylike.his.entity.SortsBean;
import com.mylike.his.entity.WoDeDaiZhenItemBean;
import com.mylike.his.http.HttpClient;
import com.mylike.his.presener.WoDeDaiZhenPresenter;
import com.mylike.his.utils.CustomerUtil;
import com.mylike.his.utils.ViewUtil;
import com.mylike.his.view.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的待诊
 * Created by thl on 2018/12/31.
 */

public class WoDeDaizhenActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.return_btn)
    ImageView mReturnBtn;
    @BindView(R.id.search_edit)
    ClearEditText mSearchEdit;
    @BindView(R.id.tag_ll)
    LinearLayout mTagLl;
    @BindView(R.id.tv_wode_daizheng_number)
    TextView mTvWodeDaizhengNumber;
    @BindView(R.id.iv_sort_image)
    ImageView mIvSortImage;
    @BindView(R.id.ll_sort_panel)
    LinearLayout mLlSortPanel;


    private CommonAdapter<WoDeDaiZhenItemBean> commonAdapter;
    private List<WoDeDaiZhenItemBean> mDatas = new ArrayList<>();
    private String mName;
    private WoDeDaiZhenPresenter mPresenter;
    private int type;
    private InHospitalSortResp inHospitalSortResp;

    private int mDaizhenTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_daizhen);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        mPresenter = new WoDeDaiZhenPresenter(this);
        if (type == 1) {
            mPresenter.setUserId(BaseApplication.getLoginEntity().getUserId());
        }

        initAdater();
        setListeners();
        mRefreshLayout.autoRefresh();
        getMyWaitingRegistSort();
    }

    private void initAdater() {
        commonAdapter = new CommonAdapter<WoDeDaiZhenItemBean>(this, R.layout.item_wode_daizhen_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, WoDeDaiZhenItemBean item, int position) {
                CustomerMenZhenBean customer = item.getCustomer();
                CustomerUtil.setCustomerInfo(holder, customer);
                TextView tvWaitDialgue = holder.getView(R.id.tv_booking_time);
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                try {
                    String waitTime = sdf2.format(sdf1.parse(item.getTriageTime()));
                    tvWaitDialgue.setVisibility(View.VISIBLE);
                    tvWaitDialgue.setText(ViewUtil.getMutilTextColor(waitTime, "#2CBCC1", "[" + (item.getWaitingTime() / 60) + "分钟" + "]", "#F35543"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                holder.setText(R.id.tv_project_name, item.getProductsName());
                holder.setText(R.id.tv_zhengdan_type, "是".equals(item.getType()) ? "初诊" : "复诊");
                TextView tvYuyue = holder.getView(R.id.tv_yuyue);
                if ("是".equals(item.getFlag())) {
                    tvYuyue.setVisibility(View.VISIBLE);
                } else {
                    tvYuyue.setVisibility(View.GONE);
                }
                holder.setText(R.id.tv_doctor_name, item.getDoctor());


            }
        };
        mListView.setAdapter(commonAdapter);
    }

    private void setListeners() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WoDeDaiZhenItemBean bean = mDatas.get(position);
                Intent intent = new Intent(WoDeDaizhenActivity.this, MenZhenDetailActivity.class);
                intent.putExtra("WoDeDaiZhenItemBean", bean);
                startActivity(intent);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
               refresLoadhData();
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore(new ResponseListener<DaiZhenResp>() {
                    @Override
                    public void onResponse(DaiZhenResp daiZhenResp) {
                        fillData(true, daiZhenResp);
                        if (daiZhenResp.getDataList() == null) {
                            Toast.makeText(getApplicationContext(), "没有加载到更多数据", Toast.LENGTH_SHORT).show();
                        }
                        mDatas.addAll(daiZhenResp.getDataList());
                        commonAdapter.notifyDataSetChanged();
                        stopOver();
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver();
                    }
                });
            }
        });
        mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
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
                    refresLoadhData();

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
                    refresLoadhData();
                }
            }
        });

    }

    public void refresLoadhData() {
        mPresenter.refresh(new ResponseListener<DaiZhenResp>() {
            @Override
            public void onResponse(DaiZhenResp daiZhenResp) {
                fillData(false, daiZhenResp);
                mDatas.clear();
                if (daiZhenResp != null&&daiZhenResp.getDataList()!=null) {
                    mDatas.addAll(daiZhenResp.getDataList());
                }

                commonAdapter.notifyDataSetChanged();
                stopOver();
            }

            @Override
            public void onError(String message, int errorCode) {
                stopOver();
            }
        });
    }

    private void stopOver() {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }


    private void fillData(boolean isLoadMore, DaiZhenResp daiZhenResp) {
        if (daiZhenResp != null && daiZhenResp.getList() != null && daiZhenResp.getList().size() > 0) {
            mDaizhenTotal = daiZhenResp.getList().get(0).getValue();
        }
        mTvWodeDaizhengNumber.setText("共" + mDaizhenTotal + "位待诊");

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


    @OnClick({R.id.ll_sort_panel, R.id.return_btn, R.id.search_edit})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_sort_panel:
                List<String> list = new ArrayList<>();
                if (inHospitalSortResp != null) {
                    if (inHospitalSortResp.getSorts() == null || inHospitalSortResp.getSorts().size() == 0) {
                        return;
                    }
                    for (SortsBean sortsBean : inHospitalSortResp.getSorts()) {
                        list.add(sortsBean.getSortName());
                    }
                }
                mIvSortImage.setImageResource(R.mipmap.icon_d_shang_la);
                new ChoiceSortPopupMenu().showPopup(this, mName, list, mLlSortPanel, new ChoiceSortPopupMenu.ChioceListener() {
                    @Override
                    public void choice(String name) {
                        mName = name;
                        if ("分诊时间".equals(mName)) {
                            mPresenter.setTriageTime("desc");
                            mPresenter.setWaitingTime("");
                        } else if ("等待时长".equals(mName)) {
                            mPresenter.setTriageTime("");
                            mPresenter.setWaitingTime("desc");
                        }
                        mIvSortImage.setImageResource(R.mipmap.icon_workbench_xiala);
                        refresLoadhData();
                    }

                    @Override
                    public void dismiss() {
                        mIvSortImage.setImageResource(R.mipmap.icon_workbench_xiala);
                    }
                });
                break;
            case R.id.return_btn:
                finish();
                break;
            case R.id.search_edit:
                break;
        }
    }
}
