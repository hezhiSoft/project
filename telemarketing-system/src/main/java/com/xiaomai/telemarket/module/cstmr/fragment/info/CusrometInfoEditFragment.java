package com.xiaomai.telemarket.module.cstmr.fragment.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.greenrobot.eventbus.EventBus;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 下午2:12$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CusrometInfoEditFragment extends ShowInfoBaseFragment implements RemetoRepoCallback<CusrometListEntity> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity = (CusrometListEntity) getArguments().getSerializable("entity");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InfoTelStatus.setVisibility(View.VISIBLE);
        InfoIntentionStatus.setVisibility(View.VISIBLE);
        initUI(entity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CusrometRemoteRepo.getInstance().cancelRequest();
    }

    @Override
    public void onSubmit() {
        super.onSubmit();
        dialog = DialogFactory.createLoadingDialog(getActivity(), "提交...");
        CusrometRemoteRepo.getInstance().editCusromet(getCusrometEntity(), this);
    }

    @Override
    public void onSuccess(CusrometListEntity data) {
        EventBusValues values = new EventBusValues();
        values.setWhat(0x201);
        values.setObject(data);
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
        showToast("数据异常");
    }

    @Override
    public void onUnauthorized() {

    }

    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }
}
