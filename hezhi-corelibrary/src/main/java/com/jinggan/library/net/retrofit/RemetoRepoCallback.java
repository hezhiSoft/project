package com.jinggan.library.net.retrofit;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/8 17:46
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public interface RemetoRepoCallback<T> {

      void onSuccess(T data);

      void onFailure(int code, String msg);

      void onThrowable(Throwable t);

      void onUnauthorized();

      void onFinish();
}
