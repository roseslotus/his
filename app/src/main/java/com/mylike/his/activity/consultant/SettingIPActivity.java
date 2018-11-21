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
import com.mylike.his.core.Constant;
import com.mylike.his.entity.IpEntiyt;
import com.mylike.his.utils.SPUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * Created by zhengluping on 2018/7/4.
 * 设置ip地址
 */
public class SettingIPActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.add_btn)
    TextView addBtn;
    @BindView(R.id.ip_list)
    ListView ipList;

    private String ipValue;//选中的值（ip地址+ 端口）
    private Gson gson = new Gson();
    private CommonAdapter commonAdapter;
    private List<IpEntiyt> ipEntiytList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
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
                viewHolder.setText(R.id.ip_text, item.getIp() + ":" + item.getPort());//ip
                viewHolder.setChecked(R.id.radio_btn, item.isChecked());//单选按钮
                if (TextUtils.isEmpty(item.getRemark())) {//备注
                    viewHolder.setText(R.id.remark_text, "暂无备注信息");
                } else {
                    viewHolder.setText(R.id.remark_text, item.getRemark());
                }

                //如果是选中ip则赋值
                if (item.isChecked()) {
                    ipValue = item.getIpValue();
                }

                //选中
                viewHolder.setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (IpEntiyt ipEntiyt : ipEntiytList) {
                            ipEntiyt.setChecked(false);
                        }
                        ipEntiytList.get(position).setChecked(true);
                        //获取选中的ip值
                        commonAdapter.notifyDataSetChanged();
                    }
                });

                //编辑
                viewHolder.setOnClickListener(R.id.btnCompile, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(AddIPActivity.class, "position", position + "");
                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();//关闭左滑行为
                    }
                });
                //删除
                viewHolder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ipValue = "";
                        ipEntiytList.remove(position);
                        SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_List, gson.toJson(ipEntiytList));
                        commonAdapter.notifyDataSetChanged();
                        ((SwipeMenuLayout) viewHolder.getConvertView()).quickClose();//关闭左滑行为
                    }
                });
            }
        };
        ipList.setAdapter(commonAdapter);
    }

    private void initData() {
        String json = SPUtils.getCache(SPUtils.FILE_IP, SPUtils.IP_List);
        if (!TextUtils.isEmpty(json)) {
            ipEntiytList.clear();
            ipEntiytList.addAll((Collection<? extends IpEntiyt>) gson.fromJson(json, new TypeToken<List<IpEntiyt>>() {
            }.getType()));
            commonAdapter.notifyDataSetChanged();
        } else if (Constant.ISSUE) {
            //---------------为了方便测试用的，正式服请屏蔽代码------------------------------
            IpEntiyt ipEntiyt = new IpEntiyt();
            ipEntiyt.setIp("172.16.61.222");
            ipEntiyt.setPort("8280");
            ipEntiyt.setIpValue("172.16.61.222:8280");
            ipEntiyt.setChecked(false);
            ipEntiyt.setRemark("服务器地址");
            ipEntiytList.add(ipEntiyt);

            ipEntiyt = new IpEntiyt();
            ipEntiyt.setIp("172.16.61.222");
            ipEntiyt.setPort("8380");
            ipEntiyt.setIpValue("172.16.61.222:8380");
            ipEntiyt.setChecked(false);
            ipEntiyt.setRemark("服务器地址");
            ipEntiytList.add(ipEntiyt);

            ipEntiyt = new IpEntiyt();
            ipEntiyt.setIp("172.16.61.242");
            ipEntiyt.setPort("9093");
            ipEntiyt.setIpValue("172.16.61.242:9093");
            ipEntiyt.setChecked(false);
            ipEntiyt.setRemark("服务器地址");
            ipEntiytList.add(ipEntiyt);
            SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_List, gson.toJson(ipEntiytList));
            initData();
            //-------------------------------------------------------------------------------------
        }
    }

    @OnClick({R.id.add_btn, R.id.return_btn, R.id.save_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_btn:
                if (!TextUtils.isEmpty(ipValue)) {
                    SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED, ipValue);
                    RetrofitUrlManager.getInstance().setGlobalDomain("http://" + ipValue);
                } else {//未有选中ip，清空缓存
                    SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_CHECKED, "");
                }

                if (ipEntiytList.isEmpty()) {
                    SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_List, "");
                } else {
                    SPUtils.setCache(SPUtils.FILE_IP, SPUtils.IP_List, gson.toJson(ipEntiytList));
                }
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
