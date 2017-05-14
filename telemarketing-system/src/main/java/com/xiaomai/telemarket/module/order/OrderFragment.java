package com.xiaomai.telemarket.module.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.xiaomai.telemarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/14 11:37
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class OrderFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener {

    @BindView(R.id.Order_RecyclerView)
    PullToRefreshRecyclerView OrderRecyclerView;
    Unbinder unbinder;

    private OrderListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new OrderListAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order, null);
        unbinder = ButterKnife.bind(this, rootView);
        OrderRecyclerView.setRecyclerViewAdapter(adapter);
        OrderRecyclerView.setMode(PullToRefreshRecyclerView.Mode.BOTH);
//        OrderRecyclerView.startUpRefresh();
        OrderRecyclerView.setEmptyTextViewVisiblity(View.GONE);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDownRefresh() {
    }

    @Override
    public void onPullRefresh() {

    }
}
