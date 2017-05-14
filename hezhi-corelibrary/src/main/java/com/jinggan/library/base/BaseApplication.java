package com.jinggan.library.base;

import android.app.Application;

/**
 * BaseApplication 定义一些公共初始化的实例
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/3/27 9:47
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
