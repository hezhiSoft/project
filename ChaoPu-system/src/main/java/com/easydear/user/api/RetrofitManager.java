package com.easydear.user.api;

import com.easydear.user.DataApplication;
import com.easydear.user.common.Constant;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.common.retrofit
 * @Description:
 * @date 2016/11/29 19:59
 */

public class RetrofitManager {

    private static RetrofitManager instance;

    private static Retrofit mRetrofit;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }
        return instance;
    }

    /**
     * 返回Service
     *
     * @return
     */
    public APIService getService() {
//        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl("http://www.chaopoo.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
//        }
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
                        .addHeader("token", ISharedPreferencesUtils.getValue(DataApplication.getInstance(), Constant.TOKEN_KEN,"").toString())
                        .build();
                return chain.proceed(request);
            }
        }).build();
        return client;
    }
}
