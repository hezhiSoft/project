package com.easydear.user.module.mine;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.easydear.user.module.cards.InterestsActivity;
import com.easydear.user.module.mine.data.source.MineRepo;
import com.easydear.user.module.order.OrderActivity;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.CommPopupWindow;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.ISystemUtil;
import com.jinggan.library.utils.PermissionHelper;

import org.json.JSONObject;

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

    private CommPopupWindow commPopupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, rootView);
        request();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
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
                ISkipActivityUtil.startIntent(getContext(), InterestsActivity.class);
                break;
            case R.id.MineFragment_businessNumber:
                ISkipActivityUtil.startIntent(getContext(), UserBusinessListActivity.class);
                break;
            case R.id.MineFragment_track:
                break;
            case R.id.MineFragment_order:
                ISkipActivityUtil.startIntent(getContext(), OrderActivity.class);
                break;
            case R.id.MineFragment_feedback:
                ISkipActivityUtil.startIntent(getContext(), FeedbackActivity.class);
                break;
            case R.id.MineFragment_constomer:
                if (PermissionHelper.checkPermission(getActivity(), Manifest.permission.CALL_PHONE, 0x990)) {
                    showConstomerDialog();
                }
                break;
            case R.id.MineFragment_AboutMe:
                ISkipActivityUtil.startIntent(getContext(), AboutActivity.class);
                break;
        }
    }


    private void request() {
        MineRepo.getInstance().getCardSize(new RemetoRepoCallbackV2<String>() {
            @Override
            public void onReqStart() {

            }

            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    MineFragmentConsumeCar.setText("我的权益   " + jsonObject.getString("CardSize"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });

        MineRepo.getInstance().getBussinessSize(new RemetoRepoCallbackV2<String>() {
            @Override
            public void onReqStart() {

            }

            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    MineFragmentBusinessNumber.setText("会员商家   " + jsonObject.getString("BusinessSize"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }


    private void showConstomerDialog() {
        View businessView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_tontact_layout, null);
        final TextView tell1 = (TextView) businessView.findViewById(R.id.dialog_tontact_tel1);
        final TextView tell2 = (TextView) businessView.findViewById(R.id.dialog_tontact_tel2);
        businessView.findViewById(R.id.dialog_tontact_calcel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commPopupWindow.dismiss();
            }
        });
        tell1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISystemUtil.makeCall(getActivity(), tell1.getText().toString(), true);
            }
        });
        tell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISystemUtil.makeCall(getActivity(), tell1.getText().toString(), true);
            }
        });
        commPopupWindow = new CommPopupWindow(businessView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        commPopupWindow.showAtButton(getView());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x990) {
            showConstomerDialog();
        }
    }
}
