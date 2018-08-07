package com.mylike.his.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.mylike.his.R;

import java.util.List;

/**
 * Created by zhengluping on 2018/2/5.
 */

public class ChargeListAdapter extends BaseAdapter {

    private List<String> date;
    private Context context;

    public ChargeListAdapter(List<String> date, Context context) {
        this.date = date;
        this.context = context;
    }

    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public Object getItem(int position) {
        return date.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = LinearLayout.inflate(R.layout.item_project_spinner, null);
        convertView=LayoutInflater.from(context).inflate(R.layout.item_refund_product_list,null);

        return convertView;
    }
}
