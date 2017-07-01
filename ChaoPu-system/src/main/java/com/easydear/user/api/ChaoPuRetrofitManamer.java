package com.easydear.user.api;

import com.easydear.user.DataApplication;
import com.easydear.user.common.Constant;
import com.jinggan.library.net.retrofit.RetrofitManager;
import com.jinggan.library.net.retrofit.StringConverterFactory;
import com.jinggan.library.utils.ISharedPreferencesUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2017-06-11
 * Time: 01:51
 * Version:1.0
 */

public class ChaoPuRetrofitManamer {

    private static Retrofit mRetrofit;

    /**
     * 获取API
     * <p>
     * author: hezhiWu
     * created at 2017/5/20 11:40
     */
    public static APIService getAPIService() {
        return getService();
    }

    /**
     * 返回字符串
     * <p>
     * author: hezhiWu
     * created at 2017/6/29 23:10
     */
    public static APIService getServiceString() {
        mRetrofit = new Retrofit.Builder()
                .client(getClient())
                .baseUrl("http://www.chaopoo.com/")
                .addConverterFactory(StringConverterFactory.create())
                .build();
        APIService service = mRetrofit.create(APIService.class);
        return service;
    }


    /**
     * 返回Service
     *
     * @return
     */
    public static APIService getService() {
        mRetrofit = new Retrofit.Builder()
                .client(getClient())
                .baseUrl("http://www.chaopoo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = mRetrofit.create(APIService.class);
        return service;
    }

    /**
     * 设置Client请求头
     *
     * @return
     */
    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("token", ISharedPreferencesUtils.getValue(DataApplication.getInstance(), Constant.TOKEN_KEN, "").toString())
                        .build();
                return chain.proceed(request);
            }
        }).build();
        return client;
    }
}
