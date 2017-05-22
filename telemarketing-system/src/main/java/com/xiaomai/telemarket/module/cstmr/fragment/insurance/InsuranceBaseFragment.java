package com.xiaomai.telemarket.module.cstmr.fragment.insurance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.FormSelectTopTitleView;
import com.jinggan.library.ui.widget.FormWriteTopTitleView;
import com.xiaomai.telemarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class InsuranceBaseFragment extends BaseFragment {

    @BindView(R.id.Insurance_InsuranceCompany)
    FormSelectTopTitleView InsuranceInsuranceCompany;
    @BindView(R.id.Insurance_InsuredAmount)
    FormWriteTopTitleView InsuranceInsuredAmount;
    @BindView(R.id.Insurance_PaymentMethods)
    FormSelectTopTitleView InsurancePaymentMethods;
    @BindView(R.id.Insurance_BuyDate)
    FormSelectTopTitleView InsuranceBuyDate;
    @BindView(R.id.Insurance_DelayDate)
    FormWriteTopTitleView InsuranceDelayDate;
    @BindView(R.id.Insurance_DelayDays)
    FormSelectTopTitleView InsuranceDelayDays;
    @BindView(R.id.Insurance_FuXiaoDate)
    FormSelectTopTitleView InsuranceFuXiaoDate;
    @BindView(R.id.Insurance_Remark)
    FormWriteTopTitleView InsuranceRemark;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_insurance, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
