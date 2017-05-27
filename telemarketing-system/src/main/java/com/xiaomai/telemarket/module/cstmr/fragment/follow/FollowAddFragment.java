package com.xiaomai.telemarket.module.cstmr.fragment.follow;

import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.module.cstmr.CusrometDetailsActivity;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 21:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FollowAddFragment extends FollowBaseFragment implements RemetoRepoCallback<Responese> {

    @Override
    public void onSubmit() {
        super.onSubmit();
        FollowEntity entity=getFollowEntity();
        entity.setCustomerID(CusrometDetailsActivity.entity.getID());

        dialog=DialogFactory.createLoadingDialog(getActivity(),"提交...");
        CusrometRemoteRepo.getInstance().addFollow(entity,this);
    }

    @Override
    public void onSuccess(Responese data) {
        showToast("新增成功");
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
