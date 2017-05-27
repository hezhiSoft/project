package com.xiaomai.telemarket.module.cstmr.fragment.company;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiaomai.telemarket.module.cstmr.data.CompanyEntity;
import com.xiaomai.telemarket.module.cstmr.fragment.car.CarBaseFragment;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 21:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CompanyEditFragment extends CompanyBaseFragment {

    private CompanyEntity entity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity=(CompanyEntity) getArguments().getSerializable("entity");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI(entity);
    }
}
