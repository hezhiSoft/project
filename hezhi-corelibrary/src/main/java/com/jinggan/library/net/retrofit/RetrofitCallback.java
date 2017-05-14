package com.jinggan.library.net.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Retrofit回调接口统一处理
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/8 10:20
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public abstract class RetrofitCallback<T> implements Callback<T> {

    public abstract void onSuccess(T data);

    public abstract void onFailure(int code, String msg);

    public abstract void onThrowable(Throwable t);

    public abstract void onUnauthorized();

    public abstract void onFinish();

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() >= 200 && response.code() < 300) {
            onSuccess(response.body());
        } else if (response.code() ==401) {
            onUnauthorized();
        } else {
            onFailure(response.code(), response.errorBody().toString());
        }
        onFinish();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onThrowable(t);
        onFinish();
    }
}
