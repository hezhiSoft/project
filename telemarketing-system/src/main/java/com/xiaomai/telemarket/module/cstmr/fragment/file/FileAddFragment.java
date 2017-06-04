package com.xiaomai.telemarket.module.cstmr.fragment.file;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.xiaomai.telemarket.module.cstmr.CusrometDetailsActivity;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.greenrobot.eventbus.EventBus;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 21:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FileAddFragment extends FileBaseFragment implements RemetoRepoCallback<FileEntity> {

    @Override
    public void onSubmit() {
        super.onSubmit();
        dialog = DialogFactory.createLoadingDialog(getActivity(), "提交...");
        CusrometRemoteRepo.getInstance().addFile(FileFileName.getContentText(), CusrometDetailsActivity.entity.getID(), FileAttachment.getImageLists(), this);
    }

    @Override
    public void onSuccess(FileEntity data) {
        EventBusValues values = new EventBusValues();
        values.setWhat(0x207);
        EventBus.getDefault().post(values);

        showToast("保存成功");
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

    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }
}
