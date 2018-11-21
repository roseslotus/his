package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.BasePageEntity;
import com.mylike.his.entity.MessageEntity;
import com.mylike.his.entity.MessageTypeEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/6/1.
 * 消息列表
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.message_list)
    ListView messageList;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.filtrate_btn)
    ImageView filtrateBtn;
    @BindView(R.id.filtrate_list)
    ListView filtrateList;
    @BindView(R.id.reset_btn)
    Button resetBtn;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.filtrate_menu)
    LinearLayout filtrateMenu;
    @BindView(R.id.DrawerLayout)
    android.support.v4.widget.DrawerLayout DrawerLayout;
    @BindView(R.id.start_time_text)
    TextView startTimeText;
    @BindView(R.id.start_time_btn)
    LinearLayout startTimeBtn;
    @BindView(R.id.end_time_text)
    TextView endTimeText;
    @BindView(R.id.end_time_btn)
    LinearLayout endTimeBtn;
    @BindView(R.id.clear_text)
    TextView clearText;

    private int sumPage = 1;//总也数
    private int pageSize = 10;//每页数据
    private int pageNumber = 1;//页码

    //消息列表
    private CommonAdapter commonAdapter;
    private List<MessageEntity> listAll = new ArrayList<>();

    //筛选数据
    private TagAdapter tagAdapter;
    private CommonAdapter commonAdapter1;
    private List<MessageTypeEntity> messageTypeEntitys = new ArrayList<>();

    Map<String, Set<String>> selectedValue = new HashMap<String, Set<String>>();
    Map<Integer, Set<Integer>> selectedMap = new HashMap<Integer, Set<Integer>>();

    //时间选择器
    private TimePickerView TimePV1;
    private TimePickerView TimePV2;
    Calendar startDate = Calendar.getInstance();//选择器开始时间
    Calendar endDate = Calendar.getInstance();//选择结束时间
    Calendar today = Calendar.getInstance();//当天

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        setListView(messageList);

        initView();
        initData();
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        //禁止筛选侧滑动
        //DrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //初始化适配器
        initAdapter1();
        initAdapter2();

        //时间初始化
        startDate.set(1900, 0, 1);
        endDate.set(2200, 11, 31);

        //时间器初始化
        initTimeView1();
        initTimeView2();
    }

    //初始化数据
    private void initData() {
        getMessageList();
        getMessageType();
    }

    //消息列表适配器
    private void initAdapter1() {
        commonAdapter = new CommonAdapter<MessageEntity>(this, R.layout.item_message_list, listAll) {
            @Override
            protected void convert(final ViewHolder viewHolder, MessageEntity item, final int position) {
                //图标
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
                    case "9"://已跑诊
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_blue4_left_10);
                        break;
                    case "10"://已跳诊
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_orange3_left_10);
                        break;
                    case "11"://vip到院
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_purple_left_10);
                        break;
                }
                viewHolder.setText(R.id.name_text, item.getMsgTypeName());//类型名称
                viewHolder.setText(R.id.content_text, item.getMsgContent());//提示内容
                viewHolder.setText(R.id.time_text, item.getCreateDate().substring(0, 16));//时间
                if ("0".equals(item.getReadingState())) {
                    viewHolder.setVisible(R.id.red_tag, true);
                } else {
                    viewHolder.setVisible(R.id.red_tag, false);
                }

                viewHolder.setOnClickListener(R.id.btnItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setMessageReadState(listAll.get(position).getFid());
                        switch (listAll.get(position).getMsgType()) {
                            case "1"://待接诊
                                startActivity(NewCReceptionActivity.class);
                                break;
                            case "2"://已结账
                                startActivity(ChargeDetailsActivity.class, "fid", listAll.get(position).getBusinessPkid());
                                break;
                            case "3"://已驳回
                                startActivity(ChargeDetailsActivity.class, "fid", listAll.get(position).getBusinessPkid());
                                break;
                            case "4"://OA已提交
                                startActivity(ChargeDetailsActivity.class, "fid", listAll.get(position).getBusinessPkid());
                                break;
                            case "5"://OA已通过
                                startActivity(ChargeDetailsActivity.class, "fid", listAll.get(position).getBusinessPkid());
                                break;
                            case "6":// OA已终止
                                break;
                            case "7"://待扫码支付
                                startActivity(PaymentActivity.class, "fid", listAll.get(position).getBusinessPkid());
                                break;
                            case "8"://待支付
                                startActivity(ChargeDetailsActivity.class, "fid", listAll.get(position).getBusinessPkid());
                                break;
                        }
                        MessageEntity messageEntity = listAll.get(position);
                        messageEntity.setReadingState("1");
                        listAll.set(position, messageEntity);
                        commonAdapter.notifyDataSetChanged();
                    }
                });

                viewHolder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteMessage(listAll.get(position).getFid(), position);
                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
                    }
                });
            }
        };
        messageList.setAdapter(commonAdapter);
    }

    //筛选适配器
    private void initAdapter2() {
        final float scale = this.getResources().getDisplayMetrics().density;
        commonAdapter1 = new CommonAdapter<MessageTypeEntity>(MessageActivity.this, R.layout.item_filtrate_product_list, messageTypeEntitys) {
            @Override
            protected void convert(final ViewHolder viewHolder, final MessageTypeEntity item, int position) {
                viewHolder.setText(R.id.cover_name, item.getName());
                TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
                tagAdapter = new TagAdapter(item.getList()) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        TextView textView = (TextView) LayoutInflater.from(MessageActivity.this).inflate(R.layout.item_text_label, null);
                        textView.setTextSize(12);
                        textView.setWidth((filtrateMenu.getWidth() / 3) - (int) (10 * scale + 0.5f));
                        textView.setPadding(0, 30, 0, 30);
                        textView.setGravity(Gravity.CENTER);

                        textView.setText(item.getList().get(position).getName());
                        return textView;
                    }
                };
                tagFlowLayout.setAdapter(tagAdapter);

                tagAdapter.setSelectedList(selectedMap.get(viewHolder.getItemPosition()));
                tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        selectedMap.put(viewHolder.getItemPosition(), selectPosSet);
                        Set<String> strings = new HashSet<>();
                        for (int i : selectPosSet) {
                            strings.add(item.getList().get(i).getId());
                        }
                        selectedValue.put(item.getId(), strings);
                    }
                });
            }
        };
        filtrateList.setAdapter(commonAdapter1);
    }

    //开始时间选择器
    private void initTimeView1() {
        TimePV1 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                startTimeText.setText(CommonUtil.getYMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(today)//默认数据
                .build();
    }

    //结束时间选择器
    private void initTimeView2() {
        TimePV2 = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                endTimeText.setText(CommonUtil.getYMD(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})//只显示日期
                .setSubCalSize(14)//确认取消文字大小
                .setContentTextSize(14)//滚轮文字大小
                .setSubmitColor(getResources().getColor(R.color.green_50))//确定按钮的颜色
                .setCancelColor(getResources().getColor(R.color.gray_49))//取消按钮的颜色
                .setDividerColor(getResources().getColor(R.color.green_50))//选中线颜色
                .setLineSpacingMultiplier((float) 2.5)//滚轮间距（此为文字高度的间距倍数）
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDate(today)//默认数据
                .build();
    }

    //获取消息列表数据
    private void getMessageList() {
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("pageNumber", pageNumber);
        map1.put("pageSize", pageSize);
        map1.put("createDateBegin", startTimeText.getText());//筛选开始时间
        map1.put("createDateEnd", endTimeText.getText());//筛选结束时间

        HashMap<String, Object> map = new HashMap<>();
        map.clear();
        map.putAll(map1);
        map.putAll(selectedValue);

        HttpClient.getHttpApi().getMessageList(HttpClient.getRequestBody(map)).enqueue(new BaseBack<BasePageEntity<MessageEntity>>() {
            @Override
            protected void onSuccess(BasePageEntity<MessageEntity> messageEntityBasePageEntity) {
                sumPage = messageEntityBasePageEntity.getTotalPages();
                if (sumPage == pageNumber) {
                    refreshLayout.setNoMoreData(true);
                    setListNotData(true, null);
                }
                listAll.addAll(messageEntityBasePageEntity.getList());
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

    //获取消息类型（筛选数据）
    private void getMessageType() {
        HttpClient.getHttpApi().getMessageType().enqueue(new BaseBack<MessageTypeEntity>() {
            @Override
            protected void onSuccess(MessageTypeEntity messageTypeEntity) {
                messageTypeEntitys.addAll(messageTypeEntity.getList());
                commonAdapter1.notifyDataSetChanged();
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //设置未读消息
    private void setMessageReadState(String fid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("fid", fid);
        HttpClient.getHttpApi().setMessageReadState(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
            }

            @Override
            protected void onFailed(String code, String msg) {
            }
        });
    }

    //删除消息
    private void deleteMessage(String fid, final int position) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("fid", fid);
        HttpClient.getHttpApi().deleteMessage(HttpClient.getRequestBody(map)).enqueue(new BaseBack<Map<String, String>>() {
            @Override
            protected void onSuccess(Map<String, String> stringStringMap) {
                listAll.remove(position);
                commonAdapter.notifyDataSetChanged();
                CommonUtil.showToast("删除成功");
            }

            @Override
            protected void onFailed(String code, String msg) {
                CommonUtil.showToast("删除失败");
            }
        });
    }

    @OnClick({R.id.return_btn, R.id.filtrate_btn, R.id.reset_btn, R.id.confirm_btn, R.id.start_time_btn, R.id.end_time_btn, R.id.clear_text})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_btn://筛选重置
                selectedMap.clear();
                selectedValue.clear();
                startTimeText.setText("");
                endTimeText.setText("");
                commonAdapter1.notifyDataSetChanged();
                break;
            case R.id.confirm_btn://筛选确认
                DrawerLayout.closeDrawer(filtrateMenu);
                pageNumber = 1;
                listAll.clear();
                refreshLayout.setNoMoreData(false);
                getMessageList();
                break;
            case R.id.filtrate_btn://消息筛选
                DrawerLayout.openDrawer(filtrateMenu);
                break;
            case R.id.return_btn://返回
                finish();
                break;
            case R.id.start_time_btn://开始时间
                TimePV1.show();
                break;
            case R.id.end_time_btn://结束时间
                TimePV2.show();
                break;
            case R.id.clear_text://清除时间
                startTimeText.setText("");
                endTimeText.setText("");
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNumber = 1;
        setListNotData(false, null);
        listAll.clear();
        getMessageList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (sumPage == 1) {
            refreshLayout.finishLoadMore();
        } else {
            pageNumber = pageNumber + 1;
            getMessageList();
        }
    }
}
