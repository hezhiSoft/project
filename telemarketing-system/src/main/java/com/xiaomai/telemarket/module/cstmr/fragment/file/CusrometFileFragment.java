package com.xiaomai.telemarket.module.cstmr.fragment.file;

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
import com.xiaomai.telemarket.module.cstmr.data.DebtoEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.CusrometDebtoAdapter;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.DebtoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/22 22:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CusrometFileFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallback<List<DebtoEntity>>, CusrometDebtoAdapter.OnClickItemLisenter {

    @BindView(R.id.Details_number_TextView)
    TextView DetailsNumberTextView;
    @BindView(R.id.File_recyclerView)
    PullToRefreshRecyclerView EdbtoRecyclerView;
    Unbinder unbinder;

    private CusrometDebtoAdapter adapter;
    private CusrometRemoteRepo remoteRepo;

    private String cusrometId;

    private DebtoEntity debtoEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cusrometId = getArguments().getString("id");
        remoteRepo = CusrometRemoteRepo.getInstance();
        adapter = new CusrometDebtoAdapter(getContext());
        adapter.setListenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_file, null);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        EdbtoRecyclerView.setRecyclerViewAdapter(adapter);
        EdbtoRecyclerView.setMode(PullToRefreshRecyclerView.Mode.DISABLED);
        EdbtoRecyclerView.setPullToRefreshListener(this);
        EdbtoRecyclerView.startUpRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        remoteRepo.cancelRequest();
    }

    @Override
    public void onDownRefresh() {
        remoteRepo.queryCusrometDebtoLists(cusrometId, this);
    }

    @Override
    public void onPullRefresh() {

    }

    @OnClick(R.id.Details_add_Button)
    public void onClick() {
        DebtoActivity.startIntentToAdd(getActivity());
    }

    @Override
    public void onSuccess(List<DebtoEntity> data) {
        if (data != null && data.size() > 0) {
            adapter.clearList();
            DetailsNumberTextView.setText("共" + data.size() + "条文件资料");
            adapter.addItems(data);
        } else {
            DetailsNumberTextView.setText("文件资料");
            EdbtoRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        EdbtoRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onThrowable(Throwable t) {
        EdbtoRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onUnauthorized() {
        EdbtoRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        EdbtoRecyclerView.closeDownRefresh();
    }

    @Override
    public void onSeleceItemPosition(DebtoEntity entity) {
        this.debtoEntity = entity;
    }

    public DebtoEntity getEntity() {
        return debtoEntity;
    }
}
