package com.xiaomai.telemarket.module.cstmr.fragment.follow;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 21:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FollowEditFragment extends FollowBaseFragment implements RemetoRepoCallback<Responese> {

    private FollowEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity = (FollowEntity) getArguments().getSerializable("entity");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI(entity);
    }

    @Override
    public void onSubmit() {
        super.onSubmit();
        FollowEntity followEntity = getFollowEntity();
        followEntity.setID(entity.getID());
        followEntity.setCustomerID(entity.getCustomerID());
        followEntity.setCreatedBy(entity.getCreatedBy());
        followEntity.setRowIndex(entity.getRowIndex());
        followEntity.setWhetherProcessing(entity.getWhetherProcessing());

        dialog = DialogFactory.createLoadingDialog(getActivity(), "提交...");
        CusrometRemoteRepo.getInstance().editFollow(followEntity, this);
    }

    @Override
    public void onSuccess(Responese data) {
        showToast("提交成功");
        getActivity().finish();
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
    }

    @Override
    public void onThrowable(Throwable t) {
        showToast("提交失败");
    }

    @Override
    public void onUnauthorized() {
        showToast("提交失败");
    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }
}
