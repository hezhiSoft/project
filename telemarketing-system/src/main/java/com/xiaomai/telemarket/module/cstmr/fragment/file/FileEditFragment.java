package com.xiaomai.telemarket.module.cstmr.fragment.file;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.IStringUtils;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.data.FileEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 21:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FileEditFragment extends FileBaseFragment implements RemetoRepoCallback<FileEntity> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity = (FileEntity) getArguments().getSerializable("entity");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI(entity);
    }

    @Subscribe
    public void onCallStatus(EventBusValues values){
        if (values.getWhat()==0x10102){
            CusrometRemoteRepo.getInstance().editFile(FileFileName.getContentText(), FileAttachment.getImageLists(), this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CusrometRemoteRepo.getInstance().cancelRequest();

        /*判断当前是否处于群呼状态,通知群呼下一个号码*/
        boolean isCall = IStringUtils.toBool(ISharedPreferencesUtils.getValue(getActivity(), Constant.IS_DIALING_GROUP_FINISHED, "false").toString());
        if (isCall){
            EventBusValues busValues = new EventBusValues();
            busValues.setWhat(0x10101);
            busValues.setObject(true);
            EventBus.getDefault().post(busValues);
        }
    }


    @Override
    public void onSubmit() {
        super.onSubmit();
        dialog = DialogFactory.createLoadingDialog(getActivity(), "提交...");
        CusrometRemoteRepo.getInstance().editFile(FileFileName.getContentText(), FileAttachment.getImageLists(), this);
    }

    @Override
    public void onSuccess(FileEntity data) {
        EventBusValues values=new EventBusValues();
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
