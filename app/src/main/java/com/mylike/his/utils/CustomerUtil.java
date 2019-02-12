package com.mylike.his.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylike.his.R;
import com.mylike.his.entity.CustomerMenZhenBean;
import com.zhy.adapter.abslistview.ViewHolder;

import butterknife.BindView;

public class CustomerUtil {


    private static boolean isBoy(String sex){
        return  "男".equals(sex);
    }

    private  static int getVipLevelIcon(String levelName){
        if ("金卡".equals(levelName)){
            return R.mipmap.icon_d_vip1;
        }else if ("银卡".equals(levelName)){
            return R.mipmap.icon_d_vip2;
        } else if ("时尚卡".equals(levelName)){
            return R.mipmap.icon_d_vip3;
        }else {
            return R.mipmap.icon_d_vip4;
        }
    }

    public static void setCustomerInfo(ViewHolder holder, CustomerMenZhenBean customer){
        if (customer!=null){
            holder.setImageResource(R.id.iv_customer_head_image,isBoy(customer.getSex())?R.mipmap.icon_customer_boy:R.mipmap.icon_customer_girl);
            holder.setText(R.id.tv_customer_name,customer.getCusName());
            holder.setImageResource(R.id.tv_customer_sex,isBoy(customer.getSex())?R.mipmap.icon_d_boy:R.mipmap.icon_d_girl);
            holder.setImageResource(R.id.tv_customer_vip_level,getVipLevelIcon(customer.getLabels().get(2).getValue()));
            ImageView vipFlag =holder.getView(R.id.tv_customer_vip_flag);
            if (customer.getLabels().get(1).getValue()!=null){
                vipFlag.setVisibility(View.VISIBLE);
            }else {
                vipFlag.setVisibility(View.GONE);
            }
            holder.setText(R.id.tv_customer_year_and_birth,DateUtil.getBirthYear(customer.getBrithday()));
            holder.setText(R.id.tv_customer_mobile_no,customer.getTel());
        }
    }

    public static void setCustomerDetailHeaderInfo(ViewGroup containerView, CustomerMenZhenBean customer){
        ImageView mIvCustomerHeadImage = containerView.findViewById(R.id.iv_customer_head_image);
        TextView mTvCustomerName= containerView.findViewById(R.id.tv_customer_name);
        ImageView mTvCustomerSex= containerView.findViewById(R.id.tv_customer_sex);
        ImageView mTvCustomerVipLevel = containerView.findViewById(R.id.tv_customer_vip_level);
        ImageView mTvCustomerVipFlag = containerView.findViewById(R.id.tv_customer_vip_flag);
        TextView mTvCustomerBingliNo = containerView.findViewById(R.id.tv_customer_bingli_no);
        TextView mTvCustomerYearAndBirth = containerView.findViewById(R.id.tv_customer_year_and_birth);
        TextView mTvCustomerMobileNo = containerView.findViewById(R.id.tv_customer_mobile_no);

        mIvCustomerHeadImage.setImageResource(isBoy(customer.getSex()) ? R.mipmap.icon_customer_boy : R.mipmap.icon_customer_girl);
        mTvCustomerName.setText(customer.getCusName());
        mTvCustomerSex.setImageResource(isBoy(customer.getSex()) ? R.mipmap.icon_d_boy : R.mipmap.icon_d_girl);
        mTvCustomerVipLevel.setImageResource(getVipLevelIcon(customer.getLabels().get(2).getValue()));
        if (customer.getLabels().get(1).getValue() != null) {
            mTvCustomerVipFlag.setVisibility(View.VISIBLE);
        } else {
            mTvCustomerVipFlag.setVisibility(View.GONE);
        }
        mTvCustomerYearAndBirth.setText(DateUtil.getBirthYear(customer.getBrithday()));
        mTvCustomerMobileNo.setText(customer.getTel());
        mTvCustomerBingliNo.setText(customer.getCaseNo());
    }
}
