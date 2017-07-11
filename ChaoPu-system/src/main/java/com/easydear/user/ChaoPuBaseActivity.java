package com.easydear.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jinggan.library.base.BaseActivity;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/7/11 10:17
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class ChaoPuBaseActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTextColor(Color.parseColor("#666666"));
    }
}
