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
import com.mylike.his.doctor.popup.ChoiceSortPopupMenu;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.mylike.his.entity.DropdownsBean;
import com.mylike.his.entity.InHospitalSortResp;
import com.mylike.his.entity.MyInHostpitalListBean;
import com.mylike.his.entity.MyInHostpitalListResp;
import com.mylike.his.entity.SelectsBean;
import com.mylike.his.entity.SortsBean;
import com.mylike.his.http.HttpClient;
import com.mylike.his.presener.MyInHostpitalPresenter;
import com.mylike.his.utils.Constacts;
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
 * Created by thl on 2018/12/31.
 */

public class WoDeChuyuanActivity extends BaseActivity {

    @BindView(R.id.customer_list)
    ListView mCustomerList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_sort_image)
    ImageView mIvSortImage;
    @BindView(R.id.ll_sort_panel)
    LinearLayout mLlSortPanel;
    @BindView(R.id.ll_shuaixuan_panel)
    LinearLayout mLlShuaixuanPanel;
    @BindView(R.id.search_edit)
    ClearEditText mSearchEdit;

    private CommonAdapter<MyInHostpitalListBean> commonAdapter;
    private List<MyInHostpitalListBean> mDatas = new ArrayList<>();
    private String mName;
    InHospitalSortResp inHospitalSortResp;

    MyInHostpitalPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_chuyuan);
        ButterKnife.bind(this);
        mPresenter = new MyInHostpitalPresenter(this);
        mPresenter.setStatus(2);
        commonAdapter = new CommonAdapter<MyInHostpitalListBean>(this, R.layout.item_wode_chuyuan_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, MyInHostpitalListBean item, int position) {
//                holder.setText(R.id.tv_illness_area,item.getArea());
                holder.setText(R.id.tv_berth_no,item.getArea());
                holder.setText(R.id.tv_nurse_status,item.getLevel());
                holder.setText(R.id.tv_product_name,item.getProductsName());
                holder.setText(R.id.tv_doctor_name,item.getDoctor());
                holder.setText(R.id.tv_resident_doctor,item.getResidentDoc());
                holder.setText(R.id.tv_duty_doctor,item.getNurse());
                holder.setText(R.id.tv_inhospital_date,item.getEnterDate());

                CustomerUtil.setCustomerInfo(holder,item.getCustomer());
            }
        };
        mCustomerList.setAdapter(commonAdapter);
        mCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(WoDeChuyuanActivity.this,ZhuyuanDetailActivity.class);
                intent.putExtra(Constacts.CONTENT_DATA,mDatas.get(i));
                startActivity(intent);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh(new ResponseListener<MyInHostpitalListResp>() {
                    @Override
                    public void onResponse(MyInHostpitalListResp resp) {
                        stopOver(mRefreshLayout);
                        mDatas.clear();
                        mDatas.addAll(resp.getDataList());
                        bindData(resp.getList());
                        commonAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String message, int errorCode) {
                        stopOver(mRefreshLayout);
                    }
                });
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore(new ResponseListener<MyInHostpitalListResp>() {
                    @Override
                    public void onResponse(MyInHostpitalListResp resp) {
                        mDatas.addAll(resp.getDataList());
                        commonAdapter.notifyDataSetChanged();
                        bindData(resp.getList());
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
        getInHospitalSort();
    }

    public void refresLoadhData() {
        mPresenter.refresh(new ResponseListener<MyInHostpitalListResp>() {
            @Override
            public void onResponse(MyInHostpitalListResp resp) {
                stopOver(mRefreshLayout);
                mDatas.clear();
                if (resp != null) {
                    mDatas.addAll(resp.getDataList());
                    bindData(resp.getList());
                }

                commonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message, int errorCode) {
                stopOver(mRefreshLayout);
            }
        });
    }


    private void bindData(List<MyInHostpitalListResp.ListBean> list) {

    }
    @OnClick({R.id.ll_sort_panel, R.id.ll_shuaixuan_panel, R.id.return_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.return_btn:
                finish();
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
                        for (SortsBean sortsBean : inHospitalSortResp.getSorts()) {
                            if ("床位排序".equals(mName)) {
                                mPresenter.setBednoId(sortsBean.getSortValue());
                                mPresenter.setInhosptimeId("");
                            } else if ("入院日期排序".equals(mName)) {
                                mPresenter.setBednoId("");
                                mPresenter.setInhosptimeId(sortsBean.getSortValue());
                            }
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
            case R.id.ll_shuaixuan_panel:
                if (inHospitalSortResp != null) {
                    new ChoiceZhuyuanShuaixuanPopupMenu().setDatas(inHospitalSortResp.getDropdowns()).show(getFragmentManager(), "ChoiceZhuyuanShuaixuanPopupMenu");
                }


                break;
            case R.id.search_edit:
                break;
        }
    }

    public void getInHospitalSort() {
//        CommonUtil.showLoadProgress(this);
        HttpClient.getHttpApi().getInHospitalSort(BaseApplication.getLoginEntity().getToken()).enqueue(new Callback<InHospitalSortResp>() {
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
