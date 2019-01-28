package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mylike.his.R;
import com.mylike.his.activity.presener.WoDeDaiZhenPresenter;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.ResponseListener;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.mylike.his.entity.DaiZhenResp;
import com.mylike.his.entity.WoDeDaiZhenItemBean;
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
    private WoDeDaiZhenPresenter daiZhenPresenter;


    private int mDaizhenTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_daizhen);
        ButterKnife.bind(this);
        daiZhenPresenter = new WoDeDaiZhenPresenter(this);
        initAdater();
        setListeners();
        mRefreshLayout.autoRefresh();
    }

    private void initAdater() {
        commonAdapter = new CommonAdapter<WoDeDaiZhenItemBean>(this, R.layout.item_wode_daizhen_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, WoDeDaiZhenItemBean item, int position) {
                CustomerMenZhenBean customer= item.getCustomer();
                if (customer!=null){
                    holder.setImageResource(R.id.iv_customer_head_image,daiZhenPresenter.isBoy(customer.getSex())?R.mipmap.icon_customer_boy:R.mipmap.icon_customer_girl);
                    holder.setText(R.id.tv_customer_name,customer.getCusName());
                    holder.setImageResource(R.id.tv_customer_sex,daiZhenPresenter.isBoy(customer.getSex())?R.mipmap.icon_d_boy:R.mipmap.icon_d_girl);
                    holder.setImageResource(R.id.tv_customer_vip_level,daiZhenPresenter.getVipLevelIcon(customer.getLabels().get(2).getValue()));
                    ImageView vipFlag =holder.getView(R.id.tv_customer_vip_flag);
                    if (customer.getLabels().get(1).getValue()!=null){
                        vipFlag.setVisibility(View.VISIBLE);
                    }else {
                        vipFlag.setVisibility(View.GONE);
                    }
                    holder.setText(R.id.tv_customer_year_and_birth,customer.getBrithday());
                    holder.setText(R.id.tv_customer_mobile_no,customer.getTel());
                    holder.setText(R.id.tv_wait_time,(item.getWaitingTime()/60)+"分钟");
                    SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    SimpleDateFormat sdf2= new SimpleDateFormat("HH:mm");
                    try {
                        holder.setText(R.id.tv_jiuzheng_time,sdf2.format(sdf1.parse(item.getTriageTime())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
//                    holder.setText(R.id.tv_customer_zuoji_no,customer.getBrithday());
                    holder.setText(R.id.tv_project_name,item.getProductsName());
                    holder.setText(R.id.tv_zhengdan_type,"是".equals(item.getType())?"初诊":"复诊");
                    TextView tvYuyue = holder.getView(R.id.tv_yuyue);
                    if ("是".equals(item.getFlag())){
                        tvYuyue.setVisibility(View.VISIBLE);
                    }else {
                        tvYuyue.setVisibility(View.GONE);
                    }
                    holder.setText(R.id.tv_doctor_name,item.getDoctor());
                }


            }
        };
        mListView.setAdapter(commonAdapter);
    }

    private void setListeners() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(MenZhenDetailActivity.class);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                daiZhenPresenter.refresh(new ResponseListener<DaiZhenResp>() {
                    @Override
                    public void onResponse(DaiZhenResp daiZhenResp) {
                        fillData(false,daiZhenResp);
                        mDatas.clear();
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

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                daiZhenPresenter.loadMore(new ResponseListener<DaiZhenResp>() {
                    @Override
                    public void onResponse(DaiZhenResp daiZhenResp) {
                        fillData(true,daiZhenResp);
                        if (daiZhenResp.getDataList()==null){
                            Toast.makeText(getApplicationContext(),"没有加载到更多数据",Toast.LENGTH_SHORT).show();
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

    }

    private void stopOver(){
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }


    private void fillData(boolean isLoadMore, DaiZhenResp daiZhenResp) {
        if (daiZhenResp!=null&&daiZhenResp.getList()!=null&&daiZhenResp.getList().size()>0){
            mDaizhenTotal = daiZhenResp.getList().get(0).getValue();
        }
        mTvWodeDaizhengNumber.setText("共"+mDaizhenTotal+"位待诊");

    }


}
