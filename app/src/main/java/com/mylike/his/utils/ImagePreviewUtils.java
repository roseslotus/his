package com.mylike.his.utils;

import android.content.Context;

import com.SuperKotlin.pictureviewer.ImagePagerActivity;
import com.SuperKotlin.pictureviewer.PictureConfig;
import com.mylike.his.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thl on 2018/10/26.
 */

public class ImagePreviewUtils {
    public static void lookBigMutilImage(Context context, List<String> list){
        //这是你的数据
//        List<String> list = new ArrayList<>();
//        //网络图片
//        list.add("https://ws1.sinaimg.cn/large/610dc034ly1fgepc1lpvfj20u011i0wv.jpg");
//        list.add("https://ws1.sinaimg.cn/large/610dc034ly1fgdmpxi7erj20qy0qyjtr.jpg");
//        list.add("https://ws1.sinaimg.cn/large/610dc034ly1fgchgnfn7dj20u00uvgnj.jpg");
//        list.add("https://ws1.sinaimg.cn/large/610dc034ly1fgbbp94y9zj20u011idkf.jpg");

        //使用方式
        PictureConfig config = new PictureConfig.Builder()
                .setListData((ArrayList<String>) list)	//图片数据List<String> list
                .setPosition(0)	//图片下标（从第position张图片开始浏览）
                .setDownloadPath("pictureviewer")	//图片下载文件夹地址
                .setIsShowNumber(true)//是否显示数字下标
                .needDownload(true)	//是否支持图片下载
                .setPlacrHolder(R.mipmap.ic_launcher)	//占位符图片（图片加载完成前显示的资源图片，来源drawable或者mipmap）
                .build();
        ImagePagerActivity.startActivity(context, config);
    }
}
