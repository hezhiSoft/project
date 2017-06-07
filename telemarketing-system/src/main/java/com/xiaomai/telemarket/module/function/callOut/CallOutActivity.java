package com.xiaomai.telemarket.module.function.callOut;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;

import butterknife.ButterKnife;

/**
 * 员工外呼
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 下午12:23$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CallOutActivity extends XiaoMaiBaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_out);
        setToolbarRightImage(R.mipmap.icon_screen);
        setToolbarTitle("外呼统计");
        ButterKnife.bind(this);
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
    }
}
