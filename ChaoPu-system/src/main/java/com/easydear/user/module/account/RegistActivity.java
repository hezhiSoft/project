package com.easydear.user.module.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.module.home.MainActivity;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.ui.widget.ResetEditView;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.IStringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:14
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class RegistActivity extends BaseActivity {

    @BindView(R.id.Regist_Phone)
    ResetEditView RegistPhone;
    @BindView(R.id.Regist_Ver_Code)
    ResetEditView RegistVerCode;
    @BindView(R.id.Regist_Pass)
    ResetEditView RegistPass;
    @BindView(R.id.Regist_Pass_comfirn)
    ResetEditView RegistPassComfirn;
    @BindView(R.id.Regist_account)
    TextView RegistAccount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        setSwipeEnabled(false);
        setToolbarVisibility(View.GONE);
    }

    @OnClick({R.id.Regist_button, R.id.Regist_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Regist_account:
                ISkipActivityUtil.startIntent(this, LoginActivity.class);
                break;
            case R.id.Regist_button:
                ISkipActivityUtil.startIntent(this, MainActivity.class);
                finish();
                break;
        }
    }
}
