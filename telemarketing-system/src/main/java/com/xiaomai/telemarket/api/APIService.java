package com.xiaomai.telemarket.api;

import com.xiaomai.telemarket.appCheck.data.VersionEntity;
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
import com.xiaomai.telemarket.module.function.data.CallOutDepStaticsEntity;
import com.xiaomai.telemarket.module.function.data.CallOutStaticsEntity;
import com.xiaomai.telemarket.module.home.setting.data.UserStateEntity;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<Responese<List<CusrometListEntity>>> cusrometLists(@Body RequestBody body);

    /**
     * 获取用户状态枚举
     *
     * @return
     */
    @GET("api/user/GetStateEnum")
    Call<Responese<List<UserStateEntity>>> getUserStateList();

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
    Call<Responese<List<CusrometListEntity>>> getCustomerInfoFromPrivate(@Body RequestBody body);

    /**
     * 清除拨号记录
     *
     * @param body {"customerid":""}
     * @return
     */
    @POST("api/customer/DelFromList")
    Call<Responese<Void>> DelFromList(@Body RequestBody body);

    /**
     * 设置客户号码为空号
     * @param body
     * @return
     */
    @POST("api/customer/SetEmptyTel")
    Call<Responese<Void>> SetEmptyTel(@Body RequestBody body);

    /**
     * 员工外呼统计
     *
     * @param body {"FromDate":"2017-1-1","ToDate":"2017-6-10"}
     * @return
     */
    @POST("api/statics/byuser")
    Call<Responese<List<CallOutStaticsEntity>>> queryStaticsByUser(@Body RequestBody body);

    /**
     * 外呼走势统计
     *
     * @param body {"DeptId":"252D62CE-63D4-4E9B-8328-A722011CA3F7","Year":2017,"Type":"call"}
     * @return
     */
    @POST("api/statics/bymonth")
    Call<Responese<List<CallOutDepStaticsEntity>>> queryStaticsByMonth(@Body RequestBody body);

    /**
     * 获取过滤每件
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 19:11
     */
    @POST("api/customer/GetFilters")
    Call<Responese<List<FiltersEntity>>> getFilters();

    /**
     * 添加用户
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 19:11
     */
    @POST("api/customer/AddCustomer")
    Call<Responese<CusrometListEntity>> addCusromet(@Body CusrometListEntity entity);

    /**
     * 添加用户
     * <p>
     * author: hezhiWu
     * created at 2017/5/27 19:11
     */
    @POST("api/customer/EditCustomer")
    Call<Responese<CusrometListEntity>> editCusromet(@Body CusrometListEntity entity);

    /**
     * 获取客户列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 8:51
     */
    @POST("api/customer/GetByFilter")
    Call<Responese<List<CusrometListEntity>>> queryCusrometLists(@Body RequestBody body);

    /**
     * 获取跟进列表
     * <p>
     * author: hezhiWu
     * created at 2017/5/28 下午12:45
     */
    @POST("api/customer/FollowingCustomers")
    Call<Responese<List<CusrometListEntity>>> queryStayFollow(@Body RequestBody body);

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
     * 添加文件
     * <p>
     * author: hezhiWu
     * created at 2017/5/31 16:19
     */
    @Multipart
    @POST
    Call<Responese<FileEntity>> addFile(@Url String url, @Part() List<MultipartBody.Part> parts);

    /**
     * 编辑文件信息
     * <p>
     * author: hezhiWu
     * created at 2017/5/31 16:20
     */
    @Multipart
    @POST
    Call<Responese<FileEntity>> editFile(@Url String url, @Part() List<MultipartBody.Part> parts);

    /**
     * 查询客户跟进明细
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 22:34
     */
    @POST("api/customer/GetFollow")
    Call<Responese<List<FollowEntity>>> queryCusrometFollowLists(@Body RequestBody body);

    /**
     * 查询客户跟进明细
     * <p>
     * author: hezhiWu
     * created at 2017/5/22 22:34
     */
    @POST("api/customer/FollowingDetail")
    Call<Responese<List<FollowEntity>>> queryCusrometFollowDetails(@Body RequestBody body);

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

    /**
     * 版本检测
     * <p>
     * author: hezhiWu
     * created at 2017/5/31 18:04
     */
    @POST("api/user/Version")
    Call<Responese<List<VersionEntity>>> checkAppVersion();

    /**
     * 设置客户电话号码为空
     * <p>
     * author: hezhiWu
     * created at 2017/6/3 18:18
     */
    @POST("api/customer/SetEmptyTel")
    Call<Responese<Void>> setEmptyTel(@Body RequestBody body);

    /**
     * 设置有意向状态
     * <p> {"customerid":"5BE01E0A-3417-4F7B-A757-0BDE2F975E6A","interested":1}
     * author: hezhiWu
     * created at 2017/6/3 18:18
     */
    @POST("api/customer/SetInterested")
    Call<Responese<Void>> setInterested(@Body RequestBody body);

    /**
     * 设置租户过期
     * <p>
     * author: hezhiWu
     * created at 2017/6/3 18:18
     */
    @POST("api/user/InValid")
    Call<Responese<Void>> setRentInValid();


}
