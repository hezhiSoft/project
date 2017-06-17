package com.easydear.user.module.account;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.account.data.UserInfoEntity;
import com.easydear.user.module.account.data.source.AccountRepo;
import com.easydear.user.module.home.MainActivity;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:10
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SplashActivity extends BaseActivity implements RemetoRepoCallbackV2<UserInfoEntity>{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setSwipeEnabled(false);
        setToolbarVisibility(View.GONE);
        String account = ISharedPreferencesUtils.getValue(this, Constant.ACCOUNT_KEY, "").toString();
        String pwd = ISharedPreferencesUtils.getValue(this, Constant.PASSWORD_KEY, "").toString();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pwd)) {
            AccountRepo.getInstance().login(account,pwd,this);
        } else
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

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(UserInfoEntity data) {
        DataApplication.getInstance().setUserInfoEntity(data);
        ISkipActivityUtil.startIntent(this, MainActivity.class);
        finish();
    }

    @Override
    public void onFailure(int code, String msg) {
        ISkipActivityUtil.startIntent(this,RegistActivity.class);
    }

    @Override
    public void onFinish() {

    }
}
