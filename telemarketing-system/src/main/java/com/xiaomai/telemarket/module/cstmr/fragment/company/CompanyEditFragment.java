package com.xiaomai.telemarket.module.cstmr.fragment.company;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.dialog.DialogFactory;
import com.jinggan.library.utils.ISharedPreferencesUtils;
import com.jinggan.library.utils.IStringUtils;
import com.xiaomai.telemarket.api.Responese;
import com.xiaomai.telemarket.common.Constant;
import com.xiaomai.telemarket.module.cstmr.data.CompanyEntity;
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.data.InsuranceEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;
import com.xiaomai.telemarket.module.cstmr.fragment.car.CarBaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 21:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CompanyEditFragment extends CompanyBaseFragment implements RemetoRepoCallback<Responese> {

    private CompanyEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity = (CompanyEntity) getArguments().getSerializable("entity");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI(entity);
    }

    @Subscribe
    public void onCallStatus(EventBusValues values){
        if (values.getWhat()==0x10102){
            CompanyEntity companyEntity = getCompanyEntity();
            companyEntity.setID(entity.getID());
            companyEntity.setCustomerID(entity.getCustomerID());
            companyEntity.setRowVersion(entity.getRowVersion());
            companyEntity.setRowIndex(entity.getRowIndex());
            companyEntity.setCreatedBy(entity.getCreatedBy());
            companyEntity.setSharesProportion(entity.getSharesProportion());
            companyEntity.setDeleteFlag(entity.getDeleteFlag());
            companyEntity.setCreatedDate(entity.getCreatedDate());
            companyEntity.setLocationRental(entity.getLocationRental());
            companyEntity.setModifiedBy(entity.getModifiedBy());
            companyEntity.setModifiedDate(entity.getModifiedDate());
            companyEntity.setRegisterDate(entity.getRegisterDate());

            CusrometRemoteRepo.getInstance().editCompany(companyEntity, this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CusrometRemoteRepo.getInstance().cancelRequest();

        /*判断当前是否处于群呼状态,通知群呼下一个号码*/
        boolean isCall = IStringUtils.toBool(ISharedPreferencesUtils.getValue(getActivity(), Constant.IS_DIALING_GROUP_FINISHED, false).toString());
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
        CompanyEntity companyEntity = getCompanyEntity();
        companyEntity.setID(entity.getID());
        companyEntity.setCustomerID(entity.getCustomerID());
        companyEntity.setRowVersion(entity.getRowVersion());
        companyEntity.setRowIndex(entity.getRowIndex());
        companyEntity.setCreatedBy(entity.getCreatedBy());
        companyEntity.setSharesProportion(entity.getSharesProportion());
        companyEntity.setDeleteFlag(entity.getDeleteFlag());
        companyEntity.setCreatedDate(entity.getCreatedDate());
        companyEntity.setLocationRental(entity.getLocationRental());
        companyEntity.setModifiedBy(entity.getModifiedBy());
        companyEntity.setModifiedDate(entity.getModifiedDate());
        companyEntity.setRegisterDate(entity.getRegisterDate());

        dialog = DialogFactory.createLoadingDialog(getActivity(), "提交...");
        CusrometRemoteRepo.getInstance().editCompany(companyEntity, this);
    }

    @Override
    public void onSuccess(Responese data) {
        EventBusValues values=new EventBusValues();
        values.setWhat(0x206);
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
