package com.xiaomai.telemarket.module.cstmr.fragment.company;

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
public class CompanyBaseFragment extends BaseFragment {
    @BindView(R.id.Company_CompanyName)
    FormWriteTopTitleView CompanyCompanyName;
    @BindView(R.id.Company_Industry)
    FormWriteTopTitleView CompanyIndustry;
    @BindView(R.id.Company_RegisterDate)
    FormSelectTopTitleView CompanyRegisterDate;
    @BindView(R.id.Company_BusinessScope)
    FormWriteTopTitleView CompanyBusinessScope;
    @BindView(R.id.Company_SharesProportion)
    FormWriteTopTitleView CompanySharesProportion;
    @BindView(R.id.Company_AccountWater)
    FormWriteTopTitleView CompanyAccountWater;
    @BindView(R.id.Company_IsRentTransfer)
    FormWriteTopTitleView CompanyIsRentTransfer;
    @BindView(R.id.Company_AmountDebt)
    FormWriteTopTitleView CompanyAmountDebt;
    @BindView(R.id.Company_Remark)
    FormWriteTopTitleView CompanyRemark;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_company, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
