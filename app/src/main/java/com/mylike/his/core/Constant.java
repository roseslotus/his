package com.mylike.his.core;

/**
 * 公共常量
 */
public class Constant {

    //测试标识
    public final static boolean ISSUE = false;//正式发布时请改成false

    //角色
    public final static String JOB_XC_COUNSELOR = "1";//现场咨询师
    public final static String JOB_WD_COUNSELOR = "2";//网电咨询师

    //页面跳转标识
    public final static String GO_CHARGE = "go_charge";//跳转收费单
    public final static String GO_OA = "go_oa";//跳转oa申请
    public final static String GO_PAYMENT = "go_payment";//跳转二维码支付

    //建档标识
    public final static String B_SS = "1";//搜索建档
    public final static String B_DS = "2";//电商渠道
    public final static String B_SJ = "3";//社交渠道
    public final static String B_XXHZ = "4";//线下合作
    public final static String B_CMMT = "5";//传媒媒体
    public final static String B_LDX = "7";//老带新
    public final static String B_YGTJ = "8";//员工推荐

    //优惠券类型
    public final static String DC_ZK = "0";//折扣
    public final static String DC_MJ = "1";//满减
    public final static String DC_ZS = "2";//赠送
    public final static String DC_XX = "3";//线下签呈

    //回调界面
    public final static int RESULT_DC = 200;//优惠券返回购物车


}
