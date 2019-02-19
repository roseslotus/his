package com.mylike.his.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.entity.DoctorAdviceOrderMain;

import java.util.List;

/**
 * Created by thl on 2018/12/31.
 */

public class DoctorAdviceAdapter extends BaseAdapter {

    private List<DoctorAdviceOrderMain> mDatas;
    private Context context;
    public DoctorAdviceAdapter(Context context, List<DoctorAdviceOrderMain> datas){
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
        DoctorAdviceOrderMain bean = mDatas.get(position);
        if (viewType==0){
            HeadViewHolder headHolder;
            if (convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_yizhu_head,null,false);
                headHolder = new HeadViewHolder();
                headHolder.orderDate = convertView.findViewById(R.id.tv_order_date);
                convertView.setTag(headHolder);
            }else {
                headHolder = (HeadViewHolder)convertView.getTag();
            }

            headHolder.orderDate.setText(bean.getOrderDate());

        }else {

            BodyViewHolder bodyHolder;
            if (convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_yizhu_body,null,false);
                bodyHolder = new BodyViewHolder();
                bodyHolder.tvProductName = convertView.findViewById(R.id.tv_product_name);
                bodyHolder.tvDoctorName = convertView.findViewById(R.id.tv_doctor_name);
                bodyHolder.tvOrderCreateTime = convertView.findViewById(R.id.tv_order_create_time);
                bodyHolder.tvExeTime = convertView.findViewById(R.id.tv_order_confirm_time);
                bodyHolder.tvOrderStatus = convertView.findViewById(R.id.tv_order_status);
                convertView.setTag(bodyHolder);
            }else {
                bodyHolder = (BodyViewHolder)convertView.getTag();
            }

            bodyHolder.tvProductName.setText(bean.getDrugName());
            bodyHolder.tvDoctorName.setText(bean.getOrderDoc());
            bodyHolder.tvOrderCreateTime.setText(bean.getOrderCreateTime());
            bodyHolder.tvExeTime.setText(bean.getOrderExeTime());
            bodyHolder.tvOrderStatus.setText(bean.getOrderStatus());
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

    static class HeadViewHolder{
        public TextView orderDate;
    }

    static class BodyViewHolder{
        public TextView tvProductName;
        public TextView tvDoctorName;
        public TextView tvOrderCreateTime;
        public TextView tvExeTime;
        public TextView tvOrderStatus;
    }
}
