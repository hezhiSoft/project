package com.easydear.user.module.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easydear.user.R;
import com.easydear.user.module.cards.data.CardEntity;
import com.easydear.user.module.cards.data.source.CardRepo;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:55
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CardsFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallbackV2<List<CardEntity>> {

    @BindView(R.id.CardsFragment_RecyclerView)
    PullToRefreshRecyclerView CardsFragmentRecyclerView;
    Unbinder unbinder;

    private CardListAdapter adapter;

    private int pageSize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cards, null);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        adapter = new CardListAdapter(getContext());
        CardsFragmentRecyclerView.setRecyclerViewAdapter(adapter);
        CardsFragmentRecyclerView.setPullToRefreshListener(this);
        CardsFragmentRecyclerView.startUpRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDownRefresh() {
        pageSize = 1;
        CardRepo.getInstance().queryCards(pageSize, "", this);
    }

    @Override
    public void onPullRefresh() {
        pageSize++;
        CardRepo.getInstance().queryCards(pageSize, "", this);
    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(List<CardEntity> data) {
        if (pageSize == 1) {
            adapter.clearList();
            ;
            adapter.addItems(data);
            if (data == null || data.size() == 0) {
                CardsFragmentRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
            }
        } else {
            adapter.addItems(data, adapter.getItemCount() - 1);
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (pageSize == 1) {
            CardsFragmentRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        CardsFragmentRecyclerView.closeDownRefresh();
    }
}
