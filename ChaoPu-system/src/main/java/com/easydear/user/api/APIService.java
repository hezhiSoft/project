package com.easydear.user.api;

import com.easydear.user.common.ResponseModel;
import com.easydear.user.module.account.data.UserInfoEntity;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.module.business.data.BusinessEntity;
import com.easydear.user.module.cards.data.CardEntity;
import com.easydear.user.module.cards.data.InterestDetailEntity;
import com.easydear.user.module.cards.data.InterestsEntity;
import com.easydear.user.module.dynamic.data.DynamicDetailsEntity;
import com.easydear.user.module.dynamic.data.DynamicEntity;
import com.easydear.user.module.location.data.LocationEntity;
import com.easydear.user.module.message.data.MessageDetailEntity;
import com.easydear.user.module.message.data.MessageItemEntity;
import com.easydear.user.module.order.data.OrderDetailsEntity;
import com.easydear.user.module.order.data.OrderEntity;
import com.easydear.user.module.search.data.SearchEntity;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * author:hezhiWu <wuhezhi007@gmail.com>
 * version:V1.0
 * created at 2017/6/16 下午3:14
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved
 */

public interface APIService {


    /**
     * 验证码
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 20:48
     */
    @POST("neweasydear-app/login/loginUser")
    Call<ResponseEntity<UserInfoEntity>> login(@Query("mobile") String mobile, @Query("password") String password);

    /**
     * 注册
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 21:29
     */
    @POST("neweasydear-app/register/insertUser")
    Call<ResponseEntity<UserInfoEntity>> regist(@Query("mobile") String mobile, @Query("password") String password, @Query("mobileCode") String mobileCode);

    /**
     * 发送验证码
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 20:48
     */
    @GET("neweasydear-app/user/sendMobile?mobile={mobile}")
    Call<ResponseEntity<String>> sendMobileValidateCode(@Part("mobile") String mobile);

    /**
     * 更新Nick
     * <p>
     * author: hezhiWu
     * created at 2017/7/4 17:08
     */
    @POST("neweasydear-app/user/updateNickName")
    Call<ResponseEntity<Void>> updateNick(@Query("nickName") String nickName);

