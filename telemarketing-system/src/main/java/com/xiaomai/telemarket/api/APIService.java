package com.xiaomai.telemarket.api;

import com.xiaomai.telemarket.module.account.data.UserInfoEntity;
import com.xiaomai.telemarket.module.cstmr.data.CarEntity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/15 21:17
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public interface APIService {

    /**
     * 登录
     * <p>
     * author: hezhiWu
     * created at 2017/5/15 21:18
     *
     * @param body
     */
    @POST("api/user/login")
    Call<Responese<UserInfoEntity>> login(@Body RequestBody body);

    /**
     * 获取客户列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/17 21:21
     *
     * @param body
     */
    @POST("api/customer/GetByFilter")
    Call<Responese<List<CusrometListEntity>>> queryCusrometLists(@Body RequestBody body);

    /**
     * 获取客户负债列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 16:24
     */
    @POST("api/customer/GetDebt")
    Call<Responese<List<DebtoEntity>>> queryCusrometDebtoLists(@Body RequestBody body);

    /**
     * 获取客户车辆信息
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 19:29
     */
    @POST("api/customer/GetCar")
    Call<Responese<List<CarEntity>>> queryCusrometCarLists(@Body RequestBody body);
}
