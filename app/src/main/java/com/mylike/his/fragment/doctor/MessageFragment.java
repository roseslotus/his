package com.mylike.his.fragment.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.mylike.his.R;
import com.mylike.his.activity.doctor.MessageDetailsActivity;
import com.mylike.his.activity.doctor.MessageSettingActivity;
import com.mylike.his.core.BaseFragment;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by zhengluping on 2018/1/2.
 * 医生端-消息
 */

public class MessageFragment extends BaseFragment implements View.OnClickListener {
    Unbinder unbinder;

    @BindView(R.id.setting_btn)
    ImageView settingBtn;
    @BindView(R.id.message_list)
    ListView messageList;

    private List<String> date = new ArrayList<>();
    private List<String> date2 = new ArrayList<>();

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        setDate();
        messageList.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.item_message_list, date) {
            @Override
            protected void convert(final ViewHolder viewHolder, final String item, int position) {
                viewHolder.setText(R.id.name_text, item);
                if (item.equals("待接诊")) {
                    viewHolder.setImageResource(R.id.message_img, R.mipmap.treat_m_icon);
                } else if (item.equals("待回访")) {
                    viewHolder.setImageResource(R.id.message_img, R.mipmap.return_visit_m_icon);
                } else if (item.equals("治疗提醒")) {
                    viewHolder.setImageResource(R.id.message_img, R.mipmap.treatment_m_icon);
                } else if (item.equals("到院提醒")) {
                    viewHolder.setImageResource(R.id.message_img, R.mipmap.hospital_m_icon);
                }

                viewHolder.setOnClickListener(R.id.btnItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(MessageDetailsActivity.class);
                    }
                });
//                viewHolder.setOnClickListener(R.id.btnUnsubscribe, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showToast("点击了退订" + item);
//                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
//                    }
//                });
//
//                viewHolder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showToast("点击了删除" + item);
//                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
//                    }
//                });
            }
        });

//        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(MessageDetailsActivity.class);
//            }
//        });

//        messageList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                View itemView = DialogUtil.commomDialog(getActivity(), R.layout.common_item_list, 0);
//                ListView listView = itemView.findViewById(R.id.common_list);
//                listView.setBackgroundColor(getResources().getColor(R.color.white));0
//                listView.setAdapter(new CommonAdapter<String>(getActivity(), R.layout.common_item_text, date2) {
//                    @Override
//                    protected void convert(ViewHolder viewHolder, String item, int position) {
//                        TextView textView = viewHolder.getView(R.id.text);
//                        textView.setPadding(20, 20, 20, 20);
//                        textView.setText(item);
//                    }
//                });
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        DialogUtil.dismissDialog();
//                    }
//                });
//                return true;
//            }
//        });
        return view;

    }

    private void setDate() {
        date.add("待接诊");
        date.add("待回访");
        date.add("到院提醒");
        date.add("治疗提醒");
        date.add("待回访");
        date.add("待接诊");
        date.add("待接诊");
        date.add("待接诊");
        date.add("待接诊");
        date.add("待接诊");

        date2.add("退订");
        date2.add("删除");
    }

    @OnClick({R.id.setting_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_btn://消息设置
                startActivity(MessageSettingActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
