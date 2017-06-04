package com.xiaomai.telemarket.module.function.callOut;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;

import com.jinggan.library.base.BaseActivity;
import com.xiaomai.telemarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 员工外呼
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 下午12:23$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CallOutActivity extends BaseActivity {

    @BindView(R.id.dl_left)
    DrawerLayout drawerLayout;

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
