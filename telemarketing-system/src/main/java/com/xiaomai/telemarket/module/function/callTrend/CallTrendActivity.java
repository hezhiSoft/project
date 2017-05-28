package com.xiaomai.telemarket.module.function.callTrend;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jinggan.library.base.BaseActivity;
import com.xiaomai.telemarket.R;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 下午12:24$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CallTrendActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_trend);
        setToolbarTitle("外呼走势");
    }
}
