package com.easydear.user.api;

import com.easydear.user.module.account.data.UserInfoEntity;
import com.easydear.user.module.business.data.BusinessDetailEntity;
import com.easydear.user.module.business.data.BusinessEntity;
import com.easydear.user.module.cards.data.CardEntity;
import com.easydear.user.module.cards.data.InterestsEntity;
import com.easydear.user.module.dynamic.data.DynamicDetailsEntity;
import com.easydear.user.module.dynamic.data.DynamicEntity;
import com.easydear.user.module.message.data.MessageDetailEntity;
import com.easydear.user.module.message.data.MessageItemEntity;
import com.easydear.user.module.order.data.OrderDetailsEntity;
import com.easydear.user.module.order.data.OrderEntity;

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
    @POST("neweasydear-app/rticleForward/addArticleForward")
    Call<ResponseEntity<String>> addArticleGood(@Query("articleId") int articleId);

    /**
     * 软文详情
     * <p>
     * author: hezhiWu
     * created at 2017/6/29 20:51
     */
    @POST("neweasydear-app/article/detailById")
    Call<ResponseEntity<DynamicDetailsEntity>> queryDynamic(@Query("articleId") int articleId);

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
    @POST("neweasydear-app/card/listByUserNo")
    Call<ResponseEntity<List<InterestsEntity>>> queryInterests(@Query("userNo") String userNo, @Query("pageSize") int pageSize, @Query("pageCount") int pageCount);

    /**
     * 获取卡卷数量
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 22:07
     */
    @GET("neweasydear-app/user/countByUserNo")
    Call<ResponseEntity<String>> getCardSize(@Query("userNo") String userNo);

    /**
     * 获取商家数量
     * <p>
     * author: hezhiWu
     * created at 2017/6/28 22:21
     */
    @GET("neweasydear-app/user/countBussinessByUserNo")
    Call<ResponseEntity<String>> getBussinessSize(@Query("userNo") String userNo);

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
}
