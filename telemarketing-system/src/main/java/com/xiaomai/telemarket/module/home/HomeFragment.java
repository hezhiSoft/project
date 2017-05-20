package com.xiaomai.telemarket.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.home.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/14 11:36
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.Home_UserHead_iamgeView)
    ImageView HomeUserHeadIamgeView;
    @BindView(R.id.Home_UserName_TextView)
    TextView HomeUserNameTextView;
    @BindView(R.id.Home_Deparment_TextView)
    TextView HomeDeparmentTextView;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Home_seting_TextView, R.id.Home_groupCall_Layout, R.id.Home_singCall_Layout, R.id.Home_customer_TextView, R.id.Home_customerStay_TextView, R.id.Home_Search_TextView, R.id.Home_order_TextView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Home_seting_TextView:/*设置*/
                ISkipActivityUtil.startIntent(getActivity(), SettingActivity.class);
                break;
            case R.id.Home_groupCall_Layout:/*群呼*/
                break;
            case R.id.Home_singCall_Layout:/*点呼*/
                break;
            case R.id.Home_customer_TextView:/*客户管理*/
                break;
            case R.id.Home_customerStay_TextView:/*待跟进订单*/
                break;
            case R.id.Home_Search_TextView:/*产品查询*/
                break;
            case R.id.Home_order_TextView:/*订单管理*/
                break;
        }
    }
}
