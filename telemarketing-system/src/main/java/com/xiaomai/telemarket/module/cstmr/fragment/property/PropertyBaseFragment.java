package com.xiaomai.telemarket.module.cstmr.fragment.property;

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
 * created at 2017/5/20 22:10
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class PropertyBaseFragment extends BaseFragment {

    @BindView(R.id.Property_LandUse)
    FormSelectTopTitleView PropertyLandUse;
    @BindView(R.id.Property_AreaM)
    FormWriteTopTitleView PropertyAreaM;
    @BindView(R.id.Property_Area)
    FormSelectTopTitleView PropertyArea;
    @BindView(R.id.Property_CompletionDate)
    FormSelectTopTitleView PropertyCompletionDate;
    @BindView(R.id.Property_PropertyRightsYear)
    FormWriteTopTitleView PropertyPropertyRightsYear;
    @BindView(R.id.Property_RegistrationPrice)
    FormWriteTopTitleView PropertyRegistrationPrice;
    @BindView(R.id.Property_VillageName)
    FormWriteTopTitleView PropertyVillageName;
    @BindView(R.id.Property_DetailedAddress)
    FormWriteTopTitleView PropertyDetailedAddress;
    @BindView(R.id.Property_Remark)
    FormWriteTopTitleView PropertyRemark;
    @BindView(R.id.Property_IsMortgage)
    FormSelectTopTitleView PropertyIsMortgage;
    @BindView(R.id.Property_MortgageBank)
    FormSelectTopTitleView PropertyMortgageBank;
    @BindView(R.id.Property_MonthlyPaymentLoan)
    FormWriteTopTitleView PropertyMonthlyPaymentLoan;
    @BindView(R.id.Property_MortgageTimeLimit)
    FormWriteTopTitleView PropertyMortgageTimeLimit;
    @BindView(R.id.Property_RemainingMortgage)
    FormWriteTopTitleView PropertyRemainingMortgage;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_property, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
