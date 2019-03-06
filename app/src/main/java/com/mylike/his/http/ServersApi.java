package com.mylike.his.http;

import com.mylike.his.entity.BaseEntity;
import com.mylike.his.entity.BaseNewEntity;
import com.mylike.his.entity.BasePageEntity;
import com.mylike.his.entity.BinLiJiLuBean;
import com.mylike.his.entity.BinLiJiLuResp;
import com.mylike.his.entity.BookbuildingEntity;
import com.mylike.his.entity.ChargeDateilsEntity;
import com.mylike.his.entity.ChargeEntity;
import com.mylike.his.entity.ChargeFiltrateEntity;
import com.mylike.his.entity.ChargeInfoEntity;
import com.mylike.his.entity.ChargeUserInfoEntity;
import com.mylike.his.entity.ClientEntity;
import com.mylike.his.entity.ConsumeDDEntity;
import com.mylike.his.entity.CreatorEntity;
import com.mylike.his.entity.CustomerListBean;
import com.mylike.his.entity.CustomerTreatRecordListBean;
import com.mylike.his.entity.DaiZhenResp;
import com.mylike.his.entity.DepCountEntity;
import com.mylike.his.entity.DepartmentDEntity;
import com.mylike.his.entity.DepartmentDoctorEntity;
import com.mylike.his.entity.DepartmentEntity;
import com.mylike.his.entity.DiscountCouponEntity;
import com.mylike.his.entity.DoctorAdviceDetailResp;
import com.mylike.his.entity.DoctorAdviceListBean;
import com.mylike.his.entity.DoctorDetailsEntity;
import com.mylike.his.entity.DoctorEntity;
import com.mylike.his.entity.FenZhenInfoResp;
import com.mylike.his.entity.HDepositEntity;
import com.mylike.his.entity.HospitalAppointmentEntity;
import com.mylike.his.entity.InHospitalDetailResp;
import com.mylike.his.entity.InHospitalSortResp;
import com.mylike.his.entity.InspectRecordListBean;
import com.mylike.his.entity.IntentionEntity;
import com.mylike.his.entity.LoginResp;
import com.mylike.his.entity.MenZhenChuFangJiLuBean;
import com.mylike.his.entity.MenZhenChuFangJiLuDetailResp;
import com.mylike.his.entity.MenZhenTreatDengJiDetailBean;
import com.mylike.his.entity.MenZhenZhiLiaoDengJiBean;
import com.mylike.his.entity.MessageEntity;
import com.mylike.his.entity.MessageItemListBean;
import com.mylike.his.entity.MessageTypeEntity;
import com.mylike.his.entity.MyBookingDetailResp;
import com.mylike.his.entity.MyBookingListResp;
import com.mylike.his.entity.MyInHospitalCostResp;
import com.mylike.his.entity.MyInHospitalDetailResp;
import com.mylike.his.entity.MyInHostpitalListResp;
import com.mylike.his.entity.OperationMyArrangementListResp;
import com.mylike.his.entity.OperationMyBookingDetailResp;
import com.mylike.his.entity.OperationMyBookingListResp;
import com.mylike.his.entity.OperationMySchedulingDetailResp;
import com.mylike.his.entity.OperationMySchedulingListResp;
import com.mylike.his.entity.OperationPhotoBean;
import com.mylike.his.entity.OperationPrePareResp;
import com.mylike.his.entity.OperationProcessResp;
import com.mylike.his.entity.PackageEntity;
import com.mylike.his.entity.ProductChildrenEntity;
import com.mylike.his.entity.ProductDetailsEntity;
import com.mylike.his.entity.ProductEntity;
import com.mylike.his.entity.ProductsThreeLevelEntity;
import com.mylike.his.entity.ProjectDetailListBean;
import com.mylike.his.entity.ReceptionEntity;
import com.mylike.his.entity.ReceptionNotEntity;
import com.mylike.his.entity.ReceptionTypeEntity;
import com.mylike.his.entity.ReferrerEntity;
import com.mylike.his.entity.SVProjectEntity;
import com.mylike.his.entity.StatisticsEntity;
import com.mylike.his.entity.TokenEntity;
import com.mylike.his.entity.TriageInfoEntity;
import com.mylike.his.entity.UserIntentionEntity;
import com.mylike.his.entity.VersionsEntity;
import com.mylike.his.entity.VisitEntity;
import com.mylike.his.entity.VisitInfoEntity;
import com.mylike.his.entity.VisitTypeEntity;
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
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

    //更新APP
    @Headers({"Domain-Name: update"})
    //测试
    @POST("http://uat8280.mylikesh.cn/app-version/version/check_version")
    //试运行
