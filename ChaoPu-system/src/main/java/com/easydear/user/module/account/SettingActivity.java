package com.easydear.user.module.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.ChaoPuBaseActivity;
import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.account.data.UserInfoEntity;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.media.picture.SelectPictureActivity;
import com.jinggan.library.media.picture.data.PictureEntity;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.utils.IActivityManage;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.IUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/17$ 下午4:31$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class SettingActivity extends ChaoPuBaseActivity {

    @BindView(R.id.Setting_UserHead)
    ImageView SettingUserHead;
    @BindView(R.id.Setting_Phone_TextView)
    TextView SettingPhoneTextView;
    @BindView(R.id.Setting_Modify_Phone_Layout)
    RelativeLayout SettingModifyPhoneLayout;
    @BindView(R.id.Setting_Nick_textView)
    TextView SettingNickTextView;
    @BindView(R.id.Setting_Modify_nick_Layout)
    RelativeLayout SettingModifyNickLayout;
    @BindView(R.id.Setting_Modify_pwd_Layout)
    RelativeLayout SettingModifyPwdLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("设置");
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUI();
    }

    private void initUI() {
        UserInfoEntity entity = DataApplication.getInstance().getUserInfoEntity();
        if (entity != null) {
            /*商家Logo*/
            Glide.with(this).load(BuildConfig.DOMAIN + entity.getImagery())
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.mipmap.default_head_img)
                    .error(R.mipmap.default_head_img)
                    .into(new RoundedBitmapImageViewTarget(this, SettingUserHead));

            SettingNickTextView.setText(entity.getNickName());
            SettingPhoneTextView.setText(entity.getMobile());

        }
    }

    @OnClick({R.id.Setting_Modify_Heard_Layout, R.id.Setting_Modify_Phone_Layout, R.id.Setting_Modify_nick_Layout, R.id.Setting_Modify_pwd_Layout, R.id.Setting_Switch_Account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Setting_Modify_Heard_Layout:
                SelectPictureActivity.startIntent(this, 1);
                break;
            case R.id.Setting_Modify_Phone_Layout:
                break;
            case R.id.Setting_Modify_nick_Layout:
                ISkipActivityUtil.startIntent(this, UpdateNickActivity.class);
                break;
            case R.id.Setting_Modify_pwd_Layout:
                break;
            case R.id.Setting_Switch_Account:
                switchAccountDialog();
                break;
        }
    }

    private void switchAccountDialog() {
        DialogFactory.showMsgDialog(this, "切换账号", "确定切换当前账号?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISharedPreferencesUtils.setValue(SettingActivity.this, Constant.PASSWORD_KEY, "");
                IActivityManage.getInstance().exit();
                ;
                ISkipActivityUtil.startIntent(SettingActivity.this, LoginActivity.class);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SelectPictureActivity.SELECT_PICTURE_RESULT_CODE) {
            List<PictureEntity> lists = (List<PictureEntity>) data.getSerializableExtra("result");

            if (lists != null && lists.size() > 0) {
                  /*商家Logo*/
                Glide.with(this).load(IUtil.fitterUrl(lists.get(0).getUrl()))
                        .asBitmap()
                        .centerCrop()
                        .placeholder(R.mipmap.default_head_img)
                        .error(R.mipmap.default_head_img)
                        .into(new RoundedBitmapImageViewTarget(this, SettingUserHead));
            }
        }
    }
}
