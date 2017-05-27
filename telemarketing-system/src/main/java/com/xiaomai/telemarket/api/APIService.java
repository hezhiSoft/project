package com.xiaomai.telemarket.api;

import com.xiaomai.telemarket.module.account.data.UserInfoEntity;
import com.xiaomai.telemarket.module.cstmr.data.CarEntity;
import com.xiaomai.telemarket.module.cstmr.data.CompanyEntity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.data.DictionaryEntity;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;
import com.xiaomai.telemarket.module.cstmr.data.FiltersEntity;
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
     * 获取过滤每件
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 19:11
     */
    @POST("api/customer/GetFilters")
    Call<Responese<List<FiltersEntity>>> getFilters();

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
     * 编辑客户负债信息
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 23:15
     */
    @POST("api/customer/EditDebt")
    Call<Responese> editDebto(@Body DebtoEntity entity);

    /**
     * 添加客户负债信息
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 23:45
     */
    @POST("api/customer/AddDebt")
    Call<Responese> addDebto(@Body DebtoEntity entity);

    /**
     * 获取客户房产列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 8:59
     */
    @POST("api/customer/GetHouse")
    Call<Responese<List<PropertyEntity>>> queryCusrometHouse(@Body RequestBody body);

    /**
     * 编辑房产
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 0:09
     */
    @POST("api/customer/EditHouse")
    Call<Responese> editHouse(@Body PropertyEntity entity);

    /**
     * 新增房产
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 18:15
     */
    @POST("api/customer/AddHouse")
    Call<Responese> addHouse(@Body PropertyEntity entity);

    /**
     * 获取客户保单列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 9:05
     */
    @POST("api/customer/GetInsurance")
    Call<Responese<List<InsuranceEntity>>> queryCusrometInsurance(@Body RequestBody body);

    /**
     * 编辑
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 10:29
     */
    @POST("api/customer/EditInsurance")
    Call<Responese> editInsurance(@Body InsuranceEntity entity);

    @POST("api/customer/AddInsurance")
    Call<Responese> addInsurance(@Body InsuranceEntity entity);

    /**
     * 查询客户公司信息列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 9:27
     */
    @POST("api/customer/GetCompany")
    Call<Responese<List<CompanyEntity>>> queryCusrometCompany(@Body RequestBody body);

    /**
     * 编辑公司
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 10:29
     */
    @POST("api/customer/EditCompany")
    Call<Responese> editCompany(@Body CompanyEntity entity);

    /**
     * 添加公司
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 18:41
     */
    @POST("api/customer/AddCompany")
    Call<Responese> addCompany(@Body CompanyEntity entity);

    /**
     * 获取客户车辆信息
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 19:29
     */
    @POST("api/customer/GetCar")
    Call<Responese<List<CarEntity>>> queryCusrometCarLists(@Body RequestBody body);

    /**
     * 编辑房产
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 0:09
     */
    @POST("api/customer/EditCar")
    Call<Responese> editCar(@Body CarEntity entity);

    /**
     * 添加添加
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 13:55
     */
    @POST("api/customer/AddCar")
    Call<Responese> addCar(@Body CarEntity entity);

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

    /**
     * 编辑公司
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 10:29
     */
    @POST("api/customer/EditFollow")
    Call<Responese> editFollow(@Body FollowEntity entity);

    /**
     * 添加跟进
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 18:43
     */
    @POST("api/customer/AddFollow")
    Call<Responese> addFollow(@Body FollowEntity entity);

    /**
     * 查询字典
     * <p>
     * author: hezhiWu
     * created at 2017/5/26 10:05
     */
    @POST("api/user/GetDataEnum")
    Call<Responese<List<DictionaryEntity>>> queryDictionary(@Body RequestBody body);
}
