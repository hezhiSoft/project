package com.xiaomai.telemarket.module.cstmr.fragment.debto;

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
public class DebtoBaseFragment extends BaseFragment {

    @BindView(R.id.Debto_TypeDept)
    FormSelectTopTitleView DebtoTypeDept;
    @BindView(R.id.Debto_LoanAmount)
    FormWriteTopTitleView DebtoLoanAmount;
    @BindView(R.id.Debto_LoanDate)
    FormSelectTopTitleView DebtoLoanDate;
    @BindView(R.id.Debto_LoanBank)
    FormSelectTopTitleView DebtoLoanBank;
    @BindView(R.id.Debto_MonthlyPayments)
    FormWriteTopTitleView DebtoMonthlyPayments;
    @BindView(R.id.Debto_RemainingLoanAmount)
    FormWriteTopTitleView DebtoRemainingLoanAmount;
    @BindView(R.id.Debto_LoanMonth)
    FormWriteTopTitleView DebtoLoanMonth;
    @BindView(R.id.Debto_RepaymentMode)
    FormSelectTopTitleView DebtoRepaymentMode;
    @BindView(R.id.Debto_DelayDays)
    FormWriteTopTitleView DebtoDelayDays;
    @BindView(R.id.Debto_DelayAccount)
    FormWriteTopTitleView DebtoDelayAccount;
    @BindView(R.id.Debto_DelayNum)
    FormWriteTopTitleView DebtoDelayNum;
    @BindView(R.id.Debto_loanProduct)
    FormSelectTopTitleView DebtoLoanProduct;
    @BindView(R.id.Debto_Remark)
    FormWriteTopTitleView DebtoRemark;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_debto, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
