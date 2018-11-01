package com.mylike.his.fragment.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.activity.LoginActivity;
import com.mylike.his.activity.consultant.ChargeDetailsActivity;
import com.mylike.his.activity.consultant.ChargeShowActivity;
import com.mylike.his.activity.consultant.ClientActivity;
import com.mylike.his.activity.consultant.DoctorActivity;
import com.mylike.his.activity.consultant.HospitalAppointmentActivity;
import com.mylike.his.activity.consultant.MessageActivity;
import com.mylike.his.activity.consultant.NewCReceptionActivity;
import com.mylike.his.activity.consultant.PaymentActivity;
import com.mylike.his.activity.consultant.SearchActivity;
import com.mylike.his.activity.consultant.StoredValueActivity;
import com.mylike.his.activity.consultant.SurgeryActivity;
import com.mylike.his.activity.consultant.TestActivity;
import com.mylike.his.activity.consultant.VisitActivity;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.entity.MessageEntity;
import com.mylike.his.entity.StatisticsEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.utils.DialogUtil;
import com.mylike.his.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengluping on 2018/1/2.
 * 首页
 */
public class CHomeFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener {
    @Bind(R.id.message_list)
    ListView messageList;
    @Bind(R.id.control_img)
    ImageView controlImg;
    @Bind(R.id.message_img)
    RelativeLayout messageImg;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.message_not_sum)
    TextView messageNotSum;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private TextView hospitalSum;
    private TextView receptionSum;
    private TextView orderSum;
    private TextView visitAlreadySum;
    private TextView visitWaitSum;

    private CommonAdapter commonAdapter;
    private List<MessageEntity> listAll = new ArrayList<>();

    public static CHomeFragment newInstance() {
        Bundle args = new Bundle();
        CHomeFragment fragment = new CHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_home, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        //刷新头设置
        ClassicsHeader classicsHeader = new ClassicsHeader(getActivity());
        classicsHeader.setEnableLastTime(false);
        classicsHeader.setPrimaryColor(getResources().getColor(R.color.green_50));
        classicsHeader.setAccentColor(getResources().getColor(R.color.green_48));
        classicsHeader.setDrawableMarginRight(10);
        classicsHeader.setSpinnerStyle(SpinnerStyle.Scale);
        refreshLayout.setRefreshHeader(classicsHeader);
        refreshLayout.setOnRefreshListener(this);//刷新
        refreshLayout.setEnableLoadMore(false);//弃用加载功能


        //标题
        titleName.setText(SPUtils.getCache(SPUtils.FILE_USER, SPUtils.HOSPITAL_NAME));

        //----------------------------------头部-------------------------------------------
        View head = View.inflate(getActivity(), R.layout.item_head_c_home, null);
        //用户姓名
        TextView userName = head.findViewById(R.id.user_name);
        userName.setText("你好，" + SPUtils.getCache(SPUtils.FILE_USER, SPUtils.USER_NAME));
        //功能模块
        TextView clinicalReceptionBtn = head.findViewById(R.id.clinical_reception_btn);//接诊
        TextView clientBtn = head.findViewById(R.id.client_btn);//客户
        TextView hospitalAppointmentBtn = head.findViewById(R.id.hospital_appointment_btn);//到院预约
        TextView payDemandNoteBtn = head.findViewById(R.id.pay_demand_note_btn);//收费单
        TextView doctorReferBtn = head.findViewById(R.id.doctor_refer_btn);//医生查询
        TextView surgeryBtn = head.findViewById(R.id.surgery_btn);//手术查询
        TextView returnVisitBtn = head.findViewById(R.id.return_visit_btn);//回访
        TextView repertoryBtn = head.findViewById(R.id.repertory_btn);//库存查询

        //统计模块
        hospitalSum = head.findViewById(R.id.hospital_sum);//预约到院人数
        receptionSum = head.findViewById(R.id.reception_sum);//接诊总数
        orderSum = head.findViewById(R.id.order_sum);//开单总数
        visitAlreadySum = head.findViewById(R.id.visit_already_sum);//回访总数
        visitWaitSum = head.findViewById(R.id.visit_wait_sum);//回访总数

        LinearLayout hospitalSumBtn = head.findViewById(R.id.hospital_sum_btn);//预约到院总数
        LinearLayout receptionSumBtn = head.findViewById(R.id.reception_sum_btn);//接诊总数
        LinearLayout orderSumBtn = head.findViewById(R.id.order_sum_btn);//开单总数
        LinearLayout visitSumBtn = head.findViewById(R.id.visit_sum_btn);//回访总数

        //消息按钮
        LinearLayout messageLl = head.findViewById(R.id.message_ll);//消息列表（更多）
        //点击事件
        clinicalReceptionBtn.setOnClickListener(this);
        clientBtn.setOnClickListener(this);
        hospitalAppointmentBtn.setOnClickListener(this);
        messageLl.setOnClickListener(this);
        payDemandNoteBtn.setOnClickListener(this);
        doctorReferBtn.setOnClickListener(this);
        surgeryBtn.setOnClickListener(this);
        returnVisitBtn.setOnClickListener(this);
        repertoryBtn.setOnClickListener(this);
        hospitalSumBtn.setOnClickListener(this);
        receptionSumBtn.setOnClickListener(this);
        orderSumBtn.setOnClickListener(this);
        visitSumBtn.setOnClickListener(this);

        messageList.addHeaderView(head);


        //----------------------------------底部-------------------------------------------
        View foot = View.inflate(getActivity(), R.layout.common_item_text, null);
        final TextView textView = foot.findViewById(R.id.text);
        textView.setText("您已经看见我的底线了");
        textView.setPadding(10, 30, 10, 30);
        messageList.addFooterView(foot);

        //----------------------------------消息列表----------------------------------------
        commonAdapter = new CommonAdapter<MessageEntity>(getActivity(), R.layout.item_home_message_list, listAll) {
            @Override
            protected void convert(ViewHolder viewHolder, MessageEntity item, int position) {
                //背景（消息列表左边的颜色变化）
                switch (item.getMsgType()) {
                    case "1"://待接诊
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_green_left_10);
                        break;
                    case "2"://已结账
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_blue_left_10);
                        break;
                    case "3"://已驳回
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_yellow_left_10);
                        break;
                    case "4"://OA已提交
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_cyan_left_10);
                        break;
                    case "5"://OA已通过
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_blue2_left_10);
                        break;
                    case "6":// OA已终止
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_blue3_left_10);
                        break;
                    /*case "7"://待扫码支付(都变更为待支付)
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_orange_left_10);
                        break;*/
                    case "8"://待支付
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_orange2_left_10);
                        break;
                }

                viewHolder.setText(R.id.name_text, item.getMsgTypeName());//类型名称
                viewHolder.setText(R.id.content_text, item.getMsgContent());//提示内容
                viewHolder.setText(R.id.time_text, item.getCreateDate());//时间
                if ("0".equals(item.getReadingState())) {//小红点（0-未读，1-已读）
                    viewHolder.setVisible(R.id.red_tag, true);
                } else {
                    viewHolder.setVisible(R.id.red_tag, false);
                }
            }
        };
        messageList.setAdapter(commonAdapter);
        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0 && position != (listAll.size() + 1)) {//判断点击时不是listView的foot、head
                    int positionValue = (int) parent.getAdapter().getItemId(position);//解决点击错位问题
                    setMessageReadState(positionValue);//设置消息已读
                    switch (listAll.get(positionValue).getMsgType()) {//点击不同类型跳转不同界面
                        case "1"://待接诊
                            startActivity(NewCReceptionActivity.class);//接诊列表
                            break;
                        case "2"://已结账
                            startActivity(ChargeDetailsActivity.class, "fid", listAll.get(positionValue).getBusinessPkid());//收费单详情
                            break;
                        case "3"://已驳回
                            startActivity(ChargeDetailsActivity.class, "fid", listAll.get(positionValue).getBusinessPkid());//收费单详情
                            break;
                        case "4"://OA已提交
                            startActivity(ChargeDetailsActivity.class, "fid", listAll.get(positionValue).getBusinessPkid());//收费单详情
                            break;
                        case "5"://OA已通过
                            startActivity(ChargeDetailsActivity.class, "fid", listAll.get(positionValue).getBusinessPkid());//收费单详情
                            break;
                        case "6":// OA已终止
                            break;
                        /*case "7"://待扫码支付
                            startActivity(PaymentActivity.class, "fid", listAll.get(positionValue).getBusinessPkid());//收费二维码
                            break;*/
                        case "8"://待支付
                            startActivity(PaymentActivity.class, "fid", listAll.get(positionValue).getBusinessPkid());//收费二维码
                            //startActivity(ChargeDetailsActivity.class, "fid", listAll.get(positionValue).getBusinessPkid());//收费单详情
                            break;
                    }
                }
            }
        });
        //滑动监听
        messageList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                title.getBackground().mutate().setAlpha(getAlphaFloat(getScroolY()));//根据滑动距离设置标题栏透明值
            }
        });
    }

    @OnClick({R.id.message_img, R.id.control_img})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.control_img://退出
                View view = DialogUtil.hintDialog(getActivity(), "是否确认退出？");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitLogin();
                    }
                });
                break;
            case R.id.message_img://消息（图标）
                startActivity(MessageActivity.class);
                break;
            case R.id.message_ll://消息列表（更多）
                startActivity(MessageActivity.class);
                break;
            case R.id.client_btn://客户
                startActivity(ClientActivity.class);
                break;
            case R.id.clinical_reception_btn://接诊
                startActivity(NewCReceptionActivity.class);
                break;
            case R.id.hospital_appointment_btn://到院预约
                startActivity(HospitalAppointmentActivity.class);
                break;
            case R.id.pay_demand_note_btn://收费单
                startActivity(ChargeShowActivity.class);
                break;
            case R.id.doctor_refer_btn://医生查询
                startActivity(DoctorActivity.class);
                break;
            case R.id.surgery_btn://手术查询
                startActivity(SurgeryActivity.class);
                break;
            case R.id.return_visit_btn://回访
                startActivity(VisitActivity.class);
                break;
            case R.id.repertory_btn://库存查询
                startActivity(SearchActivity.class);//新建
