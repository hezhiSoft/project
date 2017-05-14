package com.xiaomai.telemarket.module.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.MainActivity;
import com.xiaomai.telemarket.R;

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
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_account_EditText)
    EditText loginAccountEditText;
    @BindView(R.id.login_pwd_EditText)
    EditText loginPwdEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setToolbarVisibility(View.GONE);
        setSwipeEnabled(false);
    }

    @OnClick({R.id.login_buttom, R.id.login_forget_pwd_TextView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_buttom:
                ISkipActivityUtil.startIntent(this, MainActivity.class);
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
}
