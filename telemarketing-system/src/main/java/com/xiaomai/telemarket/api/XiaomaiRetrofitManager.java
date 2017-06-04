package com.xiaomai.telemarket.api;

import com.jinggan.library.net.retrofit.RetrofitManager;
import com.xiaomai.telemarket.BuildConfig;
import com.xiaomai.telemarket.DataApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 配制API请求
 * <p>
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 11:34
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class XiaomaiRetrofitManager {

    /**
     * 获取API
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 11:40
     */
    public static APIService getAPIService() {
        return RetrofitManager.getService(BuildConfig.DOMAIN, getClient(), APIService.class);
    }

    /**
     * 设置Http请求头
     * author: hezhiWu
     * created at 2017/3/16 9:23
     */
    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("tenantid", DataApplication.getInstance().getUserInfoEntity().getTenantID())
                        .addHeader("userid", DataApplication.getInstance().getUserInfoEntity().getUserID())
                        .addHeader("istenant",DataApplication.getInstance().getUserInfoEntity().getIsTenant()+"")
                        .build();
                return chain.proceed(request);
            }
        }).build();
        return client;
    }
}
