package com.mylike.his.http;

import com.mylike.his.entity.BaseEntity;
import com.mylike.his.entity.BasePageEntity;
import com.mylike.his.entity.BookbuildingEntity;
import com.mylike.his.entity.ChargeDateilsEntity;
import com.mylike.his.entity.ChargeEntity;
import com.mylike.his.entity.ChargeFiltrateEntity;
import com.mylike.his.entity.ChargeInfoEntity;
import com.mylike.his.entity.ChargeUserInfoEntity;
import com.mylike.his.entity.ClientEntity;
import com.mylike.his.entity.DepartmentDoctorEntity;
import com.mylike.his.entity.DepartmentEntity;
import com.mylike.his.entity.DoctorDetailsEntity;
import com.mylike.his.entity.DoctorEntity;
import com.mylike.his.entity.HDepositEntity;
import com.mylike.his.entity.HospitalAppointmentEntity;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.entity.MessageEntity;
import com.mylike.his.entity.ProductChildrenEntity;
import com.mylike.his.entity.ProductDetailsEntity;
import com.mylike.his.entity.ProductEntity;
import com.mylike.his.entity.ProductsThreeLevelEntity;
import com.mylike.his.entity.ReceptionEntity;
import com.mylike.his.entity.ReceptionNotEntity;
import com.mylike.his.entity.SVProjectEntity;
import com.mylike.his.entity.StatisticsEntity;
import com.mylike.his.entity.TokenEntity;
import com.mylike.his.entity.TriageInfoEntity;
import com.mylike.his.entity.UserIntentionEntity;
import com.mylike.his.entity.VisitEntity;
import com.mylike.his.entity.VisitInfoEntity;
import com.mylike.his.fragment.consultant.TaskFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by zhengluping on 2018/6/4.
 */
public interface ServersApi {

    //登录
    @POST("api/user/login.do")
    Call<BaseEntity<TokenEntity>> getLongin(@Body RequestBody body);

    //登出
    @POST("api/user/logout.do")
    Call<Map<String, String>> exitLongin();

    //验证api
    @POST("api/user/ping.do")
    Call<Map<String, String>> pingAPI();

    //首页统计数据
    @POST("api/user/findStatisticData.do")
    Call<BaseEntity<StatisticsEntity>> getStatisticsData();

    //首页统计报表
    @POST("api/report/report.do")
    Call<BaseEntity<Map<String, String>>> getStatistics();

    //所有产品
    @POST("api/product/findType.do")
    Call<BaseEntity<ProductsThreeLevelEntity>> getProductAll();

    //产品下集合
    @POST("api/product/findProduct.do")
    Call<BaseEntity<List<ProductDetailsEntity>>> getFindProduct(@Body RequestBody body);

    //搜索产品集合
    @POST("api/product/findAllProduct.do")
    Call<BaseEntity<List<ProductEntity>>> getSearchProduct(@Body RequestBody body);

    //收费单
    @POST("api/bill/searchChargeBillList.do")
    Call<BaseEntity<BasePageEntity<ChargeInfoEntity>>> getChargeList(@Body RequestBody body);

    //收费单详情
    @POST("api/bill/getConsumeResv.do")
    Call<BaseEntity<ChargeDateilsEntity>> getChargeDetaills(@Body RequestBody body);

    //未接诊
    @POST("api/receive/getWaitReceive.do")
    Call<BaseEntity<List<ReceptionNotEntity>>> getNotReception(@Body RequestBody body);

    //已接诊
    @POST("api/bill/triagelist.do")
    Call<BaseEntity<ReceptionEntity>> getHasReception(@Body RequestBody body);

    //消费判断是否有暂存
    @POST("api/bill/billingLoading.do")
    Call<BaseEntity<Map<String, String>>> getSave(@Body RequestBody body);

    //客户列表
    @POST("api/custom/getCustomList.do")
    Call<BaseEntity<List<ClientEntity>>> getClientList(@Body RequestBody body);

    //住院押金余额
    @POST("api/bill/getHospitalDepositBalance.do")
    Call<BaseEntity<Map<String, Double>>> getBalance(@Body RequestBody body);

    //住院押金提交
    @POST("api/bill/createHospitalDepositResv.do")
    Call<BaseEntity<HDepositEntity>> setHospitalDeposit(@Body RequestBody body);

    //储值项目
    @POST("api/account/selectCtFinAccount.do")
    Call<BaseEntity<List<SVProjectEntity>>> getSVProject();

    //储值类型
    @POST("api/account/selectCtFinAccountByAcc.do")
    Call<BaseEntity<List<SVProjectEntity>>> getSVType(@Body RequestBody body);

    //储值提交
    @POST("api/bill/createPreStoreResv.do")
    Call<BaseEntity<HDepositEntity>> setSV(@Body RequestBody body);

