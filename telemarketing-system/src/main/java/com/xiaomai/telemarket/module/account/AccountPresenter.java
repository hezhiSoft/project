package com.xiaomai.telemarket.module.account;

import android.app.Activity;
import android.app.Dialog;

import com.google.gson.Gson;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.account.data.AccountRemetoRepo;
import com.xiaomai.telemarket.module.account.data.UserInfoEntity;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午8:26$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class AccountPresenter implements AccountContract.Presenter {

    private Activity activity;

    public AccountPresenter(Activity activity){
        this.activity=activity;
    }

    @Override
    public void login(final String account, String password, final AccountContract.LoginView loginView) {
        final Dialog dialog= DialogFactory.createLoadingDialog(activity,"登录...");
        AccountRemetoRepo.getInstance().login(account, password, new RemetoRepoCallback<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity data) {
                if (data!=null){
                    DataApplication.getInstance().setUserInfoEntity(data);
                    ISharedPreferencesUtils.setValue(activity, Constant.USERINFO_KEY,new Gson().toJson(data));
                }
                loginView.onLoginSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtil.showToast(activity,msg);
            }

            @Override
            public void onThrowable(Throwable t) {
                ToastUtil.showToast(activity,"登录失败");
            }

            @Override
            public void onUnauthorized() {
                ToastUtil.showToast(activity,"登录失败");
            }

            @Override
            public void onFinish() {
                DialogFactory.dimissDialog(dialog);
            }
        });
    }
}
