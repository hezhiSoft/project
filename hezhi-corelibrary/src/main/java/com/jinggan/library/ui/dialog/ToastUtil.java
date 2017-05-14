package com.jinggan.library.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工具类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:13
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class ToastUtil {

    private static final String TAG = "IToastUtils";

    public static void showToast(Context context, int resId) {
        if (context == null || ((Activity) context).isFinishing()) {
            return;
        }
        showToast(context, context.getString(resId));
    }

    public static void showToast(Context context, String msg) {
        Toast mToast = null;
        try {
            if (context == null) {
                return;
            }
            if (mToast == null) {
                synchronized (ToastUtil.class) {
                    if (mToast == null) {
                        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(msg);
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    }
                }
            } else {
                mToast.setText(msg);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
