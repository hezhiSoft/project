package com.xiaomai.telemarket.api;

import com.xiaomai.telemarket.module.account.data.UserInfoEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
     */
    @GET("api/user/login?username={account}&password={password}")
    Call<Responese<UserInfoEntity>> login(@Query("account") String account, @Path("password") String password);
}
