package com.easydear.user.module.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.module.account.SettingActivity;
import com.easydear.user.module.account.data.UserInfoEntity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.utils.ISkipActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:56
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.MineFragment_UserHear_ImageView)
    ImageView MineFragmentUserHearImageView;
    @BindView(R.id.MineFragment_userName)
    TextView MineFragmentUserName;
    @BindView(R.id.MineFragment_sex)
    ImageView MineFragmentSex;
    @BindView(R.id.MineFragment_location)
    TextView MineFragmentLocation;
    @BindView(R.id.MineFragment_consumeCar)
    TextView MineFragmentConsumeCar;
    @BindView(R.id.MineFragment_businessNumber)
    TextView MineFragmentBusinessNumber;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initUI() {
        UserInfoEntity entity = DataApplication.getInstance().getUserInfoEntity();
        if (entity != null) {
            /*商家Logo*/
            Glide.with(getContext()).load(BuildConfig.DOMAIN + entity.getImagery())
                    .asBitmap()
                    .centerCrop()
                    .placeholder(R.mipmap.default_head_img)
                    .error(R.mipmap.default_head_img)
                    .into(new RoundedBitmapImageViewTarget(getContext(), MineFragmentUserHearImageView));

            MineFragmentUserName.setText(entity.getNickName());


        }
    }

    @OnClick({R.id.MineFragment_set, R.id.MineFragment_consumeCar, R.id.MineFragment_businessNumber, R.id.MineFragment_track, R.id.MineFragment_order, R.id.MineFragment_feedback, R.id.MineFragment_constomer, R.id.MineFragment_AboutMe})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.MineFragment_set:
                ISkipActivityUtil.startIntent(getContext(), SettingActivity.class);
                break;
            case R.id.MineFragment_consumeCar:
                break;
            case R.id.MineFragment_businessNumber:
                break;
            case R.id.MineFragment_track:
                break;
            case R.id.MineFragment_order:
                break;
            case R.id.MineFragment_feedback:
                break;
            case R.id.MineFragment_constomer:
                break;
            case R.id.MineFragment_AboutMe:
                break;
        }
    }
}
