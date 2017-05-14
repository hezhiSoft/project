package com.xiaomai.telemarket.module.cstmr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinggan.library.base.BaseFragment;
import com.xiaomai.telemarket.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 客户管理
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/14 11:37
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometManagementAllFragment extends BaseFragment {

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_all, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Customer_toolBar_add_ImageView, R.id.Customer_toolBar_screen_ImageView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Customer_toolBar_add_ImageView:
                break;
            case R.id.Customer_toolBar_screen_ImageView:
                break;
        }
    }
}
