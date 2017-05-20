package com.xiaomai.telemarket.module.cstmr.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.CusrometManagementAdapter;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import java.util.List;

import butterknife.BindView;
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
public class CusrometManagementAllFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener,RemetoRepoCallback<List<CusrometListEntity>>{

    Unbinder unbinder;
    @BindView(R.id.CustomerAll_recyclerView)
    PullToRefreshRecyclerView CustomerAllRecyclerView;

    private CusrometManagementAdapter adapter;

    private int pageIndex;
    private CusrometRemoteRepo remoteRepo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        remoteRepo=CusrometRemoteRepo.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_all, null);
        unbinder = ButterKnife.bind(this, rootView);
        adapter=new CusrometManagementAdapter(getContext());
        CustomerAllRecyclerView.setRecyclerViewAdapter(adapter);
        CustomerAllRecyclerView.setMode(PullToRefreshRecyclerView.Mode.BOTH);
        CustomerAllRecyclerView.setPullToRefreshListener(this);
        CustomerAllRecyclerView.startUpRefresh();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        remoteRepo.cancelRequest();
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

    @Override
    public void onDownRefresh() {
        pageIndex=1;
        remoteRepo.requestCusrometLists(pageIndex,null,this);
    }

    @Override
    public void onPullRefresh() {
        pageIndex++;
        remoteRepo.requestCusrometLists(pageIndex,null,this);
    }

    @Override
    public void onSuccess(List<CusrometListEntity> data) {
        if (pageIndex==1){
            adapter.clearList();
            adapter.addItems(data);
        }else {
            adapter.addItems(data,adapter.getItemCount()-1);
            if (data.size()<=0){
                CustomerAllRecyclerView.onLoadMoreFinish();
            }
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
        CustomerAllRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onThrowable(Throwable t) {
        showToast("数据异常");
        CustomerAllRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onUnauthorized() {
        showToast("数据获取失败");
        CustomerAllRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        CustomerAllRecyclerView.closeDownRefresh();
    }
}