//    @POST("http://crmapp.shmylike.cn/app-version/version/check_version")
    Call<VersionsEntity> updataApp();

    //验证api
    @POST("mylike-crm/api/user/ping.do")
    Call<Map<String, String>> pingAPI();

    //首页统计数据
    @POST("api/user/findStatisticData.do")
    Call<BaseEntity<StatisticsEntity>> getStatisticsData();

    //首页统计报表
    @POST("api/report/report.do")
    Call<BaseEntity<Map<String, String>>> getStatistics();

    //所有产品
    @POST("api/product/findType.do")
    Call<BaseEntity<ProductsThreeLevelEntity>> getProductAll(@Body RequestBody body);

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

    //消息筛选
    @POST("api/message/getMsgType.do")
    Call<BaseEntity<MessageTypeEntity>> getMessageType();

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
    @POST("api/outbound/findVisitList.do")
    Call<BaseEntity<BasePageEntity<VisitEntity>>> getVisitList(@Body RequestBody body);

    //回访列表详情
    @POST("api/outbound/findVisitDetail.do")
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


    //获取搜索建档的所有可选信息
    @POST("api/dd/getDicMult.do")
    Call<BaseEntity<Map<String, List<BookbuildingEntity>>>> getDicMult(@Body RequestBody body);

    //保存建档
    @POST("api/channel/saveTempCust.do")
    Call<BaseEntity<Object>> saveBookbuilding(@Body RequestBody body);

    //获取客户科室及该科室下分配过的医生
    @POST("api/bill/getDoctorAndDepartmentByCust.do")
    Call<BaseEntity<List<ConsumeDDEntity>>> getDepartmentAndDoctor(@Body RequestBody body);

    //建档人
    @POST("api/channel/getBuildByOptions.do")
    Call<BaseEntity<CreatorEntity>> getCreator();

    //推荐人
    @POST("api/channel/getUserInfoByPhoneOrNameLike.do")
    Call<BaseEntity<ReferrerEntity>> getReferrer(@Body RequestBody body);

    //接诊类型（筛选）
    @POST("api/bill/triagelistparam.do")
    Call<BaseEntity<List<ReceptionTypeEntity>>> getReceptionType();

    //客户筛选
    @POST("api/custom/getCustomSearchInfoData.do")
    Call<BaseEntity<ReceptionTypeEntity>> getClientType(@Body RequestBody body);

    //医生筛选
    @POST("api/doctor/getDoctorDetailScreen.do")
    Call<BaseEntity<List<ReceptionTypeEntity>>> getDoctorDetailScreen();

    //回访筛选
    @POST("api/outbound/getVisitType.do")
    Call<BaseEntity<VisitTypeEntity>> getVisitType();

    //查询套餐下的产品
    @POST("api/product/findPackageListProducts.do")
    Call<BaseEntity<List<PackageEntity>>> getPackageProduct(@Body RequestBody body);

    //获取所有科室所有医生
    @POST("api/product/findDepartmentDockers.do")
    Call<BaseEntity<DepartmentDEntity>> getDepartmentDoctor();

    //获取优惠券
    @POST("api/activity/activityCheck.do")
    Call<BaseEntity<List<DiscountCouponEntity>>> getDiscountCoupon(@Body RequestBody body);

    //获取某个优惠券
    @POST("api/activity/matchActiveProduct.do")
    Call<BaseEntity<DiscountCouponEntity>> getOneDiscountCoupon(@Body RequestBody body);

    //登录
    @POST("his-api/v1.0/app/login/{username}/{password}")
    Call<LoginResp> login(@Path("username") String username, @Path("password") String password);

    //登出
    @POST("his-api/v1.0/app/logout/{tokenId}")
    Call<BaseNewEntity> loginOut(@Path("tokenId") String tokenId);

    //修改密码
    @POST("his-api/v1.0/app/edit/{userId}/{oldPassword}/{newPassword}")
    Call<BaseNewEntity> modifyPwd(@Path("userId") String userId, @Path("oldPassword") String oldPassword,@Path("newPassword") String newPassword,@Query("token") String token);

    //今日人数总览接口
    @GET("his-api/v1.0/depCount/{tenantId}/{depId}/{userId}")
    Call<List<DepCountEntity>> todayPeopleViewCount(@Path("tenantId") String tenantId, @Path("depId") String depId, @Path("userId") String userId,@Query("token") String token);

    //待诊接口
    @GET("his-api/v1.0/mz/wait/{tenantId}/{depId}")
    Call<DaiZhenResp> getWoDeDaiZhenList(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                         @Query("userId") String userId,
                                         @Query("page") Integer page,
                                         @Query("rows") Integer rows,
                                         @Query("date") String date,
                                         @Query("status") int status, //接诊状态  1 ：待诊2：接诊
                                         @Query("triageTime") String triageTime,//分诊时间   desc:倒序  asc:正序
                                         @Query("waitingTime") String waitingTime,//等待时长  desc:倒序  asc:正序
                                         @Query("searchName") String searchName,//查询条件
                                         @Query("token") String token
    );

    //分诊信息
    @GET("his-api/v1.0/mz/triage/{tenantId}/{registId}")
    Call<FenZhenInfoResp> getFenZhenInfo(@Path("tenantId") String tenantId, @Path("registId") String registId,@Query("token") String token);

    //病历记录
    @GET("his-api/v1.0/mz/emr/{tenantId}/{depId}")
    Call<List<BinLiJiLuBean>> getBinLiJiLu(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                           @Query("registId") String registId, @Query("cusId") String cusId,@Query("token") String token);

    //治疗登记
    @GET("his-api/v1.0/mz/treat/{tenantId}/{depId}")
    Call<List<MenZhenZhiLiaoDengJiBean>> getZhiLiaoDengJi(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                                          @Query("registId") String registId, @Query("cusId") String cusId,@Query("token") String token);

    //治疗详情
    @GET("his-api/v1.0/mz/treat/dtl/{tenantId}/{treatId}")
    Call<List<MenZhenTreatDengJiDetailBean>> getZhiLiaoDengJiDetail(@Path("tenantId") String tenantId, @Path("treatId") String treatId,@Query("token") String token);

    //处方记录
    @GET("his-api/v1.0/mz/pres/{tenantId}/{depId}")
    Call<List<MenZhenChuFangJiLuBean>> getChuFangJiLu(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                                        @Query("registId") String registId, @Query("cusId") String cusId,@Query("token") String token);

    //门诊模块 处方详情
    @GET("his-api/v1.0/mz/pres/dtl/{tenantId}/{chuFangId}")
    Call<MenZhenChuFangJiLuDetailResp> getChuFangJiLuDetail(@Path("tenantId") String tenantId, @Path("chuFangId") String chuFangId,@Query("token") String token);


    //门诊模块 我的预约
    @GET("his-api/v1.0/mz/reserve/{tenantId}/{depId}")
    Call<MyBookingListResp> getMyBookingList(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                             @Query("userId") String userId,
                                             @Query("page") Integer page,
                                             @Query("rows") Integer rows,
                                             @Query("date") String date,
                                             @Query("status") String status, //接诊状态  1 ：待诊2：接诊
                                             @Query("searchName") String searchName,//查询条件
                                             @Query("token") String token
    );

    //门诊模块 预约详情
    @GET("his-api/v1.0/mz/maa/dtl/{tenantId}/{bookId}")
    Call<MyBookingDetailResp> getMyBookDetail(@Path("tenantId") String tenantId, @Path("bookId") String bookId,@Query("token") String token);


    //手术模块 我的预约
    @GET("his-api/v1.0/opera/reserve/{tenantId}/{depId}")
    Call<OperationMyBookingListResp> getOperationMyBookList(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                                            @Query("userId") String userId,
                                                            @Query("page") Integer page,
                                                            @Query("rows") Integer rows,
                                                            @Query("date") String date,
                                                            @Query("paymentCode") String paymentCode, //筛选下拉
                                                            @Query("searchName") String searchName,//查询条件
                                                            @Query("creater") String creater//预约人
                                                            ,@Query("token") String token
    );



    // 手术模块 预约详情
    @GET("his-api/v1.0/oper/surgMaa/dtl/{tenantId}/{bookId}")
    Call<OperationMyBookingDetailResp> getOperationMyBookDetail(@Path("tenantId") String tenantId, @Path("bookId") String bookId,@Query("token") String token);

    //手术模块 我的排期
    @GET("his-api/v1.0/opera/scheduling/{tenantId}/{depId}")
    Call<OperationMySchedulingListResp> getOperationMySchedulingList(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                                                     @Query("userId") String userId,
                                                                     @Query("page") Integer page,
                                                                     @Query("rows") Integer rows,
                                                                     @Query("date") String date,
                                                                     @Query("searchName") String searchName, //查询条件
                                                                     @Query("code") String code,//麻醉方式
                                                                     @Query("token") String token
    );

    // 手术模块 我的排期详情 手术详情
    @GET("his-api/v1.0/oper/operative/dtl/{tenantId}/{operationId}")
    Call<OperationMySchedulingDetailResp> getOperationSchedulingDetail(@Path("tenantId") String tenantId, @Path("operationId") String operationId,@Query("token") String token);

    // 手术模块 我的排期详情 检查记录
    @GET("his-api/v1.0/jcjy/inspect/record/{tenantId}")
    Call<List<InspectRecordListBean>> getInspectRecordList(@Path("tenantId") String tenantId, @Query("registId") String registId, @Query("cusId") String cusId,@Query("token") String token);


    // 手术模块 我的排期详情 项目明细
    @GET("his-api/v1.0/oper/project/dtl/{tenantId}/{registId}")
    Call<List<ProjectDetailListBean>> getProjectDetailList(@Path("tenantId") String tenantId, @Path("registId") String registId,@Query("token") String token);


    // 手术模块 术前准备
    @GET("his-api/v1.0/oper/prepare/{tenantId}/{registId}")
    Call<OperationPrePareResp> getOperationPrePare(@Path("tenantId") String tenantId, @Path("registId") String registId,@Query("token") String token);


    //手术模块 我的排台
    @GET("his-api/v1.0/opera/operating/{tenantId}/{depId}")
    Call<OperationMyArrangementListResp> getOperationMyArrangementList(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                                                       @Query("userId") String userId,
                                                                       @Query("date") String date,
                                                                       @Query("page") Integer page,
                                                                       @Query("rows") Integer rows,
                                                                       @Query("state") int state,
                                                                       @Query("searchName") String searchName, //查询条件
                                                                       @Query("anesthesia") String anesthesia,//麻醉方式
                                                                       @Query("operaRoom") String operaRoom,//手术室,
                                                                       @Query("token") String token
    );

    // 手术模块 手术进程
    @GET("his-api/v1.0/oper/process/{tenantId}/{registId}")
    Call<OperationProcessResp> getOperationProcess(@Path("tenantId") String tenantId, @Path("registId") String registId,@Query("token") String token);

    // 手术模块 医嘱
    @GET("his-api/v1.0/zy/order/{tenantId}/{registId}")
    Call<List<DoctorAdviceListBean>> getDoctorAdviceList(@Path("tenantId") String tenantId, @Path("registId") String registId,@Query("token") String token);

    // 手术模块 医嘱详细
    @GET("his-api/v1.0/zy/order/exec/{tenantId}/{registId}")
    Call<DoctorAdviceDetailResp> getDoctorAdviceDetail(@Path("tenantId") String tenantId, @Path("registId") String registId,@Query("token") String token);

    // 手术模块 客户照片
    @GET("his-api/v1.0/photo/{tenantId}/{patientId}/{registId}")
    Call<List<OperationPhotoBean>> getCustomerPhotoList(@Path("tenantId") String tenantId, @Path("patientId") String patientId, @Path("registId") String registId,@Query("token") String token);


    //手术模块 我的排台
    @GET("his-api/v1.0/zy/in/{tenantId}/{depId}")
    Call<MyInHostpitalListResp> getMyInHospital(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                                @Query("userId") String userId,
                                                @Query("status") int status,
                                                @Query("page") Integer page,
                                                @Query("rows") Integer rows,
                                                @Query("searchName") String searchName, //查询条件
                                                @Query("code") String code,//护理级别
                                                @Query("bednoId") String bednoId,//床位排序
                                                @Query("inhosptimeId") String inhosptimeId,//入院日期排序,
                                                @Query("token") String token
    );

    // 住院 住院详情
    @GET("his-api/v1.0/zy/in/dtl/{tenantId}/{registId}")
    Call<MyInHospitalDetailResp> getMyInHospitalDetail(@Path("tenantId") String tenantId, @Path("registId") String registId,@Query("token") String token);

    // 住院 住院费用
    @GET("his-api/v1.0/zy/bill/{tenantId}/{registId}")
    Call<MyInHospitalCostResp> getInHospitalCost(@Path("tenantId") String tenantId, @Path("registId") String registId,
                                                 @Query("page") Integer page,
                                                 @Query("rows") Integer rows,
                                                 @Query("token") String token
    );

    // 住院 住院费用详情
    @GET("his-api/v1.0/zy/hospitalization/expensesDetails/{tenantId}/{zysn}")
    Call<InHospitalDetailResp> getInHospitalCostDetail(@Path("tenantId") String tenantId, @Path("zysn") String zysn,
                                                       @Query("page") Integer page,
                                                       @Query("rows") Integer rows,
                                                       @Query("token") String token
    );

    // 客户
    @GET("his-api/v1.0/cus/{tenantId}/{depId}/{doctorId}")
    Call<List<CustomerListBean>> getCustomerList(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                                 @Path("doctorId") String doctorId,
                                                 @Query("page") Integer page,
                                                 @Query("rows") Integer rows,
                                                 @Query("token") String token
    );

    // 就诊记录
    @GET("his-api/v1.0/cus/record/{tenantId}/{depId}")
    Call<List<CustomerTreatRecordListBean>> getCustomerTreatRecordList(@Path("tenantId") String tenantId, @Path("depId") String depId,
                                                                       @Query("cusId") String cusId,
                                                            @Query("page") Integer page,
                                                            @Query("rows") Integer rows,
                                                                       @Query("token") String token
    );

    //	消息读取
    @GET("his-api/v1.0/msg/{tenantId}/{depId}")
    Call<List<MessageItemListBean>> getMessageList(@Path("tenantId") String tenantId,
                                                               @Query("empId") String empId,
                                                               @Query("page") Integer page,
                                                               @Query("rows") Integer rows,
                                                   @Query("token") String token
    );

    //	在院筛选和下拉
    @GET("his-api/v1.0/zy/patidiagrec")
    Call<InHospitalSortResp> getInHospitalSort(@Query("token") String token);

    //	待诊排序接口
    @GET("his-api/v1.0/mz/regist/dropdown")
    Call<InHospitalSortResp> getMyWaitingRegistSort(@Query("token") String token);

    //	我的预约筛选
    @GET("his-api/v1.0/mz/reserve/dropdown")
    Call<InHospitalSortResp> getMyBookSort(@Query("token") String token);

    //手术	我的预约筛选
    @GET("his-api/v1.0/oper/sopOperReserve/dropdown")
    Call<InHospitalSortResp> getOperationMyBookSort(@Query("token") String token);

    //手术	排期筛选
    @GET("his-api/v1.0/oper/scheduling/dropdown")
    Call<InHospitalSortResp> getScheduingSort(@Query("token") String token);


}

