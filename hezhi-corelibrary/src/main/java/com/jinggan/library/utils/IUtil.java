package com.jinggan.library.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

/**
 * 常用工具类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class IUtil {

    /**
     * Url过滤
     *
     * @param url
     * @return
     */
    public static String fitterUrl(String url) {
        if (TextUtils.isEmpty(url)){
            return url;
        }
        if (!url.contains("http://")) {
            url = "file://" + url;
        }
        return url;
    }

    /**
     *获取资源String
     * 
     *author: hezhiWu
     *created at 2017/3/17 13:55
     */
    public static String getStrToRes(Context context, int resId) {
        return context.getString(resId);
    }

    /**
     * CBOK +
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if(reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    /**
     * CBOK +
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void switchFragment(@NonNull FragmentManager fragmentManager,
                                      @NonNull Fragment fragment, int frameId) {

    }
}