    //医生列表
    @POST("api/doctor/searchDoctor.do")
    Call<BaseEntity<List<DoctorEntity>>> getDoctorList(@Body RequestBody body);

    //医生状态
    @POST("api/user/doctorState.do")
    Call<BaseEntity<List<Map<String, String>>>> getDoctorStatus();

    //医生详情列表/手术查询列表
    @POST("api/doctor/getDoctorDetail.do")
    Call<BaseEntity<DoctorDetailsEntity>> getDoctorDetauksList(@Body RequestBody body);

    //到院预约
    @POST("api/hospital/getAppointmentCustomList.do")
    Call<BaseEntity<HospitalAppointmentEntity>> getHospitalAppointmentList(@Body RequestBody body);

    //消息列表
    @POST("api/message/getMessageList.do")
    Call<BaseEntity<BasePageEntity<MessageEntity>>> getMessageList(@Body RequestBody body);

    //当天消息列表
    @POST("api/message/getTodayMessage.do")
    Call<BaseEntity<List<MessageEntity>>> getIntradayMessageList();

    //未读消息数
    @POST("api/message/getIfHaveNoReadMessage.do")
    Call<BaseEntity<Map<String, String>>> getMessageSum();

    //设置已读消息
    @POST("api/message/setMessageReadState.do")
    Call<BaseEntity<Map<String, String>>> setMessageReadState(@Body RequestBody body);

    //删除消息
    @POST("api/message/deleteMessage.do")
    Call<BaseEntity<Map<String, String>>> deleteMessage(@Body RequestBody body);

    //回访列表
    @POST("api/user/findVisitList.do")
    Call<BaseEntity<BasePageEntity<VisitEntity>>> getVisitList(@Body RequestBody body);

    //回访列表详情
    @POST("api/user/findVisitDetail.do")
    Call<BaseEntity<VisitInfoEntity>> getVisitListDetails(@Body RequestBody body);

    //获取所有意向
    @POST("api/product/findProductType.do")
    Call<BaseEntity<List<IntentionEntity>>> getIntentionAll();

    //提交重咨数据
    @POST("api/bill/consultation.do")
    Call<BaseEntity<Map<String, String>>> setIntention(@Body RequestBody body);

    //获取科室列表
    @POST("api/hospital/getHospitalDepartmentList.do")
    Call<BaseEntity<List<DepartmentEntity>>> getDepartmentList();

    //根据客户开单，获取分诊id
    @POST("api/bill/openBillByCust.do")
    Call<BaseEntity<Map<String, String>>> getTriage(@Body RequestBody body);

    //获取医生列表
    @POST("api/doctor/getDeptDoctorList.do")
    Call<BaseEntity<List<DepartmentDoctorEntity>>> getDepartmentDoctorList(@Body RequestBody body);

    //提交跨科数据
    @POST("api/bill/interdisciplinary.do")
    Call<BaseEntity<Map<String, String>>> setMedicine(@Body RequestBody body);

    //收费单客户信息
    @POST("api/bill/chargeBilledit.do")
    Call<BaseEntity<ChargeUserInfoEntity>> getChargeUserInfo(@Body RequestBody body);

    //收费单刷新信息（获取医生）
    @POST("api/bill/chargeBillRefresh.do")
    Call<BaseEntity<List<TriageInfoEntity>>> getTriageInfo(@Body RequestBody body);

    //收费单客户意向
    @POST("api/product/getCustItem.do")
    Call<BaseEntity<List<UserIntentionEntity>>> getUserIntentionInfo(@Body RequestBody body);

    //订单填写添加意向
    @POST("api/custom/saveCustomItem.do")
    Call<BaseEntity<Map<String, String>>> addIntention(@Body RequestBody body);

    //保存订单
    @POST("api/bill/saveConsumeResv.do")
    Call<BaseEntity<Map<String, String>>> saveCharge(@Body RequestBody body);

    //提交订单
    @POST("api/bill/submitConsumeResv.do")
    Call<BaseEntity<Map<String, String>>> addCharge(@Body RequestBody body);

    //支付
    @POST("api/pay/paynative.do")
    Call<BaseEntity<Map<String, String>>> getPayment(@Body RequestBody body);

    //收费单筛选
    @POST("api/bill/searchParamList.do")
    Call<BaseEntity<List<ChargeFiltrateEntity>>> getChargeFiltrate();

    //搜索建档
    @POST("api/channel/checkPhone.do")
    Call<BaseEntity<Map<String, String>>> getSearchData(@Body RequestBody body);

    //搜索建档
    @POST("api/dd/getDicMult.do")
    Call<BaseEntity<Map<String, List<BookbuildingEntity>>>> getDicMult(@Body RequestBody body);

    //保存建档
    @POST("api/channel/saveTempCust.do")
    Call<BaseEntity<String>> saveBookbuilding(@Body RequestBody body);
}
