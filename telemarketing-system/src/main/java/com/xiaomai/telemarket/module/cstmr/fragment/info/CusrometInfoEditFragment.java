package com.xiaomai.telemarket.module.cstmr.fragment.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.IStringUtils;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    @Subscribe
    public void onCallStatus(EventBusValues values) {
        if (values.getWhat() == 0x10102) {
            CusrometRemoteRepo.getInstance().editCusromet(getCusrometEntity(), this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CusrometRemoteRepo.getInstance().cancelRequest();

        /*判断当前是否处于群呼状态,通知群呼下一个号码*/
        boolean isCall = IStringUtils.toBool(ISharedPreferencesUtils.getValue(getActivity(), Constant.IS_DIALING_GROUP_FINISHED, false).toString());
        if (isCall) {
            EventBusValues busValues = new EventBusValues();
            busValues.setWhat(0x10101);
            busValues.setObject(true);
            EventBus.getDefault().post(busValues);
        }
    }

    @Override
    public void onSubmit() {
        super.onSubmit();
        if (isShowDialog)
            dialog = DialogFactory.createLoadingDialog(getActivity(), "提交...");
        CusrometRemoteRepo.getInstance().editCusromet(getCusrometEntity(), this);
    }

    @Override
    public void onSuccess(CusrometListEntity data) {
        EventBusValues values = new EventBusValues();
        values.setWhat(0x201);
        values.setObject(data);
        EventBus.getDefault().post(values);

        if (isShowDialog)
            showToast("保存成功");
        getActivity().finish();
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
    }

    @Override
    public void onThrowable(Throwable t) {
        if (isShowDialog)
            showToast("数据异常");
    }

    @Override
    public void onUnauthorized() {

    }

    @Override
    public void onFinish() {
        if (isShowDialog)
            DialogFactory.dimissDialog(dialog);
    }
}
