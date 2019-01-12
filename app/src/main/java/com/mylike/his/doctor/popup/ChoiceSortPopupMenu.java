package com.mylike.his.doctor.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mylike.his.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by thl on 2018/8/7.
 */

public class ChoiceSortPopupMenu implements View.OnClickListener,AdapterView.OnItemClickListener{

    private LinearLayout llContent;

    private Context mContext;


    private PopupWindow categoryPopup;
    private ListView mListView;
    private CommonAdapter<String> commonAdapter;

    private List<String> mDatas;
    private ChioceListener chioceListener;
    private String name;

    public ChoiceSortPopupMenu showPopup(Context context, final String name, final List<String> datas, View rootView, final ChioceListener chioceListener) {
        this.mContext=context;
        this.mDatas=datas;
        this.name=name;
        this.chioceListener=chioceListener;
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_menu_choice_sort, null, false);
        categoryPopup = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        categoryPopup.setTouchable(true);
        categoryPopup.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        llContent = popupView.findViewById(R.id.ll_content);
        mListView = popupView.findViewById(R.id.list_view);

        categoryPopup.setOutsideTouchable(true);
        categoryPopup.setBackgroundDrawable(new BitmapDrawable());
        categoryPopup.showAsDropDown(rootView, 0, 0);

        initAdapter();
        setListener();
        return this;
    }

    private void setListener() {
        llContent.findViewById(R.id.ll_shadow).setOnClickListener(this);
        llContent.findViewById(R.id.ll_head).setOnClickListener(this);
        mListView.setOnItemClickListener(this);
    }

    public void initAdapter(){
        commonAdapter = new CommonAdapter<String>(mContext,R.layout.item_workbench_keshi,mDatas) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
              TextView tvName = holder.getView(R.id.tv_name);
                ImageView ivSelected = holder.getView(R.id.iv_image_selected);
                if (name!=null&&name.equals(mDatas.get(position))){
                   ivSelected.setVisibility(View.VISIBLE);
                    tvName.setTextColor(mContext.getResources().getColor(R.color.doctor_green_color));
                }else {
                    ivSelected.setVisibility(View.GONE);
                    tvName.setTextColor(Color.parseColor("#575757"));
                }
                tvName.setText(item);
            }
        };
        mListView.setAdapter(commonAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.ll_shadow||view.getId()==R.id.ll_head){
            closeDialog();
            if (chioceListener != null) {
                chioceListener.dismiss();
            }
        }
    }

    private void closeDialog(){
        if(categoryPopup!=null){
            categoryPopup.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        name = mDatas.get(i);
        commonAdapter.notifyDataSetChanged();
        closeDialog();
        if (chioceListener != null) {
            chioceListener.choice(name);
        }


    }

    public interface ChioceListener{
        void choice(String name);
        void dismiss();
    }

}
