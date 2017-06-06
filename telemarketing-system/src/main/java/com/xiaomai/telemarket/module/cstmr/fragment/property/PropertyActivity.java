package com.xiaomai.telemarket.module.cstmr.fragment.property;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.module.cstmr.data.PropertyEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class PropertyActivity extends XiaoMaiBaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        try {
            if (getIntent().getExtras().containsKey("entity")) {
                switchToEditCarFragment();
            } else {
                switchToAddCartoFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
            switchToAddCartoFragment();
        }
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
        EventBusValues busValues=new EventBusValues();
        busValues.setWhat(0x1002);
        EventBus.getDefault().post(busValues);
    }

    private void switchToEditCarFragment() {
        setToolbarTitle("房产明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        PropertyEditFragment fragment=new PropertyEditFragment();
        fragment.setArguments(getIntent().getExtras());

        transaction.replace(R.id.Car_Content_Layout, fragment);
        transaction.commit();
    }

    private void switchToAddCartoFragment() {
        setToolbarTitle("添加房产明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Car_Content_Layout, new PropertyAddFragment());
        transaction.commit();
    }

    public static void startIntentToEdit(Activity activity, PropertyEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        ISkipActivityUtil.startIntent(activity, PropertyActivity.class, bundle);
    }

    public static void startIntentToAdd(Activity activity) {
        ISkipActivityUtil.startIntent(activity, PropertyActivity.class);
    }
}
