package com.easydear.user.module.account;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.api.data.UserInfoEntity;
import com.easydear.user.api.data.source.AccountRepo;
import com.easydear.user.common.Constant;
import com.easydear.user.module.home.MainActivity;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.widget.ResetEditView;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:15
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class LoginActivity extends BaseActivity implements RemetoRepoCallbackV2<UserInfoEntity> {


    @BindView(R.id.Login_Account)
    ResetEditView LoginAccount;
    @BindView(R.id.Login_Pwd)
    ResetEditView LoginPwd;

    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setToolbarTitle("登录");

        String account = ISharedPreferencesUtils.getValue(this, Constant.ACCOUNT_KEY, "").toString();
        String pwd = ISharedPreferencesUtils.getValue(this, Constant.PASSWORD_KEY, "").toString();
        if (!TextUtils.isEmpty(account)) {
            LoginAccount.setText(account);
        }
        if (!TextUtils.isEmpty(pwd)) {
            LoginPwd.setText(pwd);
        }
    }

    @Override
    public void onReqStart() {
        dialog = DialogFactory.createLoadingDialog(this, "登录...");
    }

    @Override
    public void onSuccess(UserInfoEntity data) {
        DataApplication.getInstance().setUserInfoEntity(data);
        ISharedPreferencesUtils.setValue(this, Constant.ACCOUNT_KEY, LoginAccount.getText());
        ISharedPreferencesUtils.setValue(this, Constant.PASSWORD_KEY, LoginPwd.getText());

        ISkipActivityUtil.startIntent(this, MainActivity.class);
        finish();
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }

    @OnClick({R.id.Login_ForgetPwd, R.id.Login_Button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login_ForgetPwd:
                break;
            case R.id.Login_Button:
                if (TextUtils.isEmpty(LoginAccount.getText())) {
                    showToast("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(LoginPwd.getText())) {
                    showToast("密码不能为空");
                    return;
                }
                AccountRepo.getInstance().login(LoginAccount.getText(), LoginPwd.getText(), this);
                break;
        }
    }
}
