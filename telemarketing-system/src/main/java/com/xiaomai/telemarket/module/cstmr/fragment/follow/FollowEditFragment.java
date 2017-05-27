package com.xiaomai.telemarket.module.cstmr.fragment.follow;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 21:58
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class FollowEditFragment extends FollowBaseFragment {

    private FollowEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entity=(FollowEntity) getArguments().getSerializable("entity");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI(entity);
    }
}
