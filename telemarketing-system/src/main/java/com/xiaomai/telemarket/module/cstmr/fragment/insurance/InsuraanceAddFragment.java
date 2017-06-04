package com.xiaomai.telemarket.module.cstmr.fragment.insurance;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.module.cstmr.CusrometDetailsActivity;
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.greenrobot.eventbus.EventBus;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 21:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class InsuraanceAddFragment extends InsuranceBaseFragment implements RemetoRepoCallback<Responese> {


    @Override
    public void onSubmit() {
        super.onSubmit();
        InsuranceEntity entity=getInsuranceEntity();
        entity.setCustomerID(CusrometDetailsActivity.entity.getID());
        CusrometRemoteRepo.getInstance().addInsurance(entity,this);
    }

    @Override
    public void onSuccess(Responese data) {
        EventBusValues values=new EventBusValues();
        values.setWhat(0x204);
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
        showToast("提交失败");
    }


    @Override
    public void onFinish() {
        DialogFactory.dimissDialog(dialog);
    }
}

