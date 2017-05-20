package com.xiaomai.telemarket.module.cstmr.fragment.car;

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
public class CarBaseFragment extends BaseFragment {

    @BindView(R.id.Car_NakedCarPrice)
    FormWriteTopTitleView CarNakedCarPrice;
    @BindView(R.id.Car_BuyDate)
    FormSelectTopTitleView CarBuyDate;
    @BindView(R.id.Car_Mileage)
    FormWriteTopTitleView CarMileage;
    @BindView(R.id.Car_Barnd)
    FormWriteTopTitleView CarBarnd;
    @BindView(R.id.Car_CarModel)
    FormWriteTopTitleView CarCarModel;
    @BindView(R.id.Car_IsMortgage)
    FormWriteTopTitleView CarIsMortgage;
    @BindView(R.id.Car_IsRegistrationCertificate)
    FormWriteTopTitleView CarIsRegistrationCertificate;
    @BindView(R.id.Car_Remark)
    FormWriteTopTitleView CarRemark;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
