package com.mylike.his.utils;

import android.view.View;
import android.widget.TextView;

import com.mylike.his.R;

public class BusnessUtil {

    public static void setMenZhenBookStatus(TextView textView,String bookStatus){
        int resId;
        if ("未到院".equals(bookStatus)){
            resId= R.drawable.shape_dark_red_round4;
        }else if("已接诊".equals(bookStatus)){
            resId= R.drawable.shape_orange_round4;
        } else if("已到院".equals(bookStatus)){
            resId= R.drawable.shape_green_round4;
        }else {
            resId= R.drawable.shape_dark_red_round4;
        }

        textView.setBackgroundResource(resId);
        textView.setText(bookStatus);
        textView.setVisibility(View.VISIBLE);
    }

    public static void setMenZhenJieZhenStatus(TextView textView,String status){
        int resId;
        if ("取消".equals(status)){
            resId= R.drawable.shape_dark_red_round4;
        }else if("接诊中".equals(status)){
            resId= R.drawable.shape_orange_round4;
        } else if("已诊".equals(status)){
            resId= R.drawable.shape_green_round4;
        }else {
            resId= R.drawable.shape_dark_red_round4;
        }
        textView.setBackgroundResource(resId);
        textView.setText(status);
        textView.setVisibility(View.VISIBLE);

    }

    public static void setOperationBookStatus(TextView textView,String bookStatus){
        int resId;
        if ("未缴费".equals(bookStatus)){
            resId= R.drawable.shape_dark_red_round4;
        }else if("预约金".equals(bookStatus)){
            resId= R.drawable.shape_orange_round4;
        } else if("全款".equals(bookStatus)){
            resId= R.drawable.shape_green_round4;
        }else {
            resId= R.drawable.shape_gray_round4;
        }

        textView.setBackgroundResource(resId);
        textView.setText(bookStatus);
        textView.setVisibility(View.VISIBLE);
    }

    public static void setBinLiRecordStatus(TextView textView,String status){
        if (status==null){
            status="未归档";
        }
        int resId;
        if ("未归档".equals(status)){
            resId= R.drawable.shape_dark_red_round4;
        } else if("已归档".equals(status)){
            resId= R.drawable.shape_green_round4;
        }else {
            resId= R.drawable.shape_dark_red_round4;
        }

        textView.setBackgroundResource(resId);
        textView.setText(status);
        textView.setVisibility(View.VISIBLE);
    }

    public static void setZhiLiaoDengJiStatus(TextView textView,String status){
        if (status==null){
            status="未确认";
        }
        int resId;
        if ("未确认".equals(status)){
            resId= R.drawable.shape_dark_red_round4;
        } else if("已确认".equals(status)){
            resId= R.drawable.shape_green_round4;
        }else {
            resId= R.drawable.shape_orange_round4;
        }

        textView.setBackgroundResource(resId);
        textView.setText(status);
        textView.setVisibility(View.VISIBLE);
    }

    public static void setChuFangJiLuStatus(TextView textView,String status){
        if (status==null){
            status="保存";
        }
        int resId;
        if ("保存".equals(status)){
            resId= R.drawable.shape_orange_round4;
        } else if("确认".equals(status)){
            resId= R.drawable.shape_blue_round4;
        }else if("收费".equals(status)){
            resId= R.drawable.shape_dark_red_round4;
        }else {
            resId= R.drawable.shape_green_round4;
        }

        textView.setBackgroundResource(resId);
        textView.setText(status);
        textView.setVisibility(View.VISIBLE);
    }

    public static void setSchedulingStatus(TextView textView,String status){
        if (status==null){
            status="未排台";
        }
        int resId;
        if ("未排台".equals(status)){
            resId= R.drawable.shape_dark_red_round4;
        } else if("已完成".equals(status)){
            resId= R.drawable.shape_green_round4;
        }else {
            resId= R.drawable.shape_dark_red_round4;
        }

        textView.setBackgroundResource(resId);
        textView.setText(status);
        textView.setVisibility(View.VISIBLE);
    }

    public static void setArrangementStatus(TextView textView,String status){
        if (status==null){
            status="手术中";
        }
        int resId;
        if ("手术中".equals(status)){
            resId= R.drawable.shape_dark_red_round4;
        } else if("已结束".equals(status)){
            resId= R.drawable.shape_green_round4;
        } else if("取消".equals(status)){
            resId= R.drawable.shape_gray_round4;
        } else if("未开始".equals(status)){
            resId= R.drawable.shape_orange_round4;
        }else {
            resId= R.drawable.shape_dark_red_round4;
        }

        textView.setBackgroundResource(resId);
        textView.setText(status);
        textView.setVisibility(View.VISIBLE);
    }
}
