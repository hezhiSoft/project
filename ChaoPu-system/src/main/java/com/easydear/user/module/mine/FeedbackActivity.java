package com.easydear.user.module.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.easydear.user.R;
import com.easydear.user.module.dynamic.DynamicFragment;
import com.easydear.user.module.mine.data.source.MineRepo;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 意见反馈
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/7/3 20:41
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FeedbackActivity extends BaseActivity implements RemetoRepoCallbackV2<Void> {

    @BindView(R.id.Feedback_Content)
    TextView contentView;

    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setToolbarTitle("意见反馈");

        ButterKnife.bind(this);
    }

    @OnClick(R.id.Feedback_submit)
    public void onClick() {
        if (TextUtils.isEmpty(contentView.getText())) {
            showToast("反馈内容不能为空!");
            return;
        }

        MineRepo.getInstance().addFeedback(contentView.getText().toString(),this);
    }

    @Override
    public void onReqStart() {
        dialog = DialogFactory.createLoadingDialog(this, "提交...");
    }

    @Override
    public void onSuccess(Void data) {
        showToast("提交成功");
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
