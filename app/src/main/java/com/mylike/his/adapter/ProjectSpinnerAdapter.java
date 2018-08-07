package com.mylike.his.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mylike.his.R;
import com.mylike.his.entity.ProjectInfoEntity;

import java.util.List;

/**
 * Created by zhengluping on 2018/1/29.
 */

public class ProjectSpinnerAdapter extends BaseAdapter {
    private List<ProjectInfoEntity> list;
    private LayoutInflater layoutInflater = null;

    public ProjectSpinnerAdapter(Context context, List<ProjectInfoEntity> list) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_project_spinner, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        if (TextUtils.isEmpty(list.get(position).getMoney())) {
            holder.money.setVisibility(View.GONE);
        } else {
            holder.money.setText(list.get(position).getMoney());
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView name;
        private TextView money;
    }
}
