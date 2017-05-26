package com.xiaomai.telemarket.module.cstmr.fragment.debto;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:59
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DebtoEditFragment extends DebtoBaseFragment {

    private DebtoEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity = (DebtoEntity) getArguments().getSerializable("entity");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI(entity);
    }

    @Override
    public void onSubmit() {
        super.onSubmit();
        DebtoEntity debtoEntity=getDebtoEntity();
        debtoEntity.setID(entity.getID());
        debtoEntity.setCustomerID(entity.getCustomerID());
    }
}
