package com.jinggan.library.base.handler;

import android.app.Activity;
import android.app.Service;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * handler基类，防止handler引用Activity,造成内存溢出
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:14
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class WaytoHandler extends Handler {
    WeakReference<Activity> weakReference;
    public WeakReference<Service> mW;

    public WaytoHandler(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    public WaytoHandler(Service service) {
        mW = new WeakReference<>(service);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (weakReference != null && weakReference.get() == null) {
            throw new NullPointerException();
        }
        if (mW != null && mW.get() == null) {
            throw new NullPointerException();
        }
    }
}
