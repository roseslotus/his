package com.mylike.his.doctor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.core.BaseApplication;
import com.mylike.his.core.BaseFragment;
import com.mylike.his.doctor.activity.ShouShuDongTaiActivity;
import com.mylike.his.doctor.activity.ShouShuWoDePaiTaiActivity;
import com.mylike.his.doctor.activity.ShouShuWoDePaiqiActivity;
import com.mylike.his.doctor.activity.ShouShuWoDeYuyueActivity;
import com.mylike.his.doctor.activity.WoDeChuyuanActivity;
import com.mylike.his.doctor.activity.WoDeDaizhenActivity;
import com.mylike.his.doctor.activity.WoDeYuyueActivity;
import com.mylike.his.doctor.activity.WoDeZhuyuanActivity;
import com.mylike.his.doctor.popup.ChoiceKeShiPopupMenu;
import com.mylike.his.entity.DepCountEntity;
import com.mylike.his.entity.DepartmentBean;
import com.mylike.his.entity.LoginEntity;
import com.mylike.his.entity.TextIconAction;
import com.mylike.his.http.HttpClient;
import com.mylike.his.utils.CommonUtil;
import com.mylike.his.view.GridViewForScrollView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thl on 2018/12/29.
 */

public class WorkbenchFragment extends BaseFragment {


    @BindView(R.id.menzhen_gridview)
    GridView mMenzhenGridview;
    @BindView(R.id.shoshu_gridview)
    GridView mShoushuGridview;
    @BindView(R.id.zhuyuan_gridview)
    GridView mZhuyuanuGridview;
    @BindView(R.id.tv_keshi_name)
    TextView mTvKeshiName;
    @BindView(R.id.iv_keshi_image)
    ImageView mIvKeshiImage;
    @BindView(R.id.ll_keshi_panel)
    LinearLayout mLlKeshiPanel;
    @BindView(R.id.grid_view)
    GridViewForScrollView mGridView;
    private Unbinder unbinder;

    private CommonAdapter<TextIconAction> menzhenAdapter;
    private CommonAdapter<TextIconAction> shoushuAdapter;
    private CommonAdapter<TextIconAction> zhuyuanAdapter;
    private DepartmentBean mDepartmentBean;
    private View view;