    /**
     * 修改密码
     * <p>
     * author: hezhiWu
     * created at 2017/7/4 17:08
     */
    @POST("neweasydear-app/user/updatePassword")
    Call<ResponseEntity<Void>> modifyPassword(@Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

    /**
     * 修改头像
     * <p>
     * author: hezhiWu
     * created at 2017/7/6 15:21
     */
    @POST
    Call<ResponseEntity<Object>> updateHead(@Url String url);

    /**
     * 查询商家列表
     *
     * @param url
     * @return
     */
    @POST
    Call<ResponseEntity<List<BusinessEntity>>> queryBusiness(@Url String url);

    /**
     * 查询商家详情
     *
     * @param url
     * @return
     */
    @POST
    Call<ResponseEntity<BusinessDetailEntity>> queryBusinessDetail(@Url String url);

    /**
     * 申请成为会员
     * <p>
     * author: hezhiWu
     * created at 2017/7/5 23:11
     */
    @POST("userMember/insertUserMember")
    Call<ResponseEntity<Void>> addVip(@Query("businessNo") String businessNo);

    /**
     * 查询动态列表
     * <p>
     * author: hezhiWu
     * created at 2017/6/16 下午3:14
     */
    @POST
    Call<ResponseEntity<List<DynamicEntity>>> queryDynamics(@Url String url);

    /**
     * 点赞
     *
     * @param articleId
     * @return
     */
    @POST("neweasydear-app/articleGood/addArticleGood")
    Call<ResponseEntity<Object>> addArticleGood(@Query("articleId") int articleId);

    /**
     * 软文详情
     * <p>
     * author: hezhiWu
     * created at 2017/6/29 20:51
     */
    @POST("neweasydear-app/article/detailById")
    Call<ResponseEntity<DynamicDetailsEntity>> queryDynamic(@Query("articleId") int articleId);

    /**
     * 搜索热门
     * <p>
     * author: Colin
     * created at 2017/7/8 20:51
     */
    @GET("neweasydear-app/search/hotSearch")
    Call<ResponseEntity<List<SearchEntity>>> queryHotSearch();

    /**
     * 搜索关键字匹配
     * author: Colin
     * created at 2017/7/8 21:32
     */
    @POST("neweasydear-app/search/historySearchKey")
    Call<ResponseEntity<List<SearchEntity>>> queryKeyMatch(@Query("userNo") String userNo, @Query("key") String key);

    /**
     * 查询系统消息列表
     * author: Colin
     */
    @POST
    Call<ResponseEntity<List<MessageItemEntity>>> queryTuiMessages(@Url String url);

    /**
     * 查询商家消息列表
     * author: Colin
     */
    @POST
    Call<ResponseEntity<List<MessageItemEntity>>> queryBusMessages(@Url String url);

    /**
     * 查询单个商家的消息详情
     * author: Colin
     */
    @POST
    Call<ResponseEntity<List<MessageDetailEntity>>> queryMessageDetail(@Url String url);

    /**
     * 查询卡包列表
     * <p>
     * author: hezhiWu
     * created at 2017/6/16 下午8:31
     */
    @POST
    Call<ResponseEntity<List<CardEntity>>> queryCards(@Url String url);

    /**
     * 我的权益
     * <p>
     * author: hezhiWu
     * created at 2017/6/16 下午8:31
     */
    @POST("neweasydear-app/user/userCardList")
    Call<ResponseEntity<List<InterestsEntity>>> queryInterests(@Query("userNo") String userNo, @Query("pageSize") int pageSize, @Query("pageCount") int pageCount);

    /**
     * 权益详情
     * <p>
     * created at 2017/7/16 下午2:31
     */
    @POST("neweasydear-app/card/detailById")
    Call<ResponseEntity<InterestDetailEntity>> queryInterestDetail(@Query("cardNo") String cardNo);

    /**
     * 权益领用
     * <p>
     * created at 2017/7/16 下午4:52
     */
    @POST("neweasydear-app/user/receiveCard")
    Call<ResponseEntity<String>> receiveInterestCard(@Query("cardNo") String cardNo, @Query("businessNo") String businessNo);

    /**
     * 获取卡卷数量
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 22:07
     */
    @GET("neweasydear-app/user/countByUserNo")
    Call<ResponseEntity<Object>> getCardSize();

    /**
     * 获取商家数量
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 22:21
     */
    @GET("neweasydear-app/user/countBussinessByUserNo")
    Call<ResponseEntity<Object>> getBussinessSize();

    /**
     * 查询用户商家
     * <p>
     * author: hezhiWu
     * created at 2017/7/3 20:03
     */
    @GET("neweasydear-app/user/listUserBusiness")
    Call<ResponseEntity<List<BusinessEntity>>> queryUserBusiness();

    /**
     * 订单列表
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 22:21
     */
    @POST("neweasydear-app/user/listOrderByUserNo")
    Call<ResponseEntity<List<OrderEntity>>> queryOrders(@Query("userNo") String userNo, @Query("pageSize") int pageSize, @Query("pageCount") int pageCount);

    /**
     * 订单详情
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 22:21
     */
    @POST("neweasydear-app/user/detailOrder")
    Call<ResponseEntity<OrderDetailsEntity>> queryOrderDetails(@Query("orderNo") String billNo);

    /**
     * 获取城市列表
     */
    @GET("neweasydear-app/city/changeCity")
    Call<ResponseModel<List<LocationEntity>>> reqCity();

    /**
     * 获取区域列表
     */
    @GET("neweasydear-app/city/changeArea")
    Call<ResponseModel<List<LocationEntity>>> reqDistrict(@Query("code") String code);

    /**
     * 添加反馈
     * <p>
     * author: hezhiWu
     * created at 2017/7/3 20:49
     */
    @POST("neweasydear-app/feedback/addFeedback")
    Call<ResponseEntity<Void>> addFeedback(@Query("content") String content);
}
