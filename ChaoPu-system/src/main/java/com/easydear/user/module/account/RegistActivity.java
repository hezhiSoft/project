package com.easydear.user.module.account;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.account.data.UserInfoEntity;
import com.easydear.user.module.account.data.source.AccountRepo;
import com.easydear.user.module.home.MainActivity;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.widget.ResetEditView;
import com.jinggan.library.utils.ISharedPreferencesUtils;
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
public class RegistActivity extends BaseActivity implements RemetoRepoCallbackV2<UserInfoEntity>{

    @BindView(R.id.Regist_Phone)
    ResetEditView RegistPhone;
    @BindView(R.id.Regist_Pass)
    ResetEditView RegistPass;
    @BindView(R.id.validateCode_EditText)
    EditText validateCode;
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
                if (TextUtils.isEmpty(RegistPhone.getText())){
                    showToast("手机号码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(validateCode.getText())){
                    showToast("验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(RegistPass.getText())){
                    showToast("密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(RegistPassComfirn.getText())){
                    showToast("确认密码不能为空");
                    return;
                }

                AccountRepo.getInstance().regist(RegistPhone.getText().toString(),RegistPass.getText().toString(),validateCode.getText().toString(),this);
                break;
        }
    }

    private Dialog dialog;

    @Override
    public void onReqStart() {
        dialog= DialogFactory.createLoadingDialog(this,"注册...");
    }

    @Override
    public void onSuccess(UserInfoEntity data) {
        DataApplication.getInstance().setUserInfoEntity(data);
        ISharedPreferencesUtils.setValue(this, Constant.ACCOUNT_KEY, RegistPhone.getText());
        ISharedPreferencesUtils.setValue(this, Constant.PASSWORD_KEY, RegistPass.getText());
        ISkipActivityUtil.startIntent(this,MainActivity.class);
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }
}
