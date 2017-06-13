package com.easydear.user.module.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easydear.user.R;
import com.easydear.user.module.business.data.BusinessEntity;
import com.easydear.user.module.business.data.soruce.BussinessRepo;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 * author:hezhiWu <wuhezhi007@gmail.com>
 * version:V1.0
 * created at 2017/6/12 下午3:11
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved
 */
public class BusinessListFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener,RemetoRepoCallbackV2<List<BusinessEntity>>{

    @BindView(R.id.PageListFragment_RecyclerView)
    PullToRefreshRecyclerView PageListFragmentRecyclerView;
    Unbinder unbinder;

    private String key;
    private BusinessListAdapter adapter;

    private BussinessRepo bussinessRepo;
    private int pageSize=1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        key = getArguments().getString("key");
        adapter=new BusinessListAdapter(getContext());

        bussinessRepo=BussinessRepo.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_list, null);
        unbinder = ButterKnife.bind(this, rootView);
        initRecylerView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initRecylerView(){
        PageListFragmentRecyclerView.setRecyclerViewAdapter(adapter);
        PageListFragmentRecyclerView.setPullToRefreshListener(this);
        PageListFragmentRecyclerView.startUpRefresh();
    }

    @Override
    public void onDownRefresh() {
        pageSize=1;
        bussinessRepo.queryBusiness(pageSize,20,"",key,"","","",this);
    }

    @Override
    public void onPullRefresh() {
        pageSize++;
        bussinessRepo.queryBusiness(pageSize,20,"",key,"","","",this);
    }

    @Override
    public void onSuccess(List<BusinessEntity> data) {
        if (pageSize==1){
            adapter.clearList();
            adapter.addItems(data);
        }else {
            adapter.addItems(data,adapter.getItemCount()-1);
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (code==404){
            PageListFragmentRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        PageListFragmentRecyclerView.closeDownRefresh();
    }
}
