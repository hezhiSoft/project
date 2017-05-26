package com.xiaomai.telemarket.module.cstmr.fragment.follow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.data.FollowEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.DebtoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 22:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometFolloFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallback<List<FollowEntity>> {

    @BindView(R.id.Follow_recyclerView)
    PullToRefreshRecyclerView FollowRecyclerView;
    @BindView(R.id.Details_number_TextView)
    TextView DetailsNumberTextView;

    private CusrometFollowAdapter adapter;
    private CusrometRemoteRepo remoteRepo;

    private String cusrometId;

    private FollowEntity debtoEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cusrometId = getArguments().getString("id");
        remoteRepo = CusrometRemoteRepo.getInstance();
        adapter = new CusrometFollowAdapter(getContext());
        adapter.setListenter(new CusrometFollowAdapter.OnClickItemLisenter() {
            @Override
            public void onSeleceItemPosition(FollowEntity entity) {
                debtoEntity=entity;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_follow, null);
        ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        FollowRecyclerView.setRecyclerViewAdapter(adapter);
        FollowRecyclerView.setMode(PullToRefreshRecyclerView.Mode.DISABLED);
        FollowRecyclerView.setPullToRefreshListener(this);
        FollowRecyclerView.startUpRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        remoteRepo.cancelRequest();
    }

    @Override
    public void onDownRefresh() {
        remoteRepo.queryCusrometFollowLists(cusrometId, this);
    }

    @Override
    public void onPullRefresh() {

    }

    @OnClick(R.id.Details_add_Button)
    public void onClick() {
        FollowActivity.startIntentToAdd(getActivity());
    }

    @Override
    public void onSuccess(List<FollowEntity> data) {
        if (data != null && data.size() > 0) {
            DetailsNumberTextView.setText("共"+data.size()+"条跟进明细");
            adapter.clearList();
            adapter.addItems(data);
        } else {
            DetailsNumberTextView.setText("跟进明细");
            FollowRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        FollowRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onThrowable(Throwable t) {
        FollowRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onUnauthorized() {
        FollowRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        FollowRecyclerView.closeDownRefresh();
    }

    public FollowEntity getEntity() {
        return debtoEntity;
    }
}
