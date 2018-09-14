package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.BasePageEntity;
import com.mylike.his.entity.MessageEntity;
import com.mylike.his.http.BaseBack;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
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
 * Created by zhengluping on 2018/6/1.
 * 消息列表
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    //    @Bind(R.id.setting_btn)
    //    ImageView settingBtn;
    @Bind(R.id.message_list)
    ListView messageList;
    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

//    private List<String> date = new ArrayList<>();

    private int sumPage = 1;//总也数
    private int pageSize = 10;//每页数据
    private int pageNumber = 1;//页码
    private List<MessageEntity> listAll = new ArrayList<>();
    private CommonAdapter commonAdapter;

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
                    case "7"://待扫码支付
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_orange_left_10);
                        break;
                    case "8"://待支付
                        viewHolder.setBackgroundRes(R.id.btnItem, R.drawable.bg_white_line_orange2_left_10);
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
                        deleteMessage(listAll.get(position).getFid(),position);
                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
                    }
                });
            }
        };
        messageList.setAdapter(commonAdapter);

//        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });



        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);

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



    @OnClick({R.id.return_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.setting_btn://消息设置
//                startActivity(MessageSettingActivity.class);
//                break;
            case R.id.return_btn://消息设置
                finish();
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNumber = 1;
        setListNotData(false, null);
        listAll.clear();
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


    //    private void initData() {
//        date.add("待接诊");
//        date.add("待回访");
//        date.add("到院提醒");
//        date.add("治疗提醒");
//        date.add("待回访");
//        date.add("待接诊");
//        date.add("待接诊");
//        date.add("待接诊");
//        date.add("待接诊");
//        date.add("待接诊");
//    }

    //        messageList.setAdapter(new CommonAdapter<String>(this, R.layout.item_message_list, date) {
//            @Override
//            protected void convert(final ViewHolder viewHolder, final String item, int position) {
//
//                viewHolder.setText(R.id.name_text, item);
//                if (item.equals("待接诊")) {
//                    viewHolder.setImageResource(R.id.message_img, R.mipmap.treat_m_icon);
//                } else if (item.equals("待回访")) {
//                    viewHolder.setImageResource(R.id.message_img, R.mipmap.return_visit_m_icon);
//                } else if (item.equals("治疗提醒")) {
//                    viewHolder.setImageResource(R.id.message_img, R.mipmap.treatment_m_icon);
//                } else if (item.equals("到院提醒")) {
//                    viewHolder.setImageResource(R.id.message_img, R.mipmap.hospital_m_icon);
//                }
//
//                //消息详情
//                viewHolder.setOnClickListener(R.id.btnItem, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(MessageDetailsActivity.class);
//                    }
//                });
//
//                //退订操作
//                viewHolder.setOnClickListener(R.id.btnUnsubscribe, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showToast("点击了退订" + item);
//                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
//                    }
//                });
//
//                //删除操作
//                viewHolder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showToast("点击了删除" + item);
//                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
//                    }
//                });
//            }
//        });
}
