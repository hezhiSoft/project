package com.xiaomai.telemarket.api;

import com.xiaomai.telemarket.module.account.data.UserInfoEntity;
import com.xiaomai.telemarket.module.cstmr.data.CarEntity;
import com.xiaomai.telemarket.module.cstmr.data.CompanyEntity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;
import com.xiaomai.telemarket.module.cstmr.data.PropertyEntity;
import com.xiaomai.telemarket.module.home.setting.data.UserStateEntity;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    Call<Responese<List<CusrometListEntity>>> cusrometLists(@Body RequestBody body);

    /**
     * 获取用户状态枚举
     *
     * @return
     */
    @GET("api/user/GetStateEnum")
    Call<Responese<List<UserStateEntity>>> userStateList();

//    /**
//     * 获取用户状态 TODO 接口未定义
//     * @return
//     */
//    @GET("")
//    Call<String> requestUserState();

    /**
     * 更新用户状态
     *
     * @param body {"status":1}
     * @return
     */
    @POST("api/user/SetState")
    Call<Responese<Void>> updateUserState(@Body RequestBody body);

    /**
     * 从公共名单库获取客户信息
     *
     * @return
     */
    @POST("api/customer/GetFromPublic")
    Call<Responese<List<CusrometListEntity>>> getCustomerInfoFromPublic();

    /**
     * 从私有名单库获取客户信息
     *
     * @param body {"preid":""}
     * @return
     */
    @POST("api/customer/GetFromPrivate")
    Call<Responese<List<CusrometListEntity>>> getCustomerInfoFromPublic(@Body RequestBody body);

    /**
     * 获取客户列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 8:51
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
     * 获取客户房产列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 8:59
     */
    @POST("api/customer/GetHouse")
    Call<Responese<List<PropertyEntity>>> queryCusrometHouse(@Body RequestBody body);

    /**
     * 获取客户保单列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 9:05
     */
    @POST("api/customer/GetInsurance")
    Call<Responese<List<InsuranceEntity>>> queryCusrometInsurance(@Body RequestBody body);


    /**
     * 查询客户公司信息列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 9:27
     */
    @POST("api/customer/GetCompany")
    Call<Responese<List<CompanyEntity>>> queryCusrometCompany(@Body RequestBody body);

    /**
     * 获取客户车辆信息
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 19:29
     */
    @POST("api/customer/GetCar")
    Call<Responese<List<CarEntity>>> queryCusrometCarLists(@Body RequestBody body);

    /**
     * 查询客户文件列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 22:35
     */
    @POST("api/file/get")
    Call<Responese<List<FileEntity>>> queryCusrometFileLists(@Body RequestBody body);

    /**
     * 查询客户跟进明细
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 22:34
     */
    @POST("api/customer/GetFollow")
    Call<Responese<List<FollowEntity>>> queryCusrometFollowLists(@Body RequestBody body);

}
