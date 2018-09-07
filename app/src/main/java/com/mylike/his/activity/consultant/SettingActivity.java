package com.mylike.his.activity.consultant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.entity.IpEntiyt;
import com.mylike.his.utils.SPUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengluping on 2018/7/4.
 * 设置ip地址
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.return_btn)
    ImageView returnBtn;
    @Bind(R.id.add_btn)
    TextView addBtn;
    @Bind(R.id.ip_list)
    ListView ipList;

    private CommonAdapter commonAdapter;
    private List<IpEntiyt> ipEntiytList = new ArrayList<>();
    private Gson gson = new Gson();

    private String ipValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initView() {
        commonAdapter = new CommonAdapter<IpEntiyt>(this, R.layout.item_ip_list, ipEntiytList) {
            @Override
            protected void convert(final ViewHolder viewHolder, final IpEntiyt item, final int position) {
                viewHolder.setText(R.id.ip_text, item.getIp() + ":" + item.getPort());
                viewHolder.setChecked(R.id.radio_btn, item.isChecked());
                if (TextUtils.isEmpty(item.getRemark())) {
                    viewHolder.setText(R.id.remark_text, "暂无备注信息");
                } else {
                    viewHolder.setText(R.id.remark_text, item.getRemark());
                }

                //选中
                viewHolder.setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (IpEntiyt ipEntiyt : ipEntiytList) {
                            ipEntiyt.setChecked(false);
                        }
                        ipEntiytList.get(position).setChecked(true);
                        ipValue = item.getIp() + ":" + item.getPort();
                        commonAdapter.notifyDataSetChanged();
                    }
                });
                viewHolder.setOnClickListener(R.id.radio_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (IpEntiyt ipEntiyt : ipEntiytList) {
                            ipEntiyt.setChecked(false);
                        }
                        ipEntiytList.get(position).setChecked(true);
                        ipValue = item.getIp() + ":" + item.getPort();
                        commonAdapter.notifyDataSetChanged();
                    }
                });

                
                //编辑
                viewHolder.setOnClickListener(R.id.btnCompile, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(AddIPActivity.class, "position", position + "");
                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
                    }
                });
                //删除
                viewHolder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ipEntiytList.remove(position);
                        SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_TEXT, gson.toJson(ipEntiytList));
                        commonAdapter.notifyDataSetChanged();
                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();
                    }
                });
            }
        };
        ipList.setAdapter(commonAdapter);
    }

    private void initData() {
        String json = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_TEXT);
        if (!TextUtils.isEmpty(json)) {
            ipEntiytList.clear();
            ipEntiytList.addAll((Collection<? extends IpEntiyt>) gson.fromJson(json, new TypeToken<List<IpEntiyt>>() {
            }.getType()));
            commonAdapter.notifyDataSetChanged();
        } else {//为了方便测试用的，正式服请屏蔽else里的代码
            IpEntiyt ipEntiyt = new IpEntiyt();
//            ipEntiyt.setIp("172.16.61.242");//服务器
//            ipEntiyt.setPort("9093");
            ipEntiyt.setIp("172.16.61.222");//服务器
            ipEntiyt.setPort("8280");
//            ipEntiyt.setIp("172.16.63.118");//鲁钿
//            ipEntiyt.setPort("8080");
//            ipEntiyt.setIp("172.16.63.149");//春雷
//            ipEntiyt.setPort("8085");
            ipEntiyt.setChecked(false);
            ipEntiyt.setRemark("服务器地址");
            ipEntiytList.add(ipEntiyt);
            SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_TEXT, gson.toJson(ipEntiytList));
            initData();
        }
    }

    @OnClick({R.id.add_btn, R.id.return_btn, R.id.save_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                if (!TextUtils.isEmpty(ipValue)) {
                    SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED, ipValue);
                }
                SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_TEXT, gson.toJson(ipEntiytList));
                finish();
                break;
            case R.id.add_btn:
                startActivity(AddIPActivity.class);
                break;
            case R.id.return_btn:
                finish();
                break;
        }
    }
}
