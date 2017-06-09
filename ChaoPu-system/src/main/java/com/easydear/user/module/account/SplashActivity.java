package com.easydear.user.module.account;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.easydear.user.R;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.utils.ISkipActivityUtil;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:10
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSwipeEnabled(false);
        setToolbarVisibility(View.GONE);
        mHandler.sendEmptyMessageDelayed(0x101, 4 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case 0x101:
                ISkipActivityUtil.startIntent(this, RegistActivity.class);
                break;
        }
    }
}
