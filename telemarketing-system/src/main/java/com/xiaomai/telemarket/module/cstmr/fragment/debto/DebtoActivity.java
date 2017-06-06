package com.xiaomai.telemarket.module.cstmr.fragment.debto;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DebtoActivity extends XiaoMaiBaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debto);
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
        busValues.setWhat(0x1001);
        EventBus.getDefault().post(busValues);
    }

    private void switchToEditDebtoFragment() {
        setToolbarTitle("负债明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DebtoEditFragment debtoEditFragment = new DebtoEditFragment();
        debtoEditFragment.setArguments(getIntent().getExtras());
        transaction.replace(R.id.Debto_Content_Layout, debtoEditFragment);
        transaction.commit();
    }

    private void switchToAddDebtoFragment() {
        setToolbarTitle("添加负债明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Debto_Content_Layout, new DebtoAddFragment());
        transaction.commit();
    }

    public static void startIntentToEdit(Activity activity, DebtoEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        ISkipActivityUtil.startIntent(activity, DebtoActivity.class, bundle);
    }

    public static void startIntentToAdd(Activity activity) {
        ISkipActivityUtil.startIntent(activity, DebtoActivity.class);
    }
}