    public static WorkbenchFragment newInstance() {
        Bundle args = new Bundle();
        WorkbenchFragment fragment = new WorkbenchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_workbench, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        initTabs(mMenzhenGridview, menzhenAdapter, buildMenzhenActions());
        initTabs(mShoushuGridview, shoushuAdapter, buildShoushuActions());
        initTabs(mZhuyuanuGridview, zhuyuanAdapter, buildZhuyuanActions());

        mMenzhenGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    startActivity(WoDeDaizhenActivity.class);
                } else if (position == 1) {
                    Intent intent = new Intent(getActivity(), WoDeYuyueActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(getActivity(), WoDeYuyueActivity.class);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                }

            }
        });
        mShoushuGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    startActivity(ShouShuWoDeYuyueActivity.class);
                } else if (position == 1) {
                    Intent intent = new Intent(getActivity(), ShouShuWoDePaiqiActivity.class);
                    intent.putExtra("type", 1);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(getActivity(), ShouShuWoDePaiTaiActivity.class);
                    startActivity(intent);
                } else {
                    startActivity(ShouShuDongTaiActivity.class);
                }

            }
        });
        mZhuyuanuGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    startActivity(WoDeZhuyuanActivity.class);
                } else if (position == 1) {
                    startActivity(WoDeChuyuanActivity.class);
                }

            }
        });
        findDepartmentBean(BaseApplication.getLoginEntity().getDefaultDepName());
        todayPeopleViewCount();
        return rootView;
    }

    private void initTabs(GridView gridView, CommonAdapter adapter, List<TextIconAction> list) {
        adapter = new CommonAdapter<TextIconAction>(getActivity(), R.layout.item_workbench_tab, list) {
            @Override
            protected void convert(ViewHolder holder, TextIconAction item, int position) {
                holder.setImageResource(R.id.iv_tab_image, item.getResId());
                holder.setText(R.id.tv_title, item.getName());
            }
        };
        gridView.setAdapter(adapter);
    }


    public List<TextIconAction> buildMenzhenActions() {
        List<TextIconAction> iconActions = new ArrayList<>();
        TextIconAction action1 = new TextIconAction("我的待诊", R.mipmap.icon_workbench_daizhen);
        iconActions.add(action1);
        TextIconAction action2 = new TextIconAction("我的预约", R.mipmap.icon_workbench_daizhenyuyue);
        iconActions.add(action2);
        TextIconAction action3 = new TextIconAction("我的接诊", R.mipmap.icon_workbench_jiezhen);
        iconActions.add(action3);
        TextIconAction action4 = new TextIconAction("科室待诊", R.mipmap.icon_workbench_keshidaizhen);
        iconActions.add(action4);
        TextIconAction action5 = new TextIconAction("科室预约", R.mipmap.icon_workbench_keshiyuyue);
        iconActions.add(action5);
        TextIconAction action6 = new TextIconAction("科室接诊", R.mipmap.icon_workbench_keshijiezhen);
        iconActions.add(action6);
        return iconActions;
    }

    public List<TextIconAction> buildShoushuActions() {
        List<TextIconAction> iconActions = new ArrayList<>();
        TextIconAction action1 = new TextIconAction("我的预约", R.mipmap.icon_workbench_shoushuyuyue);
        iconActions.add(action1);
        TextIconAction action2 = new TextIconAction("我的排期", R.mipmap.icon_workbench_shoushu_wodepaiqi);
        iconActions.add(action2);
        TextIconAction action3 = new TextIconAction("我的排台", R.mipmap.icon_workbench_shoushu_wodepaitai);
        iconActions.add(action3);
        TextIconAction action4 = new TextIconAction("手术动态", R.mipmap.icon_workbench_shoushu_shoushudongtai);
        iconActions.add(action4);
        TextIconAction action5 = new TextIconAction("科室预约", R.mipmap.icon_workbench_shoushu_keshiyuyue);
        iconActions.add(action5);
        TextIconAction action6 = new TextIconAction("科室排期", R.mipmap.icon_workbench_shoushu_keshipaiqi);
        iconActions.add(action6);
        TextIconAction action7 = new TextIconAction("科室排台", R.mipmap.icon_workbench_shoushu_keshipaitai);
        iconActions.add(action7);
        return iconActions;
    }

    public List<TextIconAction> buildZhuyuanActions() {
        List<TextIconAction> iconActions = new ArrayList<>();
        TextIconAction action1 = new TextIconAction("我的在院", R.mipmap.icon_workbench_zhuyuan_zaiyuan);
        iconActions.add(action1);
        TextIconAction action2 = new TextIconAction("我的出院", R.mipmap.icon_workbench_zhuyuan_chuyuan);
        iconActions.add(action2);
        TextIconAction action3 = new TextIconAction("在院一览", R.mipmap.icon_workbench_zhuyuan_zaiyuanyilan);
        iconActions.add(action3);
        TextIconAction action4 = new TextIconAction("出院一览", R.mipmap.icon_workbench_zhuyuan_chuyuanyilan);
        iconActions.add(action4);
        return iconActions;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_keshi_panel})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_keshi_panel:
                if (mDepartmentBean == null) {
                    return;
                }
                List<String> list = new ArrayList<>();
                for (DepartmentBean departmentsBean : BaseApplication.getLoginEntity().getDepartments()) {
                    list.add(departmentsBean.getDepName());
                }

                mIvKeshiImage.setImageResource(R.mipmap.icon_d_shang_la);
                new ChoiceKeShiPopupMenu().showPopup(getActivity(), mDepartmentBean.getDepName(), list, mLlKeshiPanel, new ChoiceKeShiPopupMenu.ChioceListener() {
                    @Override
                    public void choice(String name) {
                        findDepartmentBean(name);
                        LoginEntity loginEntity = BaseApplication.getLoginEntity();
                        loginEntity.setDefaultDepId(mDepartmentBean.getDepId());
                        loginEntity.setDefaultDepName(mDepartmentBean.getDepName());
                        todayPeopleViewCount();

                    }

                    @Override
                    public void dismiss() {
                        mIvKeshiImage.setImageResource(R.mipmap.icon_workbench_xiala);
                    }
                });
                break;
        }
    }

    private void findDepartmentBean(String departName) {
        for (DepartmentBean departmentsBean : BaseApplication.getLoginEntity().getDepartments()) {
            if (departName.equals(departmentsBean.getDepName())) {
                mDepartmentBean = departmentsBean;
                break;
            }
        }
        if (mDepartmentBean == null) {
            mLlKeshiPanel.setVisibility(View.INVISIBLE);
        } else {
            mTvKeshiName.setText(mDepartmentBean.getDepName());
            mIvKeshiImage.setImageResource(R.mipmap.icon_workbench_xiala);
        }
    }

    public void todayPeopleViewCount() {
        if (mDepartmentBean == null) {
            return;
        }
        CommonUtil.showLoadProgress(getActivity());

        HttpClient.getHttpApi().todayPeopleViewCount(BaseApplication.getLoginEntity().getTenantId(), mDepartmentBean.getDepId(), BaseApplication.getLoginEntity().getUserId()).enqueue(new Callback<List<DepCountEntity>>() {
            @Override
            public void onResponse(Call<List<DepCountEntity>> call, Response<List<DepCountEntity>> response) {
                CommonUtil.dismissLoadProgress();
                if (response==null||response.body()==null){
                    return;
                }
                CommonAdapter<DepCountEntity> commonAdapter = new CommonAdapter<DepCountEntity>(getActivity(),R.layout.item_today_depart_view_total,response.body()) {
                    @Override
                    protected void convert(ViewHolder holder, DepCountEntity item, int position) {
                        holder.setText(R.id.tv_view_type_name,item.getType());
                        holder.setText(R.id.tv_view_num,item.getNum());
                    }
                };
                mGridView.setAdapter(commonAdapter);
            }

            @Override
            public void onFailure(Call<List<DepCountEntity>> call, Throwable t) {
                CommonUtil.dismissLoadProgress();
            }
        });
    }
}
