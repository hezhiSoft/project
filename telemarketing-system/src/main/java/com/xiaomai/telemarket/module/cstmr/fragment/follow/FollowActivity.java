package com.xiaomai.telemarket.module.cstmr.fragment.follow;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FollowActivity extends BaseActivity{

    private Dialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        try {
            if (getIntent().getExtras().containsKey("entity")) {
                switchToEditDebtoFragment();
            }else if (getIntent().getExtras().containsKey("customerid")){
                dialog=DialogFactory.createLoadingDialog(this,"查询...");
                CusrometRemoteRepo.getInstance().queryFollowDetails(getIntent().getExtras().getString("customerid"), new RemetoRepoCallback<List<FollowEntity>>() {
                    @Override
                    public void onSuccess(List<FollowEntity> data) {
                        if (data!=null&&data.size()>0){
                            setToolbarRightText("保存");
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("entity",data.get(0));

                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            FollowEditFragment followEditFragment=new FollowEditFragment();
                            followEditFragment.setArguments(bundle);

                            transaction.replace(R.id.Follow_Content_Layout, followEditFragment);
                            transaction.commit();
                        }else {
                            showToast("数据为空");
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        showToast(msg);
                        finish();
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        showToast("数据异常");
                        finish();
                    }

                    @Override
                    public void onUnauthorized() {

                    }

                    @Override
                    public void onFinish() {
                        DialogFactory.dimissDialog(dialog);
                    }
                });
            }else {
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
        busValues.setWhat(0x1007);
        EventBus.getDefault().post(busValues);
    }

    private void switchToEditDebtoFragment() {
        setToolbarTitle("跟进明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        FollowEditFragment followEditFragment=new FollowEditFragment();
        followEditFragment.setArguments(getIntent().getExtras());

        transaction.replace(R.id.Follow_Content_Layout, followEditFragment);
        transaction.commit();
    }

    private void switchToAddDebtoFragment() {
        setToolbarTitle("添加跟进明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Follow_Content_Layout, new FollowAddFragment());
        transaction.commit();
    }

    public static void startIntentToEdit(Activity activity, FollowEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        ISkipActivityUtil.startIntent(activity, FollowActivity.class, bundle);
    }

    public static void startIntentToQuery(Activity activity,String customerid){
        Bundle bundle = new Bundle();
        bundle.putString("customerid", customerid);
        ISkipActivityUtil.startIntent(activity, FollowActivity.class, bundle);
    }

    public static void startIntentToAdd(Activity activity) {
        ISkipActivityUtil.startIntent(activity, FollowActivity.class);
    }
}
