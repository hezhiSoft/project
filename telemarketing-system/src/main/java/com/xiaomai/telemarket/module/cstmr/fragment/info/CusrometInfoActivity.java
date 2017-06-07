package com.xiaomai.telemarket.module.cstmr.fragment.info;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 下午2:02$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CusrometInfoActivity extends XiaoMaiBaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setToolbarRightText("保存");
        try {
            if (getIntent().getExtras().containsKey("entity")){
                switchToEdit();
            }else {
                switchToAdd();
            }
        }catch (Exception e){
            e.printStackTrace();
            switchToAdd();
        }
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
        EventBusValues busValues=new EventBusValues();
        busValues.setWhat(0x10010);
        EventBus.getDefault().post(busValues);
    }

    private void switchToAdd() {
        setToolbarTitle("添加客户资料");
        setToolbarRightText("保存");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Cusromet_info, new CusrometInfoAddFragment());
        transaction.commitAllowingStateLoss();
    }

    private void switchToEdit(){
        setToolbarTitle("编辑客户资料");
        setToolbarRightText("保存");
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        CusrometInfoEditFragment fragment=new CusrometInfoEditFragment();
        fragment.setArguments(getIntent().getExtras());
        transaction.replace(R.id.Cusromet_info, fragment);
        transaction.commitAllowingStateLoss();
    }

    public static void startIntentToAdd(Activity activity){
        ISkipActivityUtil.startIntent(activity,CusrometInfoActivity.class);
    }

    public static void startIntentToEdit(Activity activity, CusrometListEntity entity){
        Bundle bundle=new Bundle();
        bundle.putSerializable("entity",entity);
        ISkipActivityUtil.startIntent(activity,CusrometInfoActivity.class,bundle);
    }
}
