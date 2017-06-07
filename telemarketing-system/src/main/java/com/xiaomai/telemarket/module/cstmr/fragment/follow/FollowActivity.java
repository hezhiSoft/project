package com.xiaomai.telemarket.module.cstmr.fragment.follow;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.jinggan.library.utils.ISystemUtil;
import com.jinggan.library.utils.PermissionHelper;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FollowActivity extends XiaoMaiBaseActivity {

    @BindView(R.id.FollowActivity_titleView)
    TextView mTitleTextView;
    @BindView(R.id.FollowActivity_phone_ImageView)
    ImageView mCallImageView;

    private Dialog dialog;

    private String tel;

    public static boolean isSetNextData=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        setToolbarVisibility(View.GONE);
        ButterKnife.bind(this);
        try {
            if (getIntent().getExtras().containsKey("entity")) {
                isSetNextData=false;
                switchToEditDebtoFragment();
            } else if (getIntent().getExtras().containsKey("customerid")) {
                isSetNextData=true;
                tel=getIntent().getStringExtra("tel");
                dialog = DialogFactory.createLoadingDialog(this, "查询...");
                CusrometRemoteRepo.getInstance().queryFollowDetails(getIntent().getExtras().getString("customerid"), new RemetoRepoCallback<List<FollowEntity>>() {
                    @Override
                    public void onSuccess(List<FollowEntity> data) {
                        if (data != null && data.size() > 0) {
                            mCallImageView.setVisibility(View.VISIBLE);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("entity", data.get(0));

                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            FollowEditFragment followEditFragment = new FollowEditFragment();
                            followEditFragment.setArguments(bundle);

                            transaction.replace(R.id.Follow_Content_Layout, followEditFragment);
                            transaction.commit();
                        } else {
                            switchToAddDebtoFragment();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        switchToAddDebtoFragment();
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        switchToAddDebtoFragment();
                    }

                    @Override
                    public void onUnauthorized() {
                        switchToAddDebtoFragment();
                    }

                    @Override
                    public void onFinish() {
                        DialogFactory.dimissDialog(dialog);
                    }
                });
            } else {
                isSetNextData=false;
                switchToAddDebtoFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
            switchToAddDebtoFragment();
        }
    }

    @OnClick({R.id.FollowActivity_Back_ImageView,R.id.FollowActivity_phone_ImageView, R.id.FollowActivity_Edit_ImageView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.FollowActivity_Back_ImageView:
                finish();
                break;
            case R.id.FollowActivity_phone_ImageView:
                if (PermissionHelper.checkPermission(this, Manifest.permission.CALL_PHONE, 0x998)) {
                    ISharedPreferencesUtils.setValue(this, Constant.NOT_SEND_DIALING_MSG, true);
                    ISystemUtil.makeCall(this, tel, true);
                }
                break;
            case R.id.FollowActivity_Edit_ImageView:
                EventBusValues busValues = new EventBusValues();
                busValues.setWhat(0x1007);
                EventBus.getDefault().post(busValues);
                break;
        }
    }

    private void switchToEditDebtoFragment() {
        mTitleTextView.setText("跟进明细");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        FollowEditFragment followEditFragment = new FollowEditFragment();
        followEditFragment.setArguments(getIntent().getExtras());

        transaction.replace(R.id.Follow_Content_Layout, followEditFragment);
        transaction.commit();
    }

    private void switchToAddDebtoFragment() {
        mTitleTextView.setText("添加跟进明细");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Follow_Content_Layout, new FollowAddFragment());
        transaction.commit();
    }

    public static void startIntentToEdit(Activity activity, FollowEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        ISkipActivityUtil.startIntent(activity, FollowActivity.class, bundle);
    }

    public static void startIntentToQuery(Activity activity,String tel, String customerid) {
        Bundle bundle = new Bundle();
        bundle.putString("customerid", customerid);
        bundle.putString("tel",tel);
        ISkipActivityUtil.startIntent(activity, FollowActivity.class, bundle);
    }

    public static void startIntentToAdd(Activity activity) {
        ISkipActivityUtil.startIntent(activity, FollowActivity.class);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x998) {
            ISharedPreferencesUtils.setValue(this, Constant.NOT_SEND_DIALING_MSG, true);
            ISystemUtil.makeCall(this, tel, true);
        }
    }
}
