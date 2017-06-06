package com.xiaomai.telemarket.module.cstmr.fragment.insurance;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class InsuranceActivity extends XiaoMaiBaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        try {
            if (getIntent().getExtras().containsKey("entity")) {
                switchToEditDebtoFragment();
            } else {
                switchToAddDebtoFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
            switchToAddDebtoFragment();
        }
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
        EventBusValues busValues=new EventBusValues();
        busValues.setWhat(0x1003);
        EventBus.getDefault().post(busValues);
    }

    private void switchToEditDebtoFragment() {
        setToolbarTitle("保单明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        InsuranceEditFragment fragment=new InsuranceEditFragment();
        fragment.setArguments(getIntent().getExtras());

        transaction.replace(R.id.Insurance_Content_Layout, fragment);
        transaction.commit();
    }

    private void switchToAddDebtoFragment() {
        setToolbarTitle("添加保单明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Insurance_Content_Layout, new InsuranceAddFragment());
        transaction.commit();
    }

    public static void startIntentToEdit(Activity activity, InsuranceEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        ISkipActivityUtil.startIntent(activity, InsuranceActivity.class, bundle);
    }

    public static void startIntentToAdd(Activity activity) {
        ISkipActivityUtil.startIntent(activity, InsuranceActivity.class);
    }
}
