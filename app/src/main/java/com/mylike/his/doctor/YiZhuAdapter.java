package com.mylike.his.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mylike.his.R;

import java.util.List;

/**
 * Created by thl on 2018/12/31.
 */

public class YiZhuAdapter extends BaseAdapter {

    private List<YiZhuBean> mDatas;
    private Context context;
    public YiZhuAdapter(Context context,List<YiZhuBean> datas){
        this.mDatas=datas;
        this.context=context;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        int viewType = getItemViewType(position);
        if (viewType==0){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_yizhu_head,null,false);
        }else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_yizhu_body,null,false);
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getType();
    }
}
