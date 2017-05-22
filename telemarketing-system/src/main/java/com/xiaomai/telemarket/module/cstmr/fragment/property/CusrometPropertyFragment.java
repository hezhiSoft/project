package com.xiaomai.telemarket.module.cstmr.fragment.property;

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
import com.xiaomai.telemarket.module.cstmr.data.PropertyEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;
import com.xiaomai.telemarket.module.cstmr.fragment.debto.CusrometDebtoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午10:38$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CusrometPropertyFragment extends BaseFragment implements CusrometPropertyAdapter.OnClickItemLisenter, PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallback<List<PropertyEntity>> {

    @BindView(R.id.Details_number_TextView)
    TextView DetailsNumberTextView;
    @BindView(R.id.Property_recyclerView)
    PullToRefreshRecyclerView PropertyRecyclerView;
    Unbinder unbinder;

    private CusrometPropertyAdapter adapter;
    private CusrometRemoteRepo remoteRepo;

    private String cusrometId;

    private PropertyEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new CusrometPropertyAdapter(getContext());
        cusrometId = getArguments().getString("id");
        remoteRepo = CusrometRemoteRepo.getInstance();
        adapter.setListenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_property, null);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        PropertyRecyclerView.setRecyclerViewAdapter(adapter);
        PropertyRecyclerView.setMode(PullToRefreshRecyclerView.Mode.DISABLED);
        PropertyRecyclerView.setPullToRefreshListener(this);
        PropertyRecyclerView.startUpRefresh();
    }

    @Override
    public void onDownRefresh() {
        remoteRepo.queryCusrometHouseLists(cusrometId,this);
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onSuccess(List<PropertyEntity> data) {
        if (data != null && data.size() > 0) {
            adapter.clearList();
            DetailsNumberTextView.setText("共" + data.size() + "条房产信息");
            adapter.addItems(data);
        } else {
            DetailsNumberTextView.setText("房产信息");
            PropertyRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        PropertyRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onThrowable(Throwable t) {
        PropertyRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onUnauthorized() {
        PropertyRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        PropertyRecyclerView.closeDownRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        remoteRepo.cancelRequest();
    }

    @OnClick(R.id.Details_add_Button)
    public void onClick() {
        PropertyActivity.startIntentToAdd(getActivity());
    }

    @Override
    public void onSeleceItemPosition(PropertyEntity entity) {
        this.entity=entity;
    }

    public PropertyEntity getEntity(){
        return entity;
    }
}
