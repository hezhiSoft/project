package com.xiaomai.telemarket.module.order;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.xiaomai.telemarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/28$ 下午12:15$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class OrderActivity extends BaseActivity {


    @BindView(R.id.Order_RecyclerView)
    PullToRefreshRecyclerView OrderRecyclerView;

    private OrderListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setToolbarTitle("订单管理");
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView(){
        adapter = new OrderListAdapter(this);
        OrderRecyclerView.setRecyclerViewAdapter(adapter);
    }
}
