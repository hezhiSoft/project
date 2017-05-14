package com.jinggan.library.net.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求框架Retrofi管理框架
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/17 15:01
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class RetrofitManager {
    /**
     * 返回APIService
     * author: hezhiWu
     * created at 2017/3/16 9:23
     *
     * @param domain 域名
     * @param cls    API位类
     */
    public static <T> T getService(String domain, Class<T> cls) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        T service = mRetrofit.create(cls);
        return service;
    }

    /**
     * 返回APIService ，返回String
     * author: hezhiWu
     * created at 2017/4/19 10:47
     *
     * @param domain 域名
     * @param cls    API位类
     */
    public static <T> T getServiceStr(String domain, Class<T> cls) {
        Retrofit mRetrofit = new Retrofit.Builder()
//                .client(getClient())
                .baseUrl(domain)
                .addConverterFactory(StringConverterFactory.create())
                .build();
        T service = mRetrofit.create(cls);
        return service;
    }

    /**
     * 设置Http请求头
     * author: hezhiWu
     * created at 2017/3/16 9:23
     */
//    private static OkHttpClient getClient() {
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .addHeader("Authorization", "Credential " + Userhelper.getCredentialId())
//                        .addHeader("Accept", "application/json")
//                        .build();
//                return chain.proceed(request);
//            }
//        }).build();
//        return client;
//    }
}
