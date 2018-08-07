package com.mylike.his.fragment.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.consultant.DepositHospitalActivity;
import com.mylike.his.activity.consultant.OrderActivity;
import com.mylike.his.activity.consultant.ProductActivity;
import com.mylike.his.activity.consultant.StoredValueActivity;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.ReceptionEntity;
import com.mylike.his.entity.ReceptionInfoEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.SPUtils;
import com.mylike.his.view.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
 * Created by zhengluping on 2018/1/2.
 * 销售
 */
public class SalesFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.search_edit)
    ClearEditText searchEdit;
    @Bind(R.id.reception_list)
    ListView receptionList;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.search_btn)
    Button searchBtn;

    private View view;

    private int pageSize = 10;//每页数据
    private int pageNumber = 1;//页码
    private String DateLvel = "";//底部标识
    private String EndCreatetime;//最后时间（后台需要）

    private List<ReceptionInfoEntity> listAll = new ArrayList<>();
    private CommonAdapter commonAdapter;
    private TextView textView;

    public static SalesFragment newInstance() {
        Bundle args = new Bundle();
        SalesFragment fragment = new SalesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sales, null);
        ButterKnife.bind(this, view);

        initView();
        pageNumber = 1;
        DateLvel = "";

        listAll.clear();
        initData();

        return view;
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableAutoLoadMore(false);
        commonAdapter = new CommonAdapter<ReceptionInfoEntity>(getActivity(), R.layout.item_reception_has_list, listAll) {
            @Override
            protected void convert(ViewHolder viewHolder, final ReceptionInfoEntity item, int position) {
                viewHolder.setText(R.id.user_info_text, item.getCFNAME() + "   " + item.getCFHANDSET());
                if (item.getREGISTERTYPE().equals("1")) {
                    viewHolder.setText(R.id.state_text, "重咨");
                } else if (item.getREGISTERTYPE().equals("2")) {
                    viewHolder.setText(R.id.state_text, "跨科");
                } else {
                    viewHolder.setText(R.id.state_text, "");
                }

                if ("1".equals(item.getCanBillingFlag())) {
                    viewHolder.setVisible(R.id.consumption_btn, true);
                } else {
                    viewHolder.setVisible(R.id.consumption_btn, false);
                }

                viewHolder.setText(R.id.time_text, item.getCREATE_DATE());

                if (TextUtils.isEmpty(item.getDOCTOR_DEPARTMENT())) {
                    viewHolder.setVisible(R.id.department_text, false);
                } else {
                    viewHolder.setVisible(R.id.department_text, true);
                    if (TextUtils.isEmpty(item.getYSNAME())) {
                        viewHolder.setText(R.id.department_text, item.getDOCTOR_DEPARTMENT());
                    } else {
                        viewHolder.setText(R.id.department_text, item.getDOCTOR_DEPARTMENT() + " ——" + item.getYSNAME());
                    }
                }

                //消费
                viewHolder.setOnClickListener(R.id.consumption_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSaveData(item.getFID());

                    }
                });

                //储值
                viewHolder.setOnClickListener(R.id.stored_value_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(StoredValueActivity.class);
                    }
                });

                //住院押金
                viewHolder.setOnClickListener(R.id.hospital_deposit_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(DepositHospitalActivity.class);
                    }
                });
            }
        };
        //底部
        View view = View.inflate(getActivity(), R.layout.common_item_text, null);
        textView = view.findViewById(R.id.text);
        textView.setPadding(10, 30, 10, 30);
        receptionList.addFooterView(view);
        receptionList.setAdapter(commonAdapter);

    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        map.put("DateLvel", DateLvel);
        map.put("EndCreatetime", EndCreatetime);
        map.put("custNameOrPhone", searchEdit.getText().toString());

        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("Content-type", "application/json;charset=UTF-8");
        paramsMap.put("token", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN));

        HttpClient.getHttpApi().getHasReception(paramsMap, HttpClient.getRequestBody(map)).enqueue(new BaseBack<ReceptionEntity>() {
            @Override
            protected void onSuccess(ReceptionEntity receptionEntity) {
                EndCreatetime = receptionEntity.getEndCreatetime();

                if (TextUtils.isEmpty(DateLvel))//后台需要空数据，如果空数据赋值为1；
                    DateLvel = "1";
                //判断是否显示页脚
                if (receptionEntity.getNextLevel().equals(DateLvel)) {
                    textView.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(receptionEntity.getNextLevelText());
                }
                DateLvel = receptionEntity.getNextLevel();


                if (DateLvel.equals("0")) {
                    refreshLayout.setNoMoreData(true);
                }

                listAll.addAll(receptionEntity.getList());
                commonAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });

    }

    private void getSaveData(final String Triageid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("Triageid", Triageid);

        HttpClient.getHttpApi().getSave(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                SPUtils.setCache(SPUtils.FILE_RECEPTION, SPUtils.RECEPTION_ID, Triageid);
                if ("0".equals(stringStringMap.get("isCacheOrder"))) {
                    startActivity(ProductActivity.class);
                } else {
                    startActivity(OrderActivity.class, "chargeTag", "1");
                }
            }

            @Override
            protected void onFailed(String code, String msg) {

            }
        });


    }

    @OnClick({R.id.search_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                pageNumber = 1;
                listAll.clear();
                initData();
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNumber = 1;
        DateLvel = "";
        listAll.clear();
        refreshLayout.setNoMoreData(false);
        initData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNumber = pageNumber + 1;
        if (textView.getVisibility() == View.VISIBLE) {
            pageNumber = 1;
        }
        initData();
    }


//    private void initView() {
//        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setOnLoadMoreListener(this);
////        refreshLayout.setEnableAutoLoadMore(false);
//        commonAdapter = new CommonAdapter<ReceptionInfoEntity>(getActivity(), R.layout.item_reception_has_list, listAll) {
//            @Override
//            protected void convert(ViewHolder viewHolder, final ReceptionInfoEntity item, int position) {
//                viewHolder.setText(R.id.user_info_text, item.getCFNAME() + "   " + item.getCFHANDSET());
//                if (item.getREGISTERTYPE().equals("1")) {
//                    viewHolder.setText(R.id.state_text, "重咨");
//                } else if (item.getREGISTERTYPE().equals("2")) {
//                    viewHolder.setText(R.id.state_text, "跨科");
//                } else {
//                    viewHolder.setText(R.id.state_text, "");
//                }
//
//                viewHolder.setText(R.id.time_text, item.getCREATE_DATE());
//
//                if (TextUtils.isEmpty(item.getDOCTOR_DEPARTMENT())) {
//                    viewHolder.setVisible(R.id.department_text, false);
//                } else {
//                    viewHolder.setVisible(R.id.department_text, true);
//                    if (TextUtils.isEmpty(item.getYSNAME())) {
//                        viewHolder.setText(R.id.department_text, item.getDOCTOR_DEPARTMENT());
//                    } else {
//                        viewHolder.setText(R.id.department_text, item.getDOCTOR_DEPARTMENT() + " ——" + item.getYSNAME());
//                    }
//                }
//
//                //消费
//                viewHolder.setOnClickListener(R.id.consumption_btn, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SPUtils.setCache(SPUtils.FILE_RECEPTION, SPUtils.RECEPTION_ID, item.getFID());
//                        startActivity(ProductActivity.class);
//                    }
//                });
//
//                //储值
//                viewHolder.setOnClickListener(R.id.stored_value_btn, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(StoredValueActivity.class);
//                    }
//                });
//
//                //住院押金
//                viewHolder.setOnClickListener(R.id.hospital_deposit_btn, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(DepositHospitalActivity.class);
//                    }
//                });
//            }
//        };
//        //底部
//        View view = View.inflate(getActivity(), R.layout.common_item_text, null);
//        textView = view.findViewById(R.id.text);
//        textView.setPadding(10, 30, 10, 30);
//        receptionList.addFooterView(view);
//        receptionList.setAdapter(commonAdapter);
//
//    }
//
//    private void initData() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("pageNumber", pageNumber);
//        map.put("pageSize", pageSize);
//        map.put("DateLvel", DateLvel);
//        map.put("EndCreatetime", EndCreatetime);
//        map.put("EndCreatetime", searchEdit.getText().toString());
//
//        HashMap<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("Content-type", "application/json;charset=UTF-8");
//        paramsMap.put("token", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN));
//
//        HttpClient.getHttpApi().getHasReception(paramsMap, HttpClient.getRequestBody(map)).enqueue(new BaseBack<ReceptionEntity>() {
//            @Override
//            protected void onSuccess(ReceptionEntity receptionEntity) {
//                EndCreatetime = receptionEntity.getEndCreatetime();
//
//                if (TextUtils.isEmpty(DateLvel))//后台需要空数据，如果空数据赋值为1；
//                    DateLvel = "1";
//                //判断是否显示页脚
//                if (receptionEntity.getNextLevel().equals(DateLvel)) {
//                    textView.setVisibility(View.GONE);
//                } else {
//                    textView.setVisibility(View.VISIBLE);
//                    textView.setText(receptionEntity.getNextLevelText());
//                }
//                DateLvel = receptionEntity.getNextLevel();
//
//
//                if (DateLvel.equals("0")) {
//                    refreshLayout.setNoMoreData(true);
//                }
//
//                listAll.addAll(receptionEntity.getList());
//                commonAdapter.notifyDataSetChanged();
//                refreshLayout.finishRefresh();
//                refreshLayout.finishLoadMore();
//            }
//
//            @Override
//            protected void onFailed(String code, String msg) {
//                refreshLayout.finishRefresh(false);
//                refreshLayout.finishLoadMore(false);
//            }
//        });
//
//    }
//
//    @Override
//    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//        pageNumber = 1;
//        DateLvel = "";
//        listAll.clear();
//        refreshLayout.setNoMoreData(false);
//        initData();
//    }
//
//    @Override
//    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//        pageNumber = pageNumber + 1;
//        if (textView.getVisibility() == View.VISIBLE) {
//            pageNumber = 1;
//        }
//        initData();
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
