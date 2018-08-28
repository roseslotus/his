package com.mylike.his.fragment.consultant;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.CustomerDetailsActivity;
import com.mylike.his.activity.consultant.DepositHospitalActivity;
import com.mylike.his.activity.consultant.StoredValueActivity;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.ReceptionEntity;
import com.mylike.his.entity.ReceptionInfoEntity;
import com.mylike.his.entity.ReceptionNotEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by zhengluping on 2018/1/23.
 * 咨询师-接诊fragment
 */
public class CReceptionFragment extends BaseFragment {

    public static final String TITLE_TAG = "TITLE_TAG";

    @Bind(R.id.reception_list)
    ListView receptionList;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    //当前页标题
    private String title;

    public static CReceptionFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_TAG, title);
        CReceptionFragment pageFragment = new CReceptionFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(TITLE_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reception, container, false);
        ButterKnife.bind(this, view);

//        setData();
        setViewValue();
        initView();

        return view;
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(5000);
            }
        });
    }


    //设置控件的值
    private void setViewValue() {
        if (title.equals("未接诊")) {//未接诊
            HashMap<String, Object> map = new HashMap<>();

            HttpClient.getHttpApi().getNotReception(HttpClient.getRequestBody(map)).enqueue(new BaseBack<List<ReceptionNotEntity>>() {
                @Override
                protected void onSuccess(final List<ReceptionNotEntity> receptionNotEntities) {
                    receptionList.setAdapter(new CommonAdapter<ReceptionNotEntity>(getActivity(), R.layout.item_reception_not_list, receptionNotEntities) {
                        @Override
                        protected void convert(ViewHolder viewHolder, ReceptionNotEntity item, int position) {
                            viewHolder.setText(R.id.client_info_text, item.getCustomName() + "   " + item.getCustomMobile());
                            viewHolder.setText(R.id.time_text, item.getRegisterDatetime());
                            viewHolder.setText(R.id.wait_text, item.getWaitTime());

                            LinearLayout ll = viewHolder.getView(R.id.reception_ll);
                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
                            if (position == 0) {
                                lp.setMargins(50, 200, 50, 0);
                                viewHolder.setText(R.id.wait_text, "40");
                                viewHolder.setTextColor(R.id.wait_text, Color.RED);
                            } else {
                                lp.setMargins(50, 30, 50, 0);
                            }
                            ll.setLayoutParams(lp);
                        }
                    });
                }

                @Override
                protected void onFailed(String code, String msg) {

                }
            });


//            receptionList.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_reception_not_list, DataUtil.getData(5)) {
//                @Override
//                protected void convert(final ViewHolder viewHolder, String item, int position) {
//                    LinearLayout ll = viewHolder.getView(R.id.reception_ll);
//                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
//                    if (position == 0) {
//                        lp.setMargins(50, 200, 50, 0);
//                        viewHolder.setText(R.id.wait_text, "40");
//                        viewHolder.setTextColor(R.id.wait_text, Color.RED);
//                    } else {
//                        lp.setMargins(50, 30, 50, 0);
//                    }
//                    ll.setLayoutParams(lp);
//
//                    //意向列表
////                    ListView listView = viewHolder.getView(R.id.intention_list);
////                    listView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_intention_list, data3) {
////                        @Override
////                        protected void convert(ViewHolder viewHolder, String item, int position) {
////                            viewHolder.setVisible(R.id.consulting_hint, false);
////                        }
////                    });
////
////                    //意向列表高度
////                    measureListViewHeight(listView);
//                }
//            });

            receptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(CustomerDetailsActivity.class);//跳转客户详情
                }
            });
        } else {//已接诊

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);
            HashMap<String, Object> map = new HashMap<>();
            map.put("pageNumber", str);
            map.put("pageSize", str);
            map.put("DateLvel", "1");
            map.put("EndCreatetime", "10");

            HttpClient.getHttpApi().getHasReception(HttpClient.getRequestBody(map)).enqueue(new BaseBack<ReceptionEntity>() {

                @Override
                protected void onSuccess(ReceptionEntity receptionEntity) {


                    receptionList.setAdapter(new CommonAdapter<ReceptionInfoEntity>(getActivity(), R.layout.item_reception_has_list, receptionEntity.getList()) {
                        @Override
                        protected void convert(ViewHolder viewHolder, ReceptionInfoEntity item, int position) {
                            String phone = item.getCFHANDSET().substring(0, 3) + "****" + item.getCFHANDSET().substring(7, 11);
                            viewHolder.setText(R.id.user_info_text, item.getCFNAME() + "   " + phone);
                            if (item.getREGISTERTYPE().equals("1")) {
                                viewHolder.setText(R.id.state_text, "重咨");
                            } else if (item.getREGISTERTYPE().equals("2")) {
                                viewHolder.setText(R.id.state_text, "跨科");
                            } else {
                                viewHolder.setText(R.id.state_text, "");
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
                            LinearLayout ll = viewHolder.getView(R.id.reception_ll);
                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
                            if (position == 0) {
                                lp.setMargins(50, 200, 50, 0);
                            } else {
                                lp.setMargins(50, 30, 50, 0);
                            }
                            ll.setLayoutParams(lp);

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
                    });

                    //---------底部-------------

                    View foot = View.inflate(getActivity(), R.layout.common_item_text, null);
                    final TextView textView = foot.findViewById(R.id.text);
                    textView.setText("您已经看见我的底线了");
                    textView.setPadding(10, 30, 10, 30);
                    receptionList.addFooterView(foot);

                }

                @Override
                protected void onFailed(String code, String msg) {

                }
            });


           /* SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);
            HashMap<String, Object> map = new HashMap<>();
            map.put("startTime", str);
            map.put("endTime", str);
            map.put("pageNumber", "1");
            map.put("pageSize", "10");

            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("Content-type", "application/json;charset=UTF-8");
            paramsMap.put("token", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.TOKEN));

            HttpClient.getHttpApi().getHasReception(paramsMap, HttpClient.getRequestBody(map)).enqueue(new BaseBack<ReceptionEntity>() {

                @Override
                protected void onSuccess(ReceptionEntity receptionEntity) {
                    receptionList.setAdapter(new CommonAdapter<ReceptionInfoEntity>(getActivity(), R.layout.item_reception_has_list, receptionEntity.getList()) {
                        @Override
                        protected void convert(ViewHolder viewHolder, ReceptionInfoEntity item, int position) {
                            String phone = item.getCFHANDSET().substring(0, 3) + "****" + item.getCFHANDSET().substring(7, 11);
                            viewHolder.setText(R.id.user_info_text, item.getCFNAME() + "   " + phone);
                            if (item.getREGISTERTYPE().equals("1")) {
                                viewHolder.setText(R.id.state_text, "重咨");
                            } else if (item.getREGISTERTYPE().equals("2")) {
                                viewHolder.setText(R.id.state_text, "跨科");
                            } else {
                                viewHolder.setText(R.id.state_text, "");
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
                            LinearLayout ll = viewHolder.getView(R.id.reception_ll);
                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
                            if (position == 0) {
                                lp.setMargins(50, 200, 50, 0);
                            } else {
                                lp.setMargins(50, 30, 50, 0);
                            }
                            ll.setLayoutParams(lp);

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
                    });

                }

                @Override
                protected void onFailed(String code, String msg) {

                }
            });*/

//            receptionList.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_reception_has_list, DataUtil.getData(10)) {
//                @Override
//                protected void convert(final ViewHolder viewHolder, String item, int position) {
//                    LinearLayout ll = viewHolder.getView(R.id.reception_ll);
//                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ll.getLayoutParams();
//                    if (position == 0) {
//                        lp.setMargins(50, 200, 50, 0);
//                    } else {
//                        lp.setMargins(50, 30, 50, 0);
//                    }
//                    ll.setLayoutParams(lp);
//
//                    //储值
//                    viewHolder.setOnClickListener(R.id.stored_value_btn, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(StoredValueActivity.class);
//                        }
//                    });
//
//                    //住院押金
//                    viewHolder.setOnClickListener(R.id.hospital_deposit_btn, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(DepositHospitalActivity.class);
//                        }
//                    });
//
////                    if (position == 1) {//新客
////                        viewHolder.setImageDrawable(R.id.head_portrait_img, getActivity().getResources().getDrawable(R.mipmap.boy_icon));
////                        viewHolder.setText(R.id.state_text, "交易完成");
//
////                    }
//
////                    //个人信息
////                    viewHolder.setOnClickListener(R.id.personal_details_btn, new View.OnClickListener() {
////                        @Override
////                        public void onClick(View v) {
////                            startActivity(CustomerDetailsActivity.class);
////                        }
////                    });
//
////                    //意向列表
////                    ListView listView = viewHolder.getView(R.id.intention_list);
////                    listView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_intention_list, data2) {
////                        @Override
////                        protected void convert(ViewHolder viewHolder, String item, int position) {
////                            //重咨
////                            if (position == 1) {
////                                viewHolder.setVisible(R.id.consulting_hint, true);
////                            }
////                        }
////                    });
////
////
////                    //意向列表高度
////                    measureListViewHeight(listView);
////                    //解决点击内嵌listview无反应
////                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                        @Override
////                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//////                            Intent intent = new Intent(getActivity(), OrderActivity.class);
//////                            startActivity(intent);
////                            startActivity(OrderActivity.class);
////                        }
////                    });
//                }
////            });
////            receptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                @Override
////                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//////                    Intent intent = new Intent(getActivity(), OrderActivity.class);
//////                    startActivity(intent);
////                    startActivity(OrderActivity.class);
////
////                }
//            });
        }
    }

