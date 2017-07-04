package com.easydear.user.module.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.easydear.user.R;
import com.easydear.user.module.business.BusinessListAdapter;
import com.easydear.user.module.business.data.BusinessEntity;
import com.easydear.user.module.mine.data.source.MineRepo;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/7/3 19:55
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class UserBusinessListActivity extends BaseActivity implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener,RemetoRepoCallbackV2<List<BusinessEntity>>{

    @BindView(R.id.UserBusiness_RecyclerView)
    PullToRefreshRecyclerView recyclerView;

    private BusinessListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_business);
        setToolbarTitle("我的会员权益");
        ButterKnife.bind(this);

        initRecyclerView();
    }

    private void initRecyclerView(){
        adapter=new BusinessListAdapter(this);
        recyclerView.setRecyclerViewAdapter(adapter);
        recyclerView.setPullToRefreshListener(this);

        recyclerView.startUpRefresh();
    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(List<BusinessEntity> data) {
        adapter.clearList();
        adapter.addItems(data);
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(msg);
        recyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        recyclerView.closeDownRefresh();
    }

    @Override
    public void onDownRefresh() {
        MineRepo.getInstance().queryUserBusiness(this);
    }

    @Override
    public void onPullRefresh() {

    }
}
