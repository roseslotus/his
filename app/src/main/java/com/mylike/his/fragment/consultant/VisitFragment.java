package com.mylike.his.fragment.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.activity.consultant.VisitDetailsActivity;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.BasePageEntity;
import com.mylike.his.entity.VisitCDEntity;
import com.mylike.his.entity.VisitEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by zhengluping on 2018/1/23.
 * 咨询师-接诊fragment
 */
public class VisitFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    //当前页标题
    public static final String TITLE_TAG = "TITLE_TAG";
    View view;
    Unbinder unbinder;

    @BindView(R.id.visit_list)
    ListView visitList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private String title;
    private int sumPage = 1;//总也数
    private int pageSize = 10;//每页数据
    private int pageNumber = 1;//页码
    private int visitValue = 0;//回访状态 0未执行 1已执行
    private List<VisitEntity> listAll = new ArrayList<>();
    private CommonAdapter commonAdapter;

    private VisitCDEntity visitCDEntity = new VisitCDEntity();

    public static VisitFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE_TAG, title);
        VisitFragment pageFragment = new VisitFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_visit, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(visitCDEntity);
        return view;
    }

    public void initView(VisitCDEntity visitCDEntity) {
        this.visitCDEntity = visitCDEntity;
        if (refreshLayout != null && visitList != null) {
            title = getArguments().getString(TITLE_TAG);
            if (title.equals("待回访")) {//待回访
                visitValue = 0;
                commonAdapter = new CommonAdapter<VisitEntity>(getActivity(), R.layout.item_visit_not_list, listAll) {
                    @Override
                    protected void convert(ViewHolder viewHolder, VisitEntity item, int position) {
                        viewHolder.setText(R.id.name_text, item.getCustomerName());
                        viewHolder.setText(R.id.visit_name_text, item.getTaskKeyWord());
                        viewHolder.setText(R.id.visit_type_text, item.getObType());
                        viewHolder.setText(R.id.time_text, TextUtils.isEmpty(item.getPlanTime()) ? "暂无" : item.getPlanTime().substring(0, 11));
                    }
                };
            } else {//已回访
                visitValue = 1;
                commonAdapter = new CommonAdapter<VisitEntity>(getActivity(), R.layout.item_visit_has_list, listAll) {
                    @Override
                    protected void convert(ViewHolder viewHolder, VisitEntity item, int position) {
                        viewHolder.setText(R.id.name_text, item.getCustomerName());
                        viewHolder.setText(R.id.visit_name_text, item.getTaskKeyWord());
                        viewHolder.setText(R.id.visit_type_text, item.getObType());
                        viewHolder.setText(R.id.time_not_text, TextUtils.isEmpty(item.getPlanTime()) ? "暂无" : item.getPlanTime().substring(0, 11));
                        viewHolder.setText(R.id.time_has_text, TextUtils.isEmpty(item.getVisitTime()) ? "暂无" : item.getVisitTime().substring(0, 11));
                    }
                };
            }
            initData();
            visitList.setAdapter(commonAdapter);
            visitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("visit_tag", visitValue);
                    bundle.putString("bpd_id", listAll.get(position).getBpdId());
                    startActivity(VisitDetailsActivity.class, bundle);
                }
            });

            refreshLayout.setOnRefreshListener(this);
            refreshLayout.setOnLoadMoreListener(this);
        }
    }

    public void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNumber", pageNumber + "");
        map.put("pageSize", pageSize + "");
        map.put("visitStatus", visitValue + "");
        map.put("planTimeStart", visitCDEntity.getPlanTimeStart());
        map.put("planTimeEnd", visitCDEntity.getPlanTimeEnd());
        map.put("visitTimeStart", visitCDEntity.getVisitTimeStart());
        map.put("visitTimeEnd", visitCDEntity.getVisitTimeEnd());
        map.put("visitType", visitCDEntity.getVisitType());

        HttpClient.getHttpApi().getVisitList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<BasePageEntity<VisitEntity>>() {
            @Override
            protected void onSuccess(BasePageEntity<VisitEntity> visitEntityBasePageEntity) {
                try {
                    sumPage = visitEntityBasePageEntity.getPages();
                    if (sumPage == pageNumber) {
                        refreshLayout.setNoMoreData(true);
                    }
                    if (pageNumber == 1)
                        listAll.clear();
                    listAll.addAll(visitEntityBasePageEntity.getList());
                    commonAdapter.notifyDataSetChanged();
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                } catch (Exception e) {
                }
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);
                refreshLayout.finishLoadMore(false);
            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNumber = 1;
        initData();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (sumPage == 1) {
            refreshLayout.finishLoadMore();
        } else {
            pageNumber = pageNumber + 1;
            initData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updataData(VisitCDEntity visitCDEntity) {
        this.visitCDEntity = visitCDEntity;
        refreshLayout.setNoMoreData(false);
        pageNumber = 1;
        initData();
    }
}