//
//    public void measureListViewHeight(ListView listView) {
//        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
//        int screedWidth = wm.getDefaultDisplay().getWidth();
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 0;
//        int listViewWidth = screedWidth - dip2px(getActivity(), 10);//listView在布局时的宽度
//        int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(widthSpec, 0);
//
//            int itemHeight = listItem.getMeasuredHeight();
//            totalHeight += itemHeight;
//        }
//        // 减掉底部分割线的高度
//        int historyHeight = totalHeight
//                + (listView.getDividerHeight() * listAdapter.getCount() - 1);
////        Log.d("Javine","listViewHeight = "+historyHeight);
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = historyHeight;
//        listView.setLayoutParams(params);
//    }
//
//
//    //设置列表高度
//    public void setListViewHeightBasedOnChildren(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//        View listItem = listAdapter.getView(0, null, listView);
//        listItem.measure(0, 0);
//        int listItemHeight = listItem.getMeasuredHeight();
//        int totalHeight = listItemHeight * listAdapter.getCount();
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (1 * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }

    //设置数据
//                private void setData() {
//                    for (int i = 0; i < 3; i++) {
//                        data.add("aaa" + i);
//                    }
//                    for (int i = 0; i < 3; i++) {
//                        data2.add("aaa" + i);
//                    }
//                    for (int i = 0; i < 1; i++) {
//                        data3.add("aaa" + i);
//                    }
//                }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
