package com.easydear.user.module.dynamic;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.R;
import com.easydear.user.module.dynamic.data.DynamicDetailsEntity;
import com.easydear.user.module.dynamic.data.soruce.DynamicRepo;
import com.easydear.user.module.order.data.source.OrderRepo;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/22 20:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DynamicDetailsActivity extends BaseActivity implements RemetoRepoCallbackV2<DynamicDetailsEntity> {

    @BindView(R.id.Dynamic_bg)
    ImageView DynamicBg;
    @BindView(R.id.Dynamic_Title)
    TextView DynamicTitle;
    @BindView(R.id.Dynamic_Business_logo)
    ImageView DynamicBusinessLogo;
    @BindView(R.id.Dynamic_Business_Name)
    TextView DynamicBusinessName;
    @BindView(R.id.Dynamic_Content_WebView)
    WebView webView;
    @BindView(R.id.Dynamic_support)
    TextView supportTextView;
    @BindView(R.id.Dynamic_relay)
    TextView relayTextView;

    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        setToolbarTitle("潮铺正文");
        ButterKnife.bind(this);

        setToolbarRightImage(R.mipmap.icon_shared);

        DynamicRepo.getInstance().queryDynamic(getIntent().getIntExtra("id", -1), this);
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
    }

    @Override
    public void onReqStart() {
        dialog = DialogFactory.createLoadingDialog(this, "查询...");
    }

    @Override
    public void onSuccess(DynamicDetailsEntity data) {
        if (data != null) {
            Glide.with(this).load(BuildConfig.DOMAIN + data.getArticleImage()).into(DynamicBg);
             /*商家Logo*/
            Glide.with(this).load(BuildConfig.DOMAIN + data.getLogo())
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.mipmap.default_head_img)
                    .error(R.mipmap.default_head_img)
                    .into(new RoundedBitmapImageViewTarget(this, DynamicBusinessLogo));

            DynamicTitle.setText(data.getTitle());
            DynamicBusinessName.setText(data.getBusinessName());
            relayTextView.setText(data.getArticleForward() + "");
            supportTextView.setText(data.getArticleGood() + "");
            webView.loadDataWithBaseURL("about:blank", data.getContent(), "text/html", "utf-8", null);
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
        finish();
    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }

    @OnClick({R.id.Dynamic_support, R.id.Dynamic_relay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Dynamic_support:
                OrderRepo.getInstance().addArticleGood(getIntent().getIntExtra("id", -1), new RemetoRepoCallbackV2<String>() {
                    @Override
                    public void onReqStart() {
                        dialog=DialogFactory.createLoadingDialog(DynamicDetailsActivity.this,"点赞...");
                    }

                    @Override
                    public void onSuccess(String data) {
                        supportTextView.setText(data);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void onFinish() {
                        DialogFactory.dimissDialog(dialog);
                    }
                });
                break;
            case R.id.Dynamic_relay:
                break;
        }
    }
}
