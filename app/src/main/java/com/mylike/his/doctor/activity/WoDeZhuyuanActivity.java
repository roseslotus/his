package com.mylike.his.doctor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mylike.his.R;
import com.mylike.his.core.BaseActivity;
import com.mylike.his.doctor.popup.ChoiceSortPopupMenu;
import com.mylike.his.doctor.popup.ChoiceZhuyuanShuaixuanPopupMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thl on 2018/12/31.
 */

public class WoDeZhuyuanActivity extends BaseActivity {

    @BindView(R.id.customer_list)
    ListView mCustomerList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_sort_image)
    ImageView mIvSortImage;
    @BindView(R.id.ll_sort_panel)
    LinearLayout mLlSortPanel;
    @BindView(R.id.ll_shuaixuan_panel)
    LinearLayout mLlShuaixuanPanel;

    private CommonAdapter<String> commonAdapter;
    private List<String> mDatas = new ArrayList<>();
    private String mName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setLoadProgress(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode_zhuyuan);
        ButterKnife.bind(this);
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        mDatas.add("1");
        commonAdapter = new CommonAdapter<String>(this, R.layout.item_wode_zaiyuan_info, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {

            }
        };
        mCustomerList.setAdapter(commonAdapter);
        mCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(ZhuyuanDetailActivity.class);
            }
        });
    }


    @OnClick({R.id.ll_sort_panel, R.id.ll_shuaixuan_panel})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_sort_panel:
                List<String> list = new ArrayList<>();
                list.add("床位排序");
                list.add("入院日期排序");
                mIvSortImage.setImageResource(R.mipmap.icon_d_shang_la);
                new ChoiceSortPopupMenu().showPopup(this, mName, list, mLlSortPanel, new ChoiceSortPopupMenu.ChioceListener() {
                    @Override
                    public void choice(String name) {
                        mName = name;
                        mIvSortImage.setImageResource(R.mipmap.icon_workbench_xiala);
                    }

                    @Override
                    public void dismiss() {
                        mIvSortImage.setImageResource(R.mipmap.icon_workbench_xiala);
                    }
                });
                break;
            case R.id.ll_shuaixuan_panel:
                List<String> choiceList= new ArrayList<>();
                choiceList.add("一级护理");
                choiceList.add("二级护理");
                choiceList.add("三级护理");
                choiceList.add("四级护理");
                new ChoiceZhuyuanShuaixuanPopupMenu().setDatas(choiceList).show(getFragmentManager(),"ChoiceZhuyuanShuaixuanPopupMenu");

                break;
        }
    }
}
