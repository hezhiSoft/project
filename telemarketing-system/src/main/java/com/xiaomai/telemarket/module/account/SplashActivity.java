package com.xiaomai.telemarket.module.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.MainActivity;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.account.data.AccountRemetoRepo;
import com.xiaomai.telemarket.module.account.data.UserInfoEntity;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/26 11:56
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class SplashActivity extends BaseActivity implements RemetoRepoCallback<UserInfoEntity>{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setToolbarVisibility(View.GONE);
        ISharedPreferencesUtils.setValue(DataApplication.getInstance().getApplicationContext(), Constant.IS_DIALING_GROUP_FINISHED, true);
        String account = ISharedPreferencesUtils.getValue(this, Constant.ACCOUNT_KEY, "").toString();
        String password = ISharedPreferencesUtils.getValue(this, Constant.PASSWORD_KEY, "").toString();
        boolean isLogin= (boolean) ISharedPreferencesUtils.getValue(this, Constant.ISLOGIN_KEY, false);
        if (!isLogin) {
            AccountRemetoRepo.getInstance().login(account, password, this);
        } else {
            ISkipActivityUtil.startIntent(this, LoginActivity.class);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onSuccess(UserInfoEntity data) {
        ISkipActivityUtil.startIntent(this, MainActivity.class);
    }

    @Override
    public void onFailure(int code, String msg) {
        ISkipActivityUtil.startIntent(this, LoginActivity.class);
    }

    @Override
    public void onThrowable(Throwable t) {
        ISkipActivityUtil.startIntent(this, LoginActivity.class);
    }

    @Override
    public void onUnauthorized() {
        ISkipActivityUtil.startIntent(this, LoginActivity.class);
    }

    @Override
    public void onFinish() {
    }
}
