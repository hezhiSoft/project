package com.easydear.user.module.account;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.account.data.source.AccountRepo;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.widget.ResetEditView;
import com.jinggan.library.utils.ISharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/7/16 13:08
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class ModifyPasswordActivity extends ChaoPuBaseActivity implements RemetoRepoCallbackV2<Void> {

    @BindView(R.id.Modify_pwd_old)
    ResetEditView ModifyPwdOld;
    @BindView(R.id.Modify_pwd_new)
    ResetEditView ModifyPwdNew;
    @BindView(R.id.Modify_pwd_confirm)
    ResetEditView ModifyPwdConfirm;

    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("密码修改");
        setContentView(R.layout.activity_modify_pas);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.Modify_pwd_submit)
    public void onClick() {

        if (TextUtils.isEmpty(ModifyPwdOld.getText().toString())) {
            showToast("输入原密码");
            return;
        }

        if (TextUtils.isEmpty(ModifyPwdNew.getText().toString())) {
            showToast("输入新密码");
            return;
        }

        if (!ModifyPwdOld.getText().toString().equals(ISharedPreferencesUtils.getValue(this, Constant.PASSWORD_KEY, "").toString())) {
            showToast("输入正确的原密码");
            return;
        }

        if (!ModifyPwdNew.getText().toString().equals(ModifyPwdConfirm.getText().toString())) {
            showToast("新密码跟确认密码不一样");
            return;
        }

        AccountRepo.getInstance().modifyPassword(ModifyPwdOld.getText().toString(),ModifyPwdNew.getText().toString(),this);
    }

    @Override
    public void onReqStart() {
        dialog = DialogFactory.createLoadingDialog(this, "提交...");
    }

    @Override
    public void onSuccess(Void data) {
        showToast("修改成功");
        ISharedPreferencesUtils.setValue(this, Constant.PASSWORD_KEY, ModifyPwdConfirm.getText().toString());

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
}