//                startActivity(TestActivity.class);
                CommonUtil.showToast("敬请期待");
                break;
            case R.id.hospital_sum_btn://预约到院总数
                startActivity(HospitalAppointmentActivity.class);
                break;
            case R.id.reception_sum_btn://接诊总数
                startActivity(NewCReceptionActivity.class);
                break;
            case R.id.order_sum_btn://开单总数
                startActivity(ChargeShowActivity.class);
                break;
            case R.id.visit_sum_btn://回访总数
                startActivity(VisitActivity.class);
                break;
        }
    }

    //初始化数据
    public void initData() {
        //获取今日统计数据
        HttpClient.getHttpApi().getStatisticsData().enqueue(new BaseBack<StatisticsEntity>() {
            @Override
            protected void onSuccess(StatisticsEntity statisticsEntity) {
                hospitalSum.setText(statisticsEntity.getAppointDataCount());//预约到院总数
                receptionSum.setText(statisticsEntity.getReceiveDataCount());//接诊总数
                orderSum.setText(statisticsEntity.getChargeBillDataCount());//开单总数
                visitAlreadySum.setText(statisticsEntity.getPlanTaskDataCount_already());//已回访总数
                visitWaitSum.setText(statisticsEntity.getPlanTaskDataCount_wait());//未回访总数
                refreshLayout.finishRefresh();
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);

            }
        });

        //获取当天消息列表
        HttpClient.getHttpApi().getIntradayMessageList().enqueue(new BaseBack<List<MessageEntity>>() {
            @Override
            protected void onSuccess(List<MessageEntity> messageEntities) {
                listAll.clear();
                listAll.addAll(messageEntities);
                commonAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);

            }
        });

        //未读消息
        HttpClient.getHttpApi().getMessageSum().enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                if (CHomeFragment.this != null && CHomeFragment.this.isAdded()) {
                    if ("1".equals(stringStringMap.get("isUnReadMessage"))) {//有未读消息
                        messageNotSum.setVisibility(View.VISIBLE);//显示未读数圆点
                        messageNotSum.setText(stringStringMap.get("msgNumber").toString());//未读消息数量
                    } else {
                        messageNotSum.setVisibility(View.GONE);
                    }
                    refreshLayout.finishRefresh();
                }
            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);

            }
        });
    }

    //设置消息已读消息
    private void setMessageReadState(final int position) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("fid", listAll.get(position).getFid());//消息id

        HttpClient.getHttpApi().setMessageReadState(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                listAll.get(position).setReadingState("1");
                commonAdapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();

            }

            @Override
            protected void onFailed(String code, String msg) {
                refreshLayout.finishRefresh(false);

            }
        });
    }

    //退出登录
    private void exitLogin() {
        DialogUtil.dismissDialog();//关闭 退出提示弹框
        CommonUtil.showLoadProgress(getActivity());

        HttpClient.getHttpApi().exitLongin().enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                SPUtils.clearCache(SPUtils.FILE_USER);//清空账户信息缓存
                startActivity(LoginActivity.class);
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                SPUtils.clearCache(SPUtils.FILE_USER);//清空账户信息缓存
                startActivity(LoginActivity.class);
                getActivity().finish();
            }
        });
    }

    // 获取渐变透明值
    public int getAlphaFloat(int height) {
        int imgheight = 150;
        if (height <= 0) {
            return 0;
        } else if (height < imgheight) {
            return (int) Math.round(height * ((double) 255 / imgheight));
        } else {
            return 255;
        }
    }

    // 获取上滑的距离
    public int getScroolY() {
        View c = messageList.getChildAt(0);
        if (null == c) {
            return 0;
        }
        int firstVisiblePosition = messageList.getFirstVisiblePosition();
        int top = c.getTop();
        //负数，取绝对值
        return firstVisiblePosition * c.getHeight() + Math.abs(top);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        initData();
    }
}
