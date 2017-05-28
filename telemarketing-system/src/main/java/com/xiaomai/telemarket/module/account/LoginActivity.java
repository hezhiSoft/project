package com.xiaomai.telemarket.module.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.MainActivity;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.account.data.UserInfoEntity;
import com.xiaomai.telemarket.service.DownloadInitDataService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/14 12:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class LoginActivity extends BaseActivity implements AccountContract.LoginView {

    @BindView(R.id.login_account_EditText)
    EditText loginAccountEditText;
    @BindView(R.id.login_pwd_EditText)
    EditText loginPwdEditText;

    private AccountPresenter loginPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setToolbarVisibility(View.GONE);
        setSwipeEnabled(false);
        iniUI();
        loginPresenter = new AccountPresenter(this);
    }

    private void iniUI() {
        String account = ISharedPreferencesUtils.getValue(this, Constant.ACCOUNT_KEY, "").toString();
        String password = ISharedPreferencesUtils.getValue(this, Constant.PASSWORD_KEY, "").toString();
        if (!TextUtils.isEmpty(account)) {
            loginAccountEditText.setText(account);
        }
        if (!TextUtils.isEmpty(password)) {
            loginPwdEditText.setText(password);
        }
    }

    @OnClick({R.id.login_buttom, R.id.login_forget_pwd_TextView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_buttom:
                String account = loginAccountEditText.getText().toString();
                String password = loginPwdEditText.getText().toString();
                if (TextUtils.isEmpty(account)) {
                    showToast("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast("密码不能为空");
                    return;
                }
                loginPresenter.login(account, password, this);
                break;
            case R.id.login_forget_pwd_TextView:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onLoginSuccess(UserInfoEntity entity) {
        ISharedPreferencesUtils.setValue(this, Constant.ACCOUNT_KEY, loginAccountEditText.getText().toString());
        ISharedPreferencesUtils.setValue(this, Constant.PASSWORD_KEY, loginPwdEditText.getText().toString());
        ISharedPreferencesUtils.setValue(this, Constant.USER_STATE_KEY, Constant.UserState.INWORK.getValue());

        DownloadInitDataService.startService(getApplicationContext());
        ISkipActivityUtil.startIntent(this, MainActivity.class);
    }

    @Override
    public void onLoginFailure(int code, String msg) {
    }
}
