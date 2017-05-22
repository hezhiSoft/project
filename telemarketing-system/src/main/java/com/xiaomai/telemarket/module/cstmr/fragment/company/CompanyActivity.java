package com.xiaomai.telemarket.module.cstmr.fragment.company;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.CompanyEntity;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.DebtoAddFragment;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.DebtoEditFragment;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CompanyActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
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

    private void switchToEditDebtoFragment() {
        setToolbarTitle("公司明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Company_Content_Layout, new CompanyEditFragment());
        transaction.commit();
    }

    private void switchToAddDebtoFragment() {
        setToolbarTitle("添加公司明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Company_Content_Layout, new CompanyAddFragment());
        transaction.commit();
    }

    public static void startIntentToEdit(Activity activity, CompanyEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        ISkipActivityUtil.startIntent(activity, CompanyActivity.class, bundle);
    }

    public static void startIntentToAdd(Activity activity) {
        ISkipActivityUtil.startIntent(activity, CompanyActivity.class);
    }
}
